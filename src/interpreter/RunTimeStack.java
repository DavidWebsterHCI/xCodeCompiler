//Interpreter Project
//413
//Section 1
//David Webster
package interpreter;
import java.util.*;

/**
* Runtime Stack. Holds data that is created by ByteCodes
* @author David Webster
*/
public class RunTimeStack {
    private Stack<Integer> frameLocation;
    private Vector<Integer> RTStack;
/**
* Constructor which creates a new run time stack (RTStack)
*/
    public RunTimeStack() {
        frameLocation = new Stack<Integer>();
        //the first frame will always be at RTStack 0, so add it.
        frameLocation.add(0);
        RTStack = new Vector<Integer>();
    }

/**
* Prints stack in the format of [] = frame,  [1,2,3] = frame with things in it.
*/
      public void dump(){
        System.out.print("[" + RTStack.elementAt(0));
        for (int i = 1; i < RTStack.size(); i++) {
            //If we are at a new frame, add brackets, else seperate by a ,
            if(frameLocation.contains(i)) System.out.print("] [" + RTStack.elementAt(i));
            else System.out.print("," + RTStack.elementAt(i));
        }
        System.out.println("]");
    }

/**
* Access element at 'EAindex' of the RunTime stack
* @param index Specified index of element
* @return Element located in the run time stack at 'EAindex' location
*/
    public int elementAt(int EAindex) {
        try{
            return RTStack.elementAt(EAindex);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("**** " + e);
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }
    }

/**
* Getter for the number of frames in the Runtime Stack
* @return Number of frames in the Runtime Stack
*/
    public int frames() {
        return frameLocation.size();
    }

/**
* pull the value at 'loadOffset' to the top of the stack for use
* @param loadOffset The index of the element to be loaded
* @return The loaded value
*/
    public int load(int loadOffset) {
        try{
            RTStack.add(RTStack.get(frameLocation.lastElement() + loadOffset));
            return RTStack.lastElement();
        }catch (EmptyStackException e){
            System.out.println("**** " + e);
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }catch(NoSuchElementException e){
            System.out.println("**** " + e);
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }catch(ArrayIndexOutOfBoundsException  e){
            System.out.println("**** " + e);
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }
    }

/**
* A new frame is created at an offset of 'index' in the run time stack.
* @param offset The start of the new frame
*/
    public void newFrameAt(int index) {
        frameLocation.add(index);
    }

/**
* peek at the top element of the stack without modifying it in any way.
* @return Element last entered into the stack
*/
    public int peek() {
        try{
            return RTStack.lastElement();
        }catch(NoSuchElementException e){
            System.out.println("**** " + e);
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }
    }

/**
* Shows the index of the start of the top frame in the stack
* @return  Index of the start of the top frame
*/
    public int peekFrame() {
        try{
            return frameLocation.peek();
        }catch(EmptyStackException e){
            System.out.println("**** " + e);
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }

    }

/**
* pop the top element of the stack and return it.
* @return The item that was just popped
*/
    public int pop() {
        //Dont allow a pop when there is nothing on the runtimestack.
        if((RTStack.size()-1) != -1)
            return RTStack.remove(RTStack.size()-1);
        else{
            System.out.println("Error: stack already empty--Cant pop.");
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }
    }

/**
* remove a full frame and replace it with the first value that was in the frame being removed
*/
    public void popFrame() {
        int value = RTStack.lastElement();
        int FLocation = frameLocation.pop();
        for (int i = RTStack.size(); i > FLocation; i--){
            try{
                RTStack.remove(FLocation);
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("**** " + e);
                //There was an error, so to prevent undefined behavior, exit.
                System.exit(-1);
            }catch(EmptyStackException e){
                System.out.println("**** " + e);
                //There was an error, so to prevent undefined behavior, exit.
                System.exit(-1);
            }
        }
        //add the saved top frame value back into the stack.
        RTStack.add(value);
    }

/**
* pushes an int onto the RunTime stack
* @param i Int value which is being pushed onto the stack
* @return The int value just pushed onto the stack
*/
    public int push(int i) {
        if(!RTStack.add(i)){
            System.out.println("Error: could not add '" + i + "' to the stack.");
            System.exit(-1);
        }
        return i;
    }

/**
* Adds an Integer object value to RTStack
* @param i Integer object which is being pushed onto the stack
* @return The Integer object just pushed onto the stack
*/
    public Integer push(Integer i) {
        if(!RTStack.add(i)){
            System.out.println("Error: could not add '" + i + "' to the stack.");
            System.exit(-1);
        }
        return i;
    }

/**
* Getter for the rune time stack's size
* @return The stack's size
*/
    public int size() {
        return RTStack.size();
    }

/**
* Takes the last value entered into the stack and stores it at 'storeOffset'
* @param storeOffset Location in which to store the removed value
* @return The stored value
*/
    public int store(int storeOffset) {
        try{
            RTStack.set((frameLocation.lastElement() + storeOffset), RTStack.lastElement());
            return RTStack.remove(RTStack.size()-1);
        }catch (EmptyStackException e){
            System.out.println("**** " + e);
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }catch(NoSuchElementException e){
            System.out.println("**** " + e);
            //There was an error, so to prevent undefined behavior, exit.
            System.exit(-1);
            return -1;
        }
    }
}