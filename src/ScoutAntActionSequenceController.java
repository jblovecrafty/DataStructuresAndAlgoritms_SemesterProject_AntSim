/**
 * Action Sequence controller for the scout ants
 * 
 */

/**
 * @author joejones
 *
 */
public class ScoutAntActionSequenceController implements AntActionSequenceController
{
    //constants and vars here
    private MapSpace mapSpaceToUse;
    private Ant scoutAnt;
    private boolean shouldOnlyTravelToDiscoveredMapSquares;
    
    private RandomBasedMove randomMove;
    
    
    //constructor
    //
    public ScoutAntActionSequenceController(Ant passedAnt, MapSpace passedMapSpace, 
                                            boolean passedShouldOnlyTravelToDiscoveredMapSquares)
    {
        this.setScoutAnt(passedAnt);
        this.setMapSpaceToUse(passedMapSpace);
        this.setShouldOnlyTravelToDiscoveredMapSquares(passedShouldOnlyTravelToDiscoveredMapSquares);
        
        randomMove = new RandomBasedMove(this.getScoutAnt(), this.getMapSpaceToUse(), 
                                        this.isShouldOnlyTravelToDiscoveredMapSquares());
    }
    
    /**
     * This is the action controller and it doesnt really need to do anything other than randomly move
     * and then if the square is hidden to unhide it
     */
    public void checkStateAndPerformActions()
    {
        //update current map space
        //
        this.setMapSpaceToUse(this.getScoutAnt().getCurrentMapSpace());
        randomMove.setCurrentMapSpace(this.getMapSpaceToUse());
        randomMove.performAction();
        
        //check if we need to uncover squares
        //
        if(!this.getScoutAnt().getCurrentMapSpace().isDiscovered())
        {
            this.getScoutAnt().getCurrentMapSpace().setDiscovered(true);
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
     * @return the scoutAnt
     */
    public Ant getScoutAnt()
    {
        return scoutAnt;
    }

    /**
     * @param scoutAnt the scoutAnt to set
     */
    public void setScoutAnt(Ant scoutAnt)
    {
        this.scoutAnt = scoutAnt;
    }

    /**
     * @return the shouldOnlyTravelToDiscoveredMapSquares
     */
    public boolean isShouldOnlyTravelToDiscoveredMapSquares()
    {
        return shouldOnlyTravelToDiscoveredMapSquares;
    }

    /**
     * @param shouldOnlyTravelToDiscoveredMapSquares the shouldOnlyTravelToDiscoveredMapSquares to set
     */
    public void setShouldOnlyTravelToDiscoveredMapSquares(
            boolean shouldOnlyTravelToDiscoveredMapSquares)
    {
        this.shouldOnlyTravelToDiscoveredMapSquares = shouldOnlyTravelToDiscoveredMapSquares;
    }

    
    
    
}
