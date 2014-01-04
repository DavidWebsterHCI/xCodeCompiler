package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVirtualMachine;

/**
* Starts a new line.
* @author David Webster
*/
public class LineCode extends ByteCode {
    private int lineNumber;

    public void execute(DebugVirtualMachine vm) {
        vm.setCurrentLine(lineNumber);
    }

    public void execute(VirtualMachine vm) {
        execute((DebugVirtualMachine) vm);
    }

    public String getArgs() {
        return "" + lineNumber;
    }

    public void init(String s) {
        lineNumber = Integer.parseInt(s);
    }
}