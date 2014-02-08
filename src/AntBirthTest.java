import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for ant birth class
 */

/**
 * @author joejones
 *
 */
public class AntBirthTest
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
    public void antBirthTest()
    {
          
        //create ant references
        //
        SoldierAnt soldierAnt = new SoldierAnt();
        ForagerAnt forageAnt = new ForagerAnt();
        
        AntBirth antBirth = new AntBirth(0.0,1.0,0.0,1);
        
        antBirth.performAction();
        
        assertEquals("only forager was created", antBirth.getCreatedAnt().getClass(), forageAnt.getClass());
        assertEquals("birth id is 2", antBirth.getStartId(), 2);
        
        AntBirth antBirth1 = new AntBirth(0.0,0.0,0.1,-2);
        
        antBirth1.performAction();
        
        assertEquals("only soldier was created", antBirth1.getCreatedAnt().getClass(), soldierAnt.getClass());
        assertEquals("birth id is 2", antBirth1.getStartId(), 1);
        
        AntBirth antBirth2 = new AntBirth(0.0,1.0,0.0,-2);
        
        antBirth2.performAction();
        
        assertEquals("only forager was created", antBirth2.getCreatedAnt().getClass(), forageAnt.getClass());
        assertEquals("birth id is 2", antBirth2.getStartId(), 1);   
        
        antBirth2.performAction();
        assertEquals("birth id is 2", antBirth2.getStartId(), 2); 
        
    }

}
