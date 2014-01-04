//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* pushes a element onto the stack
* @author David Webster
*/
public class LoadCode extends ByteCode {
    private int index;
    private String indentifier = "";
    //==========================Constructor============================
    public LoadCode(){
    }

    public void execute(VirtualMachine vm) {
        vm.runStackLoad(index);
    }

    public String getArgs() {
        return Integer.toString(index)+indentifier;
    }

    public void init(String args) {
        String temp[] = args.split("\\s");
        indentifier = " "+temp[1];
        index = Integer.parseInt(temp[0]);
    }
}