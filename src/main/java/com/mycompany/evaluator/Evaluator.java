
package com.mycompany.evaluator;

import Collections.Queue;
import Collections.Stack;

/**
 * This class handles the basic functionality such as getting infix
 * expression and turning it to postfix using stacks and queues and then
 * evaluating it.
 *
 * @author Rafia
 * @version 09/12/2016
 */
public class Evaluator {
    private Stack<String> operatorStack; //stack for keeping operators
    private Queue<String> numberQueue; // queue where operands and opertors are inserted
    private Queue<String> holdingQueue; //initial queue with all the elements
    private Queue<String> resultQueue; // holds the calculated results
    private String isNumber ; //regex to check for numbers
    private String isOperator; //regex to check for operands
    /**
     * Default constructor 
     */
    public Evaluator(){
        this.operatorStack = new Stack<>();
        this.numberQueue = new Queue<>();
        isNumber = "^[-+]?[0-9]*\\.?[0-9]+$";
        isOperator = "[*+-/]";}
    
    /**
     * This method is used to set the value of holding queue.
     * It checks if the incoming queue is valid, if not then throws exception.
     * @param holdingQueue  contains the incoming expression
     * @return result queue
     * @throws InvalidExpressionException  when expression is not properly formatted
     */
    public Queue<String> setHoldingQueue(Queue<String> holdingQueue) throws InvalidExpressionException{
        this.holdingQueue = holdingQueue;
        
        checkValidity();
        return resultQueue;
    }
    
  /**
   * Checks for valid expression, if it is valid, then invokes the method to convert 
   * expression to post fix
   * @throws InvalidExpressionException when expression is not properly formatted
   */
    private void checkValidity() throws InvalidExpressionException {

        //checks if the equation data is valid
        boolean valid = validate();
        if (valid) {
            convertInfixToPostfix();
        } else {
            throw new InvalidExpressionException("The equation is not properly formatted: " + holdingQueue);
        }

    }

    /**
     * This method converts the infix expression into postfix by processing each element
     * in infix expression and it also checks for the invalid data on the go.
     * @throws InvalidExpressionException when expression is not properly formatted
     */
    public  void convertInfixToPostfix() throws InvalidExpressionException {
       
        int parenthesisCtr = 0;
        //while loop until the queue holding all the values are processed
        while (!(holdingQueue.isEmpty())) {
            //if the queue has a number, then the number if pushed to the number Queue
            if (holdingQueue.peek().matches(isNumber)) {
                processNumber();
                //if the next element is an operator
            } else if (holdingQueue.peek().matches(isOperator)) {
                processOperator();
            } else if (holdingQueue.peek().equals("(")) {
                 processOpeningBracket();
                        parenthesisCtr++;
            } //keep popping the elmeents to the numberQueue until hit (
            else if (holdingQueue.peek().equals(")")) {
                 processClosingBracket();
                        parenthesisCtr++;
            } else {
                throw new InvalidExpressionException("The equation contains something other than number,op and parentheses : " + holdingQueue);
            }
        }
        while (!operatorStack.isEmpty()) {
            numberQueue.push(operatorStack.pop());
        }
        // checks for even number of parenthesis
        if (parenthesisCtr % 2 != 0) {
            throw new InvalidExpressionException("The equation does not have equal number of parentheses: " + parenthesisCtr);
        }
        evaluatePostFixProvidedExpression(numberQueue);
    }

    /**
     * this method is used to compare the operator in holding queue
     * with the operator in stack 
     * @param inStackValue  the operator already present in stack
     * @param holdingQueueValue  the operator in queue
     * @return true if queue value is greater
     */
    private  boolean isIncomingValueGreater(String inStackValue, String holdingQueueValue) {
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

    /**
     * this method provides an int value according operator precedence order
     * @param valToCompare  the operator
     * @return  int according to incoming value
     */
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

    /**
     * this method compare operators and if queue operator is smaller, it
     * pops the stack operators and pushes them to new queue i.e. number queue.
     * if queue operator is greater, then the operator is simply placed in stack
     * @param peekedValInStack  operator value in stack
     * @param peekedValInHoldingQueue   operator in initial queue
     * @param numberQueue  operator in new queue
     */
    private  void compareOperators(String peekedValInStack, String peekedValInHoldingQueue,  Queue<String> numberQueue) {
        boolean isGreater = isIncomingValueGreater(peekedValInStack, peekedValInHoldingQueue); //if true, holding queue has greater value
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
            String number = holdingQueue.peek();            
            //take out bigger op, and put it in queue
            String greaterOperator = operatorStack.pop();
            numberQueue.push(greaterOperator);
            //keep checking
             while (!operatorStack.isEmpty() && operatorStack.peek()!=null && !operatorStack.peek().equals("(") && !isIncomingValueGreater(operatorStack.peek(), queueVal)) {
                    String stackOp = operatorStack.pop();
                    numberQueue.push(stackOp);
                }
            operatorStack.push(queueVal);
            if (!number.equals("(")) {
                 holdingQueue.pop();
                 numberQueue.push(number);
            }
        }
    }

    /**
     * It evaluates the infix expression.
     * @param infixQueue the queue holding infix expression
     * @return 
     */
    public  Queue<String> evaluatePostFixProvidedExpression(Queue<String> infixQueue) {
        Stack<String> numberStack = new Stack<>();

        while (!(infixQueue.isEmpty())) {
            if (infixQueue.peek().matches(isNumber)) {
                numberStack.push(infixQueue.pop());
            } else if (infixQueue.peek().matches(isOperator)) {
                String secondNum = numberStack.pop();
                String firstNum = numberStack.pop();
               
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
                numberStack.push(result + "");
            }
        }
        resultQueue = new Queue<>();
        resultQueue.push(numberStack.pop());
        return resultQueue;
    }

    /**
     * Validates the incoming queue to have odd length,
     * size more than 3 at least and first element should be 
     * a number or ( .
     * @return 
     */
    private  boolean validate() {

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

    /**
     * Processes the number in holdingQueue, pushes it to numberQueue,
     * and makes sure next element is not a number or ( . 
     * 
     * @throws InvalidExpressionException when expression is not properly formatted
     */
    private  void processNumber() throws InvalidExpressionException {
        numberQueue.push(holdingQueue.pop());
        //make sure next element has to be operator or parentheseis,otherwise throw exception
        if (!(holdingQueue.isEmpty())) {
            if (!holdingQueue.peek().matches(isOperator) && !holdingQueue.peek().equals(")")) {
                throw new InvalidExpressionException("The equation does not have a operator or ) after number : " + holdingQueue.peek());
            }
        }
    }

     /**
     * Processes the operator in holdingQueue, pushes it to operatorStack
     * 
     * @throws InvalidExpressionException when expression is not properly formatted
     */
    private  void processOperator() throws InvalidExpressionException {
        String peekedValInHoldingQueue = holdingQueue.peek();
        String peekedValInStack = "";
        //if stack is empty , no need to check 
        if (operatorStack.isEmpty()) {
            operatorStack.push(holdingQueue.pop());
        } //stack isnt empty, compare values
        else {
            peekedValInStack = operatorStack.peek();
            if (!peekedValInStack.equals("(")) {
                compareOperators(peekedValInStack, peekedValInHoldingQueue,numberQueue);
            }
            else{
            operatorStack.push(holdingQueue.pop());
            }
        }
        if (!(holdingQueue.isEmpty())) {
            if(! holdingQueue.peek().matches(isOperator))
            {
            if (!holdingQueue.peek().matches(isNumber) && !holdingQueue.peek().equals("(") && !holdingQueue.peek().equals(")")) {
                throw new InvalidExpressionException("The equation does not have number or ( or ) after operator: " + holdingQueue.peek());
            }
            }
        }
    }

    /**
     * processes the opening bracket in the infix expression. 
     * it makes sure next element is not a number or (. 
     * @throws InvalidExpressionException when expression is not properly formatted
     */
    private  void  processOpeningBracket() throws InvalidExpressionException {
        operatorStack.push(holdingQueue.pop());
        //only number can be present after ( or another (
        if (!(holdingQueue.isEmpty())) {
            if (!holdingQueue.peek().matches(isNumber) && !holdingQueue.peek().equals("(")) {
                throw new InvalidExpressionException("The equation does not have number or ( after (: " + holdingQueue);
            }
        }
    }
    /**
     * processes the closing bracket in the infix expression. 
     * it makes sure next element is not a operator or ) . 
     * @throws InvalidExpressionException when expression is not properly formatted
     */
    private  void processClosingBracket() throws InvalidExpressionException {
                
        String op = holdingQueue.pop(); //should pop )
        //check until the opening parenthesis
        while (!operatorStack.peek().equals("(")) {
            String v = operatorStack.pop();
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
