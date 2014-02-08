

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Test for the Bala Ant Action Squence controller
 */

/**
 * @author joejones
 *
 */
public class BalaAntActionSequenceControllerTest
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
    public void balaAntMovementTest()
    {
      //set up the map spaces and the world builder
        //
        WorldBuilder worldBuilder = new WorldBuilder();
        MapSpace[][] mapSpaceArray = new MapSpace[2][2];
        
        //create nine map spaces
        //
        MapSpace x0y0 = new MapSpace();
        x0y0.setLocationX(0);
        x0y0.setLocationY(0);

        
        MapSpace x1y0 = new MapSpace();
        x1y0.setLocationX(1);
        x1y0.setLocationY(0);
        x1y0.setColonySpace(true);
        
        MapSpace x0y1 = new MapSpace();
        x0y1.setLocationX(0);
        x0y1.setLocationY(1);

        
        MapSpace x1y1 = new MapSpace();
        x1y1.setLocationX(1);
        x1y1.setLocationY(1);

        
        mapSpaceArray[0][0] =x0y0;
        mapSpaceArray[0][1] =x0y1;
        mapSpaceArray[1][0] =x1y0;
        mapSpaceArray[1][1] =x1y1;
        
        worldBuilder.setModelOfTheWorld(mapSpaceArray);
        worldBuilder.setMapSizeOnXYAxis(mapSpaceArray.length);
        
        //create ant and map spaces
        //
        BalaAnt balaAnt = new BalaAnt(x0y0);
        balaAnt.setAwarenessRadius(1);
        x0y0.addAntOnMapSpace(balaAnt);
        
        x0y0.setMapInfoObject(worldBuilder);        
        x1y0.setMapInfoObject(worldBuilder);

        
        x0y0.setDiscovered(false);
        x1y0.setDiscovered(true);
        x0y1.setDiscovered(false);
        x1y1.setDiscovered(false);
        
        BalaAntActionSequenceController ba = new BalaAntActionSequenceController(balaAnt.getCurrentMapSpace(), balaAnt, 
                                                                                        true, 
                                                                                        .01);
        balaAnt.setAntActionSequenceController(ba);
        balaAnt.getAntActionSequenceController().checkStateAndPerformActions();
        
        //test that the bala ant is moved to the right spot
        //
        //assertSame(x1y0, balaAnt.getCurrentMapSpace());
        System.out.print(balaAnt.getHistoryOfMapMoves());    
    }
    
    //test that the bala ant stays in colony moves even if in larger pool of spaces
    //
    @Test
    public void balaAntColonyMovementTest()
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
        worldBuilder.setMapSizeOnXYAxis(mapSpaceArray.length);
        
        //create ant and map spaces with pheremones
        //
        BalaAnt balaAnt = new BalaAnt(x0y0);
        balaAnt.setAwarenessRadius(1);
        x0y0.addAntOnMapSpace(balaAnt);
        x0y0.setColonySpace(true);
        
        x0y0.setMapInfoObject(worldBuilder);
        
        x1y0.setMapInfoObject(worldBuilder);
        x2y0.setMapInfoObject(worldBuilder);
        x0y1.setMapInfoObject(worldBuilder);
        x1y1.setMapInfoObject(worldBuilder);
        x2y1.setMapInfoObject(worldBuilder);
        x0y2.setMapInfoObject(worldBuilder);
        x1y2.setMapInfoObject(worldBuilder);
        x2y2.setMapInfoObject(worldBuilder);
        
        x1y0.setColonySpace(true);
        x2y0.setColonySpace(false);
        x0y1.setColonySpace(false);
        x1y1.setColonySpace(false);
        x2y1.setColonySpace(false);
        x0y2.setColonySpace(false);
        x1y2.setColonySpace(false);
        x2y2.setColonySpace(false);
        
        System.out.print(balaAnt.getHistoryOfMapMoves());
        
        BalaAntActionSequenceController ba = new BalaAntActionSequenceController(balaAnt.getCurrentMapSpace(), balaAnt, 
                true, 
                .01);
        
        balaAnt.setAntActionSequenceController(ba);
        balaAnt.getAntActionSequenceController().checkStateAndPerformActions();
        
        //test that the balaAnt is moved to the right spot
        //
        assertSame(x1y0, balaAnt.getCurrentMapSpace());
        System.out.print(balaAnt.getHistoryOfMapMoves());

        
    }
    
    //test bala ant recognizing enemies dead or alive
    //
    @Test
    public void liveAndDeadEnemyAntSameSquareTest()
    {
      
        MapSpace x0y0 = new MapSpace();
        x0y0.setLocationX(0);
        x0y0.setLocationY(0);
        
        //create ant and map spaces with pheremones
        //
        BalaAnt balaAnt = new BalaAnt(x0y0);
        balaAnt.setAwarenessRadius(1);
        balaAnt.setCombatSkill(1.0);
        x0y0.addAntOnMapSpace(balaAnt);
        
        BalaAnt deadBala = new BalaAnt(x0y0);
        deadBala.setDead(true);
        x0y0.addAntOnMapSpace(deadBala);
        
        SoldierAnt liveSoldier = new SoldierAnt(x0y0);
        liveSoldier.setCombatSkill(0.0);
        System.out.println(liveSoldier.isDead());
        x0y0.addAntOnMapSpace(liveSoldier);
        
        ScoutAnt deadScout = new ScoutAnt(x0y0);
        deadScout.setDead(true);
        x0y0.addAntOnMapSpace(deadScout);
        

        x0y0.setDiscovered(true);        
        BalaAntActionSequenceController ba = new BalaAntActionSequenceController(balaAnt.getCurrentMapSpace(), balaAnt, 
                true, 
                .01);
        
        balaAnt.setAntActionSequenceController(ba);
        balaAnt.getAntActionSequenceController().checkStateAndPerformActions();
        
        //test that the soldier ant is the defender and dead
        //
        assertSame(liveSoldier, ba.getAntWhoIsDefending());
        assertTrue("Live Soldier is Dead", liveSoldier.isDead()); 
    }

}
