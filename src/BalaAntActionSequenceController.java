
/**
 * This is the action sequence controller for the bala ants
 */

/**
 * @author joejones
 *
 */
public class BalaAntActionSequenceController implements AntActionSequenceController
{
    private MapSpace mapSpaceToUse;
    private Ant balaAnt;
    private Ant antWhoIsDefending;
    private boolean doesMovesToMapSpaceNeedUncovering;
    private double lowestAttackChance;
    
    private RandomBasedMove randomMove;
    private RandomColonyBasedMove colonyMove;
    private Attack attack;
    
    private boolean isEnemyOnSquare = false;
    
    /**
     * Constructor for Bala Ant
     * @param passedMapSpace
     * @param passedBalaAnt
     * @param passedNeedsMoveToUncoveredMapSpace
     * @param passedLowestAttackChance
     */
    public BalaAntActionSequenceController(MapSpace passedMapSpace, Ant passedBalaAnt, 
            boolean passedNeedsMoveToUncoveredMapSpace, double passedLowestAttackChance)
    {
        this.setMapSpaceToUse(passedMapSpace);
        this.setBalaAnt(passedBalaAnt);
        this.setDoesMovesToMapSpaceNeedUncovering(passedNeedsMoveToUncoveredMapSpace);
        this.setLowestAttackChance(passedLowestAttackChance);

        randomMove = new RandomBasedMove(this.getBalaAnt(), this.getMapSpaceToUse(), this.isDoesMovesToMapSpaceNeedUncovering());
        colonyMove = new RandomColonyBasedMove(this.getBalaAnt(), this.getMapSpaceToUse());
        attack = new Attack(passedBalaAnt, null, this.getLowestAttackChance());
    }
    
    //first we check our own square for enemies and if non are found lets look around the squares for enemies
    //and then move to that square
    //if not then either random move around the board or if in colony random move in colony
    //
    public void checkStateAndPerformActions()
    {
        //update mapspace
        //
        this.setMapSpaceToUse(this.getBalaAnt().getCurrentMapSpace());
        randomMove.setCurrentMapSpace(this.getMapSpaceToUse());
        colonyMove.setCurrentMapSpace(this.getMapSpaceToUse());
        
        //check if enemy is on our square
        //setting ant to null and isEnemyOnSquare to false to start with clean slate
        //
        this.setAntWhoIsDefending(null);
        this.isEnemyOnSquare = false;
        Class<BalaAnt> freindly = BalaAnt.class;
        
        Ant enenmyInQuestion = null;
        
        //ugh this is not effiecent to check the map square each time
        //
        for (Object antToTest : this.getMapSpaceToUse().getAntsOnMapSpace()) 
        {
            if(!antToTest.equals(this))
            {
                if (!freindly.isInstance(antToTest)) 
                {
                    
                    enenmyInQuestion = (Ant)antToTest;
                
                    //check to make sure we dont attack dead ants
                    //
                    if(!enenmyInQuestion.isDead())
                    {
                        this.setAntWhoIsDefending(enenmyInQuestion);
                        this.isEnemyOnSquare = true;
                    }
                }
            }
         }
        
        if(this.isEnemyOnSquare)
        {
            attack.setDefenderAnt(this.getAntWhoIsDefending());
            attack.performAction();
        }
        else
        {
            
            if(this.getMapSpaceToUse().isColonySpace())
            {
                //move in the colony
                //
                this.colonyMove.performAction();
                
            }
            else
            {
                //random move
                //
                this.randomMove.performAction();
            }
            
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
     * @return the balaAnt
     */
    public Ant getBalaAnt()
    {
        return balaAnt;
    }

    /**
     * @param balaAnt the balaAnt to set
     */
    public void setBalaAnt(Ant balaAnt)
    {
        this.balaAnt = balaAnt;
    }

    /**
     * @return the doesMovesToMapSpaceNeedUncovering
     */
    public boolean isDoesMovesToMapSpaceNeedUncovering()
    {
        return doesMovesToMapSpaceNeedUncovering;
    }

    /**
     * @param doesMovesToMapSpaceNeedUncovering the doesMovesToMapSpaceNeedUncovering to set
     */
    public void setDoesMovesToMapSpaceNeedUncovering(
            boolean doesMovesToMapSpaceNeedUncovering)
    {
        this.doesMovesToMapSpaceNeedUncovering = doesMovesToMapSpaceNeedUncovering;
    }

    /**
     * @return the lowestAttackChance
     */
    public double getLowestAttackChance()
    {
        return lowestAttackChance;
    }

    /**
     * @param lowestAttackChance the lowestAttackChance to set
     */
    public void setLowestAttackChance(double lowestAttackChance)
    {
        this.lowestAttackChance = lowestAttackChance;
    }

    /**
     * @return the antWhoIsDefending
     */
    public Ant getAntWhoIsDefending()
    {
        return antWhoIsDefending;
    }

    /**
     * @param antWhoIsDefending the antWhoIsDefending to set
     */
    public void setAntWhoIsDefending(Ant antWhoIsDefending)
    {
        this.antWhoIsDefending = antWhoIsDefending;
    }
    
}
