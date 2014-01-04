package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.debugger.DebugVirtualMachine;

/**
* Debug version of ReturnCode. Enables removal of the top Record from the Environment Stack
* @author David Webster
* @see interpreter.bytecodes.ReturnCode
*/
public class ReturnCode extends interpreter.bytecodes.ReturnCode {

    public void execute(VirtualMachine vm) {
        execute((DebugVirtualMachine) vm);
    }

    public void execute(DebugVirtualMachine vm) {
        super.execute(vm);
        vm.popFunctionRecord();
    }
}