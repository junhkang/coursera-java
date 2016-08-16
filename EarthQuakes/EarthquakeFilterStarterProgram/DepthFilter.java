
/**
 * Write a description of DepthFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DepthFilter implements Filter{
    private double mindepth;
    private double maxdepth;
    public DepthFilter(double min, double max)  {
        mindepth = min;
        maxdepth = max;
    }
    public boolean satisfies(QuakeEntry qe)    {
        return qe.getDepth() >= mindepth && qe.getDepth() <= maxdepth;
    }
    public String getName() {
        return "DepthFilter";
    }
}
