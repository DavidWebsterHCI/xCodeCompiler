//Interpreter Project
//413
//Section 1
//David Webster
package interpreter;

import interpreter.bytecodes.ByteCode;
import java.util.*;

/**
* VM for -file-.x.cod byte files
* @author David Webster
*/
public class VirtualMachine {
    //From reader P.142
    //updated these to protected so the debug virtual machine can use them.
    protected int programCounter;
    protected Stack<Integer> returnAddress;
    protected Boolean isRunning;
    protected Program program;
    protected RunTimeStack runTimeStack;
    protected Boolean dump;

/**
* @param program Program object which has all the byte codes loaded and symbols resolved read for the VM to run.
* @see interpreter.Program
*/
    public VirtualMachine(Program program) {
        this.program = program;
    }

/**
* Iteratively executes the bytecodes that the Program object 'program' contains
* and then loads required values on to the RunTimeStack 'runTimeStack'.
*/
    public void executeProgram() {
        //reader p.142
        programCounter = 0;
        runTimeStack = new RunTimeStack();
        returnAddress = new Stack<Integer>();
        isRunning = true;
        dump = false;

        while (isRunning) {
            ByteCode code = program.getCode(programCounter);
            code.execute(this);
            if (dump==true) dump(code);

            programCounter++;
        }
    }

/**
* dumps information from the stack and decorates it with special formating for
* specific byte codes (e.g. return/call/store..etc)
* @param bc ByteCode object used to get information from the {name}Code classes.
* @see interpreter.RunTimeStack#dump()
*/
    private void dump(ByteCode bc) {
        //Used as a buffer for formatted output.
        String runningOutput = "";
        
        //Make sure its not a DUMP bytecode (getArgs isnt supported by DumpCode)
        if (!bc.getName().matches("DUMP")) {
            //Split the string by white space which leaves byteCodeArgs[0] as the bytecode name
            //and byteCodeArgs[1] as a string of the arguments.
            String[] byteCodeArgs = bc.getArgs().split("\\s");
            runningOutput = runningOutput + bc.getName() + " " + byteCodeArgs[0] + " ";
            // Check for special codes (special codes need different type of formatting)
            if ((bc.getName().matches("RETURN"))||(bc.getName().matches("CALL"))||(bc.getName().matches("STORE"))
                    ||(bc.getName().matches("LIT"))||(bc.getName().matches("LOAD"))) {
                int returnId;
                //In java JDK v7+ this switch could be based on a string.
                //If future use of this program is needed, refactor this code into
                //a string switch statement:
                //switch(byteCodeType.matches())
                //case "RETURN":
                //    xxxxxx etc.
                if(bc.getName().matches("RETURN"))
                    returnId = 1;
                else if(bc.getName().matches("CALL"))
                    returnId = 2;
                else if(bc.getName().matches("STORE"))
                    returnId = 3;
                else if(bc.getName().matches("LIT"))
                    returnId = 4;
                else if(bc.getName().matches("LOAD"))
                    returnId = 5;
                else
                    returnId = -1; //error flag.

                switch(returnId){
                    case 1:
                        //Return
                        String name = byteCodeArgs[0].split("<<")[0];
                        runningOutput = runningOutput + "\t\treturn from "+name+": "+runTimeStack.peek();
                        break;
                    case 2:
                        //Call
                        //read in would be: fib<<2>>
                        //First get 'fib' then take '2' and disregard all the rest.
                        //(do this by splitting at the first instance of '<<')
                        //Get the arguments passed to 'fib' by checking the elements of the top frame.
                        String name2 = byteCodeArgs[0].split("<<")[0];
                        String arguments = "";
                        //iterate through the last frame
                        for (int i = runTimeStack.peekFrame(); i < runTimeStack.size(); i++) {
                            arguments = arguments + runTimeStack.elementAt(i);
                            //If there are multiple arguments seperate them with a ,
                            if (i != runTimeStack.size()-1)
                                arguments = arguments + ",";
                        }
                        runningOutput = runningOutput + "\t\t"+name2+"("+arguments+")";
                        break;
                    case 3:
                        //Store
                        runningOutput = runningOutput + byteCodeArgs[1]+"\t\t"+byteCodeArgs[1]+"="+runTimeStack.peek();
                        break;
                    case 4:
                        //Lit
                        if (byteCodeArgs.length > 1)
                            runningOutput = runningOutput + byteCodeArgs[1]+"\t\tint "+byteCodeArgs[1];
                        break;
                    case 5:
                        //Load
                        runningOutput = runningOutput + byteCodeArgs[1]+"\t\t<Load "+byteCodeArgs[1]+">";
                        break;
                    default:
                        break;
                }
            //print out all formatting, special and dump
            System.out.println(runningOutput);
            runTimeStack.dump();
            }
        }
    }

//=========================================================================================
//========================Methods utilized by the run stack methods========================
//=========================================================================================
/**
* Creates a new frame at 'offset'
* @param offset Location of new frame
* @see interpreter.RunTimeStack#newFrameAt(int)
*/
    public void newRunStackFrameAt(int offset) {
        runTimeStack.newFrameAt(offset);
    }

/**
* Pop the top run time stack frame
* @see interpreter.RunTimeStack#popFrame()
*/
    public void popRunStackFrame() {
        runTimeStack.popFrame();
    }

/**
* Turns DUMP mode on/off
* @param DumpOnOffSwitch on/off (true/false)
* @see interpreter.RunTimeStack#dump()
*/
    public void runStackDump(Boolean DumpOnOffSwitch) {
        dump = DumpOnOffSwitch;
    }
    
/**
* Take the element at runtime stack(offset), and move it to the top
* @param offset Location to be put on top
* @return The value just pushed
* @see interpreter.RunTimeStack#load(int)
*/
    public int runStackLoad(int offset) {
        return runTimeStack.load(offset);
    }

/**
* Peek at top element in the stack.
* @return top element in the stack.
* @see interpreter.RunTimeStack#peek()
*/
    public int runStackPeek() {
        return runTimeStack.peek();
    }

/**
* Pops the top element of the Runtime stack
* @return The popped element.
* @see interpreter.RunTimeStack#pop()
*/
    public int runStackPop() {
        return runTimeStack.pop();
    }

/**
* Pushes an int value onto the stack
* @param pushVal The value to be pushed
* @return the value just pushed
* @see interpreter.RunTimeStack#push(int)
*/
    public int runStackPush(int pushVal) {
        return runTimeStack.push(pushVal);
    }

/**
* Pushes Integer object onto the Runtime stack
* @param pushIntObject The object just pushed
* @return The object just added
* @see interpreter.RunTimeStack#push(java.lang.Integer)
*/
    public Integer runStackPush(Integer pushIntObject) {
        return runTimeStack.push(pushIntObject);
    }

/**
* Getter for size of the run time stack
* @return Size of the run time stack
* @see interpreter.RunTimeStack#size()
*/
    public int runStackSize() {
        return runTimeStack.size();
    }

/**
* Pops the top element from the Runtime stack and stores it at 'offset'
* @param offset place to put the popped value
* @return The value just stored
* @see interpreter.RunTimeStack#store(int)
*/
    public int runStackStore(int offset) {
        return runTimeStack.store(offset);
    }











//=========================================================================================
//========================Methods utilized by the byte codes===============================
//=========================================================================================



/**
* Getter for the VM's program counter
* @return Program Counter location
*/
    public int getProgramCounter() {
        return programCounter;
    }

/**
* Sets the VM's Program Counter
* @param pc updated Program Counter location
*/
    public void setProgramCounter(int pc) {
        programCounter = pc;
    }

/**
* Stop running.
*/
    public void exit() {
        isRunning = false;
    }



//=========================================================================================
//========================Methods for returning Addresses==================================
//=========================================================================================

/**
* Pushes a return address.
* @param address The return address
* @return The address just pushed
*/
    public int pushReturnAddress(int address) {
        return returnAddress.push(address);
    }

/**
* Pops the last return address.
* @return Top item in the returnAddress stack
*/
    public int popReturnAddress() {
        return returnAddress.pop();
    }
}
