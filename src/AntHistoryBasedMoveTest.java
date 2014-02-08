import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *Tests for the ant history move 
 */

/**
 * @author joejones
 *
 */
public class AntHistoryBasedMoveTest
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
        
        x0y0.setPheremoneAmount(20);
        x1y0.setPheremoneAmount(10);
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
        
        PheremoneBasedMove phereMove = new PheremoneBasedMove(queen, x0y0,3,true);
        phereMove.performAction();
        
        //test that we have the right square
        //
        assertSame(10, phereMove.getCurrentMapSpace().getPheremoneAmount());
        
        //test that the queen is moved to the right spot
        //
        assertSame(x1y0, queen.getCurrentMapSpace());
        System.out.print(queen.getHistoryOfMapMoves());
        
        //ok now lets move back one
        //
        assertSame(queen.getCurrentMapSpace(), x1y0);
        AntHistoryBasedMove antHistMove = new AntHistoryBasedMove(queen, queen.getCurrentMapSpace());
        antHistMove.performAction();
        
        System.out.print(queen.getHistoryOfMapMoves());
        assertSame(queen.getCurrentMapSpace(), x0y0);
        
    }
    
    @Test
    public void nullMovementTest()
    {
      //set up the map spaces and the world controller
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
        
        x0y0.setPheremoneAmount(20);
        x1y0.setPheremoneAmount(10);
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
        
        PheremoneBasedMove phereMove = new PheremoneBasedMove(queen, x0y0,3,true);
        phereMove.performAction();
        
        //test that we have the right square
        //
        assertSame(20, phereMove.getCurrentMapSpace().getPheremoneAmount());
        
        //test that the queen is moved to the right spot
        //
        assertSame(x0y0, queen.getCurrentMapSpace());
        System.out.print(queen.getHistoryOfMapMoves());
        
        AntHistoryBasedMove antHistMove = new AntHistoryBasedMove(queen, queen.getCurrentMapSpace());
        antHistMove.performAction();
        
        System.out.print(queen.getHistoryOfMapMoves());
        assertSame(queen.getCurrentMapSpace(), x0y0);
    }
    
    @Test
    public void singleMovementTest()
    {
      //set up the map spaces and the world controller
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
        
        x0y0.setPheremoneAmount(3);
        x1y0.setPheremoneAmount(10);
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
        x1y1.setDiscovered(true);

        
        PheremoneBasedMove phereMove = new PheremoneBasedMove(queen, x0y0,3,true);
        phereMove.performAction();
        
        //test that we have the right square
        //
        assertSame(5, phereMove.getCurrentMapSpace().getPheremoneAmount());
        
        //test that the queen is moved to the right spot
        //
        assertSame(x1y1, queen.getCurrentMapSpace());
        System.out.print(queen.getHistoryOfMapMoves());
        
        AntHistoryBasedMove antHistMove = new AntHistoryBasedMove(queen, queen.getCurrentMapSpace());
        antHistMove.performAction();
        
        System.out.print(queen.getHistoryOfMapMoves());
        assertSame(queen.getCurrentMapSpace(), x0y0);
        
    }

}
