
/**
 * Write a description of TitleAndDepthComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class TitleAndDepthComparator implements Comparator<QuakeEntry> {
    private Location myLocation;
    private String title;
    private double depth;
    private double magnitude;

 
    
    public Location getLocation(){
        return myLocation;
    }
    
    public double getMagnitude(){
       return magnitude;
    }
    public String getInfo(){
        return title;
    }
    public double getDepth(){
        return depth;
    }
   
    
    public int compare(QuakeEntry q1, QuakeEntry q2) {
        String title1 = q1.getInfo();
        String title2 = q2.getInfo();
        if (title1.compareTo(title2) < 0)   {
            return -1;}
        if (title2.compareTo(title1) <0)    {
            return 1;}
           else 
           if (q1.getDepth() < q2.getDepth())   {
               return -1;
            }
            if (q1.getDepth() >q2.getDepth())   {
                return 1;
            }
            return 0;
        }
       
    }
    

 

