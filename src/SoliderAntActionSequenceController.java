import java.util.ArrayList;

/**
 * this is the action sequence controller for the soldier ants
 */

/**
 * @author joejones
 *
 */
public class SoliderAntActionSequenceController implements AntActionSequenceController
{
    private MapSpace mapSpaceToUse;
    private Ant soldierAnt;
    private Ant antWhoIsDefending;
    private boolean doesMovesToMapSpaceNeedUncovering;
    private double lowestAttackChance;
    
    private RandomBasedMove randomMove;
    private ArrayList<MapSpace> mapSqauresAround;
    private Attack attack;
    
    private boolean isEnemyOnSquare = false;

    public SoliderAntActionSequenceController(MapSpace passedMapSpace, Ant passedSoldierAnt, 
                                              boolean passedNeedsMoveToUncoveredMapSpace, double passedLowestAttackChance)
    {
        this.setMapSpaceToUse(passedMapSpace);
        this.setSoldierAnt(passedSoldierAnt);
        this.setDoesMovesToMapSpaceNeedUncovering(passedNeedsMoveToUncoveredMapSpace);
        this.setLowestAttackChance(passedLowestAttackChance);
        
        randomMove = new RandomBasedMove(this.getSoldierAnt(), this.getMapSpaceToUse(), this.isDoesMovesToMapSpaceNeedUncovering());
        attack = new Attack(passedSoldierAnt, null, this.getLowestAttackChance());
    }
    
    //first we check our own square for enemies and if non are found lets look around the squares for enemies
    //and then move to that square
    //
    public void checkStateAndPerformActions()
    {
        //update the map square to the current ants map square
        //
        this.setMapSpaceToUse(this.getSoldierAnt().getCurrentMapSpace());
        randomMove.setCurrentMapSpace(this.getMapSpaceToUse());
        
        //check if enemy is on our square
        //setting ant to null and isEnemyOnSquare to false to start with clean slate
        //
        this.setAntWhoIsDefending(null);
        this.isEnemyOnSquare = false;
        Class<BalaAnt> enemy = BalaAnt.class;
        MapSpace tempMapSpace = null;
        BalaAnt enenmyInQuestion = null;
        boolean enemyFound = false;
        
        //ugh this is not effiecent to check the map square each time
        //
        for (Object antToTest : this.getMapSpaceToUse().getAntsOnMapSpace()) 
        {
            if(!antToTest.equals(this))
            {
                if (enemy.isInstance(antToTest)) 
                {
                    enenmyInQuestion = (BalaAnt)antToTest;
                
                    //check to make sure we dont attack dead ants
                    //
                    if(!enenmyInQuestion.isDead())
                    {
                        this.setAntWhoIsDefending((BalaAnt)antToTest);
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
            //look around for enemy ants and if found move there else random move
            //
            this.mapSqauresAround = this.getMapSpaceToUse().getMapInfoObject().listOfValidMapSquaresBasedOnRadius(
                                                 this.getSoldierAnt().getAwarenessRadius(), 
                                                 this.getMapSpaceToUse());
            
            //now to look inside each mapspace and see if there is any around in valid mapspaces
            //
            for(MapSpace ms : this.mapSqauresAround)
            {
                enenmyInQuestion = null;
                
                //dont test the square we are currently on
                //
                if(!ms.equals(this.getMapSpaceToUse()) && (!enemyFound))
                {
                    if(this.isDoesMovesToMapSpaceNeedUncovering())
                    {
                        if(ms.isDiscovered())
                        {
                            tempMapSpace = ms;
                        }
                    }
                    else
                    {
                        tempMapSpace = ms;
                    }
                
                    //ok now loop thru that map space looking for enemies
                    //
                    //ugh this is not effiecent to check the map square each time
                    //
                    if(tempMapSpace != null)
                    {
                        for (Object antToTest : tempMapSpace.getAntsOnMapSpace()) 
                        {
                            if (enemy.isInstance(antToTest)) 
                            {
                                enenmyInQuestion = (BalaAnt)antToTest;
                                //check to make sure we dont attack dead ants
                                //
                                if(!enenmyInQuestion.isDead())
                                {
                                    this.setAntWhoIsDefending(enenmyInQuestion);
                                    this.isEnemyOnSquare = true;
                                    enemyFound = true;
                                    break;
                                }
                            }
                        }
                    }
                    
                }
                
            }
            
            if(this.isEnemyOnSquare)
            {
                //move to enemy
                //
                this.getMapSpaceToUse().removeAntOnMapSpace(this.getSoldierAnt());
                this.setMapSpaceToUse(tempMapSpace);
                this.getMapSpaceToUse().addAntOnMapSpace(this.getSoldierAnt());
                this.getSoldierAnt().setCurrentMapSpace(this.getMapSpaceToUse());
                this.getSoldierAnt().addToHistoryOfMapMoves(this.getMapSpaceToUse());
                
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
     * @return the soldierAnt
     */
    public Ant getSoldierAnt()
    {
        return soldierAnt;
    }

    /**
     * @param soldierAnt the soldierAnt to set
     */
    public void setSoldierAnt(Ant soldierAnt)
    {
        this.soldierAnt = soldierAnt;
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
    
}
