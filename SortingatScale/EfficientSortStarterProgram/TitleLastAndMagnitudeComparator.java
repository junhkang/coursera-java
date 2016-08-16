
/**
 * Write a description of TitleLastAndMagnitudeComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry>{
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
        String[] eachword1 = q1.getInfo().split(" ");
        String title1 = eachword1[eachword1.length-1];
        String[] eachword2 = q2.getInfo().split(" ");
        String title2 = eachword2[eachword2.length-1];
        
        //System.out.println(title1+"       "+ title2);
         //System.out.println(title1);
        if (title1.compareTo(title2) < 0)   {
            return -1;}
        if (title2.compareTo(title1) <0)    {
            return 1;}
           else {
           if (q1.getMagnitude() < q2.getMagnitude())   {
               return -1;
           }
           if (q1.getMagnitude() > q2.getMagnitude())   {
                return 1;
           }
            
            return 0;
        }
      
        }
       

}
