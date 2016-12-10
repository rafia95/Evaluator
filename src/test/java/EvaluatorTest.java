
import Collections.Queue;
import com.mycompany.evaluator.Evaluator;
import com.mycompany.evaluator.InvalidExpressionException;
import java.util.Arrays;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


/**
 * This class runs the tests to check that mathematical expressions works as expected
 * and give the right result
 * 
 * @author Rafia
 * @version 08/12/2016
 */
@RunWith(Parameterized.class)
public class EvaluatorTest {

    private Queue<String> holdingQueue;
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
   
    @Parameters
    public static Collection<Object[]> data() {
      
        return Arrays.asList(new Object[][] {
         {new String[]{"3","*","2"}, "6.0"},
         {new String[]{"3","*","2","+","(","8","/","4","-","9",")"}, "-1.0"},
         {new String[]{"(","5","+","3",")","*","12","/","3"}, "32.0"},
         {new String[]{"(","(","5","+","3",")","*","12",")","/","3"}, "32.0"},
         {new String[]{"6","/","12","*","18","+","99"}, "108.0"},
         
         {new String[]{"6","/","12","*","(","(","18","+","99",")",")"}, "58.5"},
         {new String[]{"(","(","22","-","2",")","+","5",")","*","(","5","-","3",")"}, "50.0"},
         {new String[]{"9","*","0","+","(","3","+","(","3","*","1",")","-","0",")"}, "6.0"},
         {new String[]{"9","-","7","+","7","/","9"}, "2.78"},
         {new String[]{"9","-","(","7","+","7","/","9",")"}, "1.22"},
         
         {new String[]{"23","+","7","*","3","*","5","+","2"}, "130.0"},
         {new String[]{"(","23","+","7",")","*","3","*","5","+","2"}, "452.0"},
         {new String[]{"(","(","23","+","7",")","*","3",")","*","(","5","+","2",")"}, "630.0"},
         {new String[]{"(","5","+","5",")","*","7","-","9","+","87"}, "148.0"},
         {new String[]{"(","5","+","5",")","*","7","-","(","9","+","87",")"}, "-26.0"},
         
         {new String[]{"5","+","5","*","7","-","9","+","87"}, "118.0"},
         {new String[]{"(","2000","-","1997",")","*","(","78","-","9",")"}, "207.0"},
         {new String[]{"(","2000","-","1997",")","*","(","78","/","9",")"}, "26.01"},
         {new String[]{"(","2000","/","1997",")","*","(","78","/","9",")"}, "8.67"},
         {new String[]{"(","2000","/","1997",")","*","(","78","/","9",")","+","1000"}, "1008.67"},
         
         {new String[]{"9","+","9","-","(","9","*","9",")","/","9"}, "9.0"},
         {new String[]{"8","-","19","+","(","9","*","10",")"}, "79.0"},
         {new String[]{"8","-","(","19","+","9",")","*","10"}, "-272.0"},
         {new String[]{"10","*","3","-","(","9","-","8",")","+","(","43","-","9",")"}, "63.0"},
         {new String[]{"10","*","3","-","9","-","8","+","43","-","9"}, "47.0"},
                
         


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
      Queue<String> resQ = eval.setHoldingQueue(holdingQueue);
     double result = Double.parseDouble(resQ.pop());
     String r = result+"";
    assertEquals(expectedResult,r);
   }
    
}


