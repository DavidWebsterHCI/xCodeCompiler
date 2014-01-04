//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* Creates a label.
* @author David Webster
*/
public class LabelCode extends ByteCode{
    private String label;
    //==========================Constructor============================
    public LabelCode(){
    }

    //Cant execute a label command, so blank method
    public void execute(VirtualMachine vm) {
    }

    public String getArgs() {
        return label;
    }

    public void init(String args) {
        label = args;
    }
    

    
}