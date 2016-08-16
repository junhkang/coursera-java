import java.util.*;
import edu.duke.*;

public class EarthQuakeClient {
    public EarthQuakeClient() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filterByMagnitude(ArrayList<QuakeEntry> quakeData,
    double magMin) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData)    {
            double mageach = qe.getMagnitude();
            if ( mageach > magMin)  {
                answer.add(qe);
            }
        }
        return answer;
    }

    public ArrayList<QuakeEntry> filterByDistanceFrom(ArrayList<QuakeEntry> quakeData,
    double distMax,
    Location from) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            Location loc = qe.getLocation();
            double distanceInMeters = from.distanceTo(loc);
            if  (distanceInMeters <= distMax)    {
                answer.add(qe);
            }
        }
        return answer;
    }
    public ArrayList<QuakeEntry> filterByDepth(ArrayList<QuakeEntry> quakeData, double minDepth, double maxDepth)    {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (QuakeEntry qe : quakeData) {
            double dept = qe.getDepth();
            if (dept <maxDepth && dept > minDepth)  {
                answer.add(qe);
            }
        }
        return answer;
    }
    public ArrayList<QuakeEntry> filterByPhrase(ArrayList<QuakeEntry> quakeData, String where, String phrase)  {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        if (where.equals("start"))   {
            for (QuakeEntry qe : quakeData) {
                String information = qe.getInfo();
                
                
                if (information.indexOf(phrase) == 0)   {
                    answer.add(qe);
                }
            }
        }
        if (where.equals("end"))    {
            for (QuakeEntry qe : quakeData) {
                String information = qe.getInfo();
                String[] splitinfo = information.split(" ");
                int len = splitinfo.length;
                String lastword = splitinfo[len-1];
                if (lastword.equals(phrase))   {
                    answer.add(qe);
                }
            }
        }
        if (where.equals("any"))    {
            for (QuakeEntry qe : quakeData) {
                String information = qe.getInfo();
                if (information.contains(phrase))   {
                    answer.add(qe);
                }
            }
        }
        return answer;
    }
    public void dumpCSV(ArrayList<QuakeEntry> list){
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }

    }
    
    public void bigQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> answer = filterByMagnitude(list, 5.0);
        for (QuakeEntry qe : answer) {
            System.out.println(qe);
        }
    }

    public void closeToMe(){
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedatasmall.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        // This location is Durham, NC
        //Location city = new Location(35.988, -78.907);
        // This location is Bridgeport, CA
        Location city2 =  new Location(38.17, -118.82);
        ArrayList<QuakeEntry> answer = filterByDistanceFrom(list, 1000000, city2);
        //System.out.println(answer);
        for (QuakeEntry qe : answer) {
            System.out.println(qe);
        }
    }
    public void quakesOfDepth() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> answer = filterByDepth(list, -12000.0, -10000.0);
        for (QuakeEntry qe : answer) {
            System.out.println(qe);
        }
        System.out.println(answer.size());
    }
    public void quakesByPhrase()    {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list = parser.read(source);
        System.out.println("read data for "+list.size()+" quakes");
        ArrayList<QuakeEntry> answer =  filterByPhrase(list, "any", "Can");
        for (QuakeEntry qe : answer) {
            System.out.println(qe);
        }
        System.out.println(answer.size());
    }
    public void createCSV(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }
    
}
