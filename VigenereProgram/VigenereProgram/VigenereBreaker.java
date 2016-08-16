import java.util.*;
import edu.duke.*;
import java.math.*;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        int leng = message.length();
        int answersize = 0;
        if ((leng-whichSlice)%totalSlices == 0) {
            answersize = (leng-whichSlice)/totalSlices;
        }
        else{
            answersize = (leng-whichSlice)/totalSlices + 1;
        }
        String sizedmessage = message.substring(0, answersize);
        StringBuilder sb = new StringBuilder(sizedmessage);
        int count = 0;
        for (int i = whichSlice; i < leng; i +=totalSlices) {
            String s = message.substring(i);
            char letter = s.charAt(0);
            sb.setCharAt(count, letter);
            count = count + 1;
        }
        //System.out.println(sb.toString());
        return sb.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        //FileResource fr = new FileResource();
        //String encrypted = "";
        //for(String word : fr.words()){
        //    encrypted = encrypted + " " + word;        
        //    }
        int[] key = new int[klength];
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for (int i = 0; i<klength; i++) {
            String slicedeach = sliceString(encrypted, i, klength);
            //String decrypt = cc.decrypt(slicedeach);
            int keys = cc.getKey(slicedeach);
            key[i] = keys;
            
        }
        //for (int i = 0; i<5; i++){
        //    System.out.println(key[i]);
        //}
        return key;
    }

    public void breakVigenere () {
        JFrame frame = new JFrame("JOptionPane showMessageDialog example");
        JOptionPane.showMessageDialog(frame, "select a file to decrypt");
        FileResource fr = new FileResource();
        JOptionPane.showMessageDialog(frame, "select files to use as dictionary");
        DirectoryResource dr = new DirectoryResource();
        HashMap<String, HashSet<String>> hm = new HashMap<String, HashSet<String>>();
        for (File f : dr.selectedFiles()){
            FileResource fr2 = new FileResource(f);
            hm.put(f.toString(), readDictionary(fr2));
        }
        //FileResource dic = new FileResource();
        String entiretext = fr.asString();
        String answer = breakForAllLanguages(entiretext, hm);
        //int[] keys = tryKeyLength(entiretext, 5, 'e');
        //VigenereCipher vs = new VigenereCipher(keys);
        //String s = vs.decrypt(entiretext);
        //System.out.println(s);
        //HashSet<String> dictionary = readDictionary(dic);
        //String answer = breakForLanguage(entiretext, dictionary);
        System.out.println(answer);
        
    }
    public HashSet<String> readDictionary(FileResource fr)  {
        HashSet<String> hs = new HashSet<String>();
        for (String lines : fr.lines()){
            hs.add(lines.toLowerCase());
        }
        return hs;
    }
    public int countWords(String message, HashSet<String> dictionary){
        String[] splitmessage = message.toLowerCase().split("\\W+");
        int count = 0;
        for (int i = 0; i < splitmessage.length; i++) {
            if ( dictionary.contains(splitmessage[i]) )  {
                count = count+1;
            }
        }
        return count;
    }
    public String breakForLanguage(String encrypted, HashSet<String> dictionary)    {
        int maxcount = 0;
        String answer = " ";
        for (int i = 1; i<=100; i++){
            int[] keys = tryKeyLength(encrypted, i, mostCommonCharIn(dictionary));
            VigenereCipher vs = new VigenereCipher(keys);
            String s = vs.decrypt(encrypted);
            int cw = countWords(s, dictionary);
            if (cw>maxcount)    {
                maxcount = cw;
            }
        }
        for (int i = 1; i<=100; i++){
            int[] keys = tryKeyLength(encrypted, i, mostCommonCharIn(dictionary));
            VigenereCipher vs = new VigenereCipher(keys);
            String s = vs.decrypt(encrypted);
            int cw = countWords(s, dictionary);
            if(cw == maxcount)  {
                answer = s;
                //for (int j = 0; j < keys.length; j++){
                //    System.out.println(keys[j]);
                //}
            } 
            
        }
        return answer;
    }
    public Character mostCommonCharIn(HashSet<String> dictionary)   {
        HashMap<String, Integer> counts = new HashMap<String,Integer>();
        for (String k : dictionary) {
            for (int len = 0; len < k.length(); len ++){
                String eachalp = k.substring(len, len+1);
                
                if (!counts.containsKey(eachalp))  {
                    counts.put(eachalp, 1);
                }
                else {
                    counts.put(eachalp, counts.get(eachalp)+1);
                }
            }
        }
        //System.out.println(counts);
        int maxcount = 0;
        char mostcc = ' ';
        for (String k : counts.keySet()) {
            if (counts.get(k) > maxcount)   {
                maxcount = counts.get(k);
            }
        }
        for (String p : counts.keySet())    {
            if (counts.get(p) == maxcount)  {
                mostcc = p.charAt(0);
            }
        }
        return mostcc;
    }
    public String breakForAllLanguages(String encrypted, HashMap<String, HashSet<String>> languages)    {
        int bestcount = 0;
        String bestlanguage = " ";
        for (String s : languages.keySet()) {
            int counts = countWords(encrypted, languages.get(s));
            if (counts > bestcount) {
                bestcount = counts;
                bestlanguage = s;
            }
        }
        System.out.println(bestlanguage);
        String answer = breakForLanguage(encrypted, languages.get(bestlanguage));
        //countWords(String message, HashSet<String> dictionary)
        //breakForLanguage(String encrypted, HashSet<String> dictionary)
        return answer;
    }
    public void checkoutasfasfdkasjdfklasjsdkfjdskfjslkdjf()    {
        String k = "asdkfjsldkfjalks";
        for (int len = 0; len < k.length(); len ++){
            
    
                String eachalp = k.substring(len,len+1);
                System.out.println(eachalp);
            
            }
    }
}
