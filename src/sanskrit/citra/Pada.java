package sanskrit.citra;

import java.util.ArrayList;

public class Pada {
	private ArrayList<String> syllables;
	private String id;

	public Pada(String id, ArrayList<String> syllables) {
		super();
		this.id = id;
		this.syllables = syllables;
	}
	
	
	public int length() {
		return syllables.size();
	}
	
	public String getSyllable(int index) {
		return syllables.get(index);
	}
	
	/**
	 * Returns a list of the unique consonants in a particular Pada.
	 * @param pada
	 * @return
	 */
	public ArrayList<String> getConsonants() {
		// need a flexible array, since we do not know the number of consonants a priori
		ArrayList<String> consonants = new ArrayList<String>(syllables.size());
		
		for(int i=0; i<syllables.size(); i++) {
			char[] syllable = syllables.get(i).toCharArray();
			
			String consonant = null; // buffer
			for(int j=0; j<syllable.length; j++) {
				char character = syllable[j];
				if(!isVowel(character)) {
					if(consonant != null && consonant.length() != 0) {
						if(character == 'h') {
							// this is a character with two roman letters
							consonant += character;
							if(consonants.indexOf(consonant) == -1) {
								consonants.add(consonant);
							}
							consonant = null;
						} else {
							// add what was in the buffer, then repopulate
							if(consonants.indexOf(consonant) == -1) {
								consonants.add(consonant);
							}
							consonant = String.valueOf(character);
						}
					} else {
						consonant = String.valueOf(character);
					}
					
				}
			}
			// if there is a final consonant, it will still be in the buffer
			if(consonant != null && consonants.indexOf(consonant) == -1) {
				consonants.add(consonant);
			}
		}
		
		return consonants;
	}
	
	private boolean isVowel(char letter) {
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


	
	

	@Override
	public String toString() {
		return "Pada [id=" + id + ", syllables=" + syllables + "]";
	}

}
