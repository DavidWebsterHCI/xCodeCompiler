//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* Calls a funtion
* @author David Webster
*/
public class CallCode extends ByteCode {
    private String name;
    //==========================Constructor============================
    public CallCode(){
    }

    public void execute(VirtualMachine vm) {
        vm.pushReturnAddress(vm.getProgramCounter());
        //-1 at end because it should be right before the label index.
        vm.setProgramCounter((Integer.parseInt(name.split(" ")[1]))-1);
    }

    public String getArgs() {
        return name;
    }

    public void init(String args) {
        name = args;
    }

    

}