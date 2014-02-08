
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test cases for the world control class
 */

/**
 * @author joejones
 *
 */
public class WorldBuilderTest
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
    public void propertiesTestLoad()
    {
        WorldBuilder wb = new WorldBuilder();
        
        wb.pullPropertiesFromConfigFile("AntSim.properties");
        assertEquals(wb.getMaxAntAmount(), 100);
        assertEquals(wb.getMapSize(), 27);
        assertEquals(wb.getScoutAntStartAmount(), 4);
        assertEquals(wb.getSoldierAntStartAmount(), 10);
        assertEquals(wb.getForagerStartAmount(), 50);
        assertEquals(wb.getQueenStartAmount(), 1);
        assertEquals(wb.getBalaStartAmount(), 3);
        
        assertEquals(wb.getSoldierAntBirthPercentage(), 0.25,0.001);
        assertEquals(wb.getForagerAntBirthPercentage(), 0.50,0.001);
        assertEquals(wb.getScoutAntBirthPercentage(), 0.25,0.001);

        assertEquals(wb.getDayLengthInTurns(), 10);
        assertEquals(wb.getPheremoneDepositAmount(),10);
        assertEquals(wb.getLowestPheremoneAmount(),10);
        assertEquals(wb.getPheremoneThresholdAmount(),1000);
        
        assertEquals(wb.getSquareFoodChance(),0.25,0.001);
        assertEquals(wb.getLowEndFoodAmount(),500);
        assertEquals(wb.getHighEndFoodAmount(), 1000);
        
        assertEquals(wb.getLowEndPheremoneAmount(),500);
        assertEquals(wb.getHighEndPheremoneAmount(),1000);
        
        assertEquals(wb.getQueenLifeSpanInTurns(), 73000);
        assertEquals(wb.getSoliderLifeSpanInTurns(),3650);
        assertEquals(wb.getScoutLifeSpanInTurns(),3650);
        assertEquals(wb.getForagerLifeSpanInTurns(),3650);
        assertEquals(wb.getBalaLifeSpanInTurns(),3650);
        
        assertEquals(wb.getAntHealth(),1);
        assertEquals(wb.getQueenHealth(),2);

        assertEquals(wb.getBalaSpawnRate(),0.03,0.001);
        assertEquals(wb.getBalaAttackChance(),0.5,0.001);
        assertEquals(wb.getSoliderAttackChance(),0.5,0.001);
        
        assertEquals(wb.getForagerFoodCapacity(),1);
        assertEquals(wb.getForagerFoodGatherRate(),1);
        assertEquals(wb.getForagerFoodLeaveRate(),1);

        assertEquals(wb.getLowestAttackAmount(),0.5,0.001);
    }
    
    @Test public void initializeWorldTest()
    {
        WorldBuilder wb = new WorldBuilder();
        
        wb.initializeWorldWithConfig("AntSim.properties",10, 10);
        
        assertEquals(wb.getModelOfTheWorld().length, 27);
        assertEquals(wb.getMapSpaceAtLocation(0, 1).getLocationY(), 1);
        assertTrue("Checking to Make Sure Colony Start has Ants", 
                wb.getMapSpaceAtLocation(10, 10).getAntsOnMapSpace().size() > 0);
        
        assertEquals(wb.getAntPopulation().size(),65);
        
        //print out all ant on colony
        //
        for(Ant ant : wb.getMapSpaceAtLocation(10, 10).getAntsOnMapSpace())
        {
            System.out.println("Ant Type: " + ant.getClass().toString() + "Ant Id: " + ant.getAntID());
        }
    }

}
