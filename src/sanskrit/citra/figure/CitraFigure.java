package sanskrit.citra.figure;

import java.io.BufferedReader;
import java.io.IOException;

import sanskrit.citra.Verse;


/**
 * Parent for classes that test for the presence of a citra figure
 * 		Extend this class in order to allow for the new test to be used.
 *
 * @author imurchie
 *
 */
public abstract class CitraFigure {
	public abstract boolean testVerse(Verse verse);
	public abstract String getFigureName();

	/**
	 * This method is intended to create an instance of a particular type of
	 * figure. It should get the information necessary for that figure,
	 * whatever that information is.
	 *
	 * Can't be static and abstract, so we need an empty version here.
	 *
	 * @return
	 * @throws FigureInfoException
	 */
	public static CitraFigure generateObject(BufferedReader in) throws FigureInfoException, IOException {
		return null;
	}
}
