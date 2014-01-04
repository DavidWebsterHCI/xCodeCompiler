//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* Function return
* @author David Webster
*/
public class ReturnCode extends ByteCode {
    private String name;
    //==========================Constructor============================
    public ReturnCode(){
    }

    public void execute(VirtualMachine vm) {
        vm.popRunStackFrame();
        vm.setProgramCounter(vm.popReturnAddress());
    }

    public String getArgs() {
        return name;
    }

    public void init(String args) {
        name = args;
    }
}