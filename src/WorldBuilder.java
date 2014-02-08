import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.util.Properties;

/**
 * This is the class that controls the world
 */

/**
 * @author joejones
 *
 */
public class WorldBuilder implements MapInformation
{   
    /*
     * Set up private vars and constants here 
     */
    private int mapSizeOnXYAxis;
    private MapSpace[][] modelOfTheWorld;
    private MapViewAndModelPair[][] mapViewAndModelPairArray;
    private ColonyView colonyView;
    private ArrayList<Ant> antPopulation;
    
    //turn and time based vars here
    //
    private int currentTimeInTurns;
    private int totalTimeInTurns;
    private int dayInTurns;
    private int passedTimeInDays;
    private int passedTimeInYears;
    
    //configuration vars here
    //
    private int mapSize;
    private int maxAntAmount;
    
    private int scoutAntStartAmount;
    private int soldierAntStartAmount;
    private int foragerStartAmount;
    private int queenStartAmount;
    private int balaStartAmount;
    
    private int queenBirthRateInTurns;
    private double scoutAntBirthPercentage;
    private double foragerAntBirthPercentage;
    private double soldierAntBirthPercentage;
    
    private int dayLengthInTurns;
    private int pheremoneDepositAmount;
    private int lowestPheremoneAmount;
    private int pheremoneThresholdAmount;
    
    private double squareFoodChance;
    private int lowEndFoodAmount;
    private int highEndFoodAmount;
    private int initalColonyStartFoodAmount;
    
    private int lowEndPheremoneAmount;
    private int highEndPheremoneAmount;
    private double pheremoneDecrementAmount;
    
    private int queenLifeSpanInTurns;
    private int soliderLifeSpanInTurns;
    private int scoutLifeSpanInTurns;
    private int foragerLifeSpanInTurns;
    private int balaLifeSpanInTurns;
    private int antHealth;
    private int queenHealth;
    private int currentAntID;
    
    private double balaSpawnRate;
    private double balaMapSpawnChance;
    private double balaAttackChance;
    private double soliderAttackChance;
    
    private int foragerFoodCapacity;
    private int foragerFoodGatherRate;
    private int foragerFoodLeaveRate;
    
    private double lowestAttackAmount;
    
    private static final int DEFAULT_MAP_SIZE_ON_XY_AXIS = 10;
    private static final int MAP_XYLOW_BOUND = 0;
    private static final int DEFAULT_START_TIME_IN_TURNS = 0;
    
    Random randomValue = new Random();

    /**
     * Way to initialize the world
     * @param passedNameOfFile
     * @param passedColonyStartX
     * @param passedColonyStartY
     */
    public void initializeWorldWithConfig(String passedNameOfFile, int passedColonyStartX
                                          , int passedColonyStartY)
    {
        //pull in the properities
        //
        this.pullPropertiesFromConfigFile(passedNameOfFile);
        
        //ok now lets create the MapSpaces Array and then populate it
        //
        MapSpace[][] newModelOfTheWorld = new MapSpace[this.getMapSize()][this.getMapSize()];
        this.setModelOfTheWorld(newModelOfTheWorld);
        
        //build colony view
        //
        colonyView = new ColonyView(this.getMapSize(), this.getMapSize());
        mapViewAndModelPairArray = new MapViewAndModelPair[this.getMapSize()][this.getMapSize()];
        
        //ok now lets loop thru and populate the modelOfTheWorld and add MapSpaces to it with
        //
        for(int i = 0; i < this.getMapSize(); i++)
        {
            for(int j = 0; j < this.getMapSize(); j++)
            {
                buildWorldPiece(i, j);          
            }        
            
        }
        
        //now call ant spin up method and place them on the designated colony square
        //
        setUpInitalColonyStartingPlace(passedColonyStartX, passedColonyStartY);
        
        //activate start time
        //
        this.setCurrentTimeInTurns(DEFAULT_START_TIME_IN_TURNS);
        
        System.out.println("Done Initializing");
    }
    
    /**
     * This is the method we call to have the world controller pull its properties from a config file
     * 
     * @param passedNameOfFile
     */
    public void pullPropertiesFromConfigFile(String passedNameOfFile)
    {
        try{
            File inputFile = new File(passedNameOfFile);
            InputStream file = new FileInputStream(inputFile.getAbsolutePath());
            Properties props = new Properties();
            props.load(file);
            
            //read out the properties
            //
            mapSize = Integer.parseInt(props.getProperty("mapSize"));
            mapSizeOnXYAxis = mapSize;
            maxAntAmount = Integer.parseInt(props.getProperty("maxAntAmount"));
            scoutAntStartAmount = Integer.parseInt(props.getProperty("scoutAntStartAmount"));
            soldierAntStartAmount = Integer.parseInt(props.getProperty("soldierAntStartAmount"));
            foragerStartAmount = Integer.parseInt(props.getProperty("foragerStartAmount"));
            queenStartAmount = Integer.parseInt(props.getProperty("queenStartAmount"));
            balaStartAmount = Integer.parseInt(props.getProperty("balaStartAmount"));
            
            queenBirthRateInTurns = Integer.parseInt(props.getProperty("queenBirthRateInTurns"));
            scoutAntBirthPercentage = Double.parseDouble(props.getProperty("scoutAntBirthPercentage"));
            foragerAntBirthPercentage = Double.parseDouble(props.getProperty("foragerAntBirthPercentage"));
            soldierAntBirthPercentage = Double.parseDouble(props.getProperty("soldierAntBirthPercentage"));
            
            dayLengthInTurns  = Integer.parseInt(props.getProperty("dayLengthInTurns"));
            pheremoneDepositAmount  = Integer.parseInt(props.getProperty("pheremoneDepositAmount"));
            lowestPheremoneAmount  = Integer.parseInt(props.getProperty("lowestPheremoneAmount"));
            pheremoneThresholdAmount  = Integer.parseInt(props.getProperty("pheremoneThresholdAmount"));
                
            squareFoodChance =Double.parseDouble(props.getProperty("squareFoodChance"));
            lowEndFoodAmount = Integer.parseInt(props.getProperty("lowEndFoodAmount"));
            highEndFoodAmount = Integer.parseInt(props.getProperty("highEndFoodAmount"));
            initalColonyStartFoodAmount = Integer.parseInt(props.getProperty("initalColonyStartFoodAmount"));
                
            lowEndPheremoneAmount = Integer.parseInt(props.getProperty("lowEndPheremoneAmount")); 
            highEndPheremoneAmount = Integer.parseInt(props.getProperty("highEndPheremoneAmount"));
            pheremoneDecrementAmount = Double.parseDouble(props.getProperty("pheremoneDecrementAmount"));
                
            queenLifeSpanInTurns = Integer.parseInt(props.getProperty("queenLifeSpanInTurns"));
            soliderLifeSpanInTurns = Integer.parseInt(props.getProperty("soliderLifeSpanInTurns"));
            scoutLifeSpanInTurns = Integer.parseInt(props.getProperty("scoutLifeSpanInTurns"));
            foragerLifeSpanInTurns = Integer.parseInt(props.getProperty("foragerLifeSpanInTurns"));
            balaLifeSpanInTurns = Integer.parseInt(props.getProperty("balaLifeSpanInTurns"));

            antHealth = Integer.parseInt(props.getProperty("antHealth"));
            queenHealth = Integer.parseInt(props.getProperty("queenHealth"));
                
            balaSpawnRate = Double.parseDouble(props.getProperty("balaSpawnRate"));
            balaMapSpawnChance = Double.parseDouble(props.getProperty("balaMapSpawnChance"));
            balaAttackChance = Double.parseDouble(props.getProperty("balaAttackChance"));
            soliderAttackChance = Double.parseDouble(props.getProperty("soliderAttackChance"));
            
            foragerFoodCapacity = Integer.parseInt(props.getProperty("foragerFoodCapacity"));
            foragerFoodGatherRate = Integer.parseInt(props.getProperty("foragerFoodGatherRate"));
            foragerFoodLeaveRate = Integer.parseInt(props.getProperty("foragerFoodLeaveRate"));
                
            lowestAttackAmount = Double.parseDouble(props.getProperty("lowestAttackAmount"));
            
            
           } 
        catch(Exception e)
            {
            System.out.println("error" + e);
           }
    }

    /**
     * @return the mapSizeOnXYAxis
     */
    public int getMapSizeOnXYAxis()
    {
        return mapSizeOnXYAxis;
    }

    /**
     * @param mapSizeOnXYAxis the mapSizeOnXYAxis to set
     */
    public void setMapSizeOnXYAxis(int mapSizeOnXYAxis)
    {
        this.mapSizeOnXYAxis = mapSizeOnXYAxis;
    }

    /**
     * @return the modelOfTheWorld
     */
    public MapSpace[][] getModelOfTheWorld()
    {
        return modelOfTheWorld;
    }

    /**
     * @return the totalTimeInTurns
     */
    public int getTotalTimeInTurns()
    {
        return totalTimeInTurns;
    }

    /**
     * @param totalTimeInTurns the totalTimeInTurns to set
     */
    public void setTotalTimeInTurns(int totalTimeInTurns)
    {
        this.totalTimeInTurns = totalTimeInTurns;
    }

    /**
     * @return the dayInTurns
     */
    public int getDayInTurns()
    {
        return dayInTurns;
    }

    /**
     * @param dayInTurns the dayInTurns to set
     */
    public void setDayInTurns(int dayInTurns)
    {
        this.dayInTurns = dayInTurns;
    }

    /**
     * @return the passedTimeInDays
     */
    public int getPassedTimeInDays()
    {
        return this.getCurrentTimeInTurns()/this.getDayLengthInTurns();
    }

    /**
     * @param passedTimeInDays the passedTimeInDays to set
     */
    public void setPassedTimeInDays(int passedTimeInDays)
    {
        this.passedTimeInDays = passedTimeInDays;
    }

    /**
     * @return the passedTimeInYears
     */
    public int getPassedTimeInYears()
    {
        return this.getCurrentTimeInTurns()/(this.getDayLengthInTurns()*365);
    }

    /**
     * @param passedTimeInYears the passedTimeInYears to set
     */
    public void setPassedTimeInYears(int passedTimeInYears)
    {
        this.passedTimeInYears = passedTimeInYears;
    }

    /**
     * @return the mapSize
     */
    public int getMapSize()
    {
        return mapSize;
    }

    /**
     * @param mapSize the mapSize to set
     */
    public void setMapSize(int mapSize)
    {
        this.mapSize = mapSize;
    }

    /**
     * @return the maxAntAmount
     */
    public int getMaxAntAmount()
    {
        return maxAntAmount;
    }

    /**
     * @param maxAntAmount the maxAntAmount to set
     */
    public void setMaxAntAmount(int maxAntAmount)
    {
        this.maxAntAmount = maxAntAmount;
    }

    /**
     * @return the scoutAntStartAmount
     */
    public int getScoutAntStartAmount()
    {
        return scoutAntStartAmount;
    }

    /**
     * @param scoutAntStartAmount the scoutAntStartAmount to set
     */
    public void setScoutAntStartAmount(int scoutAntStartAmount)
    {
        this.scoutAntStartAmount = scoutAntStartAmount;
    }

    /**
     * @return the soldierAntStartAmount
     */
    public int getSoldierAntStartAmount()
    {
        return soldierAntStartAmount;
    }

    /**
     * @param soldierAntStartAmount the soldierAntStartAmount to set
     */
    public void setSoldierAntStartAmount(int soldierAntStartAmount)
    {
        this.soldierAntStartAmount = soldierAntStartAmount;
    }

    /**
     * @return the foragerStartAmount
     */
    public int getForagerStartAmount()
    {
        return foragerStartAmount;
    }

    /**
     * @param foragerStartAmount the foragerStartAmount to set
     */
    public void setForagerStartAmount(int foragerStartAmount)
    {
        this.foragerStartAmount = foragerStartAmount;
    }

    /**
     * @return the queenStartAmount
     */
    public int getQueenStartAmount()
    {
        return queenStartAmount;
    }

    /**
     * @param queenStartAmount the queenStartAmount to set
     */
    public void setQueenStartAmount(int queenStartAmount)
    {
        this.queenStartAmount = queenStartAmount;
    }

    /**
     * @return the balaStartAmount
     */
    public int getBalaStartAmount()
    {
        return balaStartAmount;
    }

    /**
     * @param balaStartAmount the balaStartAmount to set
     */
    public void setBalaStartAmount(int balaStartAmount)
    {
        this.balaStartAmount = balaStartAmount;
    }

    /**
     * @return the queenBirthRateInTurns
     */
    public int getQueenBirthRateInTurns()
    {
        return queenBirthRateInTurns;
    }

    /**
     * @param queenBirthRateInTurns the queenBirthRateInTurns to set
     */
    public void setQueenBirthRateInTurns(int queenBirthRateInTurns)
    {
        this.queenBirthRateInTurns = queenBirthRateInTurns;
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
        this.scoutAntBirthPercentage = scoutAntBirthPercentage;
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
        this.foragerAntBirthPercentage = foragerAntBirthPercentage;
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
        this.soldierAntBirthPercentage = soldierAntBirthPercentage;
    }

    /**
     * @return the dayLengthInTurns
     */
    public int getDayLengthInTurns()
    {
        return dayLengthInTurns;
    }

    /**
     * @param dayLengthInTurns the dayLengthInTurns to set
     */
    public void setDayLengthInTurns(int dayLengthInTurns)
    {
        this.dayLengthInTurns = dayLengthInTurns;
    }

    /**
     * @return the pheremoneDepositAmount
     */
    public int getPheremoneDepositAmount()
    {
        return pheremoneDepositAmount;
    }

    /**
     * @param pheremoneDepositAmount the pheremoneDepositAmount to set
     */
    public void setPheremoneDepositAmount(int pheremoneDepositAmount)
    {
        this.pheremoneDepositAmount = pheremoneDepositAmount;
    }

    /**
     * @return the lowestPheremoneAmount
     */
    public int getLowestPheremoneAmount()
    {
        return lowestPheremoneAmount;
    }

    /**
     * @param lowestPheremoneAmount the lowestPheremoneAmount to set
     */
    public void setLowestPheremoneAmount(int lowestPheremoneAmount)
    {
        this.lowestPheremoneAmount = lowestPheremoneAmount;
    }

    /**
     * @return the pheremoneThresholdAmount
     */
    public int getPheremoneThresholdAmount()
    {
        return pheremoneThresholdAmount;
    }

    /**
     * @param pheremoneThresholdAmount the pheremoneThresholdAmount to set
     */
    public void setPheremoneThresholdAmount(int pheremoneThresholdAmount)
    {
        this.pheremoneThresholdAmount = pheremoneThresholdAmount;
    }

    /**
     * @return the squareFoodChance
     */
    public double getSquareFoodChance()
    {
        return squareFoodChance;
    }

    /**
     * @param squareFoodChance the squareFoodChance to set
     */
    public void setSquareFoodChance(double squareFoodChance)
    {
        this.squareFoodChance = squareFoodChance;
    }

    /**
     * @return the initalColonyStartFoodAmount
     */
    public int getInitalColonyStartFoodAmount()
    {
        return initalColonyStartFoodAmount;
    }

    /**
     * @param initalColonyStartFoodAmount the initalColonyStartFoodAmount to set
     */
    public void setInitalColonyStartFoodAmount(int initalColonyStartFoodAmount)
    {
        this.initalColonyStartFoodAmount = initalColonyStartFoodAmount;
    }

    /**
     * @return the lowEndFoodAmount
     */
    public int getLowEndFoodAmount()
    {
        return lowEndFoodAmount;
    }

    /**
     * @param lowEndFoodAmount the lowEndFoodAmount to set
     */
    public void setLowEndFoodAmount(int lowEndFoodAmount)
    {
        this.lowEndFoodAmount = lowEndFoodAmount;
    }

    /**
     * @return the highEndFoodAmount
     */
    public int getHighEndFoodAmount()
    {
        return highEndFoodAmount;
    }

    /**
     * @param highEndFoodAmount the highEndFoodAmount to set
     */
    public void setHighEndFoodAmount(int highEndFoodAmount)
    {
        this.highEndFoodAmount = highEndFoodAmount;
    }

    /**
     * @return the lowEndPheremoneAmount
     */
    public int getLowEndPheremoneAmount()
    {
        return lowEndPheremoneAmount;
    }

    /**
     * @param lowEndPheremoneAmount the lowEndPheremoneAmount to set
     */
    public void setLowEndPheremoneAmount(int lowEndPheremoneAmount)
    {
        this.lowEndPheremoneAmount = lowEndPheremoneAmount;
    }

    /**
     * @return the highEndPheremoneAmount
     */
    public int getHighEndPheremoneAmount()
    {
        return highEndPheremoneAmount;
    }

    /**
     * @param highEndPheremoneAmount the highEndPheremoneAmount to set
     */
    public void setHighEndPheremoneAmount(int highEndPheremoneAmount)
    {
        this.highEndPheremoneAmount = highEndPheremoneAmount;
    }

    /**
     * @return the pheremoneDecrementAmount
     */
    public double getPheremoneDecrementAmount()
    {
        return pheremoneDecrementAmount;
    }

    /**
     * @param pheremoneDecrementAmount the pheremoneDecrementAmount to set
     */
    public void setPheremoneDecrementAmount(double pheremoneDecrementAmount)
    {
        this.pheremoneDecrementAmount = pheremoneDecrementAmount;
    }

    /**
     * @return the queenLifeSpanInTurns
     */
    public int getQueenLifeSpanInTurns()
    {
        return queenLifeSpanInTurns;
    }

    /**
     * @param queenLifeSpanInTurns the queenLifeSpanInTurns to set
     */
    public void setQueenLifeSpanInTurns(int queenLifeSpanInTurns)
    {
        this.queenLifeSpanInTurns = queenLifeSpanInTurns;
    }

    /**
     * @return the soliderLifeSpanInTurns
     */
    public int getSoliderLifeSpanInTurns()
    {
        return soliderLifeSpanInTurns;
    }

    /**
     * @param soliderLifeSpanInTurns the soliderLifeSpanInTurns to set
     */
    public void setSoliderLifeSpanInTurns(int soliderLifeSpanInTurns)
    {
        this.soliderLifeSpanInTurns = soliderLifeSpanInTurns;
    }

    /**
     * @return the scoutLifeSpanInTurns
     */
    public int getScoutLifeSpanInTurns()
    {
        return scoutLifeSpanInTurns;
    }

    /**
     * @param scoutLifeSpanInTurns the scoutLifeSpanInTurns to set
     */
    public void setScoutLifeSpanInTurns(int scoutLifeSpanInTurns)
    {
        this.scoutLifeSpanInTurns = scoutLifeSpanInTurns;
    }

    /**
     * @return the foragerLifeSpanInTurns
     */
    public int getForagerLifeSpanInTurns()
    {
        return foragerLifeSpanInTurns;
    }

    /**
     * @param foragerLifeSpanInTurns the foragerLifeSpanInTurns to set
     */
    public void setForagerLifeSpanInTurns(int foragerLifeSpanInTurns)
    {
        this.foragerLifeSpanInTurns = foragerLifeSpanInTurns;
    }

    /**
     * @return the balaLifeSpanInTurns
     */
    public int getBalaLifeSpanInTurns()
    {
        return balaLifeSpanInTurns;
    }

    /**
     * @param balaLifeSpanInTurns the balaLifeSpanInTurns to set
     */
    public void setBalaLifeSpanInTurns(int balaLifeSpanInTurns)
    {
        this.balaLifeSpanInTurns = balaLifeSpanInTurns;
    }

    /**
     * @return the antHealth
     */
    public int getAntHealth()
    {
        return antHealth;
    }

    /**
     * @param antHealth the antHealth to set
     */
    public void setAntHealth(int antHealth)
    {
        this.antHealth = antHealth;
    }

    /**
     * @return the queenHealth
     */
    public int getQueenHealth()
    {
        return queenHealth;
    }

    /**
     * @param queenHealth the queenHealth to set
     */
    public void setQueenHealth(int queenHealth)
    {
        this.queenHealth = queenHealth;
    }

    /**
     * @return the balaSpawnRate
     */
    public double getBalaSpawnRate()
    {
        return balaSpawnRate;
    }

    /**
     * @return the currentAntID
     */
    public int getCurrentAntID()
    {
        return currentAntID;
    }

    /**
     * @param currentAntID the currentAntID to set
     */
    public void setCurrentAntID(int currentAntID)
    {
        this.currentAntID = currentAntID;
    }

    /**
     * @param balaSpawnRate the balaSpawnRate to set
     */
    public void setBalaSpawnRate(double balaSpawnRate)
    {
        this.balaSpawnRate = balaSpawnRate;
    }

    /**
     * @return the balaMapSpawnChance
     */
    public double getBalaMapSpawnChance()
    {
        return balaMapSpawnChance;
    }

    /**
     * @param balaMapSpawnChance the balaMapSpawnChance to set
     */
    public void setBalaMapSpawnChance(double balaMapSpawnChance)
    {
        this.balaMapSpawnChance = balaMapSpawnChance;
    }

    /**
     * @return the balaAttackChance
     */
    public double getBalaAttackChance()
    {
        return balaAttackChance;
    }

    /**
     * @param balaAttackChance the balaAttackChance to set
     */
    public void setBalaAttackChance(double balaAttackChance)
    {
        this.balaAttackChance = balaAttackChance;
    }

    /**
     * @return the soliderAttackChance
     */
    public double getSoliderAttackChance()
    {
        return soliderAttackChance;
    }

    /**
     * @param soliderAttackChance the soliderAttackChance to set
     */
    public void setSoliderAttackChance(double soliderAttackChance)
    {
        this.soliderAttackChance = soliderAttackChance;
    }

    /**
     * @return the foragerFoodCapacity
     */
    public int getForagerFoodCapacity()
    {
        return foragerFoodCapacity;
    }

    /**
     * @param foragerFoodCapacity the foragerFoodCapacity to set
     */
    public void setForagerFoodCapacity(int foragerFoodCapacity)
    {
        this.foragerFoodCapacity = foragerFoodCapacity;
    }

    /**
     * @return the foragerFoodGatherRate
     */
    public int getForagerFoodGatherRate()
    {
        return foragerFoodGatherRate;
    }

    /**
     * @param foragerFoodGatherRate the foragerFoodGatherRate to set
     */
    public void setForagerFoodGatherRate(int foragerFoodGatherRate)
    {
        this.foragerFoodGatherRate = foragerFoodGatherRate;
    }

    /**
     * @return the foragerFoodLeaveRate
     */
    public int getForagerFoodLeaveRate()
    {
        return foragerFoodLeaveRate;
    }

    /**
     * @param foragerFoodLeaveRate the foragerFoodLeaveRate to set
     */
    public void setForagerFoodLeaveRate(int foragerFoodLeaveRate)
    {
        this.foragerFoodLeaveRate = foragerFoodLeaveRate;
    }

    /**
     * @return the lowestAttackAmount
     */
    public double getLowestAttackAmount()
    {
        return lowestAttackAmount;
    }

    /**
     * @param lowestAttackAmount the lowestAttackAmount to set
     */
    public void setLowestAttackAmount(double lowestAttackAmount)
    {
        this.lowestAttackAmount = lowestAttackAmount;
    }

    /**
     * @param modelOfTheWorld the modelOfTheWorld to set
     */
    public void setModelOfTheWorld(MapSpace[][] modelOfTheWorld)
    {
        this.modelOfTheWorld = modelOfTheWorld;
    }
    
    public MapSpace getMapSpaceAtLocation(int passedX, int passedY)
    {
        if((isIntWithinMapBounds(passedX)) && (isIntWithinMapBounds(passedY)))
        {
            //System.out.println("Map Space with Xvalue: "+ passedX + " and Y value: " + passedY);
            return this.getModelOfTheWorld()[passedX][passedY];
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Setter for the model of the world array
     * @param passedMapSpace
     * @param passedX
     * @param passedY
     */
    public void setMapSpaceAtLocation(MapSpace passedMapSpace, int passedX, int passedY)
    {
        if((isIntWithinMapBounds(passedX)) && (isIntWithinMapBounds(passedY)))
        {
           this.getModelOfTheWorld()[passedX][passedY] = passedMapSpace;
        }
        
    }
    
    private boolean isIntWithinMapBounds(int passedNumberToCheck)
    {
        boolean isInBounds = false;
        
        if(passedNumberToCheck >= MAP_XYLOW_BOUND && passedNumberToCheck < this.getMapSizeOnXYAxis())
        {
            isInBounds= true;    
        }
        return isInBounds;
    }
    
    /*
     * Map Information Methods here
     */
    
    /**
     * @param currentTimeInTurns the currentTimeInTurns to set
     */
    public void setCurrentTimeInTurns(int currentTimeInTurns)
    {
        this.currentTimeInTurns = currentTimeInTurns;
    }
    
    /**
     * @param currentTimeInTurns the currentTimeInTurns to set
     */
    public int getCurrentTimeInTurns()
    {
        return this.currentTimeInTurns;
    }

    /**
     * Get the size of the map on the X axis
     */
    public int sizeOfMapOnXAxis()
    {
        return this.getMapSizeOnXYAxis();
    }

    /**
     * Get the size of the map on the Y axis
     */
    public int sizeOfMapOnYAxis()
    {
        return this.getMapSizeOnXYAxis();
    }
    
    /***
     * This is a method for handing back a list of valid MapSpaces based on where the ant is and what is its
     * movememnt radius
     */
    public ArrayList <MapSpace> listOfValidMapSquaresBasedOnRadius(int radiusOfMoves, MapSpace centerSpace)
    {
        //ok let us take the x and y of the passed in mapSpace and then use the raidus to build a list of mapSpace
        //objects that we can pass back to the caller. At each point we check if we are with in valid bounds
        //
        ArrayList <MapSpace> listOfValidMoves = new ArrayList<MapSpace>();
        
        for(int i = -radiusOfMoves; i <= radiusOfMoves; i++)
        {
            //set up the x value
            //
            int xValue = centerSpace.getLocationX() + i;
            
            //ok lets make sure that the x value is in range otherwise do nothing
            //
            if(isIntWithinMapBounds(xValue))
            {
                for(int j = -radiusOfMoves; j <= radiusOfMoves; j++)
                {
                    int yValue = centerSpace.getLocationY() + j;
                    
                    if(isIntWithinMapBounds(yValue))
                    {
                        MapSpace mp = getMapSpaceAtLocation(xValue,yValue);
                        if(mp != null)
                        {
                            listOfValidMoves.add(mp);
                        }
                    }
                
                }
            }
        }
        

        return listOfValidMoves;
        
    }
    
    public int getTheCurrentTimeInTurns()
    {
        return this.getCurrentTimeInTurns();
    }
    
    /*Ant Action Squence Controller Creator Methods*/
    
    /**
     * Create SoliderAntActionSequenceController
     * @param passedAnt
     * @param passedMapSpace
     * @return SoliderAntActionSequenceController
     */
    public SoliderAntActionSequenceController createSoliderAntActionSequenceController (Ant passedAnt, MapSpace passedMapSpace)
    {
        
        SoliderAntActionSequenceController soliderAntBehavior = new SoliderAntActionSequenceController(passedMapSpace, 
                passedAnt,true, this.getLowestAttackAmount());
        
        return soliderAntBehavior;
    }
    
    /**
     * Create ScoutAntActionSequenceController
     * @param passedAnt
     * @param passedMapSpace
     * @return ScoutAntActionSequenceController
     */
    public ScoutAntActionSequenceController createScoutAntActionSequenceController(Ant passedAnt, MapSpace passedMapSpace)
    {
        ScoutAntActionSequenceController  scoutAntBehavior = new ScoutAntActionSequenceController(passedAnt, passedMapSpace, false);
        
        return scoutAntBehavior;
    }
    
    /**
     * Create ForagerAntSequenceController
     * @param passedAnt
     * @param passedMapSpace
     * @return ForagerAntSequenceController
     */
    public ForagerAntSequenceController createForagerAntSequenceController(Ant passedAnt, MapSpace passedMapSpace)
    {
        ForagerAntSequenceController foragerAntBehavior = new ForagerAntSequenceController(passedAnt, passedMapSpace, 
                this.getForagerFoodGatherRate(),
                this.getForagerFoodCapacity(),this.getForagerFoodLeaveRate(), 
                this.getLowestPheremoneAmount(), this.getPheremoneDepositAmount(),
                this.getPheremoneThresholdAmount(), true);
        
        return foragerAntBehavior;
    }
    
    /**
     * Create BalaAntActionSequenceController
     * @param passedAnt
     * @param passedMapSpace
     * @return BalaAntActionSequenceController
     */
    public BalaAntActionSequenceController createBalaAntSequenceController(Ant passedAnt, MapSpace passedMapSpace)
    {
        BalaAntActionSequenceController balaAntBehavior = new BalaAntActionSequenceController(passedMapSpace, 
                passedAnt,false, this.getLowestAttackAmount());
        
        return balaAntBehavior;
    }
    
    /*Private Classes*/
    
    /***
     * This is a helper method for generating random food amounts for map squares
     */
    private int getFoodAmountForMapSquare()
    {
        int foodAmount = 0;
        double foodChance = randomValue.nextDouble();
        
        //ok lets check if there is a chance food is on the square
        //
        if(this.getSquareFoodChance() < foodChance)
        {
            foodAmount = randomValue.nextInt(this.getHighEndFoodAmount() - this.getLowEndFoodAmount() + 1) + this.getLowEndFoodAmount();
        }
        
        return foodAmount;
    }
    
    /***
     * This is a helper method for generating random pheremone amounts for map squares
     */
    private int getPheremoneAmountForMapSquare()
    {
        int pheremoneAmount = 0;

        pheremoneAmount = randomValue.nextInt(this.getHighEndPheremoneAmount() - this.getLowEndPheremoneAmount() + 1) + this.getLowEndPheremoneAmount();
        
        return pheremoneAmount;
    }

    /**
     * @return the mapViewAndModelPairArrayList
     */
    public MapViewAndModelPair[][]  getMapViewAndModelPairArray()
    {
        return this.mapViewAndModelPairArray;
    }

    /**
     * @param mapViewAndModelPairArrayList the mapViewAndModelPairArrayList to set
     */
    public void setMapViewAndModelPairArray(MapViewAndModelPair[][] passedMapModelArray)
    {
        this.mapViewAndModelPairArray = passedMapModelArray;
    }
    
    /**
     * Getter for MapViewAndModelPair array
     * @param passedX
     * @param passedY
     * @return
     */
    public MapViewAndModelPair getMapViewAndModelPairAtLocation(int passedX, int passedY)
    {
        if((isIntWithinMapBounds(passedX)) && (isIntWithinMapBounds(passedY)))
        {
            return this.mapViewAndModelPairArray[passedX][passedY];
        }
        else
        {
            return null;
        }
    }
    
    /**
     * Setter for the MapViewAndModelPair array
     * @param MapViewAndModelPair
     * @param passedX
     * @param passedY
     */
    public void setMapViewAndModelPairAtLocation(MapViewAndModelPair passedMapViewAndModelPair, int passedX, int passedY)
    {
        if((isIntWithinMapBounds(passedX)) && (isIntWithinMapBounds(passedY)))
        {
            this.mapViewAndModelPairArray[passedX][passedY] = passedMapViewAndModelPair;
        }
        
    }

    /**
     * @return the antPopulation
     */
    public ArrayList<Ant> getAntPopulation()
    {
        return antPopulation;
    }
    
    public void setAntPopulation(ArrayList<Ant>antPopulation)
    {
        this.antPopulation = antPopulation;
    }

    /**
     * @return the colonyView
     */
    public ColonyView getColonyView()
    {
        return colonyView;
    }

    /**
     * @param colonyView the colonyView to set
     */
    public void setColonyView(ColonyView colonyView)
    {
        this.colonyView = colonyView;
    }

    
    private void setUpInitalColonyStartingPlace(int passedX, int passedY)
    {
        //ok lets get the MapViewAndModelPair and then generate the ants for it
        //
        MapViewAndModelPair mapModel;
        mapModel = mapViewAndModelPairArray[passedX][passedY];
        
        mapModel.getMapSpace().setFoodAmount(this.getInitalColonyStartFoodAmount());
        mapModel.getColonyNodeView().setFoodAmount(this.getInitalColonyStartFoodAmount());
        
        //set up ant array
        //
        ArrayList<Ant> antArray = new ArrayList<Ant>();     
        this.setAntPopulation(antArray);
        
        //ant behavoir set up
        //
        QueenAntActionSequenceController queenAntBehavior;
        SoliderAntActionSequenceController soliderAntBehavior;
        ScoutAntActionSequenceController scoutAntBehavior;
        ForagerAntSequenceController foragerAntBehavior;
        
        this.setCurrentAntID(0);
        
        QueenAnt queenAnt;
        
        //create queen
        //
        for(int i = 0; i < this.getQueenStartAmount(); i++)
        {
            queenAnt = new QueenAnt(mapModel.getMapSpace());
            this.setCurrentAntID(this.getCurrentAntID()+1);
            
            queenAnt.setAntID(this.getCurrentAntID());
            queenAnt.setLifeSpan(this.getQueenLifeSpanInTurns());
            queenAnt.setCurrentAge(0);
            
            queenAntBehavior = new QueenAntActionSequenceController(mapModel.getMapSpace(), queenAnt,
                    1, this.getScoutAntBirthPercentage(),
                    this.getForagerAntBirthPercentage(),
                    this.getSoldierAntBirthPercentage(), 1,
                    this.getQueenBirthRateInTurns());
            
            queenAnt.setAntActionSequenceController(queenAntBehavior);
            mapModel.getMapSpace().addAntOnMapSpace(queenAnt);
            mapModel.getMapSpace().setQueenOnSpace(true);
            mapModel.getColonyNodeView().setQueen(true);
            this.getAntPopulation().add(queenAnt);
            queenAnt.setAwarenessRadius(1);
                  
        }
        
        //create soldier ants
        //
        for(int i =0; i < this.getSoldierAntStartAmount(); i++)
        {
            SoldierAnt ant = new SoldierAnt(mapModel.getMapSpace());
            this.setCurrentAntID(this.getCurrentAntID()+1);
            
            ant.setAntID(this.getCurrentAntID());
            ant.setLifeSpan(this.getSoliderLifeSpanInTurns());
            ant.setCurrentAge(0);
            
            soliderAntBehavior = createSoliderAntActionSequenceController(ant, mapModel.getMapSpace());
            ant.setAntActionSequenceController(soliderAntBehavior);
            
            mapModel.getMapSpace().addAntOnMapSpace(ant);    
            this.getAntPopulation().add(ant);
            ant.setAwarenessRadius(1);
        }
        
        //create scout ants
        //
        for(int i =0; i < this.getScoutAntStartAmount(); i++)
        {
            ScoutAnt ant = new ScoutAnt(mapModel.getMapSpace());
            this.setCurrentAntID(this.getCurrentAntID()+1);
            
            ant.setAntID(this.getCurrentAntID());
            ant.setLifeSpan(this.getScoutLifeSpanInTurns());
            ant.setCurrentAge(0);
            
            scoutAntBehavior = createScoutAntActionSequenceController(ant, mapModel.getMapSpace());
            ant.setAntActionSequenceController(scoutAntBehavior);
            
            mapModel.getMapSpace().addAntOnMapSpace(ant);   
            this.getAntPopulation().add(ant);
            ant.setAwarenessRadius(1);
        }
        
        //create forager ants
        //
        for(int i =0; i < this.getForagerStartAmount(); i++)
        {
            ForagerAnt ant = new ForagerAnt(mapModel.getMapSpace());
            this.setCurrentAntID(this.getCurrentAntID()+1);
            
            ant.setAntID(this.getCurrentAntID());
            ant.setLifeSpan(this.getForagerLifeSpanInTurns());
            ant.setCurrentAge(0);
            
            foragerAntBehavior = createForagerAntSequenceController(ant,mapModel.getMapSpace());
            
            
            ant.setAntActionSequenceController(foragerAntBehavior);
            
            mapModel.getMapSpace().addAntOnMapSpace(ant);     
            this.getAntPopulation().add(ant);
            ant.setAwarenessRadius(1);
        }
        
        //ok now sync up the colonyviewnode
        //
        mapModel.getColonyNodeView().showNode();
        
        mapModel.getColonyNodeView().setForagerCount(this.getForagerStartAmount());
        mapModel.getColonyNodeView().setSoldierCount(this.getSoldierAntStartAmount());
        mapModel.getColonyNodeView().setScoutCount(this.getScoutAntStartAmount());
        
        mapModel.getColonyNodeView().showForagerIcon();
        mapModel.getColonyNodeView().showQueenIcon();
        mapModel.getColonyNodeView().showScoutIcon();
        mapModel.getColonyNodeView().showSoldierIcon();
        
        //ok now show the adjacent nodes
        //
        ArrayList<MapViewAndModelPair> adjacentMS = this.listOfValidMapViewAndModelPairBasedOnRadius(1, mapModel);
        
        for(MapViewAndModelPair mp : adjacentMS)
        {
            mp.getColonyNodeView().showNode();
        }
        
    }
    
    /***
     * Helper method for MapViewAndModelPair
     * @param radiusOfMoves
     * @param centerSpace
     * @return
     */
    public ArrayList <MapViewAndModelPair> listOfValidMapViewAndModelPairBasedOnRadius(int radiusOfMoves, MapViewAndModelPair centerSpace)
    {
        //ok let us take the x and y of the passed in MapViewAndModelPair and then use the raidus to build a list of mapSpace
        //objects that we can pass back to the caller. At each point we check if we are with in valid bounds
        //
        ArrayList <MapViewAndModelPair> listOfValidMoves = new ArrayList<MapViewAndModelPair>();
        
        for(int i = -radiusOfMoves; i <= radiusOfMoves; i++)
        {
            //set up the x value
            //
            int xValue = centerSpace.getMapSpace().getLocationX() + i;
            
            //ok lets make sure that the x value is in range otherwise do nothing
            //
            if(isIntWithinMapBounds(xValue))
            {
                for(int j = -radiusOfMoves; j <= radiusOfMoves; j++)
                {
                    int yValue = centerSpace.getMapSpace().getLocationY() + j;
                    
                    if(isIntWithinMapBounds(yValue))
                    {
                        MapViewAndModelPair mp = getMapViewAndModelPairAtLocation(xValue,yValue);
                        if(mp != null)
                        {
                            listOfValidMoves.add(mp);
                        }
                    }
                
                }
            }
        }
        

        return listOfValidMoves;
        
    }
    
    /**
     * This is a helper method to store the logic for building one piece of the model
     * and the view for the simulation
     * @param passedX
     * @param passedY
     */
    private void buildWorldPiece(int passedX, int passedY)
    {
        //build out the MapSpace objects and load them into the array with
        //attendent x and y values with ids as well
        //
        MapSpace mapSpace = new MapSpace();
        mapSpace.setLocationX(passedX);
        mapSpace.setLocationY(passedY);
        mapSpace.setMapInfoObject(this);
        
        
        //ok now populate the pheremones and the food values
        //
        mapSpace.setFoodAmount(getFoodAmountForMapSquare());
        mapSpace.setPheremoneAmount(0);
        
        this.setMapSpaceAtLocation(mapSpace, mapSpace.getLocationX(), mapSpace.getLocationY());
        
        //now build out colony nodes
        //
        ColonyNodeView  colonyNodeView = new ColonyNodeView();
        colonyNodeView.hideNode();
        colonyNodeView.setID(mapSpace.getLocationX() + "," + mapSpace.getLocationY());
        colonyNodeView.setFoodAmount(mapSpace.getFoodAmount());
        colonyNodeView.setPheromoneLevel(mapSpace.getPheremoneAmount());
        
        colonyView.addColonyNodeView(colonyNodeView, mapSpace.getLocationX(), mapSpace.getLocationY());
        
        //now link the two together (map spaces and colony nodes)
        //
        MapViewAndModelPair mapViewAndModel = new MapViewAndModelPair(mapSpace, colonyNodeView);
        mapViewAndModelPairArray[mapSpace.getLocationX()][mapSpace.getLocationY()] = mapViewAndModel;
    }
}
