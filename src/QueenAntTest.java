

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 */

/**
 * @author joejones
 *
 */
public class QueenAntTest
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
    public void queenMapSpaceTest()
    {
        //set up MapSpace
        //
        MapSpace mapSpace = new MapSpace();
        mapSpace.setFoodAmount(50);
        mapSpace.setLocationX(2);
        mapSpace.setLocationY(2);
        
        //test that the queen has the right mapSpace
        //
        assertSame(mapSpace.getFoodAmount(), 50);
    }
    
    @Test
    public void queenActionControllerTest()
    {
        //set up MapSpace
        //
        MapSpace mapSpace = new MapSpace();
        mapSpace.setFoodAmount(50);
        mapSpace.setLocationX(2);
        mapSpace.setLocationY(2);
        mapSpace.setPheremoneAmount(10);
        WorldBuilder worldBuilder = new WorldBuilder();
        mapSpace.setMapInfoObject(worldBuilder);
        worldBuilder.setCurrentTimeInTurns(4);
        
        Ant birthedAnt;
        
        //set up Queen Ant
        //
        QueenAnt queen = new QueenAnt(mapSpace);
        System.out.println("Queen Hunger Threshhold is :" + queen.getHungerThreshhold());
        
        QueenAntActionSequenceController actionSequnce = new QueenAntActionSequenceController(mapSpace, queen, queen.getHungerThreshhold(), 1.0,1.0,1.0,1,4);
        queen.setAntActionSequenceController(actionSequnce);
        queen.getAntActionSequenceController().checkStateAndPerformActions();
        
        assertSame(mapSpace.getFoodAmount(), 48);
        birthedAnt = actionSequnce.getBirthedAnt();
        
        System.out.println("Birthed Ant is a :" + birthedAnt.getClass().getName());
        
        queen.setHealth(1);
        queen.getAntActionSequenceController().checkStateAndPerformActions();
        assertSame(mapSpace.getFoodAmount(), 46);
        assertSame(queen.getHealth(), 3);
    }
    
    @Test
    public void queenStarvedActionControllerTest()
    {
        //set up MapSpace
        //
        MapSpace mapSpace = new MapSpace();
        mapSpace.setFoodAmount(0);
        mapSpace.setLocationX(2);
        mapSpace.setLocationY(2);
        mapSpace.setPheremoneAmount(10);
        
        //set up Queen Ant
        //
        QueenAnt queen = new QueenAnt(mapSpace);
        System.out.println("Queen Hunger Threshhold is :" + queen.getHungerThreshhold());
        queen.setHealth(1);
        
        QueenAntActionSequenceController actionSequnce = new QueenAntActionSequenceController(mapSpace, queen, queen.getHungerThreshhold(), 1.0,1.0,1.0,1,1);
        queen.setAntActionSequenceController(actionSequnce);
        queen.getAntActionSequenceController().checkStateAndPerformActions();
        
        assertSame(queen.getHealth(), 1);
        queen.getAntActionSequenceController().checkStateAndPerformActions();
        assertTrue("Queen is Dead", queen.isDead());
    }
    

}
