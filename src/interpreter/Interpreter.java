//Interpreter Project
//413
//Section 1
//David Webster
//File produced by professor and unmodified by David Webster
//File is now modified for debug by David Webster 4/16/2012
package interpreter;

import interpreter.debugger.UI.DebugUserInterface;
import interpreter.debugger.*;
import java.io.*;
import java.util.List;

/**
* <pre>
*
*
*
* Interpreter class runs the interpreter:
* 1. Perform all initializations
* 2. Load the bytecodes from file
* 3. Run the virtual machine
*
*
*
* </pre>
*/
public class Interpreter {
    private Boolean debugMode;
    private ByteCodeLoader bcl;
    private List<xLine> xCode;

    public Interpreter(String codeFile, Boolean debug) {
        debugMode = debug;
        try {
            CodeTable.init();
            if (debugMode) {
                String source = codeFile + ".x";
                codeFile += ".x.cod";
                bcl = new ByteCodeLoader(codeFile);
                xCode = xLoader.load(source);
                System.out.println("====Debugging " + source + "====");
            } else {
                bcl = new ByteCodeLoader(codeFile);
            }
} catch (IOException e) {
            System.out.println("**** " + e);
}
    }

    void run() {
        Program program;
        VirtualMachine vm;
        
        if (debugMode) {
            program = bcl.loadCodes(true);
            vm = new DebugVirtualMachine(program, xCode);
            DebugUserInterface.showUI((DebugVirtualMachine) vm);
        } else {
            program = bcl.loadCodes(false);
            vm = new VirtualMachine(program);
            vm.executeProgram();
        }
    }

    public static void main(String args[]) {
        if (args.length == 0) {
            System.out.println("***Incorrect usage, try: java interpreter.Interpreter <file>");
            System.exit(-1);
        }

        Interpreter interpreter;
        if (args[0].equals("-d")) interpreter = new Interpreter(args[1], true);
        else interpreter = new Interpreter(args[0], false);

        interpreter.run();
    }
}