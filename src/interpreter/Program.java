//Interpreter Project
//413
//Section 1
//David Webster
package interpreter;

import interpreter.bytecodes.ByteCode;
import java.util.*;

/**
* Creates a Program object that holds all the ByteCodes read in by the ByteCodeLoader
* and resolves all the movement based byte code addresses (functions/goto/label...etc)
* @author David Webster
*/
public class Program {
    //Counter for what line a specific thing happens on.
    private int lineOfCode;
    //vector of actual bytecodes stored from reading in program
    private List<ByteCode> IndexListOfByteCodes;
    //Vector of bytecodes that are "LABEL"
    private List<Integer> IndexListOfLabelByteCodes;
    //Vector of bytecodes that are movement based.
    private List<Integer> IndexListOfMovementByteCodes;

/**
* Constructor
*/
    public Program() {
        lineOfCode = 0;
        IndexListOfByteCodes = new Vector<ByteCode>();
        //Vector of bytecodes that are "LABEL" and a vector of "FALSEBRANCH", "GOTO", "CALL", "RETURN"
        //that will be used when resolving addresses.  This allows us to only scan through the -file-.x.cod
        //program only one time and yet gather all the information needed.
        IndexListOfLabelByteCodes = new Vector<Integer>();
        IndexListOfMovementByteCodes = new Vector<Integer>();
    }


/**
* Adds a byte code into a Program object.  Separates byte codes if they are LABEL or any of "FALSEBRANCH", "GOTO", "CALL", "RETURN"
* into either the LABEL or Movement vectors for later AddressSymbolicResolution processing.
* @param bytecode
*/
    public void addCode(ByteCode bc) {
        //check to see if its a label or movement, and if it is add it to the respective array
        if ((bc.getName().matches("FALSEBRANCH")) || (bc.getName().matches("GOTO")) || (bc.getName().matches("CALL"))
                || (bc.getName().matches("RETURN"))) IndexListOfMovementByteCodes.add(lineOfCode);
        else if (bc.getName().matches("LABEL")) IndexListOfLabelByteCodes.add(lineOfCode);

        //doesnt matter if its a label, movement or bytecode add it to the indexlist.
        //Address resolution comes later.
        IndexListOfByteCodes.add(bc);
        //update the line counter.
        lineOfCode++;
    }

/**
* Getter for the byte code at 'code' location
* @param code index of the ByteCode requested
* @return ByteCode at the given index
*/
    public ByteCode getCode(int code) {
        return IndexListOfByteCodes.get(code);
    }

    public int length(){
        return IndexListOfByteCodes.size();
    }
/**
* resolve "LABEL", "FALSEBRANCH", "GOTO", "CALL", and "RETURN" byte code movement.
*/
    public void resolveSymbolicAddresses() {
        //The resolve Algorithm is as follows:
        //Get unresolved bytecode from IndexListOfMovementByteCodes vector
        //search through the label vector to find a match for unresolved bytecode
        //reinitialize the bytecode with the correct information.
        //Repeat until all things in the movement vector have been completely resolved.

        for(int i=0;i<IndexListOfMovementByteCodes.size();i++){
            ByteCode unresolvedCode = IndexListOfByteCodes.get((IndexListOfMovementByteCodes.get(i)));
            String labelNeeded = unresolvedCode.getArgs();
            //Scan through labels to find a match.
            for(int j=0;j<IndexListOfLabelByteCodes.size();j++){
                String label = IndexListOfByteCodes.get(IndexListOfLabelByteCodes.get(j)).getArgs();
                //If the arguments are the same, then reinitialize. (e.g. "CALL Read" "LABEL Read" <-- same arguments 'Read')
                if (label.equals(labelNeeded)) {
                    //re-init the bytecode with the resolved arguments
                    unresolvedCode.init(unresolvedCode.getArgs() + " " + Integer.toString(IndexListOfLabelByteCodes.get(j)));
                    //update bytecode index with the resolved bytecode
                    IndexListOfByteCodes.set((IndexListOfMovementByteCodes.get(i)), unresolvedCode);
                    //brake from inside loop to move on to the next unresolved "movement" bytecode
                    break;
                }
            }
        }
    }
}