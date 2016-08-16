
/**
 * Find N-closest quakes
 * 
 * @author Duke Software/Learn to Program
 * @version 1.0, November 2015
 */

import java.util.*;

public class ClosestQuakes {
    public ArrayList<QuakeEntry> getClosest(ArrayList<QuakeEntry> quakeData, Location current, int howMany) {
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        for (int k = 0; k<howMany; k++) {
            double closestdis = Double.MAX_VALUE;
            int smallest = 0;
            for (QuakeEntry qe : copy) {
                Location loc = qe.getLocation();
                double distanceInMeters = current.distanceTo(loc);
                if  (distanceInMeters < closestdis)    {
                    closestdis = distanceInMeters;
                }
            }
            for (int i = 1; i<copy.size(); i++) {
                QuakeEntry qentry = copy.get(i);
                Location loc = qentry.getLocation();
                double distanceInMeters = current.distanceTo(loc);
                if  (distanceInMeters == closestdis)    {
                    smallest = i;
                }
            }
            ret.add(copy.get(smallest));
            copy.remove(copy.get(smallest));
        }
        return ret;
    }
    public void findLargestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        
        
        ArrayList<QuakeEntry> answer = getLargest(list, 1518);
        for (QuakeEntry qe: answer) {
            System.out.println(qe);
        }
        
        System.out.println("read data for "+list.size());
    }
    public int indexOfLargest(ArrayList<QuakeEntry> data) {
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        double largestmag = 0;
        int answerind = 0;
        for (QuakeEntry qe : data)  {
            double magnitude = qe.getMagnitude();
            if (magnitude > largestmag) {
                largestmag = magnitude;
            }
        }
        for (int i = 0; i<data.size(); i++) {
            QuakeEntry qentry = data.get(i);
            double mageach = qentry.getMagnitude();
            if  (mageach == largestmag){
                answerind = i;
            }
        }
        return answerind;
    }
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData, int howMany)    {
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for (int i = 0; i < howMany; i++)   {
            int maxind = indexOfLargest(copy);
            answer.add(copy.get(maxind));
            copy.remove(copy.get(maxind));
        }
        return answer;
    }

    public void findClosestQuakes() {
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        System.out.println("read data for "+list.size());

        Location jakarta  = new Location(-6.211,106.845);

        ArrayList<QuakeEntry> close = getClosest(list,jakarta,3);
        for(int k=0; k < close.size(); k++){
            QuakeEntry entry = close.get(k);
            double distanceInMeters = jakarta.distanceTo(entry.getLocation());
            System.out.printf("%4.2f\t %s\n", distanceInMeters/1000,entry);
        }
        System.out.println("number found: "+close.size());
    }
    
}
