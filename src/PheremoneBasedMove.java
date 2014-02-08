import java.util.ArrayList;

/**
 * This class is used to handle pheremone based movement meaning that the 
 * object using this will go to the largest pheremone deposit within its
 * valid move radius
 */

/**
 * @author joejones
 *
 */
public class PheremoneBasedMove implements AntAction
{

    //list of constants and vars
    //
    Ant antToMove;
    MapSpace currentMapSpace;
    int lowestAcceptablePheremoneLevel;
    boolean needsToBeUncovered;
    private final int SMALLEST_PHEREMONE_LEVEL = 0;
    private final int SMALLEST_FOOD_LEVEL = 0;
    private RandomBasedMove randomMove;
    
    //constructor here
    //
    public PheremoneBasedMove(Ant passedAnt, MapSpace passedMapSpace, int passedLowestPheremoneLevel, 
                              boolean passedNeedsToBeUncovered)
    {
        this.setAntToMove(passedAnt);
        this.setCurrentMapSpace(passedMapSpace);
        this.setLowestAcceptablePheremoneLevel(passedLowestPheremoneLevel);
        this.setNeedsToBeUncovered(passedNeedsToBeUncovered);
        
        
        randomMove = new RandomBasedMove(this.getAntToMove(), this.getCurrentMapSpace(), 
                true);
    }
    
    //ok lets implement the movement here
    //
    public void performAction()
    {
        //first find a valid set of map objects
        //
        int awareness = this.getAntToMove().getAwarenessRadius();
        ArrayList <MapSpace> validAreas = this.getCurrentMapSpace().listOfValidMapSpace(awareness);
        
        int highestPheremoneCount = 0;
        
        MapSpace mapSpaceToMoveTo = new MapSpace();
        
        //then ignore the one we are on since we could get stuck in this square
        //
        validAreas.remove(this.getCurrentMapSpace());

        //and find the one that is largest and based on boolean needs to be uncovered
        //
        if(validAreas != null)
        {
            for(MapSpace ms : validAreas)
            {
                if(!ms.isQueenOnSpace())
                {
                //ok lets check if being uncovered is an issue
                //
                if((this.isNeedsToBeUncovered() && ms.isDiscovered()) && (ms.getFoodAmount() > SMALLEST_FOOD_LEVEL))
                {
                    if(ms.getPheremoneAmount() > highestPheremoneCount)
                     {
                        mapSpaceToMoveTo = ms;
                        highestPheremoneCount = ms.getPheremoneAmount();
                     }
                }
                else if(!this.isNeedsToBeUncovered() && (ms.getFoodAmount() > SMALLEST_FOOD_LEVEL))
                {
                    if(ms.getPheremoneAmount() > highestPheremoneCount)
                    {
                        mapSpaceToMoveTo = ms;
                        highestPheremoneCount = ms.getPheremoneAmount();
                    }
                }
                
                }
            }
        }
        else
        {
            return;
        }
        
        
        
        //move to that square by first removing the ant from current map square and 
        //then adding ant to new map square
        //
        if(mapSpaceToMoveTo.getPheremoneAmount() != SMALLEST_PHEREMONE_LEVEL)
        {   
            this.getCurrentMapSpace().removeAntOnMapSpace(this.getAntToMove());
            this.setCurrentMapSpace(mapSpaceToMoveTo);
            this.getCurrentMapSpace().addAntOnMapSpace(this.getAntToMove());
            this.getAntToMove().setCurrentMapSpace(this.getCurrentMapSpace());
            this.getAntToMove().addToHistoryOfMapMoves(this.getCurrentMapSpace());
            
        }
        else
        {
            //if we have no pheremones then random it is
            //
            randomMove.performAction();
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
        if(lowestAcceptablePheremoneLevel >= SMALLEST_PHEREMONE_LEVEL)
        {
            this.lowestAcceptablePheremoneLevel = lowestAcceptablePheremoneLevel;
        }
        else
        {
            this.lowestAcceptablePheremoneLevel = SMALLEST_PHEREMONE_LEVEL;
        }
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
