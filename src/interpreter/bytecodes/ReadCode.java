//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;
import java.io.*;

/**
* Reads user input
* @author David Webster
*/
public class ReadCode extends ByteCode {
    //==========================Constructor============================
    public ReadCode(){
    }

    public void execute(VirtualMachine vm) {
        try {
            System.out.println("Please enter a number: ");
            //code chunk taken from InterpreterProject.doc off ilearn
            BufferedReader in = new BufferedReader( new InputStreamReader( System.in ) );
            vm.runStackPush(Integer.parseInt(in.readLine()));
        } catch( java.io.IOException e ) {
            System.out.println("***ERROR***" + e);
        }
    }

    public String getArgs() {
        return "";
    }

    //Nothing to init on a readcode, so blank method
    public void init(String args){
    }
}