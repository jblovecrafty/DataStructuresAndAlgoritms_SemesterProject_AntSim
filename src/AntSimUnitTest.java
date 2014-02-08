/**
 * This unites all of the unit test classes
 */

/**
 * @author joejones
 *
 */
import org.junit.runners.Suite;
import org.junit.runner.RunWith;

@RunWith(Suite.class)
@Suite.SuiteClasses({AntBirthTest.class, AntHistoryBasedMoveTest.class, AttackTest.class
                    ,BalaAntActionSequenceControllerTest.class ,ForagerAntSequenceControllerTest.class
                    ,MapSpaceTest.class,PheremoneBasedMoveTest.class,QueenAntTest.class,RandomBasedMoveTest.class
                    ,ScoutAntActionSequenceControllerTest.class,SoldierAntTest.class,WorldBuilderTest.class
                    ,WorldControllerTest.class})

public class AntSimUnitTest 
{
  //nothing
}

