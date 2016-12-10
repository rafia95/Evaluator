
package com.mycompany.evaluator;

/**
 * This is a custom exception class.
 * It is being used when invalid expression is provided
 * to a class.
 * 
 * @author Rafia
 * @version 08/12/2016
 */
public class InvalidExpressionException extends Exception{
    	private static final long  serialVersionUID = 4205171L;

    public InvalidExpressionException(){}
    
    public InvalidExpressionException (String message) {
        super (message);
    }

    public InvalidExpressionException (Throwable cause) {
        super (cause);
    }
}
