import java.util.LinkedList;

/**
 * This is the abstract class for the ant simulator
 */

/**
 * @author joejones
 *
 */

public abstract class Ant 
{
    //unique Id for ant
    //
    private int antID;
    
    //base skills for the ant
    //
    private double combatSkill;
    private int foodConsumptionRate;
    private int hungerThreshhold;
    private int awarenessRadius;
    private int movementAllowance;
    
    //state of the ant
    //
    private int health;
    private int lifeSpan;
    private int currentAge;
    private boolean isDead;
    
    //current location of the ant
    //
    private MapSpace currentMapSpace;
    
    private AntActionSequenceController antActionSequenceController;
    
    
    //implement map space history
    //
    LinkedList<MapSpace> historyOfMapMoves = new LinkedList<MapSpace>();
    
    /**
     * @return the antID
     */
    public int getAntID()
    {
        return antID;
    }
    /**
     * @param antID the antID to set
     */
    public void setAntID(int antID)
    {
        this.antID = antID;
    }
    /**
     * @return the combatSkill
     */
    public double getCombatSkill()
    {
        return combatSkill;
    }
    /**
     * @param combatSkill the combatSkill to set
     */
    public void setCombatSkill(double combatSkill)
    {
        this.combatSkill = combatSkill;
    }
    /**
     * @return the foodConsumptionRate
     */
    public int getFoodConsumptionRate()
    {
        return foodConsumptionRate;
    }
    /**
     * @param foodConsumptionRate the foodConsumptionRate to set
     */
    public void setFoodConsumptionRate(int foodConsumptionRate)
    {
        this.foodConsumptionRate = foodConsumptionRate;
    }
    /**
     * @return the awarenessRadius
     */
    public int getAwarenessRadius()
    {
        return awarenessRadius;
    }
    /**
     * @param awarenessRadius the awarenessRadius to set
     */
    public void setAwarenessRadius(int awarenessRadius)
    {
        this.awarenessRadius = awarenessRadius;
    }
    /**
     * @return the hungerThreshhold
     */
    public int getHungerThreshhold()
    {
        return hungerThreshhold;
    }
    /**
     * @param hungerThreshhold the hungerThreshhold to set
     */
    public void setHungerThreshhold(int hungerThreshhold)
    {
        this.hungerThreshhold = hungerThreshhold;
    }
    /**
     * @return the movementAllowance
     */
    public int getMovementAllowance()
    {
        return movementAllowance;
    }
    /**
     * @param movementAllowance the movementAllowance to set
     */
    public void setMovementAllowance(int movementAllowance)
    {
        this.movementAllowance = movementAllowance;
    }
    /**
     * @return the health
     */
    public int getHealth()
    {
        return health;
    }
    /**
     * @param health the health to set
     */
    public void setHealth(int health)
    {
        this.health = health;
    }
    /**
     * @return the lifeSpan
     */
    public int getLifeSpan()
    {
        return lifeSpan;
    }
    /**
     * @param lifeSpan the lifeSpan to set
     */
    public void setLifeSpan(int lifeSpan)
    {
        this.lifeSpan = lifeSpan;
    }
    /**
     * @return the currentAge
     */
    public int getCurrentAge()
    {
        return currentAge;
    }
    /**
     * @param currentAge the currentAge to set
     */
    public void setCurrentAge(int currentAge)
    {
        if((this.currentAge + currentAge) >= this.getLifeSpan())
        {
            this.setDead(true);
        }
        else
        {
            this.currentAge = this.currentAge + currentAge;
        }
    }
    /**
     * @return the isDead
     */
    public boolean isDead()
    {
        return isDead;
    }
    /**
     * @param isDead the isDead to set
     */
    public void setDead(boolean isDead)
    {
        this.isDead = isDead;
    }
    /**
     * @return the currentMapSpace
     */
    public MapSpace getCurrentMapSpace()
    {
        return currentMapSpace;
    }
    /**
     * @param currentMapSpace the currentMapSpace to set
     */
    public void setCurrentMapSpace(MapSpace currentMapSpace)
    {
        this.currentMapSpace = currentMapSpace;
    }

    /**
     * @return the antActionSequenceController
     */
    public AntActionSequenceController getAntActionSequenceController()
    {
        return antActionSequenceController;
    }
    /**
     * @param antActionSequenceController the antActionSequenceController to set
     */
    public void setAntActionSequenceController(
            AntActionSequenceController antActionSequenceController)
    {
        this.antActionSequenceController = antActionSequenceController;
    }
    /**
     * @return the historyOfMapMoves
     */
    public LinkedList<MapSpace> getHistoryOfMapMoves()
    {
        return historyOfMapMoves;
    }
    /**
     * @param historyOfMapMoves the historyOfMapMoves to set
     */
    public void setHistoryOfMapMoves(LinkedList<MapSpace> historyOfMapMoves)
    {
        this.historyOfMapMoves = historyOfMapMoves;
    }
    
    //methods for adding updating and deleting mapMoves
    //
    public void addToHistoryOfMapMoves(MapSpace passedMapSpace)
    {
        this.getHistoryOfMapMoves().addLast(passedMapSpace);
    }
    
    public MapSpace getLastMapMove()
    {
        return this.historyOfMapMoves.removeLast();
    }
    
    public void deleteLastMapMove()
    {
        this.historyOfMapMoves.removeLast();
    }
    
    /**
     * Helper Method for creating a valid ant starting state
     * @param passedAntId
     * @param passedCombatSkill
     * @param passedFoodConsumptionRate
     * @param passedAwarenessRadius
     * @param passedMovementAllowance
     * @param passedHealth
     * @param passedLifeSpan
     * @param passedCurrentAge
     * @param passedX
     * @param passedY
     * @param passedMapSpace
     */
    protected void createAntStartState(int passedAntId, double passedCombatSkill, int passedFoodConsumptionRate, int passedAwarenessRadius,
            int passedHungerThreshold,int passedMovementAllowance, int passedHealth, int passedLifeSpan, boolean passedIsDead, int passedCurrentAge, 
           MapSpace passedMapSpace)
    {
        this.setAntID(passedAntId);
        this.setCombatSkill(passedCombatSkill);
        this.setFoodConsumptionRate(passedFoodConsumptionRate);
        this.setAwarenessRadius(passedAwarenessRadius);
        this.setHungerThreshhold(passedHungerThreshold);
        this.setMovementAllowance(passedMovementAllowance);
        this.setHealth(passedHealth);
        this.setLifeSpan(passedLifeSpan);
        this.setCurrentAge(passedCurrentAge);
        this.setDead(passedIsDead);
        this.setCurrentMapSpace(passedMapSpace);
        
        
        //check if we have a valid mapspace
        //
        if(passedMapSpace != null)
        {
            this.addToHistoryOfMapMoves(passedMapSpace);
        }
    }

}
