package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
* Debug version of PopCode enabling PopCode to delete things.
* @author David Webster
* @see interpreter.bytecodes.PopCode
*/
public class PopCode extends interpreter.bytecodes.PopCode {

    public void execute(VirtualMachine vm) {
        execute((DebugVirtualMachine) vm);
    }

    public void execute(DebugVirtualMachine vm) {
        super.execute(vm);
        vm.popRecords(numberToDestroy);
    }
}