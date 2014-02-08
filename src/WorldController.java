import java.util.ArrayList;
import java.util.Random;

/**
 * This is the class that controls the ant simulator
 */

/**
 * @author joejones
 *
 */
public class WorldController implements SimulationEventListener
{   
    //vars and constants here
    //
    private WorldBuilder builtWorld;
    private String initWorldFile;
    private WorldInformation worldInfo;
    private int colonyStartX;
    private int colonyStartY;
    private int currentTimeInTurns;
    
    private boolean stopSimulation;
    Random randomValue = new Random();
    
    //constructor
    //
    public WorldController(String passedInitFile, int passedColonyX, int passedColonyY)
    {
        builtWorld = new WorldBuilder();
        
        initWorldFile = passedInitFile;
        colonyStartX = passedColonyX;
        colonyStartY = passedColonyY;
        this.setCurrentTimeInTurns(0);
        this.setStopSimulation(false);
        
        //ok now initialize the world
        //
        initializeWorld();
    }
    
    //helper method to initialize the ant world
    //
    private void initializeWorld()
    {
        builtWorld.initializeWorldWithConfig(initWorldFile,colonyStartX, colonyStartY);
        this.setCurrentTimeInTurns(0);
        System.out.println("initializing World"); 
    }
    
    //turn method for the world
    //
    public void worldTurn()
    {
        //increment time
        //
        this.setCurrentTimeInTurns(this.getCurrentTimeInTurns()+1);
        this.getBuiltWorld().setCurrentTimeInTurns(this.getCurrentTimeInTurns());
        
        MapViewAndModelPair mapViewAndModelPair;
        
        boolean balaAntSpawned = false;
        
        Class<QueenAnt> queenClass = QueenAnt.class;
        Class<ForagerAnt> foragerClass = ForagerAnt.class;
        Class<SoldierAnt> soldierClass = SoldierAnt.class;
        Class<ScoutAnt> scoutClass = ScoutAnt.class;
        Class<BalaAnt> balaClass = BalaAnt.class;
        
        ArrayList<Ant> deadAnt = new ArrayList<Ant>();
        
        QueenAnt queenAnt = null;
        BalaAnt balaAnt = null;
        
        //loop thru the array of ants
        //
        for(Ant ant : this.getBuiltWorld().getAntPopulation())
        {
            ant.setCurrentAge(1);
            
            //if ant is not dead then lets do things
            //
            if(ant.isDead())
            {
                //ok if the queen is dead then we are done
                //
                if(queenClass.isInstance(ant))
                {
                    System.out.println("Queen Is Dead");
                    this.setStopSimulation(true);
                    this.getWorldInfo().hasWorldStopped(this.isStopSimulation());
                }
                
                deadAnt.add(ant);
            }
            else
            {
                ant.getAntActionSequenceController().checkStateAndPerformActions();
                
                //if a queen then then set queenAnt to the ant so we can handle birthing behavior
                //
                if(queenClass.isInstance(ant))
                { 
                    queenAnt = (QueenAnt)ant;                   
                   
                }
            }
        }
        
        //add baby ant to list
        //
        if((queenAnt != null && (!queenAnt.isDead())) && (this.getCurrentTimeInTurns()%this.getBuiltWorld().getQueenBirthRateInTurns() == 0))
        {
            
            //ok we have a baby we need to build out
            //
            QueenAntActionSequenceController queenAction = null;
            queenAction = (QueenAntActionSequenceController)queenAnt.getAntActionSequenceController();
            
            Ant babyAnt = null;
            babyAnt = queenAction.getBirthedAnt();
            
            if(babyAnt != null)
            { 
                //Ugly big if statement
                //
                if(foragerClass.isInstance(babyAnt))
                {
                    babyAnt.setAntActionSequenceController(this.getBuiltWorld().createForagerAntSequenceController(babyAnt, queenAnt.getCurrentMapSpace()));
                    babyAnt.setLifeSpan(this.getBuiltWorld().getForagerLifeSpanInTurns());
                    System.out.println("Forager Born"); 
                    
                }
                else if(soldierClass.isInstance(babyAnt))
                {
                    babyAnt.setAntActionSequenceController(this.getBuiltWorld().createSoliderAntActionSequenceController(babyAnt, queenAnt.getCurrentMapSpace()));
                    babyAnt.setLifeSpan(this.getBuiltWorld().getSoliderLifeSpanInTurns());
                    System.out.println("Soldier Born"); 
                }
                else if(scoutClass.isInstance(babyAnt))
                {
                    babyAnt.setAntActionSequenceController(this.getBuiltWorld().createScoutAntActionSequenceController(babyAnt, queenAnt.getCurrentMapSpace()));
                    babyAnt.setLifeSpan(this.getBuiltWorld().getScoutLifeSpanInTurns());
                    System.out.println("Scout Born"); 
                }
                
                //set up the baby ant
                //
                this.getBuiltWorld().setCurrentAntID(this.getBuiltWorld().getCurrentAntID()+1);
                babyAnt.setAntID(this.getBuiltWorld().getCurrentAntID());
                babyAnt.setCurrentAge(1);
                babyAnt.setAwarenessRadius(1);
                babyAnt.setCurrentMapSpace(queenAnt.getCurrentMapSpace());
                queenAnt.getCurrentMapSpace().addAntOnMapSpace(babyAnt); 
                this.getBuiltWorld().getAntPopulation().add(babyAnt);
            }
            
        }
        
        //take care of dead ants
        //
        if(deadAnt.size() > 0)
        {
            for(Ant ant : deadAnt)
            {
                ant.getCurrentMapSpace().removeAntOnMapSpace(ant);
                this.getBuiltWorld().getAntPopulation().remove(ant);
                ant = null;
                
            }
        }
        
        deadAnt.clear();
        
        //let us see if we have spawned a bala ant this turn and if not and
        //we arent on a colony square then spawn
        //
        if((!balaAntSpawned && (isTimeForBalaAntSpawning())))
        {
            balaAntSpawned = true;
        }
        
        //loop thru the array of MapSpace Objects
        //
        for(int i = 0; i < this.getBuiltWorld().getMapSizeOnXYAxis(); i++)
        {
            for(int j = 0; j < this.getBuiltWorld().getMapSizeOnXYAxis(); j++)
            {
                mapViewAndModelPair = this.getBuiltWorld().getMapViewAndModelPairAtLocation(i, j);

                //keep counts of queens, foragers, soldiers, scouts and bala ants
                //
                int queenCount = 0;
                int foragerCount = 0;
                int soldierCount = 0;
                int scoutCount = 0;
                int balaCount = 0;
                
                //ok decrement the pheremones if we are in a day
                //
                if(this.getCurrentTimeInTurns()%this.getBuiltWorld().getDayLengthInTurns() == 0)
                {
                    mapViewAndModelPair.getMapSpace().setPheremoneAmount( (int)(mapViewAndModelPair.getMapSpace().getPheremoneAmount()
                                                                        * this.getBuiltWorld().getPheremoneDecrementAmount()));
                    
                    if(mapViewAndModelPair.getMapSpace().getPheremoneAmount() <= 0 && (mapViewAndModelPair.getMapSpace().isHasPheremonesBeenDeposited()))
                    {
                        mapViewAndModelPair.getMapSpace().setPheremoneAmount(this.getBuiltWorld().getLowEndPheremoneAmount());
                    }
                }
                
                
                //let us see if we have spawned a bala ant this turn and if not and
                //we arent on a colony square then spawn
                //
                if((balaAntSpawned && (isMapSpaceForBalaAntSpawning())) && (!mapViewAndModelPair.getMapSpace().isColonySpace()))
                {
                    balaAnt = new BalaAnt(mapViewAndModelPair.getMapSpace());
                    mapViewAndModelPair.getMapSpace().addAntOnMapSpace(balaAnt);
                    balaAnt.setCurrentAge(0);
                    balaAntSpawned = false;
                    balaAnt.setAwarenessRadius(1);
                    balaAnt.setLifeSpan(this.getBuiltWorld().getBalaLifeSpanInTurns());
                    balaAnt.setAntActionSequenceController(this.getBuiltWorld().createBalaAntSequenceController(balaAnt, mapViewAndModelPair.getMapSpace()));
                    this.getBuiltWorld().setCurrentAntID(this.getBuiltWorld().getCurrentAntID()+1);
                    balaAnt.setAntID(this.getBuiltWorld().getCurrentAntID());
                    this.getBuiltWorld().getAntPopulation().add(balaAnt);
                    System.out.println("Bala Spawned Ant ID:" + balaAnt.getAntID()); 
                }
                
                //loop thru all the ants in the square
                //
                for(Ant ant : mapViewAndModelPair.getMapSpace().getAntsOnMapSpace())
                {
                    //clear out ants 
                    //
                    
                    //increment the ant count here
                    //
                    if(foragerClass.isInstance(ant))
                    {
                        foragerCount ++;
                    }
                    else if(soldierClass.isInstance(ant))
                    {
                        soldierCount ++;
                    }
                    else if(scoutClass.isInstance(ant))
                    {
                        scoutCount ++;
                    }
                    else if(queenClass.isInstance(ant))
                    {
                        queenCount++;
                    }
                    else if(balaClass.isInstance(ant))
                    {
                        balaCount ++;
                    }

                }
                
                //update the ant state in the colony view
                //update food and pheremone state
                //
                mapViewAndModelPair.getColonyNodeView().setForagerCount(foragerCount);
                mapViewAndModelPair.getColonyNodeView().setScoutCount(scoutCount);
                mapViewAndModelPair.getColonyNodeView().setSoldierCount(soldierCount);
                mapViewAndModelPair.getColonyNodeView().setBalaCount(balaCount);
                
                mapViewAndModelPair.getColonyNodeView().setFoodAmount(mapViewAndModelPair.getMapSpace().getFoodAmount());
                mapViewAndModelPair.getColonyNodeView().setPheromoneLevel(mapViewAndModelPair.getMapSpace().getPheremoneAmount());
                
                //should show node space if discovered
                //
                if(mapViewAndModelPair.getMapSpace().isDiscovered())
                {
                    mapViewAndModelPair.getColonyNodeView().showNode();
                    
                    //Show or hide ant icons
                    //
                    if(foragerCount > 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().showForagerIcon(); 
                    }
                    else if(foragerCount <= 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().hideForagerIcon();
                    }
                    
                    if(queenCount > 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().showQueenIcon(); 
                    }
                    else
                    {
                        mapViewAndModelPair.getColonyNodeView().hideQueenIcon();
                    }
                        
                    if(scoutCount > 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().showScoutIcon(); 
                    }
                    else if(scoutCount <= 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().hideScoutIcon();
                    }
                    
                    if(soldierCount > 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().showSoldierIcon(); 
                    }
                    else if(soldierCount <= 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().hideSoldierIcon();
                    }
                    
                    if(balaCount > 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().showBalaIcon(); 
                    }
                    else if(balaCount <= 0)
                    {
                        mapViewAndModelPair.getColonyNodeView().hideBalaIcon();
                    }
                    
                }
                
            }
        }        
        
    }
    
    
    /**
     * This method is for continous simulation
     */
    public void continousSimulation()
    {
        while(!this.isStopSimulation())
        {
            this.worldTurn();
        }
    }
    
    /**
     * This is a method so we can check if a Bala ant can be spawned
     * @return
     */
    private boolean isTimeForBalaAntSpawning()
    {
        boolean canSpawnBala = false;
        
        //ok now lets see if we get the number we want 
        //
        double balaChance = randomValue.nextDouble();
        
        
        if(balaChance <= this.getBuiltWorld().getBalaSpawnRate())
        {
            //ok we can spawn a Bala ant
            //
            canSpawnBala = true;
        }
        
        return canSpawnBala;
    }
    
    /**
     * This is a method so we can check if a Bala ant can be spawned
     * on this map space
     * @return
     */
    private boolean isMapSpaceForBalaAntSpawning()
    {
        boolean isMapSpace = false;
        
        //ok now lets see if we get the number we want 
        //
        double balaChance = randomValue.nextDouble();
        
        
        if(balaChance <= this.getBuiltWorld().getBalaMapSpawnChance())
        {
            //ok we can spawn a Bala ant on this map space
            //
            isMapSpace = true;
        }
        
        return isMapSpace;
    }
    
    /**
     * Handle simulationEvents
     */
    public void simulationEventOccurred(SimulationEvent simEvent)
    {
       if(simEvent.getEventType() == SimulationEvent.STEP_EVENT)
       {
           //ok we only advance if the stopSimulation is false
           //
           if(!this.isStopSimulation())
           {
               this.worldTurn();
               
               //ok if someone has signed up for the worldInformation object
               //lets call the two methods provided
               //
               if(this.getWorldInfo() != null)
               {
                   this.getWorldInfo().currentWorldTime(this.getCurrentTimeInTurns());
                   
               }
           
               System.out.println("it works its a step event"); 
           }
           else
           {
               this.getWorldInfo().hasWorldStopped(this.isStopSimulation());
           }
       }
       else if(simEvent.getEventType() == SimulationEvent.RUN_EVENT)
       {
           //ok we only advance if the stopSimulation is false
           //
           if(!this.isStopSimulation())
           {
               continousSimulation();
           }
           else
           {
               this.getWorldInfo().hasWorldStopped(this.isStopSimulation());
           }
       }
       else if(simEvent.getEventType() == SimulationEvent.NORMAL_SETUP_EVENT)
       {
           //TODO:REWORK THIS
           /*initializeWorld(); 
           if(this.getWorldInfo() != null)
           {
               this.getWorldInfo().initializeColonyView();
           }*/
       }
       else
       {
           System.out.println("not handling this");
       }
    }

    /**
     * @return the builtWorld
     */
    public WorldBuilder getBuiltWorld()
    {
        return builtWorld;
    }

    /**
     * @param builtWorld the builtWorld to set
     */
    public void setBuiltWorld(WorldBuilder builtWorld)
    {
        this.builtWorld = builtWorld;
    }

    /**
     * @return the worldInfo
     */
    public WorldInformation getWorldInfo()
    {
        return worldInfo;
    }

    /**
     * @param worldInfo the worldInfo to set
     */
    public void setWorldInfo(WorldInformation worldInfo)
    {
        this.worldInfo = worldInfo;
    }

    /**
     * @return the currentTimeInTurns
     */
    public int getCurrentTimeInTurns()
    {
        return currentTimeInTurns;
    }

    /**
     * @param currentTimeInTurns the currentTimeInTurns to set
     */
    public void setCurrentTimeInTurns(int currentTimeInTurns)
    {
        this.currentTimeInTurns = currentTimeInTurns;
    }

    /**
     * @return the stopSimulation
     */
    public boolean isStopSimulation()
    {
        return stopSimulation;
    }

    /**
     * @param stopSimulation the stopSimulation to set
     */
    public void setStopSimulation(boolean stopSimulation)
    {
        this.stopSimulation = stopSimulation;
    }

}
