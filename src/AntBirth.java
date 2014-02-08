import java.util.Random;

/**
 * This class is used to give birth
 */

/**
 * @author joejones
 *
 */
public class AntBirth implements AntAction
{
    //List of supported ant types here
    //
    SoldierAnt soldierAnt;
    ForagerAnt foragerAnt;
    ScoutAnt scoutAnt;
    
    //ant to be born
    //
    Ant createdAnt = null;
    
    private int startId;
    private static final int DEFAULT_START_ID = 0;
    
    private double scoutAntBirthPercentage;
    private double foragerAntBirthPercentage;
    private double soldierAntBirthPercentage;
    
    private static final double DEFAULT_SCOUT_ANT_BIRTH_PERCENTAGE = 0.0;
    private static final double DEFAULT_FORAGER_ANT_BIRTH_PERCENTAGE = 0.0;
    private static final double DEFAULT_SOLDIER_ANT_BIRTH_PERCENTAGE = 0.0;
    
    Random randomValue = new Random();
    
    //constructor here
    //
    public AntBirth(double passedScoutAntBirthPercentage, double passedForagerAntBirthPercentage,
                    double passedSoldierAntBirthPercentage, int passedStartId)
    {
        this.setScoutAntBirthPercentage(passedScoutAntBirthPercentage);
        this.setForagerAntBirthPercentage(passedForagerAntBirthPercentage);
        this.setSoldierAntBirthPercentage(passedSoldierAntBirthPercentage);
        this.setStartId(passedStartId);
    }
    
    public void performAction()
    {
        //lets set up out if statement and see what is born
        //first lets null out the created ant
        //
        this.setCreatedAnt(null);
        
        //increment birthId
        //
        this.setStartId(this.getStartId()+1);
        
        //ok now lets see if we get the number we want 
        //
        double birthChance = randomValue.nextDouble();
        
        if(birthChance <= this.getForagerAntBirthPercentage())
        {
            //create forager and set the ant to setCreatedAnt
            //
            this.foragerAnt = new ForagerAnt();
            this.setCreatedAnt(this.foragerAnt);
        }
        else if((this.getForagerAntBirthPercentage() < birthChance) 
                && (birthChance <= (this.getForagerAntBirthPercentage()+this.getScoutAntBirthPercentage())))
        {
          //create scout and set the ant to setCreatedAnt
          //
          this.scoutAnt = new ScoutAnt();
          this.setCreatedAnt(this.foragerAnt);
        }
        else
        {
          //create soldier and set the ant to setCreatedAnt
          // 
            this.soldierAnt = new SoldierAnt();
            this.setCreatedAnt(this.soldierAnt);
        }
        
    }
    
    /**
     * @return the startId
     */
    public int getStartId()
    {
        return startId;
    }
    /**
     * @param startId the startId to set
     */
    public void setStartId(int startId)
    {
        if(startId <= DEFAULT_START_ID)
        {
            this.startId = DEFAULT_START_ID;
        }
        else
        {
            this.startId = startId;
        }
    }
    /**
     * @return the createdAnt
     */
    public Ant getCreatedAnt()
    {
        return createdAnt;
    }

    /**
     * @param createdAnt the createdAnt to set
     */
    public void setCreatedAnt(Ant createdAnt)
    {
        this.createdAnt = createdAnt;
    }

    /**
     * @return the scoutAntBirthPercentage
     */
    public double getScoutAntBirthPercentage()
    {
        return scoutAntBirthPercentage;
    }
    /**
     * @param scoutAntBirthPercentage the scoutAntBirthPercentage to set
     */
    public void setScoutAntBirthPercentage(double scoutAntBirthPercentage)
    {
        if(scoutAntBirthPercentage <= DEFAULT_SCOUT_ANT_BIRTH_PERCENTAGE)
        {
            this.scoutAntBirthPercentage = DEFAULT_SCOUT_ANT_BIRTH_PERCENTAGE;
        }
        else
        {
            this.scoutAntBirthPercentage = scoutAntBirthPercentage;
        }
    }
    /**
     * @return the foragerAntBirthPercentage
     */
    public double getForagerAntBirthPercentage()
    {
        return foragerAntBirthPercentage;
    }
    /**
     * @param foragerAntBirthPercentage the foragerAntBirthPercentage to set
     */
    public void setForagerAntBirthPercentage(double foragerAntBirthPercentage)
    {
        if(foragerAntBirthPercentage <= DEFAULT_FORAGER_ANT_BIRTH_PERCENTAGE)
        {
            this.foragerAntBirthPercentage = DEFAULT_FORAGER_ANT_BIRTH_PERCENTAGE;
        }
        else
        {
            this.foragerAntBirthPercentage = foragerAntBirthPercentage;
        }

    }
    /**
     * @return the soldierAntBirthPercentage
     */
    public double getSoldierAntBirthPercentage()
    {
        return soldierAntBirthPercentage;
    }
    /**
     * @param soldierAntBirthPercentage the soldierAntBirthPercentage to set
     */
    public void setSoldierAntBirthPercentage(double soldierAntBirthPercentage)
    {
        if(soldierAntBirthPercentage <= DEFAULT_SOLDIER_ANT_BIRTH_PERCENTAGE)
        {
            this.soldierAntBirthPercentage = DEFAULT_SOLDIER_ANT_BIRTH_PERCENTAGE;
        }
        else
        {
            this.soldierAntBirthPercentage = soldierAntBirthPercentage;
        }

    }
    
    
    

}
