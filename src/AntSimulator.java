import javax.swing.JOptionPane;

/**
 * Main Class for the Ant Simulator
 */

/**
 * @author joejones
 *
 */
public class AntSimulator implements WorldInformation
{
    //vars and consts 
    //
    private int currentWorldTimeInTurns;
    private boolean hasWorldStopped;
    private ColonyView colonyView;
    private AntSimGUI antSimGUI;
    private WorldController worldController;
    
    private static final String DEFAULT_END_SIMULATION_MESSAGE = "The Queen Ant is Dead Simulation Ended";
    
    
    public AntSimulator()
    {
        this.currentWorldTimeInTurns = 0;
        this.hasWorldStopped = false;
    }

    /*World Information Interface
     * 
     */
    
    /**
     * This is the method to set the current time
     */
    public void currentWorldTime(int passedTime)
    {
        this.currentWorldTimeInTurns = passedTime;
        antSimGUI.setTime(String.valueOf(passedTime));

    }
    
    /**
     * This is the method to report on if the world has stopped 
     */
    public void hasWorldStopped(boolean passedHasWorldStopped)
    {
        this.hasWorldStopped = passedHasWorldStopped;
        antSimGUI.setTime(String.valueOf(worldController.getCurrentTimeInTurns()));
        
        //fire off alert here
        //
        JOptionPane.showMessageDialog(null, DEFAULT_END_SIMULATION_MESSAGE);
    }
    
    /**
     * This is the method to reset the colony view when the world is reinitialized
     */
    public void initializeColonyView()
    {
        setUpAntSimGUI();
        this.hasWorldStopped = false;
        this.worldController.setStopSimulation(false);
    }
    
    /**
     * This is a helper method to reinit the world
     */
    private void setUpAntSimGUI()
    {  
        //wire up the ant sim gui
        //

        antSimGUI = new AntSimGUI();
        antSimGUI.addSimulationEventListener(worldController);
        
        antSimGUI.initGUI(worldController.getBuiltWorld().getColonyView());
        antSimGUI.validate();
        antSimGUI.setTime(String.valueOf(worldController.getBuiltWorld().getCurrentTimeInTurns()));
        
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {     
        //set up ant simulator
        //
        AntSimulator as = new AntSimulator(); 
        
        //set up world controller
        //
        as.worldController = new WorldController("AntSim.properties",10, 10);        
        as.worldController.setWorldInfo(as);    
        as.initializeColonyView();

    }

}
