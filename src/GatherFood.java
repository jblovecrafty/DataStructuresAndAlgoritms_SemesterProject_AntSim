/**
 * This is an ant action to gather food from an existing square
 */

/**
 * @author joejones
 *
 */
public class GatherFood implements AntAction
{
    private MapSpace mapSpaceToGatherFoodFrom;
    private Ant antToGatherFood;
    private int foodGathered;
    private int foodGatherRate;
    private static final int MIN_FOOD_AMOUNT = 0;

    
    //set up constructor
    //
    public GatherFood(Ant passedAnt, MapSpace passedMapSpace, int passedFoodGatherRate)
    {
        this.setAntToGatherFood(passedAnt);
        this.setMapSpaceToGatherFoodFrom(passedMapSpace);
        this.setFoodGatherRate(passedFoodGatherRate);
        
    }
    //method to update mapspace's food amount so that it doesnt dip below zero
    //
    public void performAction()
    {
        ///check if there is enough food to gather
        //
        if(this.getMapSpaceToGatherFoodFrom().getFoodAmount() < this.getFoodGatherRate())
        {
            this.setFoodGathered(this.getFoodGathered()+ this.getMapSpaceToGatherFoodFrom().getFoodAmount());
            this.getMapSpaceToGatherFoodFrom().setFoodAmount(MIN_FOOD_AMOUNT);
        }
        else
        {
            this.setFoodGathered(this.getFoodGathered()+ this.getFoodGatherRate());
            
            if(this.getMapSpaceToGatherFoodFrom().getFoodAmount()-this.getFoodGatherRate() >= MIN_FOOD_AMOUNT)
            {
                this.getMapSpaceToGatherFoodFrom().setFoodAmount(this.getMapSpaceToGatherFoodFrom().getFoodAmount()-
                                                             this.getFoodGatherRate());
            }
            else
            {
                this.getMapSpaceToGatherFoodFrom().setFoodAmount(MIN_FOOD_AMOUNT);
            }
        }
    }
    
    /**
     * @return the mapSpaceToGatherFoodFrom
     */
    public MapSpace getMapSpaceToGatherFoodFrom()
    {
        return mapSpaceToGatherFoodFrom;
    }
    /**
     * @param mapSpaceToGatherFoodFrom the mapSpaceToGatherFoodFrom to set
     */
    public void setMapSpaceToGatherFoodFrom(MapSpace mapSpaceToGatherFoodFrom)
    {
        this.mapSpaceToGatherFoodFrom = mapSpaceToGatherFoodFrom;
    }
    /**
     * @return the foodGathered
     */
    public int getFoodGathered()
    {
        return foodGathered;
    }
    /**
     * @param foodGathered the foodGathered to set
     */
    public void setFoodGathered(int foodGathered)
    {
        this.foodGathered = foodGathered;
    }

    /**
     * @return the antToGatherFood
     */
    public Ant getAntToGatherFood()
    {
        return antToGatherFood;
    }

    /**
     * @param antToGatherFood the antToGatherFood to set
     */
    public void setAntToGatherFood(Ant antToGatherFood)
    {
        this.antToGatherFood = antToGatherFood;
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
        this.foodGatherRate = foodGatherRate;
    }
    
}
