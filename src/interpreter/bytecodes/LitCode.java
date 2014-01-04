//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* puts a value into the runtime stack.
* @author David Webster
*/
public class LitCode extends ByteCode {
    private int value;
    protected String identifier = "";
    //==========================Constructor============================
    public LitCode(){
    }

    public void execute(VirtualMachine vm) {
        vm.runStackPush(value);
    }

    public String getArgs() {
        return Integer.toString(value)+identifier;
    }

    public void init(String args) {
        String temp[] = args.split("\\s");
        if (temp.length >= 2 ) identifier = " "+temp[1];
        value = Integer.parseInt(temp[0]);
    }

    
}