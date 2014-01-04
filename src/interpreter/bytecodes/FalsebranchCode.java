//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* Branches execution
* @author David Webster
*/
public class FalsebranchCode extends ByteCode{
    private String label;
    //==========================Constructor============================
    public FalsebranchCode(){
    }

    public void execute(VirtualMachine vm) {
        //this pop will either return 0 or 1 (true/false) if 0 move PC to the label
        if (vm.runStackPop() == 0) {
            vm.setProgramCounter(Integer.parseInt(label.split("\\s")[1]));
        }
    }

    public String getArgs() {
        return label;
    }

    public void init(String args) {
        label = args;
    }

    
}