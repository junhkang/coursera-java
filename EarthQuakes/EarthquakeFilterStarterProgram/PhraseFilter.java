
/**
 * Write a description of PhraseFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PhraseFilter implements Filter{
    private String parameter;
    private String phrase;
    public PhraseFilter(String para, String phra)   {
        parameter = para;
        phrase = phra;
    }
    public boolean satisfies(QuakeEntry qe) {
        if (parameter.equals("start"))  {
            String info = qe.getInfo();
            
            return info.indexOf(phrase) ==0;
        }
        else if (parameter.equals("end"))    {
            String info = qe.getInfo();
            String[] each = info.split(" ");
            int sizeeach = each.length;
            String lastword = each[sizeeach-1];
            String lastalphabet = lastword.substring(lastword.length()-1);
            
            return lastalphabet.equals(phrase);
        }
        else if (parameter.equals("any"))    {
            String info = qe.getInfo();
            return info.contains(phrase);
        }
        else return false;
    }
    public String getName() {
        return "PhraseFilter";
    }
}
