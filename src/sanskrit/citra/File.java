package sanskrit.citra;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import sanskrit.citra.figure.CitraFigure;




/**
 * File class: models a Sanskrit versified work.
 * @author imurchie
 *
 */
public class File {
	private String filename;
	private BufferedReader reader;
	
	
	
	public File(String filename) throws FileNotFoundException {
		this.filename = filename;
		
		openFile();
	}
	
	
	public Verse getNextVerse() throws FileNotFoundException, IOException {
		openFile();
		
		String line = null;
		ArrayList<String> padas = new ArrayList<String>(2);
        while ((line=reader.readLine()) != null) {
        	//System.out.println("Verse: " + line);
            if(Verse.isSanskritLine(line)) {
            	// the line could be empty, or a valid line
            	if(line.length() == 0) {
            		continue;
            	}
            	padas.add(line);
            	
            	if(line.indexOf("//") != -1) {
            		// last line of the verse
            		break;
            	}
            }
        }
        
        if(padas.size() == 0) {
        	// no verse to deal with
        	return null;
        }
        
		return new Verse(padas);
	}
	public Verse getNextVerse(CitraFigure figure) throws FileNotFoundException, IOException {
		Verse v = null;
		while((v = this.getNextVerse()) != null) {
			if(v.isFigure(figure)) {
				return v;
			}
		}
		return null;
	}
	
	private void openFile() throws FileNotFoundException {
		if(reader == null) {
			reader = new BufferedReader(new FileReader(filename));
		}
	}

}
