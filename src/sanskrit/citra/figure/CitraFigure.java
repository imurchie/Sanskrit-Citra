package sanskrit.citra.figure;

import sanskrit.citra.Verse;


/**
 * Parent for classes that test for the presence of a citra figure
 * 		Extend this class in order to allow for the new test to be used.
 * 
 * @author imurchie
 *
 */
public interface CitraFigure {
	public boolean testVerse(Verse verse);
}
