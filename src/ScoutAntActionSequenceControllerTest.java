import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * this is the test for the scout ant action sequence controller
 */

/**
 * @author joejones
 *
 */
public class ScoutAntActionSequenceControllerTest
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
    public void uncoveredMovementTest()
    {
      //set up the map spaces and the world builder
        //
        WorldBuilder worldBuilder = new WorldBuilder();
        MapSpace[][] mapSpaceArray = new MapSpace[1][1];
        
        //create map space
        //
        MapSpace x0y0 = new MapSpace();
        x0y0.setLocationX(0);
        x0y0.setLocationY(0);
        
        mapSpaceArray[0][0] =x0y0;

        worldBuilder.setModelOfTheWorld(mapSpaceArray);
        worldBuilder.setMapSizeOnXYAxis(mapSpaceArray.length-1);
        
        //create ant and map spaces
        //
        ScoutAnt scout = new ScoutAnt(x0y0);
        scout.setAwarenessRadius(1);
        x0y0.addAntOnMapSpace(scout);
        
        x0y0.setMapInfoObject(worldBuilder);
        
        assertTrue("Is Map Not Uncovered", (!scout.getCurrentMapSpace().isDiscovered()));
        
        ScoutAntActionSequenceController sa = new ScoutAntActionSequenceController(scout, x0y0,true);
        sa.checkStateAndPerformActions();
        
        //test that the queen is moved to the right spot
        //
        assertSame(x0y0, scout.getCurrentMapSpace());
        assertTrue("Is Map Uncovered", scout.getCurrentMapSpace().isDiscovered());
        
    }

}
