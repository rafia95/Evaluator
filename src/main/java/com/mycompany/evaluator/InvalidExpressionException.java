/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.evaluator;

/**
 *
 * @author 1410926
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
