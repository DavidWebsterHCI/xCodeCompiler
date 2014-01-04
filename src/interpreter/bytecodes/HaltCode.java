//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* Exit VM execution.
* @author David Webster
*/
public class HaltCode extends ByteCode {
    //==========================Constructor============================
    public HaltCode(){
    }

    public void execute(VirtualMachine vm) {
        vm.exit();
    }

    public String getArgs() {
        return "";
    }

    //Nothing to init with this code, so blank method
    public void init(String args) {
    }

    

    
}