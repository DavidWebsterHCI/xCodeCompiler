package interpreter.bytecodes.debuggerByteCodes;

import interpreter.VirtualMachine;
import interpreter.bytecodes.ByteCode;
import interpreter.debugger.DebugVirtualMachine;

/**
* Starts a function.
* @author David Webster
*/
public class FunctionCode extends ByteCode {
    private String name;
    private int startLine;
    private int endLine;

    public void execute(DebugVirtualMachine vm) {
        vm.createFunctionRecord(name, startLine, endLine);
        vm.setCurrentLine(startLine);
    }

    public void execute(VirtualMachine vm) {
        execute((DebugVirtualMachine) vm);
    }

    public String getArgs() {
        return name + " " + startLine + " " + endLine;
    }

    public void init(String s) {
        name = s.split(" ")[0];
        startLine = Integer.parseInt(s.split(" ")[1]);
        endLine = Integer.parseInt(s.split(" ")[2]);
    }
}