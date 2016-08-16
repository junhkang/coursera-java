package Gladlib;


/**
 * Write a description of GladLibMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private HashMap<String, ArrayList<String>> UsedmyMap;
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibMap(){
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }
    
    public GladLibMap(String source){
        initializeFromSource(source);
        myRandom = new Random();
    }
    
    private void initializeFromSource(String source) {
        myMap = new HashMap<String, ArrayList<String>>();
        UsedmyMap = new HashMap<String, ArrayList<String>>();
        String[] labels = {"adjective", "noun", "color", "country", "name", "animal", "timeframe"};
        for (String s : labels){
           ArrayList<String> list = readIt(source+"/"+s+".txt");
           myMap.put(s, list);
           }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("country")) {
            UsedmyMap.put("country", myMap.get("country"));
            return randomFrom(myMap.get("country"));           
        }
        if (label.equals("color")){
            UsedmyMap.put("color", myMap.get("color"));
            return randomFrom(myMap.get("color"));
        }
        if (label.equals("noun")){
             UsedmyMap.put("noun", myMap.get("noun"));
            return randomFrom(myMap.get("noun"));
        }
        if (label.equals("name")){
             UsedmyMap.put("name", myMap.get("name"));
            return randomFrom(myMap.get("name"));
        }
        if (label.equals("adjective")){
             UsedmyMap.put("adjective", myMap.get("adjective"));
            return randomFrom(myMap.get("adjective"));
        }
        if (label.equals("animal")){
             UsedmyMap.put("animal", myMap.get("animal"));
            return randomFrom(myMap.get("animal"));
        }
        if (label.equals("timeframe")){
             UsedmyMap.put("timeframe", myMap.get("timeframe"));
            return randomFrom(myMap.get("timeframe"));
        }
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        return "**UNKNOWN**";
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public void makeStory(){
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate.txt");
        printOut(story, 60);
        System.out.println("\n");
        totalWordsInMap();
        System.out.println("\n");
        totalWordsConsidered();
    }
    public void totalWordsInMap(){
        int totalwordcount = 0;
        for (String s : myMap.keySet())   {
            totalwordcount = totalwordcount+myMap.get(s).size();
        }
        System.out.println(totalwordcount);
    }
    public void totalWordsConsidered(){
        int totalwordcount = 0;
        for (String s : UsedmyMap.keySet())   {
            totalwordcount = totalwordcount+UsedmyMap.get(s).size();
        }
        System.out.println(totalwordcount);
    }
    


}

