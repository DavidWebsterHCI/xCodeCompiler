
package interpreter.debugger;

import java.io.*;
import java.util.*;

/**
* Translates a .x file into a List of it's lines of code.
* @author David Webster
*/
public class xLoader {
    private xLoader() {}

/**
* Translates .x lines into a List of it's lines of code.
* @param xFile The .x source code file
* @return A list of xLine objects
* @throws FileNotFoundException
* @throws IOException
* @see interpreter.debugger.xLine
*/
    public static List<xLine> load(String xFile) throws FileNotFoundException, IOException {
        List<xLine> xCode = new Vector<xLine>();
        try{
            //read in from file
            BufferedReader programFile = new BufferedReader(new FileReader(xFile));
            //Load the xLines until there are none left.
            while (programFile.ready())
                xCode.add( new xLine(programFile.readLine(), false) );
        } catch(FileNotFoundException e){
            System.out.println("****Error!**** Couldn't open file: " + e);
            System.exit(-1);
        }
         catch(IOException e){
            System.out.println("****Error!**** Input output error: " + e);
            System.exit(-1);
        }
        return xCode;
    }
}