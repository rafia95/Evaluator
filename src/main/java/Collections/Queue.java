/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Collections;

import java.util.ArrayList;


/**
 *
 * @author Rafia
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
  /*   public Queue(int size) {
        t = new ArrayList<T>(size);
        startPointer = -1;  
        endPointer = -1;
        this.size=size;
    }
*/
    //push
    // adds element to the end and increments the end pointer
    public boolean push(T s){
        boolean pushed = false;
        if (isEmpty())
        {
            t.add(s);
            startPointer++; // = 0
            endPointer = startPointer + 1;   // =0
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
       
    //isfull
  /*  private boolean isFull(){
        if(stackPointer >= size)
            return true;
        return false;
    }
  */  
    //pop
    //returns the first value in queue 
    public T pop(){
        
         if(!isEmpty())
        {
            T element = t.get(startPointer); //stackPointer keeps check of present location of element
            //delete the element from stack 
            //t.remove(0);
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
