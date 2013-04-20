package sanskrit.citra.figure;

import java.util.ArrayList;

import sanskrit.citra.Pada;
import sanskrit.citra.Verse;


/**
 * Niyama: gives tests for lipogrammatic verses
 * 
 * Can be:
 * 	PADA: individual restrictions in each verse quarter
 * 	HALF: individual restrictions in each half-verse
 * 	VERSE: the restriction applies to the entire verse
 * 
 * 
 * @author imurchie
 *
 */
public class Niyama implements CitraFigure {
	private int type;
	private int number;
	
	public static final int PADA = 0;
	public static final int HALF = 1;
	public static final int VERSE = 2;

	/**
	 * Creates the default situation, in which the entire verse is
	 * considered, and only a single consonant
	 */
	public Niyama() {
		this.type = Niyama.VERSE;
		this.number = 1;
	}
	
	public Niyama(int type, int number) {
		this.type = type;
		this.number = number;
	}

	/**
	 * Implements the CitraFigure method
	 */
	public boolean testVerse(Verse verse) {
		// just for debugging
		//Pada p = verse.getPada(0);
		//ArrayList<String> c = p.getConsonants();
		//System.out.println(p);
		//System.out.println(c);
		
		if(this.type == Niyama.PADA) {
			for(int i=0; i<verse.length(); i++) {
				Pada p = verse.getPada(i);
				
				ArrayList<String> c = p.getConsonants();
				//System.out.println(c.size() + ", " + getCount(c) + ": " + c);
				if(getCount(c) > this.number) {
					// we do not have a good enough restriction
					return false;
				}
			}
		} else if(this.type == Niyama.HALF) {
			// loop through all the padas
			ArrayList<String> consonants = new ArrayList<String>(8);
			for(int i=0; i<verse.length(); i++) {
				Pada p = verse.getPada(i);
				mergeUniques(consonants, p.getConsonants());
				System.out.println(p);
				System.out.println(consonants);
				if(i==verse.length()/2-1) {
					// first half done
					if(getCount(consonants) > this.number) {
						return false;
					}
					consonants = new ArrayList<String>(8);
				} else if(i==verse.length()-1) {
					// second half
					if(getCount(consonants) > this.number) {
						return false;
					}
				}
			}
		} else if(this.type == Niyama.VERSE) {
			// loop through all the padas
			ArrayList<String> consonants = new ArrayList<String>(8);
			for(int i=0; i<verse.length(); i++) {
				Pada p = verse.getPada(i);
				mergeUniques(consonants, p.getConsonants());
			}
			if(getCount(consonants) > this.number) {
				return false;
			}
		}
		
		
		return true;
	}
	
	
	/**
	 * Takes two arrays and adds the elements from the second to the first, so that 
	 * the elements are unique.
	 * 
	 * @param first
	 * @param second
	 */
	private void mergeUniques(ArrayList<String> first, ArrayList<String> second) {
		for(int i=0; i<second.size(); i++) {
			if(first.indexOf(second.get(i)) == -1) {
				first.add(second.get(i));
			}
		}
	}
	
	
	/**
	 * This is necessary because there are exceptions to the restriction
	 * 
	 * - anusvāra and visarga are not counted, nor are avagraha
	 * - final consonants are also exempt (because of sandhi, mostly)
	 * 
	 * @param c
	 * @return
	 */
	private int getCount(ArrayList<String> c) {
		int count = 0;
		for(int i=0; i<c.size()-1; i++) {
			char first = c.get(i).charAt(0);
			if(first != 'ṃ' && first != 'ṁ' && first != 'ḥ' && first != '\'') {
				count++;
			}
		}
		
		return count;
	}
}
