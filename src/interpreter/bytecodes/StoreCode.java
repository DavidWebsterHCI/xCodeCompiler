//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;

/**
* Puts a value into the run time stack.
* @author David Webster
*/
public class StoreCode extends ByteCode {
     private String indentifier="";
     private int index;


    //==========================Constructor============================
    public StoreCode(){
    }

    public void execute(VirtualMachine vm) {
        vm.runStackStore(index);
    }

    public String getArgs() {
        return Integer.toString(index)+indentifier;
    }

    public void init(String args) {
        String temp[] = args.split("\\s");
        indentifier = " "+temp[1];
        index = Integer.parseInt(temp[0]);
    }  
}