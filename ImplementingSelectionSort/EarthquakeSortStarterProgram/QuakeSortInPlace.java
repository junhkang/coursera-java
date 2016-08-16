
/**
 * Write a description of class QuakeSortInPlace here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class QuakeSortInPlace {
    public QuakeSortInPlace() {
        // TODO Auto-generated constructor stub
    }
   
    public int getSmallestMagnitude(ArrayList<QuakeEntry> quakes, int from) {
        int minIdx = from;
        for (int i=from+1; i< quakes.size(); i++) {
            if (quakes.get(i).getMagnitude() < quakes.get(minIdx).getMagnitude()) {
                minIdx = i;
            }
        }
        return minIdx;
    }
    public int getLargestDepth(ArrayList<QuakeEntry> QuakeData, int from)   {
        int minidx = from;
        for (int i = from+1; i<QuakeData.size(); i++)   {
            if  (QuakeData.get(i).getDepth() > QuakeData.get(minidx).getDepth())    {
                minidx = i;
            }
        }
        return minidx;
    }
    
   
    public void sortByLargestDepth(ArrayList<QuakeEntry> in)    {
        for (int i = 0; i<in.size(); i++)   {
            int maxidx = getLargestDepth(in, i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmax = in.get(maxidx);
            in.set(i, qmax);
            in.set(maxidx, qi);
        }
    }
    public void sortByMagnitude(ArrayList<QuakeEntry> in) {
       
       for (int i=0; i< in.size(); i++) {
            int minIdx = getSmallestMagnitude(in,i);
            QuakeEntry qi = in.get(i);
            QuakeEntry qmin = in.get(minIdx);
            in.set(i,qmin);
            in.set(minIdx,qi);
        }     
    }
    public void onePassBubbleSort(ArrayList<QuakeEntry> quakeData, int numSorted)   {
        for (int i = 1; i<quakeData.size()-numSorted+1; i++)  {
            if (quakeData.get(i-1).getMagnitude() > quakeData.get(i).getMagnitude())    {
                QuakeEntry qi1 = quakeData.get(i-1);
                QuakeEntry qi2 = quakeData.get(i);
                quakeData.set(i-1, qi2);
                quakeData.set(i, qi1);
                
            }
        }
    }
    public void sortByMagnitudeWithBubbleSort(ArrayList<QuakeEntry> in) {
        for (int i = 1; i<in.size()-1; i++)    {
           // for(QuakeEntry qe : in)    {
          //      System.out.println(qe);
           // }
            onePassBubbleSort(in, i);
            //System.out.println(in);
        }
    }
    public boolean checkInSortedOrder(ArrayList<QuakeEntry> quakes) {
        for (int i = 1; i<quakes.size(); i++)   {
            if (quakes.get(i-1).getMagnitude() > quakes.get(i).getMagnitude())    {
                return false;
            }
        }
        return true;
    }
    public void sortByMagnitudeWithBubbleSortWithCheck(ArrayList<QuakeEntry> in)    {
        int countpass = 0;
        while (checkInSortedOrder(in) == false){
                int i = 1;
                onePassBubbleSort(in, i);
                countpass = countpass+1;
                i = i+1;
        }
        System.out.println("sort after " + countpass + " passes");
    }
    public void sortByMagnitudeWithCheck(ArrayList<QuakeEntry> in)  {
        int countpass = 0;
        int i = 0;
        while (checkInSortedOrder(in) == false){
                
                int minidx = getSmallestMagnitude(in, i);
                QuakeEntry qi = in.get(i);
                QuakeEntry qmin = in.get(minidx);
                in.set(i,qmin);
                in.set(minidx,qi);
                countpass = countpass+1;
                i = i+1;
            }
        //    for (int i = 0; i<in.size(); i++)   {
        //    int minidx = getLargestDepth(in, i);
        //    QuakeEntry qi = in.get(i);
        //    QuakeEntry qmin = in.get(minidx);
       //     in.set(i, qmin);
        //    in.set(minidx, qi);
       // }
        System.out.println("sort after " + countpass + " passes");
    }
    public void testSort() {
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/earthQuakeDataWeekDec6sample1.atom";
        //String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);  
       
        System.out.println("read data for "+list.size()+" quakes");    
        //sortByMagnitude(list);
        sortByLargestDepth(list);
        //sortByMagnitudeWithBubbleSort(list);
        //sortByMagnitudeWithBubbleSortWithCheck(list);
        //sortByMagnitudeWithCheck(list);
        for (QuakeEntry qe: list) { 
            System.out.println(qe);
        } 
        
    }
    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: " + list.size());
    }
    
    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                              qe.getLocation().getLatitude(),
                              qe.getLocation().getLongitude(),
                              qe.getMagnitude(),
                              qe.getInfo());
        }
        
    }
}
