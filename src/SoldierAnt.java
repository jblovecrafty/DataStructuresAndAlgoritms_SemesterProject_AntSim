/**
 * Class for the soldier ant
 */

/**
 * @author joejones
 *
 */
public class SoldierAnt extends Ant
{

    //set up defaults here but allow them to be overridden via a constructor
    //
    //unique Id for ant
    //
    private static final int DEFAULT_ANT_ID = 0;
    
    //base skills for the ant
    //
    private  static final double DEFAULT_COMBAT_SKILL = .5;
    private  static final int DEFAULT_FOOD_CONSUMPTION_RATE = 0;
    private static final int DEFAULT_HUNGER_THRESHOLD = 2;
    private  static final int DEFAULT_AWARENESS_RADIUS = 1;
    private  static final int DEFAULT_MOVEMENT_ALLOWANCE = 1;
    
    //state of the ant
    //
    private  static final int DEFAULT_HEALTH = 4;
    private  static final int DEFAULT_LIFE_SPAN = 1;
    private  static final int DEFAULT_CURRENT_AGE = 0;
    private  static final boolean DEFAULT_IS_DEAD = false;
  

    //set up various constructors
    //
    public SoldierAnt()
    {
        this.createSoldierAntStartState(DEFAULT_ANT_ID, DEFAULT_COMBAT_SKILL, DEFAULT_FOOD_CONSUMPTION_RATE, DEFAULT_AWARENESS_RADIUS,
                DEFAULT_HUNGER_THRESHOLD,DEFAULT_MOVEMENT_ALLOWANCE, DEFAULT_HEALTH, DEFAULT_LIFE_SPAN, DEFAULT_IS_DEAD, DEFAULT_CURRENT_AGE, null, null);
    }
    
    /**
     * easy to use default constructor
     */
    public SoldierAnt(MapSpace passedMapSpace)
    {
        this.createSoldierAntStartState(DEFAULT_ANT_ID, DEFAULT_COMBAT_SKILL, DEFAULT_FOOD_CONSUMPTION_RATE, DEFAULT_AWARENESS_RADIUS,
                DEFAULT_HUNGER_THRESHOLD,DEFAULT_MOVEMENT_ALLOWANCE, DEFAULT_HEALTH, DEFAULT_LIFE_SPAN, DEFAULT_IS_DEAD, DEFAULT_CURRENT_AGE, passedMapSpace, null);
    }
    
    /**
     * Default SoldierAnt Constructor
     */
    public SoldierAnt(int passedAntId, double passedCombatSkill, int passedFoodConsumptionRate, int passedAwarenessRadius,
                    int passedHungerThreshold, int passedMovementAllowance, int passedHealth, int passedLifeSpan, int passedCurrentAge, boolean passedIsDead, MapSpace passedMapSpace,
                    AntActionSequenceController passedAntActionSequenceController)
    {
        this.createSoldierAntStartState(passedAntId, passedCombatSkill, passedFoodConsumptionRate, passedAwarenessRadius, 
                                      passedHungerThreshold, passedMovementAllowance, passedHealth, passedLifeSpan, passedIsDead, 
                                      passedCurrentAge, passedMapSpace,passedAntActionSequenceController);

    }
    
    /**
     * Helper method to make construction of a valid Soldier ant easier
     * @param passedAntId
     * @param passedCombatSkill
     * @param passedFoodConsumptionRate
     * @param passedAwarenessRadius
     * @param passedMovementAllowance
     * @param passedHungerThreshold
     * @param passedHealth
     * @param passedLifeSpan
     * @param passedIsDead
     * @param passedCurrentAge
     * @param passedMapSpace
     * @param passedAntActionSequenceController
     */
    private void createSoldierAntStartState(int passedAntId, double passedCombatSkill, int passedFoodConsumptionRate, int passedAwarenessRadius,
            int passedHungerThreshold,int passedMovementAllowance, int passedHealth, int passedLifeSpan, boolean passedIsDead, int passedCurrentAge, 
            MapSpace passedMapSpace, AntActionSequenceController passedAntActionSequenceController)
    {
        this.createAntStartState(passedAntId, passedCombatSkill, passedFoodConsumptionRate, passedAwarenessRadius, 
                passedHungerThreshold, passedMovementAllowance,passedHealth, passedLifeSpan, passedIsDead, passedCurrentAge, passedMapSpace);
        this.setAntActionSequenceController(passedAntActionSequenceController);

    }
    
}
