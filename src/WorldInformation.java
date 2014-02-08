/**
 * This is a delegate for world information
 */

/**
 * @author joejones
 *
 */
public interface WorldInformation
{
    public void currentWorldTime(int passedTime);
    public void hasWorldStopped(boolean passedHasWorldStopped);
    public void initializeColonyView();
}
