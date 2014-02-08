import java.util.ArrayList;
import java.util.Random;

/**
 * This is the class for a randomly moving ant
 */

/**
 * @author joejones
 *
 */
public class RandomBasedMove implements AntAction
{
    //list of constants and vars
    //
    Ant antToMove;
    MapSpace currentMapSpace;
    boolean needsToBeUncovered;
    Random mapSpaceChooser = new Random();
    
    //set up constructor here
    //
    public RandomBasedMove(Ant passedAnt, MapSpace passedMapSpace, boolean passedNeedsToBeUncovered)
    {
        this.setAntToMove(passedAnt);
        this.setCurrentMapSpace(passedMapSpace);
        this.setNeedsToBeUncovered(passedNeedsToBeUncovered);
    }
    
    //move take number of squares the ant can move then divide by one over that and that is the 
    //random percentage to use then go to it
    //
    public void performAction()
    {
        //first find a valid set of map objects
        //
        ArrayList <MapSpace> validAreas = null;
        
        validAreas = this.getCurrentMapSpace().listOfValidMapSpace(this.getAntToMove().getAwarenessRadius());
        
        
        MapSpace mapSpaceToMoveTo = null;
        ArrayList <MapSpace> randomValidAreas = new ArrayList <MapSpace>();
        
        
        if(validAreas.size() > 0 && validAreas != null && (!validAreas.isEmpty()))
        {
            if(this.isNeedsToBeUncovered())
            {
                //ok now we have to make sure that the space is uncovered before moving
                //so remove all of the map squares that are not uncovered
                //
                for(MapSpace ms : validAreas)
                {
                    if(ms.isDiscovered())
                    {
                        randomValidAreas.add(ms);
                    }

                }
                
                
                validAreas = randomValidAreas;
                
                //lets check if we have anything valid
                //
                if(validAreas.size() > 0 && (!validAreas.isEmpty()))
                {
                    mapSpaceToMoveTo = validAreas.get(mapSpaceChooser.nextInt(validAreas.size()));  
                }
                
            }
            else
            {   
                //lets check if we have anything valid
                //
                if(validAreas.size() > 0 && (!validAreas.isEmpty()))
                {
                    mapSpaceToMoveTo = validAreas.get(mapSpaceChooser.nextInt(validAreas.size()));
                    
                }
                
            }
        }
        
        if(mapSpaceToMoveTo != null && (validAreas.size() > 0 && (!validAreas.isEmpty())))
        {
            this.getCurrentMapSpace().removeAntOnMapSpace(this.getAntToMove());
            this.setCurrentMapSpace(mapSpaceToMoveTo);
            this.getCurrentMapSpace().addAntOnMapSpace(this.getAntToMove());
            this.getAntToMove().setCurrentMapSpace(this.getCurrentMapSpace());
            this.getAntToMove().addToHistoryOfMapMoves(this.getCurrentMapSpace());
        }
    }
    
    /**
     * @return the antToMove
     */
    public Ant getAntToMove()
    {
        return antToMove;
    }
    /**
     * @param antToMove the antToMove to set
     */
    public void setAntToMove(Ant antToMove)
    {
        this.antToMove = antToMove;
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
     * @return the needsToBeUncovered
     */
    public boolean isNeedsToBeUncovered()
    {
        return needsToBeUncovered;
    }
    /**
     * @param needsToBeUncovered the needsToBeUncovered to set
     */
    public void setNeedsToBeUncovered(boolean needsToBeUncovered)
    {
        this.needsToBeUncovered = needsToBeUncovered;
    }

}
