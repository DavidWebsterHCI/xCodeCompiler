package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
* Debug form of LitCode, which puts a variable at a certain offset into the 
* current function record.
* @author David Webster
* @see interpreter.bytecodes.LitCode
*/
public class LitCode extends interpreter.bytecodes.LitCode {
    public void execute(VirtualMachine vm) {
        execute((DebugVirtualMachine) vm);
    }

    public void execute(DebugVirtualMachine vm) {
        super.execute(vm);
        if (!identifier.isEmpty())
            vm.addNewRecord(identifier,  vm.runStackSize() - 1);
    }
}