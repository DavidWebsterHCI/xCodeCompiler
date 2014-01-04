//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* prints last run time stack element
* @author David Webster
*/
public class WriteCode extends ByteCode {
    //==========================Constructor============================
    public WriteCode(){
    }

    public void execute(VirtualMachine vm) {
        System.out.println(vm.runStackPeek());
    }

    public String getArgs() {
        return "";
    }
    
    //Nothing to init so empty method
    public void init(String args){
    }

    

    
}