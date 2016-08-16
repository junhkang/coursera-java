 


/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
public class WordPlay {
    public boolean isVowel(char ch){
        char chl = Character.toLowerCase(ch);
        //String chs = Character.toString(ch);
        if (chl == 'a' || chl == 'i' || chl == 'u' || chl == 'o' || chl == 'e')   {
            return true;
        }
        else return false;
    }
    public String replaceVowels(String phrase, char ch){
        StringBuilder newstring = new StringBuilder(phrase);
        int len = phrase.length();
        for (int i = 0; i < len; i++){
            String s = phrase.substring(i);
            char cha = s.charAt(0);
            //String chs = Character.toString(ch);
            if (isVowel(cha)){
                newstring.setCharAt(i, ch);
            }
        }
        return newstring.toString();
    }
    public String emphasize(String phrase, char ch){
        StringBuilder newstring = new StringBuilder(phrase);
        int len = phrase.length();
        for (int i = 0; i < len; i +=2){
            String s = phrase.substring(i);
            char cha = s.charAt(0);
            if (cha == ch){
                newstring.setCharAt(i, '*');
            }
        }
        for (int i = 1; i < len; i+=2){
            String s = phrase.substring(i);
            char cha = s.charAt(0);
            //String chs = Character.toString(ch);
            if (isVowel(cha)){
                newstring.setCharAt(i, '+');
            }
        }
        return newstring.toString();
    }
    
    public void replacetest(){
        String test = "Hello World";
        char testc = '*';
        String k = replaceVowels(test, testc);
        //System.out.println(k);
        String test2 = "dna ctgaaactga";
        char testc2 = 'a';
        String kk = emphasize(test2, testc2);
        System.out.println(kk);
    }
    public void testisvowel(){
        char ch ='A';
        boolean asdf = isVowel(ch);
        System.out.println(asdf);
    }
}
