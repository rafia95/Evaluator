
import Collections.Queue;
import com.mycompany.evaluator.Evaluator;
import com.mycompany.evaluator.InvalidExpressionException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


/**
 * This class runs the test to check for exception being thrown.
 * Invalid data is sent to Evaluator, which throws a named exception
 * 
 * @author Rafia
 * @version 08/12/2016
 */
@RunWith(Parameterized.class)
public class InvalidExpressionExceptionTest {
    
    private Queue<String> holdingQueue;
    private boolean expectedResult;
    private  Evaluator eval;
    
    public InvalidExpressionExceptionTest( String[] vals,boolean expectedResult) throws InvalidExpressionException {
        this.expectedResult=expectedResult;
        this.eval = new Evaluator();
        holdingQueue= new Queue<>();

        for(String val : vals)
        {
            holdingQueue.push(val);
        }
        
        
    }
   
    @Parameterized.Parameters
    public static Collection<Object[]> data() {
      // true is when exception thrown is true
        return Arrays.asList(new Object[][] {
         {new String[]{"(","3","*","2"}, true},
         {new String[]{"3","-","*","2","+","(","8","/","4","-","9",")"}, true},
         {new String[]{"(","+","3",")","*","12","/","3"}, true},
         {new String[]{"(","(","5","+","3",")","*","12",")","/","3","3"}, true},
         {new String[]{"6","/","12","*","18","+","99",""}, true}
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
   public void testEvaluator() {
      System.out.println("Parameterized Number is : " );
      Queue<String> resQ=new Queue<>();
      boolean exceptionThrown = false;
        try {
            resQ = eval.setHoldingQueue(holdingQueue);
        } catch (InvalidExpressionException ex) {
            exceptionThrown = true;
        }
    
    assertEquals(expectedResult,exceptionThrown);
   }
    
}


