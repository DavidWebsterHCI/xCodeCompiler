
package interpreter.debugger.UI;

import interpreter.debugger.DebugVirtualMachine;
import java.io.*;
import java.util.Iterator;

/**
* User interface for a basic .x debugger.
* @author David Webster
*/
public class DebugUserInterface {
    private static boolean stop = false;
    private static DebugVirtualMachine dvm;
   
/**
* Constructor
*/
    private DebugUserInterface() {}

/**
* Printer for function variables.
*/
    private static void displayVariables() {
        Iterator i = dvm.getVariableNames().iterator();
        Object temp;
        while(i.hasNext()){
           temp = i.next();
           System.out.print(temp + ": " + dvm.getValue(temp.toString()) + "\n");
        }
    }

/**
* Printer to display the .x source code
*/
    private static void displayXCode() {
        if (dvm.getStartingLine() < 0){
            System.out.println("===="+dvm.getCurrentFunction().toUpperCase()+"====");
        }else{
            for (int i = dvm.getStartingLine(); i <= dvm.getEndingLine(); i++) {
                if (dvm.isLineABreakPoint(i)){
                    //NOTE: (char)249 is extended ascii for a little solid square.
                    //I'm sorry if it comes out as something else on your machine
                    //I do not know how (or if its possible) to check if the machine
                    //that will run this program supports extended ascii.  If it doesnt
                    //It should just show up as an empty box, which is acceptable
                    //For my purposes as well.
                    System.out.print((char)249);
                }else
                    System.out.print(" ");

                if (i == dvm.getCurrentLine())
                    System.out.println(String.format("%1$4s %2$s", i + ")", dvm.getLineOfSourceCode(i)+ " <-----------"));
                else
                    System.out.println(String.format("%1$4s %2$s", i + ")", dvm.getLineOfSourceCode(i)));
            }
        }
    }

/**
* Setter for user requested step type. Gives focus back to the VM until another breakpoint
* is it or the program terminates.
* @param stepType The type of user requested movement to direct the VM how to execute.
*/
    private static void doUserRequestedStepType(String stepType) {
        dvm.setStepType(stepType);
        dvm.executeProgram();
        //When focus returns back to the UI, print out the sourcecode again.
        displayXCode();
    }

/**
* stops the debug virtual machine from executing, and stops the running loop in the UI
*/
    private static void exit() {
        dvm.exit();
        stop = true;
    }

/**
* Printer for the call stack
*/
    private static void printFunctionCalls() {
        dvm.printFunctionCalls();
    }

/**
* Prints help functions
*/
    private static void printHelp() {
        System.out.println( String.format("%1$-14s %2$s \n","\nCOMMAND"," DESCRIPTION") +
                            String.format("%1$-14s %2$s \n","-------------------------------------------------------------------------------","") +
                            String.format("%1$-14s %2$s \n","'?' or help", "Displays help") +
                            String.format("%1$-14s %2$s \n","bp <#(s)>", "Sets breakpoint(s) at the <#1>, <#2>,...etc-th line.") +
                            String.format("%1$-14s %2$s \n","cs", "Prints the *C*all *S*tack") +
                            String.format("%1$-14s %2$s \n","delbp <#(s)>", "Deletes breakpoint(s) at the <#1>, <#2>,...etc-th line.") +
                            String.format("%1$-14s %2$s \n","delbp", "Deletes all currently set breakpoints") +
                            String.format("%1$-14s %2$s \n","in", "Step *in*to a function") +
                            String.format("%1$-14s %2$s \n","listbp", "Lists currently set breakpoints") +
                            String.format("%1$-14s %2$s \n","out", "Steps *out* of a function") +
                            String.format("%1$-14s %2$s \n","over", "Step *over* the current expression") +
                            String.format("%1$-14s %2$s \n","q or quit", "Quits the program") +
                            String.format("%1$-14s %2$s \n","s", "Executes until the next set breakpoint") +
                            String.format("%1$-14s %2$s \n","sc", "Shows the *s*ource *c*ode of the current function") +
                            String.format("%1$-14s %2$s \n","trace ON/OFF", "Shows (or doesnt show) function calls caused by step/next") +
                            String.format("%1$-14s %2$s \n","vs", "List current program variables"));

    }

/**
* Processes the user's request
* @param userRequest The user's request
*/
    private static void processRequest(String userRequest) {
        String arguments = "";
        if (userRequest.split(" ").length > 1) arguments = userRequest.split(" ",2)[1];
        userRequest = userRequest.split(" ")[0];
        if (userRequest.startsWith("?")||userRequest.startsWith("help")) printHelp();
        else if (userRequest.matches("bp") && arguments.matches("[\\d+\\s*]+")) setBreakPoint(arguments);
        else if (userRequest.matches("cs")) printFunctionCalls();
        else if (userRequest.matches("delbp") && arguments.matches("[\\d+\\s*]+")) removeBreakPoint(arguments);
        else if (userRequest.matches("delbp")) removeBreakPoint();
        else if (userRequest.matches("in")) doUserRequestedStepType("into");
        else if (userRequest.matches("listbp")) showCurrentBreakPoints();
        else if (userRequest.matches("out")) doUserRequestedStepType("out");
        else if (userRequest.matches("over")) doUserRequestedStepType("over");
        else if (userRequest.matches("q") || userRequest.matches("quit")) exit();
        else if (userRequest.matches("s")) doUserRequestedStepType("step");
        else if (userRequest.matches("sc")) displayXCode();
        else if (userRequest.matches("trace") && arguments.matches("on|off")) setTraceONOFF(arguments);
        else if (userRequest.matches("vs")) displayVariables();
        else System.out.println("Sorry, I didnt understand the request. Enter '?' for help and a list of accepted commands.");
    }

/**
* Removes all current breakpoints.
* @param requestedBreakPoints The list of requested break points to clear.
*/
    private static void removeBreakPoint() {
    for (int i=1;i<=dvm.getSourceCodeLength();i++) {
        dvm.setBreakPoint(i-1, false);
        }
    }
/**
* Removes the break point(s) listed in 'requestedBreakPoints'
* @param requestedBreakPoints The list of requested break points to clear.
*/
    private static void removeBreakPoint(String requestedBreakPoints) {
        String buffer = "";
        String[] TentativeBreakPointLines = requestedBreakPoints.split(" ");

        for (int i=0;i<TentativeBreakPointLines.length;i++) {
            if (Integer.parseInt(TentativeBreakPointLines[i]) <= dvm.getSourceCodeLength() && Integer.parseInt(TentativeBreakPointLines[i]) >=1) {
                if (dvm.setBreakPoint(Integer.parseInt(TentativeBreakPointLines[i]) - 1, false))
                    buffer += TentativeBreakPointLines[i] + ", "; //BP was cleared correctly
                else
                    System.out.println("***Error*** Line #" + TentativeBreakPointLines[i] + " is an invalid location."); //BP clear failed
            } else
                System.out.println("***Error*** line #" + TentativeBreakPointLines[i] + " does not exist.");
        }
        //Chop off the last trailing ','
        if(buffer.length()-2>0)
            if(buffer.charAt(buffer.length()-2)==',')
                buffer = buffer.substring(0, buffer.length()-2);

        if (buffer.length()>=1)
            System.out.println("Break points removed (if present) from lines: " + buffer + ".");
        else
            System.out.println("***Error*** No breakpoints were removed.");
    }

/**
* Setter for the break point(s) listed in 'requestedBreakPoints'
* @param requestedBreakPoints The list of requested break points to set.
*/
    private static void setBreakPoint(String requestedBreakPoints) {
        String buffer = "";
        String[] TentativeBreakPointLines = requestedBreakPoints.split(" ");
        for (int i=0;i<TentativeBreakPointLines.length;i++) {
            //Check to see if the ith line is a valid bp or not.
            if (Integer.parseInt(TentativeBreakPointLines[i]) <= dvm.getSourceCodeLength() && Integer.parseInt(TentativeBreakPointLines[i]) >= 1) {
                if (dvm.setBreakPoint(Integer.parseInt(TentativeBreakPointLines[i]) - 1, true))
                    buffer += TentativeBreakPointLines[i] + ", "; //BP was set correctly
                else
                    System.out.println("***Error*** Line #" + TentativeBreakPointLines[i] + " is an invalid breakpoint location."); //BP failed
            } else
                System.out.println("***Error*** Line #" + TentativeBreakPointLines[i] + " does not exist.");
        }
        //Chop off the last trailing ','
        if(buffer.length()-2>0)
            if(buffer.charAt(buffer.length()-2)==',')
                buffer = buffer.substring(0, buffer.length()-2);

        if (buffer.length()>=1)
            System.out.println("Break point set: " + buffer);
        else 
            System.out.println("***Error*** No breakpoints were set.");
    }

/**
* Setter for a record of trace to be kept or not.
* @param traceONOFF "on" for TRUE, "off" for FALSE.
*/
    private static void setTraceONOFF(String traceONOFF) {
        if (traceONOFF.matches("off")){
            System.out.println("Trace is now off");
            dvm.setTraceONOFF(false);
        }
        else{
            System.out.println("Trace is now on");
            dvm.setTraceONOFF(true);
        }
    }

/**
* Lists the current break points
*/
    private static void showCurrentBreakPoints() {
        System.out.print("Current BreakPoints: ");
        for (int i=1;i<=dvm.getSourceCodeLength();i++)
            if (dvm.isLineABreakPoint(i)) System.out.print(i + " ");
        System.out.print("\n");
    }

/**
* Presents user with an interface to interact with.
* @param vm Instance of a DebugVirtualMachine to be used while debugging
*/
    public static void showUI(DebugVirtualMachine vm) {
        System.out.println("==============Debug Mode============== (\"?\" for help)");
        dvm = vm;
        displayXCode();
        //Start fetch-execute cycle
        while (dvm.isRunning()) {
            if(!stop){
                try {
                    //Consol prompt
                    System.out.print("('?' for help) ~>");
                    BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
                    processRequest(reader.readLine().toLowerCase());
                } catch (IOException e) {
                    System.out.println("Error in displayInterface(): "+e);
                }
            }else{
                break;
            }
        }
        System.out.println("Thank you for using the .x Debugger!\n===========End Execution============");
    }
}