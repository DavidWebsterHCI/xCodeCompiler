//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* Goto a new index to execute from
* @author David Webster
*/
public class GotoCode extends ByteCode {
    private String label;
    //==========================Constructor============================
    public GotoCode(){
    }

    public void execute(VirtualMachine vm) {
        //take the arg from the LABEL statement and move pc to that index - 1.
        vm.setProgramCounter(Integer.parseInt(label.split(" ")[1])-1);
    }

    public String getArgs() {
        return label;
    }

    public void init(String args) {
        label = args;
    }

    

    
}