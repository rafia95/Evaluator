
package Collections;

import java.util.ArrayList;

/**
 * This class behaves like Stack in java.util.
 * It has push,peek and pop methods implemented in it.
 * 
 * @author Rafia
 * @version 30/11/2016
 */
public class Stack<T> {
    private ArrayList<T> t;
    private int stackPointer;

    public Stack() {
        t = new ArrayList<T>();
        stackPointer = -1;  
    }

    //delete before submitting
    public int getSize() {
        System.out.println("stack pointer " + stackPointer);
        return t.size();
    }
    public boolean isEmpty(){
        if(stackPointer == -1)
            return true;
        else return false;
    }
    //shows the top element in stack
    public T peek(){
        if(!isEmpty())
        {
            return t.get(stackPointer ); //stackPointer keeps check of present location of element
        }
        else return null; //throw exceptions
    }
    
    public boolean push(T s){
       
            t.add(s);
            stackPointer++; //increments the stackPointer after adding an element
            return true;
     
    }
    //returns ghe value from the top of stack
    public T pop(){
        
         if(!isEmpty())
        {
            T element = t.get(stackPointer); //stackPointer keeps check of present location of element
            //delete the element from stack 
            t.remove(stackPointer);
            stackPointer--;
            return element;
        }
         else
             return null;
    }


    @Override
    public String toString() {
        return "Stack{" + "t=" + t + ", stackPointer=" + stackPointer + '}';
    }
}