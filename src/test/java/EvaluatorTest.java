
import Collections.Queue;
import com.mycompany.evaluator.Evaluator;
import com.mycompany.evaluator.InvalidExpressionException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Rafia
 */
@RunWith(Parameterized.class)
public class EvaluatorTest {

    private Queue<String> holdingQueue;
    private String actualResult ;
    private String expectedResult;
    private  Evaluator eval;
    
    public EvaluatorTest( String[] vals,String expectedResult) throws InvalidExpressionException {
        this.expectedResult=expectedResult;
        this.eval = new Evaluator();
        holdingQueue= new Queue<>();

        for(String val : vals)
        {
            holdingQueue.push(val);
        }
        
        
    }
    private void setValues() throws InvalidExpressionException{
        Queue<String> q1= new Queue<>();
        q1.push("3");
        q1.push("*");
        q1.push("2"); //3*2 + 8/4 - 9 -> 6+2-9 = -1
        q1.push("+");
        q1.push("(");
        q1.push("8");
        q1.push("/");
        q1.push("4");
        q1.push("-");
        q1.push("9");
        q1.push(")");
        Queue<String> res = eval.setHoldingQueue(q1);
        double r = Double.parseDouble(res.pop());
        actualResult = r+"";
    //   expectedResult= -1.0 + "";
    }
    @Parameters
    public static Collection<Object[]> data() {
      
        return Arrays.asList(new Object[][] {
         {new String[]{"3","*","2"}, "6.0"},
         {new String[]{"3","*","2","+","(","8","/","4","-","9",")"}, "-1.0"},
         {new String[]{"(","5","+","3",")","*","12","/","3"}, "32.0"},
         {new String[]{"(","(","5","+","3",")","*","12",")","/","3"}, "32.0"},
         {new String[]{"6","/","12","*","18","+","99"}, "108.0"},
         {new String[]{"6","/","12","*","(","(","18","+","99",")",")"}, "58.5"},
         {new String[]{"(","(","22","-","2",")","+","5",")","*","(","5","-","3",")"}, "50.0"}

        });
    }
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
   @Test
   public void testEvaluator() throws InvalidExpressionException {
      System.out.println("Parameterized Number is : " );
      Queue<String> resQ = eval.setHoldingQueue(holdingQueue);
     double result = Double.parseDouble(resQ.pop());
     String r = result+"";
    assertEquals(expectedResult,r);
   }
    
}


