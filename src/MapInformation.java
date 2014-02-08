import java.util.ArrayList;

/**
 * This interface is used to provide map information to anyone who asks
 */

/**
 * @author joejones
 *
 */
public interface MapInformation
{
    public int sizeOfMapOnXAxis();
    public int sizeOfMapOnYAxis();
    public ArrayList <MapSpace> listOfValidMapSquaresBasedOnRadius(int radiusOFMoves, MapSpace centerSpace);
    public int getTheCurrentTimeInTurns();
}
