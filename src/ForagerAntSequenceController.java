/**
 * This is the action seqeunce for a forager ant
 */

/**
 * @author joejones
 *
 */
public class ForagerAntSequenceController implements AntActionSequenceController
{
    private MapSpace mapSpaceToUse;
    private Ant foragerAnt;
    
    //actions
    //
    private GatherFood getFood; 
    private LeaveFood leaveFood;
    private AntHistoryBasedMove historyMove;
    private PheremoneBasedMove pheremoneMove;
    
    //vars for actions
    //
    private int foodGatherRate;
    private int foodGatheredCapacity;
    private int leftFoodRate;
    private int lowestAcceptablePheremoneLevel;
    private boolean doesMapNeedToBeUncovered;
    private int pheremoneDepositAmount;
    private int peheremoneDepositThreshold;
    
    //set up some defaults
    //
    private static final int DEFAULT_FOOD_GATHER_RATE = 1;
    private static final int DEFAULT_LEFT_FOOD_RATE = 1;
    private static final int DEFAULT_FOOD_CAPACITY = 1;
    private static final int DEFAULT_LOWEST_PHEREMONE_RATE = 1;
    private static final int DEFAULT_PHEREMONE_DEPOSIT_AMOUNT = 1;
    private static final int DEFAULT_PHEREMONE_DEPOSIT_THRESHOLD = 0;
    
    private static final int DEFAULT_EMPTY_VALUE_FOR_FOOD = 0;
    private static final int DEFAULT_LOW_END_PHEREMONE = 0;
    
    
    //set up conscrutor here
    //
    /**
     * Public contructor 
     */
    public ForagerAntSequenceController(Ant passedAnt, MapSpace passedMapSpace, int passedFoodGatherRate,
                                        int passedFoodGatheredCapacity,int passedLeftFoodRate, 
                                        int passedLowestAcceptablePheremoneRate, int passedPheremoneDepositAmount,
                                        int passedPheremoneDepositThreshhold, boolean passedDoesMapNeedToBeUncovered)
    {
        this.setForagerAnt(passedAnt);
        this.setMapSpaceToUse(passedMapSpace);
        this.setFoodGatherRate(passedFoodGatherRate);
        this.setLeftFoodRate(passedLeftFoodRate);
        this.setFoodGatheredCapacity(passedFoodGatheredCapacity);
        this.setLowestAcceptablePheremoneLevel(passedLowestAcceptablePheremoneRate);
        this.setPheremoneDepositAmount(passedPheremoneDepositAmount);
        this.setDoesMapNeedToBeUncovered(passedDoesMapNeedToBeUncovered);
        this.setPeheremoneDepositThreshold(passedPheremoneDepositThreshhold);
        
        getFood = new GatherFood(this.getForagerAnt(), this.getMapSpaceToUse(), this.getFoodGatherRate());
        leaveFood = new LeaveFood(this.getMapSpaceToUse(), this.getLeftFoodRate());
        historyMove = new AntHistoryBasedMove(this.getForagerAnt(), this.getMapSpaceToUse());
        pheremoneMove = new PheremoneBasedMove(this.getForagerAnt(), this.getMapSpaceToUse(), 
                                               this.getLowestAcceptablePheremoneLevel(), 
                                               this.isDoesMapNeedToBeUncovered());
        
    }
    
    //ok first we check if we have any food if on queen square leave food else if so then head back to base
    //otherwise lets check the square we are in and make sure there is no queen in the square
    //if not gather food  
    //else lets pheremone move 
    //
    public void checkStateAndPerformActions()
    {
        //update map space
        //
        this.setMapSpaceToUse(this.getForagerAnt().getCurrentMapSpace());
        getFood.setMapSpaceToGatherFoodFrom(this.getMapSpaceToUse());
        leaveFood.setMapSpaceToLeaveFoodIn(this.getMapSpaceToUse());
        pheremoneMove.setCurrentMapSpace(this.getMapSpaceToUse());
        
        //if we have food then lets figure out what to do with it
        //
        if(getFood.getFoodGathered() >= this.getFoodGatheredCapacity())
        {
            //we are where we need to be do deliver food
            //
            if(this.getMapSpaceToUse().isQueenOnSpace())
            {
                leaveFood.setFoodLeftRate(this.getLeftFoodRate());
                leaveFood.performAction();
                
                //empty our food since we no longer have any
                //
                getFood.setFoodGathered(DEFAULT_EMPTY_VALUE_FOR_FOOD);
                pheremoneMove.performAction();
            }
            else
            {
                if(this.getMapSpaceToUse().getPheremoneAmount() < this.getPeheremoneDepositThreshold())
                {
                    this.getMapSpaceToUse().setPheremoneAmount(this.getMapSpaceToUse().getPheremoneAmount()+
                        this.getPheremoneDepositAmount());
                    this.getMapSpaceToUse().setHasPheremonesBeenDeposited(true);
                }
                historyMove.performAction();
                
            }
        }
        else
        {
            //ok there is food in this space
            //
            if(this.getMapSpaceToUse().getFoodAmount() > DEFAULT_EMPTY_VALUE_FOR_FOOD)
            {
                if(this.getMapSpaceToUse().isQueenOnSpace())
                {
                    pheremoneMove.performAction();
                }
                else
                {
                    getFood.performAction();
                }
            }
            else
            {
                pheremoneMove.performAction();
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
     * @return the foragerAnt
     */
    public Ant getForagerAnt()
    {
        return foragerAnt;
    }
    /**
     * @param foragerAnt the foragerAnt to set
     */
    public void setForagerAnt(Ant foragerAnt)
    {
        this.foragerAnt = foragerAnt;
    }


    /**
     * @return the foodGatherRate
     */
    public int getFoodGatherRate()
    {
        return foodGatherRate;
    }


    /**
     * @param foodGatherRate the foodGatherRate to set
     */
    public void setFoodGatherRate(int foodGatherRate)
    {
        if(foodGatherRate <= DEFAULT_EMPTY_VALUE_FOR_FOOD)
        {
            this.foodGatherRate = DEFAULT_FOOD_GATHER_RATE;
        }
        else
        {
            this.foodGatherRate = foodGatherRate;
        }
        
    }


    /**
     * @return the foodGatheredCapacity
     */
    public int getFoodGatheredCapacity()
    {
        return foodGatheredCapacity;
    }

    /**
     * @param foodGatheredCapacity the foodGatheredCapacity to set
     */
    public void setFoodGatheredCapacity(int foodGatheredCapacity)
    {
        if(foodGatherRate <= DEFAULT_EMPTY_VALUE_FOR_FOOD)
        {
            this.foodGatheredCapacity = DEFAULT_FOOD_CAPACITY;
        }
        else
        {
            this.foodGatheredCapacity = foodGatheredCapacity;
        }
        
    }

    /**
     * @return the leftFoodRate
     */
    public int getLeftFoodRate()
    {
        return leftFoodRate;
    }


    /**
     * @param leftFoodRate the leftFoodRate to set
     */
    public void setLeftFoodRate(int leftFoodRate)
    {
        if(leftFoodRate <= DEFAULT_EMPTY_VALUE_FOR_FOOD)
        {
            this.leftFoodRate = DEFAULT_LEFT_FOOD_RATE;
        }
        else
        {
            this.leftFoodRate = leftFoodRate; 
        }
        
    }


    /**
     * @return the lowestAcceptablePheremoneLevel
     */
    public int getLowestAcceptablePheremoneLevel()
    {
        return lowestAcceptablePheremoneLevel;
    }


    /**
     * @param lowestAcceptablePheremoneLevel the lowestAcceptablePheremoneLevel to set
     */
    public void setLowestAcceptablePheremoneLevel(int lowestAcceptablePheremoneLevel)
    {
        if(lowestAcceptablePheremoneLevel <= DEFAULT_LOW_END_PHEREMONE)
        {
            this.lowestAcceptablePheremoneLevel = DEFAULT_LOWEST_PHEREMONE_RATE;
        }
        else
        {
            this.lowestAcceptablePheremoneLevel = lowestAcceptablePheremoneLevel;
        }
        
    }


    /**
     * @return the doesMapNeedToBeUncovered
     */
    public boolean isDoesMapNeedToBeUncovered()
    {
        return doesMapNeedToBeUncovered;
    }


    /**
     * @param doesMapNeedToBeUncovered the doesMapNeedToBeUncovered to set
     */
    public void setDoesMapNeedToBeUncovered(boolean doesMapNeedToBeUncovered)
    {
        this.doesMapNeedToBeUncovered = doesMapNeedToBeUncovered;
    }

    /**
     * @return the pheremoneDepositAmount
     */
    public int getPheremoneDepositAmount()
    {
        return pheremoneDepositAmount;
    }

    /**
     * @param pheremoneDepositAmount the pheremoneDepositAmount to set
     */
    public void setPheremoneDepositAmount(int pheremoneDepositAmount)
    {
        if(pheremoneDepositAmount <= DEFAULT_LOW_END_PHEREMONE)
        {
            this.pheremoneDepositAmount = DEFAULT_PHEREMONE_DEPOSIT_AMOUNT;  
        }
        else
        {
            this.pheremoneDepositAmount = pheremoneDepositAmount;
        }
        
    }

    /**
     * @return the peheremoneDepositThreshold
     */
    public int getPeheremoneDepositThreshold()
    {
        return peheremoneDepositThreshold;
    }

    /**
     * @param peheremoneDepositThreshold the peheremoneDepositThreshold to set
     */
    public void setPeheremoneDepositThreshold(int peheremoneDepositThreshold)
    {
        if(peheremoneDepositThreshold <= DEFAULT_PHEREMONE_DEPOSIT_THRESHOLD)
        {
            this.peheremoneDepositThreshold = DEFAULT_PHEREMONE_DEPOSIT_THRESHOLD;
        }
        else
        {
            this.peheremoneDepositThreshold = peheremoneDepositThreshold;
        }
        
    }
    
}
