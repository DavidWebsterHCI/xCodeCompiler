//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* destroys the top "inti(String args)" elements of the stack.
* @author David Webster
*/
public class PopCode extends ByteCode {
    protected int numberToDestroy;
    //==========================Constructor============================
    public PopCode() {
    }

    public void execute(VirtualMachine vm) {
        //pop off the stack the numberToDestroy times with individual vm.runStackPop() calls.
        for (int i = 0; i < numberToDestroy; i++) vm.runStackPop();
    }

    public String getArgs() {
        return Integer.toString(numberToDestroy);
    }

    public void init(String args) {
        numberToDestroy = Integer.parseInt(args);
    }

    

    
}