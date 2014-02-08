/**
 * AntAction for leaving food
 */

/**
 * @author joejones
 *
 */
public class LeaveFood implements AntAction
{
    private MapSpace mapSpaceToLeaveFoodIn;
    private int foodLeftRate;
    private static final int MIN_FOOD_LEFT = 0;
    
    public LeaveFood(MapSpace passedMapSpace, int passedFoodLeft)
    {
        this.setMapSpaceToLeaveFoodIn(passedMapSpace);
        this.setFoodLeftRate(passedFoodLeft);
    }
    
    
    /**
     * @return the mapSpaceToLeaveFoodIn
     */
    public MapSpace getMapSpaceToLeaveFoodIn()
    {
        return mapSpaceToLeaveFoodIn;
    }
    /**
     * @param mapSpaceToLeaveFoodIn the mapSpaceToLeaveFoodIn to set
     */
    public void setMapSpaceToLeaveFoodIn(MapSpace mapSpaceToLeaveFoodIn)
    {
        this.mapSpaceToLeaveFoodIn = mapSpaceToLeaveFoodIn;
    }
    /**
     * @return the foodLeft
     */
    public int getFoodLeftRate()
    {
        return foodLeftRate;
    }
    /**
     * @param foodLeft the foodLeft to set
     */
    public void setFoodLeftRate(int foodLeftRate)
    {
        if(foodLeftRate <= MIN_FOOD_LEFT)
        {
            this.foodLeftRate = MIN_FOOD_LEFT;
        }
        else
        {
            this.foodLeftRate = foodLeftRate;
        }
    }
    
    public void performAction()
    {
        //add food to mapSquare
        //
        this.getMapSpaceToLeaveFoodIn().setFoodAmount(this.getMapSpaceToLeaveFoodIn().getFoodAmount() + this.getFoodLeftRate());
        
        //now remove food the food we left
        //
        this.setFoodLeftRate(MIN_FOOD_LEFT);
    }
    
}
