
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the ForagerAntSequenceController 
 */

/**
 * @author joejones
 *
 */
public class ForagerAntSequenceControllerTest
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
    public void foragerAntTest()
    {
      //set up the map spaces and the world builder
        //
        WorldBuilder worldBuilder = new WorldBuilder();
        MapSpace[][] mapSpaceArray = new MapSpace[3][3];
        
        //create nine map spaces
        //
        MapSpace x0y0 = new MapSpace();
        x0y0.setLocationX(0);
        x0y0.setLocationY(0);
        
        MapSpace x1y0 = new MapSpace();
        x1y0.setLocationX(1);
        x1y0.setLocationY(0);
        
        MapSpace x2y0 = new MapSpace();
        x2y0.setLocationX(2);
        x2y0.setLocationY(0);
        
        MapSpace x0y1 = new MapSpace();
        x0y1.setLocationX(0);
        x0y1.setLocationY(1);
        
        MapSpace x1y1 = new MapSpace();
        x1y1.setLocationX(1);
        x1y1.setLocationY(1);
        
        MapSpace x2y1 = new MapSpace();
        x2y1.setLocationX(2);
        x2y1.setLocationY(1);
        
        MapSpace x0y2 = new MapSpace();
        x0y2.setLocationX(0);
        x0y2.setLocationY(2);
        
        MapSpace x1y2 = new MapSpace();
        x1y2.setLocationX(1);
        x1y2.setLocationY(2);
        
        MapSpace x2y2 = new MapSpace();
        x2y2.setLocationX(2);
        x2y2.setLocationY(2);
        
        mapSpaceArray[0][0] =x0y0;
        mapSpaceArray[1][0] =x1y0;
        mapSpaceArray[2][0] =x2y0;
        
        mapSpaceArray[0][1] =x0y1;
        mapSpaceArray[1][1] =x1y1;
        mapSpaceArray[2][1] =x2y1;
        
        mapSpaceArray[0][2] =x0y2;
        mapSpaceArray[1][2] =x1y2;
        mapSpaceArray[2][2] =x2y2;
        
        worldBuilder.setModelOfTheWorld(mapSpaceArray);
        worldBuilder.setMapSizeOnXYAxis(mapSpaceArray.length);
        
        //create ant and map spaces with pheremones
        //
        ForagerAnt forgager = new ForagerAnt(x0y0);
        System.out.print(forgager.getHistoryOfMapMoves());
        forgager.setAwarenessRadius(1);
        x0y0.addAntOnMapSpace(forgager);
        x0y0.setQueenOnSpace(true);
        x1y0.setFoodAmount(10);
        
        x0y0.setPheremoneAmount(20);
        x1y0.setPheremoneAmount(30);
        x2y0.setPheremoneAmount(2);
        x0y1.setPheremoneAmount(2);
        x1y1.setPheremoneAmount(5);
        x2y1.setPheremoneAmount(1);
        x0y2.setPheremoneAmount(1);
        x1y2.setPheremoneAmount(1);
        x2y2.setPheremoneAmount(0);
        
        x0y0.setMapInfoObject(worldBuilder);
        
        x1y0.setMapInfoObject(worldBuilder);
        x2y0.setMapInfoObject(worldBuilder);
        x0y1.setMapInfoObject(worldBuilder);
        x1y1.setMapInfoObject(worldBuilder);
        x2y1.setMapInfoObject(worldBuilder);
        x0y2.setMapInfoObject(worldBuilder);
        x1y2.setMapInfoObject(worldBuilder);
        x2y2.setMapInfoObject(worldBuilder);
        
        x0y0.setDiscovered(true);
        x1y0.setDiscovered(true);
        x2y0.setDiscovered(true);
        x0y1.setDiscovered(true);
        x1y1.setDiscovered(true);
        x2y1.setDiscovered(true);
        x0y2.setDiscovered(true);
        x1y2.setDiscovered(true);
        x2y2.setDiscovered(true);
        

        //create the forager ant controller object
        //
        ForagerAntSequenceController fa = new ForagerAntSequenceController(forgager, forgager.getCurrentMapSpace(), 1,
                1,1,1, 10,100,true);
        
        forgager.setAntActionSequenceController(fa);
        
        forgager.getAntActionSequenceController().checkStateAndPerformActions();
        
        //first test that the ant moves out of the queen square
        //
        //test that we have the right square
        //
        assertSame(30, forgager.getCurrentMapSpace().getPheremoneAmount());
        
        //test that the forgager is moved to the right spot
        //
        assertSame(x1y0, forgager.getCurrentMapSpace());

        
        //then test that the ant gathers food
        //
        forgager.getAntActionSequenceController().checkStateAndPerformActions();
        
        assertSame(x1y0.getFoodAmount(), 9);
        
        //then test that the ant heads back and deposits pheremones
        //
        forgager.getAntActionSequenceController().checkStateAndPerformActions();
        assertSame(x1y0.getPheremoneAmount(), 40);
        assertSame(x0y0, forgager.getCurrentMapSpace());
        System.out.print(forgager.getHistoryOfMapMoves());
        
        //then test that the ant delivers food to the queen space
        //
        forgager.getAntActionSequenceController().checkStateAndPerformActions();
        assertSame(x0y0.getFoodAmount(), 1);
        
        //test that the forgager is moved to the right spot
        //
        forgager.getAntActionSequenceController().checkStateAndPerformActions();
        assertSame(x1y0, forgager.getCurrentMapSpace());
        
        //then test that the ant gathers food
        //
        forgager.getAntActionSequenceController().checkStateAndPerformActions();
        
        assertSame(x1y0.getFoodAmount(), 8);
        
        //then test that the ant heads back and deposits pheremones
        //
        forgager.getAntActionSequenceController().checkStateAndPerformActions();
        assertSame(x1y0.getPheremoneAmount(), 50);
        System.out.print(forgager.getHistoryOfMapMoves());
        assertSame(x1y0, forgager.getCurrentMapSpace());
        
        //then test that the ant delivers food to the queen space
        //
        forgager.getAntActionSequenceController().checkStateAndPerformActions();
        assertSame(x0y0.getFoodAmount(), 2);

    }

}
