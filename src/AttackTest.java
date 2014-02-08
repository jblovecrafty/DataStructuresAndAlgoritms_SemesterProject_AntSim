
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for the Attack class
 */

/**
 * @author joejones
 *
 */
public class AttackTest
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
    public void attackTest()
    {
          
        //create ants
        //
        QueenAnt attacker1 = new QueenAnt();
        attacker1.setCombatSkill(1.0);
        
        QueenAnt defender1 = new QueenAnt();
        defender1.setCombatSkill(0.0);
        
        
        Attack attack1 = new Attack(attacker1, defender1, -2);
        
        //assert that the lowest is 0
        //
        assertEquals("true", 0.0, attack1.getLowestAttackValue(), .1);
        assertTrue("defender1 is not dead", (!defender1.isDead()));
        assertEquals("defender1 reference is null", defender1, attack1.getDefenderAnt());
        
        attack1.performAction();
        assertTrue("defender1 is dead", defender1.isDead());
        
        assertEquals("defender1 reference is null", null, attack1.getDefenderAnt());
        
        //create ants
        //
        QueenAnt attacker2 = new QueenAnt();
        attacker1.setCombatSkill(0.5);
        
        QueenAnt defender2 = new QueenAnt();
        defender1.setCombatSkill(1.0);
        
        Attack attack2 = new Attack(attacker2, defender2, 0.1);
        assertEquals("lowest chance is .01", 0.1, attack2.getLowestAttackValue(), .01);
        
        attack2.setLowestAttackValue(0.0);
        attack2.performAction();
        
        assertTrue("defender2 is not dead", (!defender2.isDead()));
        
    }


}
