//Interpreter Project
//413
//Section 1
//David Webster
package interpreter;

import java.util.*;


/**
* Creates a hashmap of bytecodes with how they are represented in the .cod files
* (e.g. "Args" is represented as "ARGS" in a file.x.cod file, and it is utilized by the 'ArgsCode.java'
* file)
* @author David Webster
*/
public class CodeTable {
    //Private variables
    private static HashMap<String, String> codes = new HashMap<String, String>();

/**
* Array of strings that is used by the init method.
* update this alphabetical list of byte codes when adding a new bytecode
*/
    private static String[] codeNames = new String[] {
        //Hard coding is ok P.134 of reader top.  Think about updating this to
        //Dynamically create this list from "interpreter.bytecodes" files...
        "Args","Bop","Call","Dump","Falsebranch","Goto","Halt","Label",
        "Lit","Load","Pop","Read","Return","Store","Write",
        "Formal","Function","Line"
    };

/**
* Initiates the code table and matches the ByteCode to their class names in 'interpreter.bytecodes'
*/
    public static void init() {
        // Go through all the elements of the 'codes' string array, add a "Code" to the end of them, and put them in an array
        // that has the key of their code name in caps. (e.g. [ARGS, ArgsCode] [BOP, BopCode])
        for(int i = 0; i<codeNames.length;i++)
        {
            codes.put(codeNames[i].toUpperCase(),codeNames[i]+"Code");
        }
    }


/**
* Getter for a byte code from the hashmap
* @param byteCode Name of the byte code to be found
* @return returns the byte code that is requested.
*/
    public static String get(String byteCode) {
        return codes.get(byteCode);
    }
}