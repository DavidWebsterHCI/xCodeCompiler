//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* turns dump on/off
* @author David Webster
*/
public class DumpCode extends ByteCode {
    private Boolean dump;
    //==========================Constructor============================
    public DumpCode() {
    }

    public void execute(VirtualMachine vm) {
        vm.runStackDump(dump);
    }

    public String getArgs() {
        throw new UnsupportedOperationException("DumpCode cannot use getArgs method.");
    }

    public void init(String args) {
        if (args.toUpperCase().equals("OFF")) dump = false;
        else dump = true;
    }

    

    
}