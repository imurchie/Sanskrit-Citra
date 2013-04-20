package sanskrit.citra;

import java.io.*;

import sanskrit.citra.figure.CitraFigure;
import sanskrit.citra.figure.Niyama;



public class Citra {

	
	public static java.io.File findFile(String filename, java.io.File[] files) {
		// first try the name as is
	    for(java.io.File file : files) {
	    	if(file.getName().equals(filename)) {
	    		return file;
	    	} else if(file.isDirectory()) {
	    		// recurse if this is a directory
	            java.io.File f = findFile(filename, file.listFiles());
	            if(f != null) {
	            	return f;
	            }
	        }
	    }
	    
	    return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
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

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		// query for file
		String filename = null;
		while(true) {
			System.out.print("Enter a filename: ");
			
			String fname = in.readLine();
			if(fname.isEmpty()) {
				System.out.println("No file entered. Exiting...");
				return;
			}
			
			// we have a filename, which we need to try to find
			// if not, loop through the directory tree
			java.io.File file = findFile(fname, new java.io.File("./").listFiles());
			if(file != null) {
				// we found the file
				filename = file.getAbsolutePath();
				break;
			} else {
				System.out.println("No file \'" + fname + "\' found.");
			}
		}
		
		// query for figure
		CitraFigure figure = null;
		while(true) {
			System.out.println("Choose a figure:");
			System.out.println("    (a) niyama");
			
			String fname = in.readLine();
			if(fname.equals("a")) {
				// this is a NIYAMA
				// we also need more data
				System.out.println("Choose the extent of restriction:");
				System.out.println("    (a) quarter");
				System.out.println("    (b) half");
				System.out.println("    (c) verse");
				
				String ename = in.readLine();
				int extent = Niyama.HALF;
				if(ename.equals("a")) {
					extent = Niyama.PADA;
				} else if(ename.equals("b")) {
					extent = Niyama.HALF;
				} else if(ename.equals("c")) {
					extent = Niyama.VERSE;
				} else {
					System.out.println("Invalid input \'" + ename + "\'");
					continue;
				}
			
				System.out.print("Choose the number of syllables allowed: ");
				int num = 0;
				try {
					num = Integer.parseInt(in.readLine());
				} catch(NumberFormatException ex) {
					System.out.println("Invalid input: " + ex);
				}
				
				// we now have all the info we need
				figure = new Niyama(extent, num);
			}
			
			break;
		}
	    
		
		//String filename = "/Users/imurchie/Documents - Scholarly/GitHub/Eclipse/Sanskrit/src/sanskrit/citra/data/niyama_test.txt";
		try {
			File file = new File(filename);
			Verse v = null;
			while((v = file.getNextVerse()) != null) {
				//System.out.println(v);
				//Niyama n = null;//new Niyama(Niyama.PADA, 1);
				//n = new Niyama(Niyama.HALF, 5);
				//n = new Niyama(Niyama.VERSE, 5);
				if(v.isFigure(figure)) {
					System.out.println("Niyama! " + v);
				} else {
					System.out.println("Not a niyama. " + v);
				}
			}
		} catch(FileNotFoundException ex) {
			System.out.println(ex);
		} catch(IOException ex) {
			System.out.println(ex);
		}
	}

}
