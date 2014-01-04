
package interpreter.debugger;

import java.util.*;


/**
* This .java stores functions' variable and line information 
* @author David Webster
*/
public class FunctionEnvironmentRecord {
    private int currentLine, startLine, endLine;
    private DebugSymbolTable table;
    private String functionName;

    
/**
* Function Environment Record constructor.
*/
    public FunctionEnvironmentRecord() {
        table = new DebugSymbolTable();
        table.beginScope();
    }



/**
* Dumps the FunctionEvironmentRecord this method is utilized by the unit test and debug
*/
    public void dump(){
    Iterator iterator = this.getElements().iterator();
    Object temp;
    System.out.print("(<");
    for(int i=0;i<getElements().size();i++)
    {
        temp = iterator.next();
        if(i!=0) System.out.print(",");
        System.out.print(temp.toString() +"/"+ this.getElementOffset(temp.toString()));
    }
    System.out.print(">,");
    if (this.getFunctionName() != null)
        System.out.print(this.getFunctionName()+",");
    else
        System.out.print("-,");

    if (this.getStartLine() != 0)
        System.out.print(this.getStartLine()+",");
    else
        System.out.print("-,");

    if (this.getEndLine() != 0)
        System.out.print(this.getEndLine()+",");
    else
        System.out.print("-,");

    if (this.getCurrentLine() != 0)
        System.out.print(this.getCurrentLine());
    else
        System.out.print("-");

    System.out.println(")");
}

/**
* puts a new variable into the DebugSymbolTable
* @param identification Variable's Identification
* @param offset variable's offset
*/
    public void enterVariableRecord(String identification, int offset) {
        table.put(identification, offset);
    }

/**
* Getter for the function's current line
* @return currentLine number
*/
    public int getCurrentLine() {
        return currentLine;
    }

/**
* Getter for the runtime offset where 'element' is
* @param element The target element's identifier
* @return The target element's offset
*/
    public int getElementOffset(String element) {
        return table.get(element);
    }

/**
* Getter for all the elements that are in the function record
* @return Function record elements
* @see interpreter.debugger.DebugSymbolTable#keys()
*/
    public Set<String> getElements() {
        return table.keys();
    }

/**
* Getter for the function's ending line
* @return endLine number
*/
    public int getEndLine() {
        return endLine;
    }

/**
* Getter for functionName
* @return functionName
*/
    public String getFunctionName() {
        return functionName;
    }

/**
* Getter for the function's starting line
* @return startLine number
*/
    public int getStartLine() {
        return startLine;
    }
    
/**
*=====Unit test with hard-coded test case=====
*BeginScope,
*Function g 1 20,
*Line 5,
*Enter a 4,
*Enter b 2,
*Enter c 7,
*Enter a 1,
*Pop 2,
*Pop 1
*/
    public static void main(String args[]){
        System.out.println("\n=====Unit Test for Debug Milestone #2=====\n");
        FunctionEnvironmentRecord fctEnvRecord = new FunctionEnvironmentRecord(); //"BS (begin scope)" is done in the constructor
        String a="a",b="b",c="c";

        fctEnvRecord.dump();
        fctEnvRecord.setFunctionName("g");
        fctEnvRecord.setStartLine(1);
        fctEnvRecord.setEndLine(20);
        fctEnvRecord.dump();
        fctEnvRecord.setCurrentLine(5);
        fctEnvRecord.dump();
        fctEnvRecord.enterVariableRecord(a,4);
        fctEnvRecord.dump();
        fctEnvRecord.enterVariableRecord(b,2);
        fctEnvRecord.dump();
        fctEnvRecord.enterVariableRecord(c,7);
        fctEnvRecord.dump();
        fctEnvRecord.enterVariableRecord(a,1);
        fctEnvRecord.dump();
        fctEnvRecord.pop(2);
        fctEnvRecord.dump();
        fctEnvRecord.pop(1);
        fctEnvRecord.dump();

    }

/**
* Removes 'valToRemove' items
* @param valToRemove Number of entries to remove
*/
    public void pop(int valToRemove) {
        table.popElements(valToRemove);
    }

/**
* Setter for the current line
* @param line Line number
*/
    public void setCurrentLine(int line) {
        currentLine = line;
    }

/**
* Setter for ending line
* @param line Line number
*/
    public void setEndLine(int line) {
        endLine = line;
    }

/**
* Setter for the function name
* @param FName Function name
*/
    public void setFunctionName(String FName) {
        functionName = FName; 
    }
    
/**
* Setter for starting line.
* @param line Line number
*/
    public void setStartLine(int line) {
        startLine = line;
    }





















}

