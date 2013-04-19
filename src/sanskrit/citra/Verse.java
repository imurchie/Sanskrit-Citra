package sanskrit.citra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import sanskrit.citra.figure.CitraFigure;


/*
 * NEED TO MOVE THE SYLLABLE TOOLS FROM PADA TO VERSE
 */




public class Verse {
	private String id;
	private Pada[] padas;
	
	public Verse(String id, Pada[] padas) {
		super();
		this.id = id;
		this.padas = padas;
	}

	
	
	public Verse(String id, String[] padas) {
		super();
		this.id = id;
		
		System.out.println(padas.length);
		
		ArrayList<String> padas_list = new ArrayList<String>(padas.length);
		for(int i=0; i<padas.length; i++) {
			padas_list.set(i, padas[i]);
		}
		
		this.padas = parsePadas(padas_list);
	}
	
	/**
	 * Constructor when we don't know the identifier for the verse
	 * Parses the identifier, if there is one, from the verse itself
	 * 
	 * - 
	 * 
	 * @param padas
	 */
	public Verse(String[] padas) {
		this(parseId(padas), padas);
	}
	
	
	
	public Verse(ArrayList<String> padas) {
		String[] padas_string = new String[padas.size()];
		for(int i=0; i<padas.size(); i++) {
			padas_string[i] = padas.get(i);
		}
		
		this.id = parseId(padas_string);
		
		this.padas = parsePadas(padas);
	}
	
	private Pada[] parsePadas(ArrayList<String> padas) {
		Pada[] padas_ret = null;
		
		// AT THIS POINT: if the verse is two lines long,
		// treat as having four padas
		if (padas.size() == 2) {
			padas_ret = new Pada[4];
			
			ArrayList<String> line = parseSyllables(padas.get(0));
			
			// first half
			ArrayList<String> pada = new ArrayList<String>(padas.size()/2);
			for(int i=0; i<line.size()/2; i++) {
				pada.add(line.get(i));
			}
			padas_ret[0] = new Pada(id, pada);
			
			pada = new ArrayList<String>(padas.size()/2);
			for(int i=line.size()/2; i<line.size(); i++) {
				pada.add(line.get(i));
			}
			padas_ret[1] = new Pada(id, pada);
			
			// second half
			line = parseSyllables(padas.get(1));
			pada = new ArrayList<String>(padas.size()/2);
			for(int i=0; i<line.size()/2; i++) {
				pada.add(line.get(i));
			}
			padas_ret[2] = new Pada(id, pada);
			
			pada = new ArrayList<String>(padas.size()/2);
			for(int i=line.size()/2; i<line.size(); i++) {
				pada.add(line.get(i));
			}
			padas_ret[3] = new Pada(id, pada);
		} else {
			padas_ret = new Pada[padas.size()];
			for(int i=0; i<padas.size(); i++) {
				padas_ret[i] = new Pada(id, parseSyllables(padas.get(i)));
			}
		}
		
		return padas_ret;
		
	}
	
	
	
	public boolean isFigure(CitraFigure figure) {
		return figure.testVerse(this);
	}
	
	
	private static String parseId(String[] padas) {
		String id = "";
		
		// the id is any numbers in the verse
		Pattern p = Pattern.compile("[\\d]+\\.[\\d]+");
	    
		for(int i=0; i<padas.length; i++) {
			Matcher m = p.matcher(padas[i]);
			if(m.find()) {
				id = padas[i].substring(m.start(), m.end());
			}
		}
		
		return id;
	}
	
	/**
	 * parseSyllables()
	 * from a String that is a fragment of Sanskrit text,
	 * the syllables are separated, returning an array of 
	 * syllables.
	 * 
	 * @param pada
	 * @return ArrayList<String>
	 */
	public static ArrayList<String> parseSyllables(String pada) {
		// initialized to '8', as default syllable count
		ArrayList<String> syllables = new ArrayList<String>(8);
		
		// remove the final '/', and all whitespace
		if(pada.indexOf("/") != -1) {
			pada = pada.substring(0, pada.indexOf("/"));
		}
		
		pada.replaceAll("[\\s-]","");
		/**
		 * 
		 */
		char[] pada_array = pada.toCharArray();
		int i = 0, j = 0;
		while(i < pada_array.length) {
			//System.out.print(pada_array[i]);
			boolean vowel = false;
			
			// the line might end with a '|' or a '/'
			if(pada_array[i] == '|' || pada_array[i] == '/') {
				break;
			}
			
			
			String syllable = "";
			
			while(i < pada_array.length) {
				//System.out.println("<<" + pada_array[i] + ">>");
				
				if(pada_array[i] == ' ') {
					i++;
					continue;
				}
				
				if(isVowel(pada_array[i])) {
					vowel = true;
					
					syllable += pada_array[i];
					i++;
				} else if(pada_array[i] == '/') {
					break;
				} else if(vowel == false) {
					// we have not found a vowel yet
					syllable += pada_array[i];
					i++;
				} else {
					// we have already found a vowel
					// so we only want to add this if it is
					// a visarga or an anusvāra
					if(pada_array[i] == 'ḥ' || pada_array[i] == 'ṃ' || pada_array[i] == 'ṁ') {
						syllable += pada_array[i];
						i++;
					// we might have a final consonant
					} else if(i+1 == pada_array.length-1 && !isVowel(pada_array[i])) {
						syllable += pada_array[i];
						i++;
					}
					// now we want to get out of this internal loop
					// because we have a syllable
					break;
				}
			}
			
			// add the syllable to the list
			if(syllable != null && syllable.length() != 0) {
				syllables.add(j, syllable);
			}
			j++;
		}
		
		return syllables;
	}
	
	
	private static boolean isVowel(char letter) {
		return letter == 'a'
				|| letter == 'ā'
				|| letter == 'i'
				|| letter == 'ī'
				|| letter == 'u'
				|| letter == 'ū'
				|| letter == 'ṛ'
				|| letter == 'ṝ'
				|| letter == 'ḷ'
				|| letter == 'ḹ'
				|| letter == 'o'
				|| letter == 'e';
	}
	
	
	
	public static boolean isSanskritLine(String line) {
		if(line.indexOf("www") != -1) {
			// the line has a url in it.
			return false;
		}
		
		if(line.length() == 0) {
			// leave in any blank lines, to deal with later
			return true;
		}
		
		// otherwise at the moment, use the presence of '|' or '/' as markers
		if(line.indexOf("/") != -1 || line.indexOf("|") != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Need to do error checking
	 * @param index
	 * @return
	 */
	public Pada getPada(int index) {
		return this.padas[index];
	}
	
	public int length() {
		return padas.length;
	}
	
	
	
	
	

	@Override
	public String toString() {
		return "Verse [id=" + id + ", padas=" + Arrays.toString(padas) + "]";
	}
}
