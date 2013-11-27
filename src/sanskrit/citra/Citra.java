package sanskrit.citra;

import java.io.*;

import sanskrit.citra.figure.CitraFigure;
import sanskrit.citra.figure.FigureInfoException;
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
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    // query for file
    String filename = null;
    while(true) {
      System.out.print("Enter a filename: ");

      String fname = in.readLine();
      if(fname.isEmpty()) {
        System.out.println("No file entered. Exiting...");
        return;
      } else if(fname.equalsIgnoreCase("quit")) {
        return;
      }
      System.out.println(fname.equalsIgnoreCase("quit"));
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

      // get the object
      try {
        if(fname.equals("a")) {
          // this is a NIYAMA
          figure = Niyama.generateObject(in);
        } else {
          System.out.println("Invalid choice.");
        }
      } catch(IOException ex) {
        // do something
        System.out.println("Error creating niyama object: " + ex);
      } catch(FigureInfoException ex) {
        // do something else
        System.out.println("Unable to create niyama object: " + ex);
      }
      if(figure != null) {
        break;
      }

      break;
    }


    try {
      System.out.println("\n\n*************************************");
      System.out.println("File: " + filename);
      File file = new File(filename);
      Verse v = null;
      while((v = file.getNextVerse()) != null) {
        if(v.isFigure(figure)) {
          System.out.println(figure.getFigureName() + "! " + v);
        } else {
          System.out.println("Not a " + figure.getFigureName() + ". " + v);
        }
      }
    } catch(FileNotFoundException ex) {
      System.out.println(ex);
    } catch(IOException ex) {
      System.out.println(ex);
    }
  }

}
