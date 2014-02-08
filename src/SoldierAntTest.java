


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Test for the soldier ant
 */

/**
 * @author joejones
 *
 */
public class SoldierAntTest
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
    public void soldierMovementTest()
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
        
        //create ant and map spaces
        //
        SoldierAnt soldier = new SoldierAnt(x0y0);
        soldier.setAwarenessRadius(1);
        x0y0.addAntOnMapSpace(soldier);
        
        x0y0.setMapInfoObject(worldBuilder);
        
        x1y0.setMapInfoObject(worldBuilder);
        x2y0.setMapInfoObject(worldBuilder);
        x0y1.setMapInfoObject(worldBuilder);
        x1y1.setMapInfoObject(worldBuilder);
        x2y1.setMapInfoObject(worldBuilder);
        x0y2.setMapInfoObject(worldBuilder);
        x1y2.setMapInfoObject(worldBuilder);
        x2y2.setMapInfoObject(worldBuilder);
        
        x0y0.setDiscovered(false);
        x1y0.setDiscovered(true);
        x2y0.setDiscovered(false);
        x0y1.setDiscovered(false);
        x1y1.setDiscovered(false);
        x2y1.setDiscovered(false);
        x0y2.setDiscovered(false);
        x1y2.setDiscovered(false);
        x2y2.setDiscovered(false);
        
        SoliderAntActionSequenceController sa = new SoliderAntActionSequenceController(soldier.getCurrentMapSpace(), 
                                                                                        soldier, 
                                                                                        true, 
                                                                                        .01);
        soldier.setAntActionSequenceController(sa);
        soldier.getAntActionSequenceController().checkStateAndPerformActions();
        
        //test that the soldier is moved to the right spot
        //
        assertSame(x1y0, soldier.getCurrentMapSpace());
        System.out.print(soldier.getHistoryOfMapMoves());

        
    }
    
    //test fighting a bala ant
    //test a live bala and a dead bala ant and it should fight the live one
    //
    @Test
    public void liveAndDeadBalaAntSameSquareTest()
    {
      
        MapSpace x0y0 = new MapSpace();
        x0y0.setLocationX(0);
        x0y0.setLocationY(0);
        
        //create ant and map spaces with pheremones
        //
        SoldierAnt soldier = new SoldierAnt(x0y0);
        soldier.setAwarenessRadius(1);
        soldier.setCombatSkill(1.0);
        x0y0.addAntOnMapSpace(soldier);
        
        BalaAnt deadBala = new BalaAnt(x0y0);
        deadBala.setDead(true);
        x0y0.addAntOnMapSpace(deadBala);
        
        BalaAnt liveBala = new BalaAnt(x0y0);
        liveBala.setCombatSkill(0.0);
        System.out.println(liveBala.isDead());
        x0y0.addAntOnMapSpace(liveBala);
        

        x0y0.setDiscovered(true);        
        SoliderAntActionSequenceController sa = new SoliderAntActionSequenceController(soldier.getCurrentMapSpace(), 
                                                                                        soldier, 
                                                                                        true, 
                                                                                        .01);
        soldier.setAntActionSequenceController(sa);
        soldier.getAntActionSequenceController().checkStateAndPerformActions();
        
        //test that the bala ant is the defender and dead
        //
        assertSame(liveBala, sa.getAntWhoIsDefending());
        assertTrue("Live Bala is Dead", liveBala.isDead());
        System.out.print(soldier.getHistoryOfMapMoves());   
    }
    
    //test moveing to a square with a bala ant on it
    //test moving to a square with a dead bala on it
    //
    @Test
    public void liveAndDeadBalaAntMovementTest()
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
        
        //create ant and map spaces
        //
        SoldierAnt soldier = new SoldierAnt(x0y0);
        soldier.setAwarenessRadius(1);
        x0y0.addAntOnMapSpace(soldier);
        
        BalaAnt deadBala = new BalaAnt(x1y0);
        deadBala.setDead(true);
        x1y0.addAntOnMapSpace(deadBala);
        
        BalaAnt liveBala = new BalaAnt(x0y1);
        System.out.println(liveBala.isDead());
        x0y1.addAntOnMapSpace(liveBala);
        
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
        x1y1.setDiscovered(false);
        x2y1.setDiscovered(false);
        x0y2.setDiscovered(false);
        x1y2.setDiscovered(false);
        x2y2.setDiscovered(false);
        
        SoliderAntActionSequenceController sa = new SoliderAntActionSequenceController(soldier.getCurrentMapSpace(), 
                                                                                        soldier, 
                                                                                        true, 
                                                                                        .01);
        soldier.setAntActionSequenceController(sa);
        soldier.getAntActionSequenceController().checkStateAndPerformActions();
        
        //test that the soldier is moved to the right spot
        //
        assertSame(x0y1, soldier.getCurrentMapSpace());
        System.out.print(soldier.getHistoryOfMapMoves());   
    }
}
