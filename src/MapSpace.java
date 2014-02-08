import java.util.ArrayList;
/**
 * This is the in model representation of a unit of physical space in the 
 * simulation
 */

/**
 * @author joejones
 *
 */
public class MapSpace
{
    //id for this MapSpace unit
    //
    private int mapSpaceID;
    
    //location info
    //
    private int locationX;
    private int locationY;
    
    //what is on the map space 
    //
    private int foodAmount;
    private int pheremoneAmount;
    private ArrayList<Ant> antsOnMapSpace; 
    
    //represents if has been uncovered in simulation
    //
    private boolean isDiscovered;
   
    //is there a queen on this space
    //
    private boolean isQueenOnSpace;
    
    //is this the colony space
    //
    private boolean isColonySpace;
    
    //has this space been visited with pheremones before
    //
    private boolean hasPheremonesBeenDeposited;
    
    //minimum food and pheremone value for a space
    //
    private static final int MIN_FOOD_AMOUNT =0;
    private static final int MIN_PHEREMONE_AMOUNT = 0;
    
    //hold reference to mapInformation Object
    //
    private MapInformation mapInfoObject;
    
    //set up constructor here to have food, pheremone and map information objects here
    //
    public MapSpace()
    {
        this.antsOnMapSpace = new ArrayList<Ant> ();
        this.setQueenOnSpace(false);
        this.setColonySpace(false);
        this.setDiscovered(false);
        this.setHasPheremonesBeenDeposited(false);
    }

    /**
     * @return the mapSpaceID
     */
    public int getMapSpaceID()
    {
        return mapSpaceID;
    }

    /**
     * @param mapSpaceID the mapSpaceID to set
     */
    public void setMapSpaceID(int mapSpaceID)
    {
        this.mapSpaceID = mapSpaceID;
    }

    /**
     * @return the locationX
     */
    public int getLocationX()
    {
        return locationX;
    }

    /**
     * @param locationX the locationX to set
     */
    public void setLocationX(int locationX)
    {
        this.locationX = locationX;
    }

    /**
     * @return the locationY
     */
    public int getLocationY()
    {
        return locationY;
    }

    /**
     * @param locationY the locationY to set
     */
    public void setLocationY(int locationY)
    {
        this.locationY = locationY;
    }

    /**
     * @return the foodAmount
     */
    public int getFoodAmount()
    {
        return foodAmount;
    }

    /**
     * @param foodAmount the foodAmount to set
     */
    public void setFoodAmount(int foodAmount)
    {
        if(foodAmount <= MIN_FOOD_AMOUNT)
        {
            this.foodAmount = MIN_FOOD_AMOUNT;
        }
        else
        {
            this.foodAmount = foodAmount;
        }
    }

    /**
     * @return the pheremoneAmount
     */
    public int getPheremoneAmount()
    {
        return pheremoneAmount;
    }

    /**
     * @param pheremoneAmount the pheremoneAmount to set
     */
    public void setPheremoneAmount(int pheremoneAmount)
    {
        if(pheremoneAmount <= MIN_PHEREMONE_AMOUNT)
        {
            this.pheremoneAmount = MIN_PHEREMONE_AMOUNT;
        }
        else
        {
            this.pheremoneAmount = pheremoneAmount;
        }
    }

    /**
     * @return the antsOnMapSpace
     */
    public ArrayList<Ant> getAntsOnMapSpace()
    {
        return antsOnMapSpace;
    }

    /**
     * @param antsOnMapSpace the antsOnMapSpace to set
     */
    public void setAntsOnMapSpace(ArrayList<Ant> antsOnMapSpace)
    {
        this.antsOnMapSpace = antsOnMapSpace;
    }
    
    public void addAntOnMapSpace(Ant passedAnt)
    {
        if(!this.antsOnMapSpace.contains(passedAnt))
        {
            this.antsOnMapSpace.add(passedAnt);
        }
    }
    
    public void removeAntOnMapSpace(Ant passedAnt)
    {
        this.antsOnMapSpace.remove(passedAnt);
    }

    /**
     * @return the isDiscovered
     */
    public boolean isDiscovered()
    {
        return isDiscovered;
    }

    /**
     * @param isDiscovered the isDiscovered to set
     */
    public void setDiscovered(boolean isDiscovered)
    {
        this.isDiscovered = isDiscovered;
    }

    /**
     * @return the isQueenOnSpace
     */
    public boolean isQueenOnSpace()
    {
        return isQueenOnSpace;
    }

    /**
     * @param isQueenOnSpace the isQueenOnSpace to set
     */
    public void setQueenOnSpace(boolean isQueenOnSpace)
    {
        this.isQueenOnSpace = isQueenOnSpace;
    }

    /**
     * @return the isColonySpace
     */
    public boolean isColonySpace()
    {
        return isColonySpace;
    }

    /**
     * @param isColonySpace the isColonySpace to set
     */
    public void setColonySpace(boolean isColonySpace)
    {
        this.isColonySpace = isColonySpace;
    }

    /**
     * @return the hasPheremonesBeenDeposited
     */
    public boolean isHasPheremonesBeenDeposited()
    {
        return hasPheremonesBeenDeposited;
    }

    /**
     * @param hasPheremonesBeenDeposited the hasPheremonesBeenDeposited to set
     */
    public void setHasPheremonesBeenDeposited(boolean hasPheremonesBeenDeposited)
    {
        this.hasPheremonesBeenDeposited = hasPheremonesBeenDeposited;
    }

    /**
     * @return the mapInfoObject
     */
    public MapInformation getMapInfoObject()
    {
        return mapInfoObject;
    }

    /**
     * @param mapInfoObject the mapInfoObject to set
     */
    public void setMapInfoObject(MapInformation mapInfoObject)
    {
        this.mapInfoObject = mapInfoObject;
    }
    
    /**
     * Get an array list of valid MapSpace Objects
     * @param passedRadius
     * @return
     */
    public ArrayList <MapSpace> listOfValidMapSpace(int passedRadius)
    {
        ArrayList <MapSpace> validAreas = new ArrayList <MapSpace>();
        validAreas = this.getMapInfoObject().listOfValidMapSquaresBasedOnRadius(passedRadius, this);
        
        if(validAreas.isEmpty() || validAreas == null)
        {
            System.out.println("Array is empty or null");
        }
        
        return validAreas;
    }
    
    /**
     * Gets the current time in turns from the mapInfoObject
     * @return
     */
    public int getCurrentTimeInTurns()
    {
        return this.getMapInfoObject().getTheCurrentTimeInTurns();
    }
    
}
