
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for the random move
 */

/**
 * @author joejones
 *
 */
public class RandomBasedMoveTest
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
    public void movementTest()
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
        worldBuilder.setMapSizeOnXYAxis(mapSpaceArray.length-1);
        
        //create ant and map spaces with pheremones
        //
        QueenAnt queen = new QueenAnt(x0y0);
        
        queen.setAwarenessRadius(1);
        x0y0.addAntOnMapSpace(queen);
  
        
        x0y0.setMapInfoObject(worldBuilder);      
        x1y0.setMapInfoObject(worldBuilder);
        x2y0.setMapInfoObject(worldBuilder);
        x0y1.setMapInfoObject(worldBuilder);
        x1y1.setMapInfoObject(worldBuilder);
        x2y1.setMapInfoObject(worldBuilder);
        x0y2.setMapInfoObject(worldBuilder);
        x1y2.setMapInfoObject(worldBuilder);
        x2y2.setMapInfoObject(worldBuilder);
        
        x1y0.setDiscovered(true);
        
        RandomBasedMove randMove = new RandomBasedMove(queen, x0y0,true);
        randMove.performAction();
        
        //test that the queen is moved to the right spot
        //
        assertSame(x1y0, queen.getCurrentMapSpace());
        System.out.print(queen.getHistoryOfMapMoves());
        
    }
    
    @Test
    public void uncoveredMovementTest()
    {
      //set up the map spaces and the world controller
        //
        WorldBuilder worldBuilder = new WorldBuilder();
        MapSpace[][] mapSpaceArray = new MapSpace[1][1];
        
        //create nine map spaces
        //
        MapSpace x0y0 = new MapSpace();
        x0y0.setLocationX(0);
        x0y0.setLocationY(0);
        
        mapSpaceArray[0][0] =x0y0;

        worldBuilder.setModelOfTheWorld(mapSpaceArray);
        worldBuilder.setMapSizeOnXYAxis(mapSpaceArray.length-1);
        
        //create ant and map spaces with pheremones
        //
        QueenAnt queen = new QueenAnt(x0y0);
        queen.setAwarenessRadius(1);
        x0y0.addAntOnMapSpace(queen);
        
        x0y0.setMapInfoObject(worldBuilder);
        
        RandomBasedMove randMove = new RandomBasedMove(queen, x0y0,true);
        randMove.performAction();
        
        //test that the queen is moved to the right spot
        //
        assertSame(x0y0, queen.getCurrentMapSpace());
        System.out.print(queen.getHistoryOfMapMoves());
        
    }
    

}
