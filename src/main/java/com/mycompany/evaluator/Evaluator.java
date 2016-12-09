/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.evaluator;

import Collections.Queue;
import Collections.Stack;

/**
 * This class should handle the basic functionality such as getting infix
 * expression and turning it to postfix using stacks and queues and then
 * evaluating it.
 *
 * @author Rafia
 */
public class Evaluator {
    private Stack<String> operatorStack;
    private Queue<String> numberQueue;
    private Queue<String> holdingQueue;
    private Queue<String> resultQueue;
    /**
     * Default constructor 
     */
    public Evaluator(){}
    public Queue<String> setHoldingQueue(Queue<String> holdingQueue) throws InvalidExpressionException{
        this.holdingQueue = holdingQueue;
        this.operatorStack = new Stack<>();
        this.numberQueue = new Queue<>();
        checkValidity();
        return resultQueue;
    }
    
    public Evaluator(Queue<String> holdingQueue) throws InvalidExpressionException
    {
        this.holdingQueue = holdingQueue;
        Stack<String> operatorStack = new Stack<>();
        Queue<String> numberQueue = new Queue<>();
        checkValidity();
    }
  
    public void checkValidity() throws InvalidExpressionException {

        //checks if the equation data is valid
        boolean valid = validate(holdingQueue);
        if (valid) {
            convertInfixToPostfix();
        } else {
            throw new InvalidExpressionException("The equation is not properly formatted: " + holdingQueue);
        }

    }

    public  void convertInfixToPostfix() throws InvalidExpressionException {
        String isNumber = "^[-+]?[0-9]*\\.?[0-9]+$";
        String isOperator = "[*+-/]";
        int parenthesisCtr = 0;
        //while loop until the queue holding all the values are processed
        while (!(holdingQueue.isEmpty())) {
            //if the queue has a number, then the number if pushed to the number Queue
            if (holdingQueue.peek().matches(isNumber)) {
                System.out.println("peeked num " +holdingQueue.peek() );
                processNumber();
                //if the next element is an operator
            } else if (holdingQueue.peek().matches(isOperator)) {
                processOperator(holdingQueue, operatorStack, numberQueue);
            } else if (holdingQueue.peek().equals("(")) {
                 processOpeningBracket(operatorStack, holdingQueue, parenthesisCtr);
                        parenthesisCtr++;
                System.out.println("ctr op " + parenthesisCtr);
            } //keep popping the elmeents to the numberQueue until hit (
            else if (holdingQueue.peek().equals(")")) {
                 processClosingBracket(holdingQueue, operatorStack, parenthesisCtr, numberQueue);
                        parenthesisCtr++;

                System.out.println("ctr " +parenthesisCtr );
            } else {
                throw new InvalidExpressionException("The equation contains something other than number,op and parentheses : " + holdingQueue);
            }
        }
        
        while (!operatorStack.isEmpty()) {
            System.out.println("whileee");
            numberQueue.push(operatorStack.pop());
        }
        if (parenthesisCtr % 2 != 0) {
            throw new InvalidExpressionException("The equation does not have equal number of parentheses: " + parenthesisCtr);
        }
        System.out.println("stack string rep : " + operatorStack.toString());
        System.out.println("queue string rep : " + numberQueue.toString());
        evaluatePostFixProvidedExpression(numberQueue);
    }

    //make it unstatic
    private  boolean isIncomingValueGreater(String inStackValue, String holdingQueueValue) {
        System.out.println("instack val------" + inStackValue + "------- " + holdingQueueValue);
        boolean isGreater;
        int valueToInsert = switchForOperators(holdingQueueValue);
        int valueInStack = switchForOperators(inStackValue);

        if (valueToInsert <= valueInStack) {
            isGreater = false;
        } else {
            isGreater = true; //the value to insert is greater than value already on stack
        }
        return isGreater;

    }

    private  int switchForOperators(String valToCompare) {
        int val = -1;
        switch (valToCompare) {
            case "*":
                val = 1;
                break;
            case "/":
                val = 1;
                break;
            case "+":
                val = 0;
                break;
            case "-":
                val = 0;
                break;
        }
        return val;
    }

    private  void compareSigns(String peekedValInStack, String peekedValInHoldingQueue, Queue<String> holdingQueue, Stack<String> operatorStack, Queue<String> numberQueue) {
        System.out.println("in compare signs");
        System.out.println("stack op " + peekedValInStack + "   op in queue "+ peekedValInHoldingQueue );
        
        boolean isGreater = isIncomingValueGreater(peekedValInStack, peekedValInHoldingQueue); //if true, holding queue has greater value
        System.out.println("is queue greater than stack " + isGreater);
        String queueVal = "";
        //if value is bigger then put on stack
        //if equal or less, then put on queue with numbers
        if (isGreater) {
            queueVal = holdingQueue.pop();
            operatorStack.push(queueVal);

        }
        //if queue value is smaller/equal than stack value ,
        // then send the bigger values to the queue holding new numbers
        else {
            queueVal = holdingQueue.pop();
            System.out.println("operator from queue " + queueVal);

            String number = holdingQueue.peek();
            System.out.println("number " + number);
            
                //take out bigger op, and put it in queue
            String greaterOperator = operatorStack.pop();
            System.out.println("number2 " + greaterOperator);
            numberQueue.push(greaterOperator);
            //keep checking
             while (!operatorStack.isEmpty() && operatorStack.peek()!=null && !operatorStack.peek().equals("(") && !isIncomingValueGreater(operatorStack.peek(), queueVal)) {
                    System.out.println("operatorStack.peek() " + operatorStack.peek());
                    String stackOp = operatorStack.pop();
                    numberQueue.push(stackOp);
                }
            operatorStack.push(queueVal);
            if (!number.equals("(")) {
                 holdingQueue.pop();
                 numberQueue.push(number);
            }
            System.out.println("stack "+operatorStack);
           
        }
    }

    public  Queue<String> evaluatePostFixProvidedExpression(Queue<String> infixQueue) {
        Stack<String> numberStack = new Stack<>();

        String isNumber = "^[-+]?[0-9]*\\.?[0-9]+$";
        String isOperator = "[*+-/]";
        while (!(infixQueue.isEmpty())) {
            if (infixQueue.peek().matches(isNumber)) {
                System.out.println("number stack " + numberStack);
                numberStack.push(infixQueue.pop());

            } else if (infixQueue.peek().matches(isOperator)) {
                String secondNum = numberStack.pop();
                String firstNum = numberStack.pop();
                System.out.println("secondNum  " + secondNum);
                System.out.println("fisrt num " + firstNum);

                double i2 = Double.parseDouble(secondNum);
                double i1 = Double.parseDouble(firstNum);

                String operator = infixQueue.pop();
                double result = -1;
                if (operator.equals("*")) {
                    result = i1 * i2;
                } else if (operator.equals("+")) {
                    result = i1 + i2;
                } else if (operator.equals("-")) {
                    result = i1 - i2;
                } else if (operator.equals("/")) {
                    result = i1 / i2;
                }
                result = Math.round(result * 100.0) / 100.0;
                System.out.println("resuult " + result);
                numberStack.push(result + "");
            }
        }
        resultQueue = new Queue<>();
        resultQueue.push(numberStack.pop());
        return resultQueue;
    }

    private  boolean validate(Queue<String> holdingQueue) {
        String isNumber = "^[-+]?[0-9]*\\.?[0-9]+$";

        //the equation should always be odd in size
        if (holdingQueue.getSize() % 2 == 0) {
            return false;
        }
        //size should be atleast 3
        if (holdingQueue.getSize() < 3) {
            return false;
        }
        if (!holdingQueue.peek().matches(isNumber) && !holdingQueue.peek().equals("(")) {
            return false;
        }
        return true;
    }

    private  void processNumber() throws InvalidExpressionException {
        System.out.println("IN PROCESS NUBER");
        String isOperator = "[*+-/]";
        System.out.println("----------" +holdingQueue.peek() );
        numberQueue.push(holdingQueue.pop());
        //make sure next element has to be operator or parentheseis,otherwise throw exception
        if (!(holdingQueue.isEmpty())) {
            if (!holdingQueue.peek().matches(isOperator) && !holdingQueue.peek().equals(")")) {
                throw new InvalidExpressionException("The equation does not have a operator or ) after number : " + holdingQueue.peek());
            }
        }
    }

    private  void processOperator(Queue<String> holdingQueue, Stack<String> operatorStack, Queue<String> numberQueue) throws InvalidExpressionException {
        String isNumber = "^[-+]?[0-9]*\\.?[0-9]+$";
                String isOperator = "[*+-/]";

        String peekedValInHoldingQueue = holdingQueue.peek();
        System.out.println("should be operatr " +peekedValInHoldingQueue );
        String peekedValInStack = "";
        //if stack is empty , no need to check 
        if (operatorStack.isEmpty()) {
            operatorStack.push(holdingQueue.pop());
        } //stack isnt empty, compare values
        else {
            peekedValInStack = operatorStack.peek();
            System.out.println("the operator in stack " + peekedValInStack);
            if (!peekedValInStack.equals("(")) {
                System.out.println("not a ( so sending to compareSigns");
                compareSigns(peekedValInStack, peekedValInHoldingQueue, holdingQueue, operatorStack, numberQueue);
            }
            else{
            operatorStack.push(holdingQueue.pop());
            }
        }
        System.out.println("queue in process op " + numberQueue);
        System.out.println("holding queue  " + holdingQueue);
        if (!(holdingQueue.isEmpty())) {
            if(! holdingQueue.peek().matches(isOperator))
            {
            if (!holdingQueue.peek().matches(isNumber) && !holdingQueue.peek().equals("(") && !holdingQueue.peek().equals(")")) {
                throw new InvalidExpressionException("The equation does not have number or ( or ) after operator: " + holdingQueue.peek());
            }
            }
        }
    }

    private  void  processOpeningBracket(Stack<String> operatorStack, Queue<String> holdingQueue, int parenthesisCtr) throws InvalidExpressionException {
        String isNumber = "^[-+]?[0-9]*\\.?[0-9]+$";
        operatorStack.push(holdingQueue.pop());
        //only number can be present after ( or another (
        if (!(holdingQueue.isEmpty())) {
            if (!holdingQueue.peek().matches(isNumber) && !holdingQueue.peek().equals("(")) {
                throw new InvalidExpressionException("The equation does not have number or ( after (: " + holdingQueue);
            }
        }
    }

    private  void processClosingBracket(Queue<String> holdingQueue, Stack<String> operatorStack, int parenthesisCtr, Queue<String> numberQueue) throws InvalidExpressionException {
                
                String isOperator = "[*+-/]";
System.out.println("queue " + numberQueue);
        String op = holdingQueue.pop(); //should pop )
        //check until the opening parenthesis
        while (!operatorStack.peek().equals("(")) {
            String v = operatorStack.pop();
                        System.out.println("while its ( " + v);
            numberQueue.push(v);
        }
        String var = operatorStack.pop();
        if (!(holdingQueue.isEmpty())) {
            if (!holdingQueue.peek().matches(isOperator) && !holdingQueue.peek().equals(")")) {
                throw new InvalidExpressionException("The equation does not have operator or ) after ): " + holdingQueue);
            }
        }
      
    }

}
