
/**
 * Write a description of MatchAllFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
public class MatchAllFilter implements Filter{
    private ArrayList<Filter> Filters;
    private ArrayList<String> Filternames;
    public MatchAllFilter() {
        Filters = new ArrayList<Filter>();
        Filternames = new ArrayList<String>();
    }
    public void addFilter(Filter f) {
        Filters.add(f);
        Filternames.add(f.getName());
    }
    public boolean satisfies(QuakeEntry qe) {
        for (Filter f : Filters)   {
            if (!f.satisfies(qe))   {
                return false;
            }
        }
        return true;
    }
    public String getName() {
        return Filternames.toString();
    }
}
