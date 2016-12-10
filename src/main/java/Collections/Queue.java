
package Collections;

import java.util.ArrayList;


/**
 * This class behaves like Queue in java.util.
 * It has push,peek and pop methods implemented in it.
 * 
 * @author Rafia
 * @version 30/11/2016
 */
public class Queue<T> {
    private ArrayList<T> t;
    private int startPointer;
    private int endPointer;
        
    // confirm
    public Queue(){
        t = new ArrayList<T>();
        startPointer = -1;  
        endPointer = -1;
    }
    public int getSize(){
      
        return t.size();
    }
 
    // adds element to the end and increments the end pointer
    public boolean push(T s){
        boolean pushed = false;
        if (isEmpty())
        {
            t.add(s);
            startPointer++; 
            endPointer = startPointer + 1;  
            pushed = true;

        }
        else
        {
            t.add(s);
            endPointer++; //increments the stackPointer after adding an element
            pushed = true;
        }
        return pushed;
    }
       
    
    //returns the first value in queue 
    public T pop(){
        
         if(!isEmpty())
        {
            T element = t.get(startPointer); //stackPointer keeps check of present location of element
          
            startPointer++; //increments the start pointer so that next element popped will from next position
            return element;
        }
         // when the queue is empty and no element to pop
         
         else
             //throw new IllegalArgumentException();
             return null;
    } 
    //isempty
    public boolean isEmpty(){
        if(startPointer == endPointer)
            return true;
        else return false;
    }
     //shows the top element in queue
    public T peek(){
        if(!isEmpty())
        {
            return t.get(startPointer);
        }
        else return null; //throw exceptions
    }

    @Override
    public String toString() {
        return "Queue{" + "t=" + t + ", startPointer=" + startPointer + ", endPointer=" + endPointer + '}';
    }
}
