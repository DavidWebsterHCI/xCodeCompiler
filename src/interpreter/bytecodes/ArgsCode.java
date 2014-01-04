//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;
/**
* Prepares the arguments to be passed to a function call
* @author David Webster
*/
public class ArgsCode extends ByteCode {
    private int amount;
    //==========================Constructor============================
    public ArgsCode(){
    }

    public void execute(VirtualMachine vm) {
        vm.newRunStackFrameAt(vm.runStackSize() - amount);
    }

    public String getArgs() {
        return Integer.toString(amount);
    }

    public void init(int a) {
        amount = a;
    }

    public void init(String a) {
        amount = Integer.parseInt(a);
    }
}