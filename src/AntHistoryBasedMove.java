/**
 * This move is based on the history of the ant's movements
 */

/**
 * @author joejones
 *
 */
public class AntHistoryBasedMove implements AntAction
{
    //vars and constants here
    //
    Ant antToMove;
    MapSpace currentMapSpace;
    
    //constructor
    //
    public AntHistoryBasedMove(Ant passedAnt, MapSpace passedMapSpace)
    {
        this.setAntToMove(passedAnt);
        this.setCurrentMapSpace(passedMapSpace);
    }
    
    //if the ant has more than one item in its history then take the last one and move the ant
    //otherwise no op
    //
    public void performAction()
    {
        //lets only move the ant if it has more than one item in its history
        //
        if(this.getAntToMove().getHistoryOfMapMoves().size() > 1)
        {   
            //ok remove and get reference to current square
            //then get referece to current square on history list
            //then set remove ant from old square
            //set current ant square to new square and add ant to new square
            //
            MapSpace previousMapSpace = this.getAntToMove().getLastMapMove();
            
            //remove the ant from its current mapspace and decrement the history
            //
            previousMapSpace.removeAntOnMapSpace(this.getAntToMove());
            
            
            //now set the ant's current mapspace to the next item in the map history
            //
            MapSpace newMapSpace = this.getAntToMove().getLastMapMove();
            
            this.setCurrentMapSpace(newMapSpace);           
            this.getCurrentMapSpace().addAntOnMapSpace(this.getAntToMove());
            
            this.getAntToMove().setCurrentMapSpace(newMapSpace);
            this.getAntToMove().addToHistoryOfMapMoves(newMapSpace);
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

}
