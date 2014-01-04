//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;
/**
* pops two values, and compares them with an operator of some sort.
* valid operators: /*-+ == != > < >= <= | &
* @author David Webster
*/
public class BopCode extends ByteCode {
    private String operator;
    //==========================Constructor============================
    public BopCode(){
    }

    public void execute(VirtualMachine vm) {
        //First element poped is the top element (this order is important)
        int topInt = vm.runStackPop();
        int botInt = vm.runStackPop();
        vm.runStackPush(executeBop(topInt,botInt,operator));
    }

    private int executeBop(int topInt, int botInt, String operator) {
        if (operator.equals("+"))
            return botInt + topInt;
        if (operator.equals("-"))
            return botInt - topInt;
        if (operator.equals("/"))
            return botInt / topInt;
        if (operator.equals("*"))
            return botInt * topInt;
        if (operator.equals("=="))
            if (botInt == topInt) return 1;
            else return 0;
        if (operator.equals("!="))
            if (botInt != topInt) return 1;
            else return 0;
        if (operator.equals(">"))
            if (botInt > topInt) return 1;
            else return 0;
        if (operator.equals("<"))
            if (botInt < topInt) return 1;
            else return 0;
        if (operator.equals(">="))
            if (botInt >= topInt) return 1;
            else return 0;
        if (operator.equals("<="))
            if (botInt <= topInt) return 1;
            else return 0;
        if (operator.equals("|"))
            if ((botInt > 0) || (topInt > 0)) return 1;
            else return 0;
        if (operator.equals("&"))
            if ((botInt > 0) && (topInt > 0)) return 1;
            else return 0;
        return -1; //error flag, program should never make it this far.
    }

    public String getArgs() {
        return operator;
    }

    public void init(String args) {
        operator = args;
    }
}