//Interpreter Project
//413
//Section 1
//David Webster
package interpreter.bytecodes;
import interpreter.VirtualMachine;


/**
* ByteCode parent class
* @author David Webster
*/
    public abstract class ByteCode {

/**
* Executes a byte code command
* @param vm VM instance which will execute the byte codes
*/
    public abstract void execute(VirtualMachine vm);

/**
* Gets a byte code's arguments
* @return byte code's arguments
*/
    public abstract String getArgs();

/**
* Dynamically Gets the name of the object
* @return ByteCode Name
*/
    public String getName() {
        return this.getClass().getName().replaceFirst("interpreter.bytecodes.", "").replaceAll("Code", "").toUpperCase();
    }

/**
* initiate a byte code command
* @param args Arguments 
*/
    public abstract void init(String args);
}