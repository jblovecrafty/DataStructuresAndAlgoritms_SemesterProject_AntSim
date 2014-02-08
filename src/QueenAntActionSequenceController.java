/**
 * Queen Action Squence Controller
 */

/**
 * @author joejones
 *
 */
public class QueenAntActionSequenceController implements AntActionSequenceController
{
    
    private MapSpace mapSpaceToUse;
    private Ant antWhoIsTakingAction;
    private int hungerThreshold;
    
    private ConsumeFood consume;
    
    private AntBirth antBirth;
    private double scoutAntBirthPercentage;
    private double foragerAntBirthPercentage;
    private double soldierAntBirthPercentage;
    private int healthDecrementAmount;
    
    
    private Ant birthedAnt;
    private int numberOfTurnsToBirth;
    
    private static final int DEFAULT_NO_FOOD_VALUE = 0;
    private static final int DEFAULT_DEAD_HEALTH_AMOUNT = 0;
    private static final int DEFAULT_HEALTH_DECREMENT_AMOUNT = 0;
    private static final int DEFAULT_NUMBER_OF_TURNS_TO_BIRTH = 1;
    
    //constructor
    //
    public QueenAntActionSequenceController(MapSpace passedMapSpace, Ant passedAnt,
                                            int passedHungerThreshold, double passedScoutAntBirthPercentage,
                                            double passedForagerAntBirthPercentage,
                                            double passedSoldierAntBirthPercentage, int passedHealthDecrementAmount,
                                            int passedTimeInTurnsForBirth)
    {
        this.setAntWhoIsTakingAction(passedAnt);
        
        this.setMapSpaceToUse(passedMapSpace);
        this.setHungerThreshold(passedHungerThreshold);
        this.setHealthDecrementAmount(passedHealthDecrementAmount);
        
        this.setForagerAntBirthPercentage(passedForagerAntBirthPercentage);
        this.setScoutAntBirthPercentage(passedScoutAntBirthPercentage);
        this.setSoldierAntBirthPercentage(passedSoldierAntBirthPercentage);
        
        consume = new ConsumeFood(passedMapSpace, passedAnt, passedAnt.getFoodConsumptionRate());
        antBirth = new AntBirth(this.getScoutAntBirthPercentage(), this.getForagerAntBirthPercentage(),
                this.getSoldierAntBirthPercentage(), this.getAntWhoIsTakingAction().getAntID());
        this.setNumberOfTurnsToBirth(passedTimeInTurnsForBirth);
        
    }
    
    //ok check state and perform action
    //
    public void checkStateAndPerformActions()
    {
        //lets check that if our health is and if it is below hunger threshold then eat
        //otherwise make babies
        //
        
        if(this.getAntWhoIsTakingAction().getHealth() <= this.getHungerThreshold())
        {
            if(this.getAntWhoIsTakingAction().getHealth() <= DEFAULT_DEAD_HEALTH_AMOUNT)
            {
                this.getAntWhoIsTakingAction().setDead(true);
            }
            else
            {
                if(this.getMapSpaceToUse().getFoodAmount() <= DEFAULT_NO_FOOD_VALUE)
                {
                    //decrement ant dies
                    //
                    this.getAntWhoIsTakingAction().setDead(true);
                }
                else
                {                 
                    this.getConsume().performAction();
                }
            }
        }
        else
        {
            //add only have babies on x turns
            //otherwise decrement food instead
            //
            if((this.getMapSpaceToUse().getCurrentTimeInTurns()%this.getNumberOfTurnsToBirth()) == 0)
            {
                this.getAntBirth().performAction();
                this.setBirthedAnt(this.getAntBirth().getCreatedAnt());
                
                if(this.getMapSpaceToUse().getFoodAmount() <= DEFAULT_NO_FOOD_VALUE)
                {
                    //decrement ant dies
                    //
                    this.getAntWhoIsTakingAction().setDead(true);
                }
                else
                {                 
                    this.getConsume().performAction();
                }
            }
        }
    }

    /**
     * @return the mapSpaceToUse
     */
    public MapSpace getMapSpaceToUse()
    {
        return mapSpaceToUse;
    }

    /**
     * @param mapSpaceToUse the mapSpaceToUse to set
     */
    public void setMapSpaceToUse(MapSpace mapSpaceToUse)
    {
        this.mapSpaceToUse = mapSpaceToUse;
    }

    /**
     * @return the antWhoIsTakingAction
     */
    public Ant getAntWhoIsTakingAction()
    {
        return antWhoIsTakingAction;
    }

    /**
     * @param antWhoIsTakingAction the antWhoIsTakingAction to set
     */
    public void setAntWhoIsTakingAction(Ant antWhoIsTakingAction)
    {
        this.antWhoIsTakingAction = antWhoIsTakingAction;
    }

    /**
     * @return the consume
     */
    public ConsumeFood getConsume()
    {
        return consume;
    }

    /**
     * @param consume the consume to set
     */
    public void setConsume(ConsumeFood consume)
    {
        this.consume = consume;
    }

    /**
     * @return the hungerThreshold
     */
    public int getHungerThreshold()
    {
        return hungerThreshold;
    }

    /**
     * @param hungerThreshold the hungerThreshold to set
     */
    public void setHungerThreshold(int hungerThreshold)
    {
        this.hungerThreshold = hungerThreshold;
    }

    /**
     * @return the antBirth
     */
    public AntBirth getAntBirth()
    {
        return antBirth;
    }

    /**
     * @param antBirth the antBirth to set
     */
    public void setAntBirth(AntBirth antBirth)
    {
        this.antBirth = antBirth;
    }

    /**
     * @return the birthedAnt
     */
    public Ant getBirthedAnt()
    {
        return birthedAnt;
    }

    /**
     * @param birthedAnt the birthedAnt to set
     */
    public void setBirthedAnt(Ant birthedAnt)
    {
        this.birthedAnt = birthedAnt;
    }

    /**
     * @return the scoutAntBirthPercentage
     */
    public double getScoutAntBirthPercentage()
    {
        return scoutAntBirthPercentage;
    }

    /**
     * @param scoutAntBirthPercentage the scoutAntBirthPercentage to set
     */
    public void setScoutAntBirthPercentage(double scoutAntBirthPercentage)
    {
        this.scoutAntBirthPercentage = scoutAntBirthPercentage;
    }

    /**
     * @return the foragerAntBirthPercentage
     */
    public double getForagerAntBirthPercentage()
    {
        return foragerAntBirthPercentage;
    }

    /**
     * @param foragerAntBirthPercentage the foragerAntBirthPercentage to set
     */
    public void setForagerAntBirthPercentage(double foragerAntBirthPercentage)
    {
        this.foragerAntBirthPercentage = foragerAntBirthPercentage;
    }

    /**
     * @return the soldierAntBirthPercentage
     */
    public double getSoldierAntBirthPercentage()
    {
        return soldierAntBirthPercentage;
    }

    /**
     * @param soldierAntBirthPercentage the soldierAntBirthPercentage to set
     */
    public void setSoldierAntBirthPercentage(double soldierAntBirthPercentage)
    {
        this.soldierAntBirthPercentage = soldierAntBirthPercentage;
    }

    /**
     * @return the healthDecrementAmount
     */
    public int getHealthDecrementAmount()
    {
        return healthDecrementAmount;
    }

    /**
     * @param healthDecrementAmount the healthDecrementAmount to set
     */
    public void setHealthDecrementAmount(int healthDecrementAmount)
    {
        if(healthDecrementAmount <= DEFAULT_HEALTH_DECREMENT_AMOUNT)
        {
            this.healthDecrementAmount = DEFAULT_HEALTH_DECREMENT_AMOUNT;
        }
        else
        {
            this.healthDecrementAmount = healthDecrementAmount;
        }
    }

    /**
     * @return the numberOfTurnsToBirth
     */
    public int getNumberOfTurnsToBirth()
    {
        return numberOfTurnsToBirth;
    }

    /**
     * @param numberOfTurnsToBirth the numberOfTurnsToBirth to set
     */
    public void setNumberOfTurnsToBirth(int numberOfTurnsToBirth)
    {
        if(numberOfTurnsToBirth <= DEFAULT_NUMBER_OF_TURNS_TO_BIRTH)
        {
            this.numberOfTurnsToBirth =   DEFAULT_NUMBER_OF_TURNS_TO_BIRTH;
        }
        else
        {
            this.numberOfTurnsToBirth = numberOfTurnsToBirth;
        }
    }
    
    
}
