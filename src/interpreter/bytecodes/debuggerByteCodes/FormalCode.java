
package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVirtualMachine;

/**
* Makes a Formal bytecode (for use with debug virtual machine)
* @author David Webster
*/
public class FormalCode extends ByteCode {
    private String name;
    private int offset;

    public void execute(DebugVirtualMachine vm) {
        vm.addNewRecord(name, offset + vm.runStackSize() - 1);
    }

    public void execute(VirtualMachine vm) {
        execute((DebugVirtualMachine) vm);
    }

    public String getArgs() {
        return name + " " + offset;
    }

    public void init(String args) {
        name = args.split(" ")[0];
        offset = Integer.parseInt(args.split(" ")[1]);
    }
}