
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Test for the world controller
 */

/**
 * @author joejones
 *
 */
public class WorldControllerTest
{

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }
    
    @Test
    public void turnTest()
    {
        //set up world controller
        //
        WorldController wc = new WorldController("AntSim.properties",0, 0);
        
        wc.worldTurn();
    }

}
