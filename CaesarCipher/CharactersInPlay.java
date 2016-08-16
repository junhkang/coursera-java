 


/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
public class CharactersInPlay {
    private ArrayList<String> name;
    private ArrayList<Integer> count;
    public CharactersInPlay(){
        name = new ArrayList<String>();
        count = new ArrayList<Integer>();
    }
    public void update(String person)   {
        int index = person.indexOf(".");
        if (index != -1)    {
            String maybename = person.substring(0, index);
            int indexinside = name.indexOf(maybename);
            if (indexinside ==-1)   {
                name.add(maybename);
                count.add(1);
            }
            else {
				int value = count.get(indexinside);
				count.set(indexinside, value + 1);
            }
        }
    }
    public void findAllCharacters() {
        FileResource fr = new FileResource();
        for (String s : fr.lines()){
            s = s.toLowerCase();
            update(s);
        }
    }
    public void tester()    {
        findAllCharacters();
        int maxspeak = 0;
        int location = 0;
        for (int i = 0; i< count.size(); i++)   {
            if (count.get(i) > maxspeak)    {
                maxspeak = count.get(i);
                location  = i;
            }
        }
        System.out.println("Most main character : " + name.get(location) + "and speaked " + maxspeak + " times.");
        charactersWithNumParts(10, 15);
    }
    public void charactersWithNumParts(int num1, int num2)  {
        System.out.println("Speaked" + num1 +"~" + num2 + " times ");
        for (int i = 0; i< count.size(); i++)   {
            if (count.get(i) >= num1 && count.get(i) <= num2  )    {
                System.out.println(name.get(i) + " speaked " +count.get(i)+ " times."); 
            }
        }
    }
}
