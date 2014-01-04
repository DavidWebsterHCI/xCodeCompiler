
package interpreter.debugger;

/**
* Contains a line of .x code and breakpoint information
* @author David Webster
*/
public class xLine {
    private String xCode;
    private boolean isBP;

/**
* Constructor.  Stores a line of .x code and a break point flag
* @param xCode A line of .x Code
* @param isBP TRUE means that this line is a breakpoint.  FALSE it isn't.
*/
    public xLine(String xCode, boolean isBP) {
        this.isBP = isBP;
        this.xCode = xCode;
    }

/**
* Getter for the line of .x code
* @return line of .x code
*/
    public String getxLine() {
        return xCode;
    }

/**
* Setter for a breakpoint.
* NOTE: to remove a breakpoint, just call this function with a "false" flag.
* @param ibp TRUE set breakpoint. FALSE remove breakpoint.
*/
    public void setBreakPoint(boolean ibp) {
        isBP = ibp;
    }

/**
* Flag for break points
* @return TRUE .x line has a breakpoint. FALSE doesn't have a breakpoint
*/
    public boolean isBreakPoint() {
        return isBP;
    }


}