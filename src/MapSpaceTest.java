
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test for the map spaces
 */

/**
 * @author joejones
 *
 */
public class MapSpaceTest
{

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void validSetOfMapSpaces()
    {
        //set up the map spaces and the world builder
        //
        WorldBuilder worldBuilder = new WorldBuilder();
        MapSpace[][] mapSpaceArray = new MapSpace[3][3];
        
        //create nine map spaces
        //
        MapSpace x0y0 = new MapSpace();
        x0y0.setLocationX(0);
        x0y0.setLocationY(0);
        
        MapSpace x1y0 = new MapSpace();
        x1y0.setLocationX(1);
        x1y0.setLocationY(0);
        
        MapSpace x2y0 = new MapSpace();
        x2y0.setLocationX(2);
        x2y0.setLocationY(0);
        
        MapSpace x0y1 = new MapSpace();
        x0y1.setLocationX(0);
        x0y1.setLocationY(1);
        
        MapSpace x1y1 = new MapSpace();
        x1y1.setLocationX(1);
        x1y1.setLocationY(1);
        
        MapSpace x2y1 = new MapSpace();
        x2y1.setLocationX(2);
        x2y1.setLocationY(1);
        
        MapSpace x0y2 = new MapSpace();
        x0y2.setLocationX(0);
        x0y2.setLocationY(2);
        
        MapSpace x1y2 = new MapSpace();
        x1y2.setLocationX(1);
        x1y2.setLocationY(2);
        
        MapSpace x2y2 = new MapSpace();
        x2y2.setLocationX(2);
        x2y2.setLocationY(2);
        
        mapSpaceArray[0][0] =x0y0;
        mapSpaceArray[1][0] =x1y0;
        mapSpaceArray[2][0] =x2y0;
        
        mapSpaceArray[0][1] =x0y1;
        mapSpaceArray[1][1] =x1y1;
        mapSpaceArray[2][1] =x2y1;
        
        mapSpaceArray[0][2] =x0y2;
        mapSpaceArray[1][2] =x1y2;
        mapSpaceArray[2][2] =x2y2;
        
        worldBuilder.setModelOfTheWorld(mapSpaceArray);
        worldBuilder.setMapSizeOnXYAxis(mapSpaceArray.length-1);
        
        assertSame(worldBuilder.getMapSpaceAtLocation(0, 1), mapSpaceArray[0][1]);
        
        ArrayList<MapSpace> mapMoves = worldBuilder.listOfValidMapSquaresBasedOnRadius(1, x2y2);
        
        for(int i = 0; i < mapMoves.size(); i++)
        {
            System.out.println("X Value " + mapMoves.get(i).getLocationX() + " Y Value " + mapMoves.get(i).getLocationY() );
        }
    }
        
}
