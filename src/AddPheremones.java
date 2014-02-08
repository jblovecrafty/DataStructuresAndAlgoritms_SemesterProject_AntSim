/**
 * This is the add pheremones action class that allows an ant to add pheremones to 
 * a given MapSquare
 */

/**
 * @author joejones
 *
 */
public class AddPheremones implements AntAction
{

    private MapSpace mapSpaceToAddPheremonesTo;
    private Ant antWhoIsAddingPheremones;
    private int addPheremonesRate;
    private static final int MIN_ADD_PHEREMONES_RATE = 0;
    
    //set up constructor here
    //
    public AddPheremones(MapSpace passedMapSpace, Ant passedAnt, int passedAddPheremonesRate)
    {
        this.setAddPheremonesRate(passedAddPheremonesRate);
        this.setAntWhoIsAddingPheremones(passedAnt);
        this.setMapSpaceToAddPheremonesTo(passedMapSpace);
    }
    
    //default action for this class
    //
    public void performAction()
    {
        this.getMapSpaceToAddPheremonesTo().setPheremoneAmount(
                this.getMapSpaceToAddPheremonesTo().getPheremoneAmount() + this.getAddPheremonesRate());
    }

    /**
     * @return the mapSpaceToAddPheremonesTo
     */
    public MapSpace getMapSpaceToAddPheremonesTo()
    {
        return mapSpaceToAddPheremonesTo;
    }

    /**
     * @param mapSpaceToAddPheremonesTo the mapSpaceToAddPheremonesTo to set
     */
    public void setMapSpaceToAddPheremonesTo(MapSpace mapSpaceToAddPheremonesTo)
    {
        this.mapSpaceToAddPheremonesTo = mapSpaceToAddPheremonesTo;
    }

    /**
     * @return the antWhoIsAddingPheremones
     */
    public Ant getAntWhoIsAddingPheremones()
    {
        return antWhoIsAddingPheremones;
    }

    /**
     * @param antWhoIsAddingPheremones the antWhoIsAddingPheremones to set
     */
    public void setAntWhoIsAddingPheremones(Ant antWhoIsAddingPheremones)
    {
        this.antWhoIsAddingPheremones = antWhoIsAddingPheremones;
    }

    /**
     * @return the addPheremonesRate
     */
    public int getAddPheremonesRate()
    {
        return addPheremonesRate;
    }

    /**
     * @param addPheremonesRate the addPheremonesRate to set
     */
    public void setAddPheremonesRate(int addPheremonesRate)
    {
        if(addPheremonesRate <= MIN_ADD_PHEREMONES_RATE)
        {
            this.addPheremonesRate = MIN_ADD_PHEREMONES_RATE;
        }
        else
        {
            this.addPheremonesRate = addPheremonesRate;
        }
    }
    
}
