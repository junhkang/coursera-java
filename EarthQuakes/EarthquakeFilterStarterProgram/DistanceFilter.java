
/**
 * Write a description of DistanceFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DistanceFilter implements Filter{
    private double maxdis;
    private Location currentloc;
    public DistanceFilter(double max, Location current)  {
        maxdis = max;
        currentloc = current;
    }
    public boolean satisfies(QuakeEntry qe) {
        Location loc = qe.getLocation();
        return currentloc.distanceTo(loc)<=maxdis;
    }
    public String getName() {
        return "DistanceFilter";
    }
}
