
package interpreter.debugger;

import interpreter.*;
import java.util.*;
import interpreter.bytecodes.ByteCode;


/**
* A Debug Version of the X language Virtual Machine.
* @author David Webster
*/
public class DebugVirtualMachine extends VirtualMachine {
    private boolean isTrace;
    private boolean isLineModified;
    private List<xLine> userSrc;
    private Stack<FunctionEnvironmentRecord> StackOfFunctionEnvironmentRecords;
    private ByteCode codeName;
    private String stepType;
    private String bufferOfTrace;

/**
* DebugVirtualMachine constructor
* @param program A program object that's loaded with bytecodes
* @param userSrc The original .x source code for the program
*/
    public DebugVirtualMachine(Program program, List<xLine> code) {
        //Initializations with an initial FER
        super(program);
        this.StackOfFunctionEnvironmentRecords = new Stack<FunctionEnvironmentRecord>();
        FunctionEnvironmentRecord temp = new FunctionEnvironmentRecord();
        temp.setFunctionName("main");
        temp.setStartLine(1);
        temp.setEndLine(code.size());
        temp.setCurrentLine(1);
        this.StackOfFunctionEnvironmentRecords.add(temp);

        //Initalization of inherited variables.
        this.isRunning = true;
        this.isLineModified = false;
        this.isTrace = false;
        this.codeName = null;
        this.programCounter = 0;
        this.returnAddress = new Stack<Integer>();
        this.runTimeStack = new RunTimeStack();
        this.userSrc = code;

    }

/**
* Pushes a variable 'name' at 'offset' onto the top FER in the StackOfFER
* @param name The name of the variable
* @param offset The variables offset
* @see interpreter.debugger.FunctionEnvironmentRecord#enter(java.lang.String, int)
*/
    public void addNewRecord(String name, int offset) {
         StackOfFunctionEnvironmentRecords.lastElement().enterVariableRecord(name, offset);
    }

/**
* When entering a new function ByteCodes use this method to add a new FER to the stack of FERs
* @param function Name
* @param startLine Starting line
* @param endLine Ending line
* @see interpreter.debugger.FunctionEnvironmentRecord
*/
    public void createFunctionRecord(String function, int startLine, int endLine) {
        //Create and initialize a record
        FunctionEnvironmentRecord FER = new FunctionEnvironmentRecord();
        FER.setFunctionName(function);
        FER.setStartLine(startLine);
        FER.setEndLine(endLine);
        FER.setCurrentLine(getCurrentLine());
        //Push it into the stack
        StackOfFunctionEnvironmentRecords.add(FER);

        // only trace non-intrinsic functions.
        if ((StackOfFunctionEnvironmentRecords.peek().getStartLine() > 0) && isTrace)
            storeTraceOutput(false);
    }

/**
* Based on what stepType is set, this function will execute bytecodes required
*/
    public void executeProgram() {
        //Get the stack size currently.  Will be needed for a check later
        int size = StackOfFunctionEnvironmentRecords.size();

        //Fetch-execute cycle started.
        while (isRunning && isValidStep(size)) {
            codeName = program.getCode(programCounter);
            codeName.execute(this);
            programCounter++;
        }

        stepType = "";
        if ((StackOfFunctionEnvironmentRecords.size() != size) && (StackOfFunctionEnvironmentRecords.peek().getStartLine() > 0) && isTrace)
            System.out.println(bufferOfTrace);
    }

/**
* Getter for the current function's name.
* @return Function's name
*/
    public String getCurrentFunction() {
        return StackOfFunctionEnvironmentRecords.peek().getFunctionName().split("<<")[0];
    }

/**
* Getter for currently executing line
* @return Current line number
*/
    public int getCurrentLine() {
         return StackOfFunctionEnvironmentRecords.peek().getCurrentLine();
    }

/**
* Getter for the current function's ending line number.
* @return Ending line number
*/
    public int getEndingLine() {
        return StackOfFunctionEnvironmentRecords.peek().getEndLine();
    }

/**
* Getter for a line of code
* @param lineNumber The desired line number.
* @return The requested code.
*/
    public String getLineOfSourceCode(int lineNumber) {
        return userSrc.get(lineNumber - 1).getxLine();
    }

/**
* Getter for code size (in lines)
* @return Number of lines
*/
    public int getSourceCodeLength() {
        return userSrc.size();
    }

/**
* Getter for the current function's starting line number.
* @return Starting line number
*/
    public int getStartingLine() {
        return StackOfFunctionEnvironmentRecords.peek().getStartLine();
    }

/**
* Getter for the current function's 'variableName' variable.
* @param variableName Variable name
* @return Variable's value
*/
    public int getValue(String variableName) {
        return runTimeStack.elementAt(StackOfFunctionEnvironmentRecords.peek().getElementOffset(variableName));
    }

/**
* Getter for the current function's variables
* @return Variables
* @see interpreter.debugger.FunctionEnvironmentRecord#getVariableNames()
*/
    public Set<String> getVariableNames() {
        return StackOfFunctionEnvironmentRecords.peek().getElements();
    }

/**
* Scans through .x code to see if its a valid BP line.
* @param line The line of code in question
* @return TRUE if its a valid line of code and FALSE if not.
*/
    private boolean isAllowedBreakPointLine(String line) {
        if(line.contains("=")) return true;
        if(line.contains("{")) return true;
        if(line.contains("boolean")) return true;
        if(line.contains("int")) return true;
        if(line.contains("if")) return true;
        if(line.contains("return")) return true;
        if(line.contains("while")) return true;

        //If it doesnt have any of those valid markers, return false.
        return false;
    }

/**
* Boolean check for a breakpoint on <line>
* @param line Line in question
* @return TRUE if there is a breakpoint and FALSE if there isn't.
*/
    public boolean isLineABreakPoint(int line) {
        //Note: if user wants to know if line 4 is a breakpoint, in reality the
        //program has to check line 3 hence the decrement.
            return userSrc.get(--line).isBreakPoint();
    }

/**
* Status of execution check
* @return Returns TRUE if program is being executed and FALSE if execution is halted.
*/
    public boolean isRunning() {
        return isRunning;
    }

/**
* This function enables DebugVirtualMachine to know when to stop fetching and executing
* @param size Environment Stack's size at point of entry
* @return Returns TRUE if the F-E cycle should continue, FALSE if it shouldn't.
*/
    private boolean isValidStep(int size) {
        //This test is used in the fetch-execute cycle.  If its true, it continues, if its false, it stops.
        boolean test = false;

        //If it was "continue" it will do this chunk and return
        if (stepType.matches("step")) {
            if(!isLineABreakPoint(getCurrentLine()))
                test = true;
            else if(size == StackOfFunctionEnvironmentRecords.size())
                test = true;
            else if(!isLineModified)
                test = true;
            if (isLineABreakPoint(getCurrentLine()) && isLineModified)
                test = false;

            isLineModified = false;
            return test;
        } 
        
        //If it was "into" it will do this chunk and return
        if (stepType.matches("into")) {
            if(StackOfFunctionEnvironmentRecords.size() <= size)
                test = true;
            else
                test = false;

            // going into a non-intrinsic function so read the FormalCode in
            if(test == false)
                if(StackOfFunctionEnvironmentRecords.peek().getStartLine() > 0 &&codeName.getName().matches("DEBUGGERBYTES.FUNCTION"))
                    test = true;
 
            isLineModified = false;
            return test;
        }
        
        //If it was "out" it will do this chunk and return
        if (stepType.matches("out")) {
            if(StackOfFunctionEnvironmentRecords.size() >= size)
                test = true;
            else
                test = false;
            
            if (isLineABreakPoint(getCurrentLine()) && isLineModified)
                test = false;
            isLineModified = false;
            return test;
        }
        
        //If it was "over" it will do this chunk and return
        if (stepType.matches("over")){
            if(!isLineModified)
                test = true;
        }
        //These two statements are in essence attached to "over"'s chunk,
        //but the function needs to return soething outside of an if statement.
        isLineModified = false;
        return test;
    }

/**
* Popper for the top FER on the StackOfFER
*/
    public void popFunctionRecord() {
        // only trace non-intrinsic functions.

        if(StackOfFunctionEnvironmentRecords.size()>0)
            if ((StackOfFunctionEnvironmentRecords.peek().getStartLine()) > 0 && isTrace)
                storeTraceOutput(true);
        //after trace is set, pop the top record.
        if(StackOfFunctionEnvironmentRecords.size()>=1)
            StackOfFunctionEnvironmentRecords.pop();
        else{
            System.out.println("***Error*** in popFunctionRecord:  Popping out of bounds");
            System.exit(-1);
        }
    }

/**
* Pops 'numberToRemove' entries from the top FER
* @param numberToRemove Number of entries to remove
* @see interpreter.debugger.FunctionEnvironmentRecord#pop(int)
*/
    public void popRecords(int numberToRemove) {
        if(StackOfFunctionEnvironmentRecords.size()-numberToRemove>=0)
            StackOfFunctionEnvironmentRecords.lastElement().pop(numberToRemove);
        else{
            System.out.println("***Error*** in popRecords:  Popping out of bounds");
            System.exit(-1);
        }
    }

/**
* Printer for the function calls.
*/
    public void printFunctionCalls() {
        //uses stackBuffer as a buffer
        String stackBuffer = "";
        for (int i = StackOfFunctionEnvironmentRecords.size() - 1; i > 0; i--)
            stackBuffer += StackOfFunctionEnvironmentRecords.elementAt(i).getFunctionName().split("<<")[0] + ": " +  StackOfFunctionEnvironmentRecords.elementAt(i).getCurrentLine() + "\n";
        System.out.println(stackBuffer);
    }

/**
* Setter for Breakpoints (used to "set" a non-breakpoint over a previously set breakpoint
* in the 'delbp' user command.)
* @param line Line number
* @param isBP TRUE sets the breakpoint, FALSE removes a previously set breakpoint.
* @return Success of function.
*/
    public boolean setBreakPoint(int line, boolean isBP) {
        if (!isAllowedBreakPointLine(userSrc.get(line).getxLine()))
            return false; //If its not a valid line just stop and return false.

        userSrc.get(line).setBreakPoint(isBP);
        return true;
    }

/**
* Setter for the current line of execution (used by bytecodes requests)
* @param lineNo Current line number
*/
    public void setCurrentLine(int lineNo) {
        if((StackOfFunctionEnvironmentRecords.size() == runTimeStack.frames() + 1) && (lineNo >= 0) ) {
            isLineModified = true;
            StackOfFunctionEnvironmentRecords.elementAt(StackOfFunctionEnvironmentRecords.size()-1).setCurrentLine(lineNo);
        }
    }

/**
* Setter for stepType.  Note: if no type is set, then NO bytecodes can execute correctly.
* Valid types are step, into, out, and over.
* @param type
*/
    public void setStepType(String type) {
        try{
        if (type.matches("step|into|out|over"))
            stepType = type;
        else
        {
            Exception e = new Exception();
            throw e;
        }
        }catch(Exception e)
        {
            System.out.println("Error in setStepType(): " + e);
            System.exit(-1);
        }

    }

/**
* Setter for tracing
* @param traceONOFF true - ON; false - OFF
*/
    public void setTraceONOFF(boolean traceONOFF) {
        isTrace = traceONOFF;
        //Make sure the buffer doesnt contain junk from the last trace
        bufferOfTrace = "";
    }

/**
* Remembers all entries and exits from functions. (maintained by FunctionRecord methods)
* @param isExiting TRUE if exiting from a function FALSE if entering a function.
*/
    private void storeTraceOutput(boolean isExiting) {
        //Add the appropriate amount of space indentation
        for (int i = 0; i < StackOfFunctionEnvironmentRecords.size(); i++)
            bufferOfTrace += " ";
        
        //Get the function
        String function = StackOfFunctionEnvironmentRecords.peek().getFunctionName().split("<<")[0];
       
        //If its not exiting a function, then print the function name AND args else just function name and its result
        if (!isExiting) {
            //Find the value of the args passed.
            String val = "";
            for (int i = runTimeStack.peekFrame(); i < runTimeStack.size(); i++) {
                if (i < runTimeStack.size()-1) val += runTimeStack.elementAt(i) + ",";
                else val += runTimeStack.elementAt(i);
            }
            bufferOfTrace += function + "(" + val + ")" + "\n";
        } else {
            bufferOfTrace += function + "() exits w/ result: " + runTimeStack.peek() + "\n";
        }
    }















}