/**
 * AntAction for eating food
 */

/**
 * @author joejones
 *
 */
public class ConsumeFood implements AntAction
{
    private MapSpace mapSpaceToEatFrom;
    private Ant antToFeed;
    private int feedingRate;
    private static final int MIN_FEEDING_RATE = 0;
    
    public ConsumeFood(MapSpace passedMapSpace, Ant passedAnt, int passedFeedingRate)
    {
        this.setAntToFeed(passedAnt);
        this.setMapSpaceToEatFrom(passedMapSpace);
        this.setFeedingRate(passedFeedingRate);
    }

    /**
     * @return the mapSpaceToEatFrom
     */
    public MapSpace getMapSpaceToEatFrom()
    {
        return mapSpaceToEatFrom;
    }

    /**
     * @param mapSpaceToEatFrom the mapSpaceToEatFrom to set
     */
    public void setMapSpaceToEatFrom(MapSpace mapSpaceToEatFrom)
    {
        this.mapSpaceToEatFrom = mapSpaceToEatFrom;
    }

    /**
     * @return the antToFeed
     */
    public Ant getAntToFeed()
    {
        return antToFeed;
    }

    /**
     * @param antToFeed the antToFeed to set
     */
    public void setAntToFeed(Ant antToFeed)
    {
        this.antToFeed = antToFeed;
    }
    
    /**
     * @return the feedingRate
     */
    public int getFeedingRate()
    {
        return feedingRate;
    }

    /**
     * @param feedingRate the feedingRate to set
     */
    public void setFeedingRate(int feedingRate)
    {
        if(feedingRate <= MIN_FEEDING_RATE)
        {
            this.feedingRate = MIN_FEEDING_RATE;
        }
        else
        {
            this.feedingRate = feedingRate;
        }
    }

    //implement feeding method
    //
    public void performAction()
    {
        this.getMapSpaceToEatFrom().setFoodAmount(this.getMapSpaceToEatFrom().getFoodAmount() - this.getFeedingRate());
        this.getAntToFeed().setHealth(this.getFeedingRate()+this.getAntToFeed().getHealth());
    }
    
}
