package sanskrit.citra;

import java.io.*;
import java.util.ArrayList;

import sanskrit.citra.figure.Niyama;



public class Citra {
	private String figure;
	private String filename;
	private BufferedReader reader;
	
	/**
	 * 
	 */
	public Citra(String figure, String filename) throws FileNotFoundException {
		this.figure = figure;
		this.filename = filename;
		
		reader = new BufferedReader(new FileReader(filename));
	}
	
	
	/**
	 * 
	 * @return Verse if it is this figure, otherwise null
	 */
	public Verse testNextVerse() {
		return null;
	}
	
	private Verse parseVerse() throws IOException {
		
        
        return null;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String id = "15.7";
		//String[] padas = {"athāgre hasatā sāci", 
		//                  "sthitena sthirakīrtinā /",
		//                  "senānyā te jagadire",
		//                  "kiṃcidāyastacetasā // BhKir_15.7 //"};
		//Verse verse = new Verse(id, padas);
		//System.out.println(verse);
		//
		//Verse verse2 = new Verse(padas);
		//System.out.println(verse2);
		//
		//System.out.println("\n\n\nUsing File object...");
		
		// with a file
		///*
		String filename = "/Users/imurchie/Documents - Scholarly/GitHub/Eclipse/Sanskrit/src/sanskrit/citra/data/niyama_test.txt";
		try {
			File file = new File(filename);
			Verse v = null;
			while((v = file.getNextVerse()) != null) {
				//System.out.println(v);
				Niyama n = new Niyama(Niyama.PADA, 1);
				if(v.isFigure(n)) {
					System.out.println("Niyama!");
				} else {
					System.out.println("Not a niyama");
				}
			}
		} catch(FileNotFoundException ex) {
			System.out.println(ex);
		} catch(IOException ex) {
			System.out.println(ex);
		}
		//*/
		
		//String line = "lalau līlāṃ lalo 'lolaḥ śaśīśaśiśuśīḥ śaśan // BhKir_15.5 //";
		//ArrayList<String> v = Verse.parseSyllables(line);
		//System.out.println(v);
	}

}
