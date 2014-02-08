import java.util.Random;

/**
 * This is the class used for one ant to attack another
 */

/**
 * @author joejones
 *
 */
public class Attack implements AntAction
{
    Ant aggressorAnt;
    Ant defenderAnt;
    double lowestAttackValue;
    private static final double DEFAULT_LOWEST_ATTACK_VALUE = 0.0;
    Random randomValue = new Random();
    
    //constructor
    //
    public Attack(Ant passedAgressorAnt, Ant passedDefenderAnt, double passedLowestAttackValue)
    {
        this.setAggressorAnt(passedAgressorAnt);
        this.setDefenderAnt(passedDefenderAnt);
        this.setLowestAttackValue(passedLowestAttackValue);
    }
    
    public void performAction()
    {
        //ok lets take the defender's defense value and subtract it from the attacker's
        //attack value as our chance to kill the defender
        //
        if(!this.getDefenderAnt().isDead())
        {
            double finalAttackValue = this.getAggressorAnt().getCombatSkill() - this.getDefenderAnt().getCombatSkill();
        
            if(finalAttackValue <= this.getLowestAttackValue())
            {
                finalAttackValue = this.getLowestAttackValue();
            }
        
            //ok now lets see if we get the number we want
            //
            double chanceToHit = randomValue.nextDouble();
        
            if( chanceToHit <= finalAttackValue)
            {
                //ok we have a hit so kill the defender ant and null out the pointer to the defender
                //
                this.getDefenderAnt().setDead(true);
                this.setDefenderAnt(null);
            }
        }
    }
    
    /**
     * @return the aggressorAnt
     */
    public Ant getAggressorAnt()
    {
        return aggressorAnt;
    }
    /**
     * @param aggressorAnt the aggressorAnt to set
     */
    public void setAggressorAnt(Ant aggressorAnt)
    {
        this.aggressorAnt = aggressorAnt;
    }
    /**
     * @return the defenderAnt
     */
    public Ant getDefenderAnt()
    {
        return defenderAnt;
    }
    /**
     * @param defenderAnt the defenderAnt to set
     */
    public void setDefenderAnt(Ant defenderAnt)
    {
        this.defenderAnt = defenderAnt;
    }

    /**
     * @return the lowestAttackValue
     */
    public double getLowestAttackValue()
    {
        return lowestAttackValue;
    }

    /**
     * @param lowestAttackValue the lowestAttackValue to set
     */
    public void setLowestAttackValue(double lowestAttackValue)
    {
        if(lowestAttackValue < DEFAULT_LOWEST_ATTACK_VALUE)
        {
            this.lowestAttackValue = DEFAULT_LOWEST_ATTACK_VALUE;
        }
        else
        {
            this.lowestAttackValue = lowestAttackValue;
        }
    }
    
    

}
