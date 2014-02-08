import java.util.ArrayList;
import java.util.Random;

/**
 * This is random movement for only picking colony squares
 */

/**
 * @author joejones
 *
 */
public class RandomColonyBasedMove implements AntAction
{

    //list of constants and vars
    //
    Ant antToMove;
    MapSpace currentMapSpace;
    
    //constructor
    //
    public RandomColonyBasedMove(Ant passedAnt, MapSpace passedMapSpace)
    {
        this.setAntToMove(passedAnt);
        this.setCurrentMapSpace(passedMapSpace);
    }
    
    
    //move take number of squares the ant can move then divide by one over that and that is the 
    //random percentage to use then go to it if it is a colony square
    //
    public void performAction()
    {
        //first find a valid set of map objects
        //
        int awareness = this.getAntToMove().getAwarenessRadius();
        ArrayList <MapSpace> validAreas = this.getCurrentMapSpace().listOfValidMapSpace(awareness);
        MapSpace mapSpaceToMoveTo = null;
        ArrayList <MapSpace> randomValidAreas = new ArrayList <MapSpace>();
        
        //now lets get a random space to move on if we have a valid square
        //
        Random mapSpaceChooser = new Random();
        if(validAreas.size() > 0)
        {
            for(MapSpace ms : validAreas)
                {
                    if(ms.isColonySpace())
                    {
                        randomValidAreas.add(ms);
                    }

                }
                
                validAreas = randomValidAreas;
                
                //lets check if we have anything valid
                //
                if(validAreas.size() > 0)
                {
                    mapSpaceToMoveTo = validAreas.get(mapSpaceChooser.nextInt(validAreas.size()));
                }
                
        }

        if(mapSpaceToMoveTo != null)
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
    
}
