//Interpreter Project
//413
//Section 1
//David Webster
package interpreter;

import interpreter.bytecodes.ByteCode;
import java.io.*;
import java.util.*;

/**
* Utilizing a Program object, byteCodes are read start to finish from a .cod file
* using the bufferedReader 'inputFileReader'.
* @see interpreter.Program
* @author David Webster
*/
public class ByteCodeLoader {
    //Private Variables
    private BufferedReader inputFileReader;

/**
* Reads the -file-.x.cod file and puts all the found ByteCodes into a Program object.
* @return a Program object that is fully loaded with all the ByteCodes contained within
* the input -file-.x.cod
* @see interpreter.Program
*/
    public Program loadCodes(boolean isDebug) {
        
        //refer to Page 133 in reader (top)
        Program program = new Program();
        try {
            while (inputFileReader.ready()) {
                // Read line and split by the first whitespace ("\\s"), and take the first chunk (argsRead[0]) as the bytecode,
                // and the second chunk (argsRead[1]) as a string of the arguments to pass to the respective bytecode
                // (e.g. "LIT 0 i" will turn into (argsRead[0])=="LIT", (argsRead[1])=="0 i")
                String[] argsRead = inputFileReader.readLine().split("\\s",2);
                //From reader page 134 dynamic statement utilizing reflection
                ByteCode bytecode;
                if(isDebug)
                    bytecode = (ByteCode)(Class.forName("interpreter.bytecodes." + getCodeClass(argsRead[0])).newInstance());
                else{
                    bytecode = (ByteCode)(Class.forName("interpreter.bytecodes." + getCodeClass(argsRead[0]).replace("debuggerByteCodes.", "")).newInstance());
                }
                //If there are arguments (beyond just the bytecode name), pass them to
                //the 'init' method of the bytecode being initialized else pass an empty string
                if (argsRead.length >= 2) bytecode.init(argsRead[1]); //argsRead[1] is the string of arguments
                else bytecode.init("");
                //After figuring out what bytecode it is, what args it has, and initializing it,
                //add it to the Program object 'program' so that the VM can use it once it loads up 'program'
                program.addCode(bytecode);
            }
        } catch (Exception e) {
            System.out.println("Error in loadCodes()" + e);
            System.exit(-1);
        }

        //Resolve all the addresses in the .cod program that was just read into Program program.
        program.resolveSymbolicAddresses();
        return program;
    }

/**
* Loads the -file-.x.cod file to read byte codes from
* @param inputFileLocalPath this should be the path of the -file-.x.cod file to be read
* @throws IOException
*/
    public ByteCodeLoader(String inputFileLocalPath) throws IOException {
        try{
            inputFileReader = new BufferedReader(new FileReader(inputFileLocalPath));
        }catch (IOException e) {
            System.out.println("******Error******\n" + inputFileLocalPath + " could not be accessed.\n******Error******" + e);
            System.exit(-1);
        }
    }
/**
* Returns the byte code's class name from the vector in CodeTable 'codes' and if it is a specialized
* debug byte code, it will add the package name onto the front of it.
* Needed for the code from reader page 134
* NOTE: I understand that 1 point was taken off for this in the interperter project,
    * however, I have added the "final" tag to this method, which should have been there
    * to explain that no subclass was actually going to overload this, its just more simple
    * and easier code to read than typing it into each of the subclasses.
* @param codeClassString CodeClassString read from input file.
* @return byte code class name
*/
    protected final String getCodeClass(String codeClassString) {
         if (codeClassString.matches("FORMAL|FUNCTION|LINE|LIT|POP|RETURN"))
            return "debuggerByteCodes." + CodeTable.get(codeClassString);
        else
            return CodeTable.get(codeClassString);
    }
}