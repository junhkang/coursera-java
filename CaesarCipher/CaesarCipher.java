 


/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainkey;
    public CaesarCipher(int key){
        alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        shiftedAlphabet = alphabet.substring(key)+alphabet.substring(0,key);
        mainkey = key;
    }
    //CaesarCipher cc = new CaesarCipher(mainkey);
    
    public String encrypt(String input){
        CaesarCipher cc = new CaesarCipher(mainkey);
        StringBuilder sb = new StringBuilder(input);
        for (int i =0; i <sb.length(); i++){
            char c = sb.charAt(i);
            int idx = alphabet.indexOf(c);
            if(idx!=-1){
                c = shiftedAlphabet.charAt(idx);
                sb.setCharAt(i, c);
            }
        }
        return sb.toString();
    }
    public String decrypt(String input) {
        CaesarCipher cc = new CaesarCipher(26-mainkey);
        String input2 = cc.encrypt(input);
        //StringBuilder sb = new StringBuilder(input2);
        //for (int i =0; i <sb.length(); i++){
       //     char c = sb.charAt(i);
       //     int idx = alphabet.indexOf(c);
       //     if(idx!=-1){
       //         c = shiftedAlphabet.charAt(idx);
       //         sb.setCharAt(i, c);
       //     }
       // }
        return input2;
    }
    public void SimpleTest(){
        //CaesarCipher cc = new CaesarCipher(mainkey);
        String input = "I LOVE WHAT I LOVE";
        String encinput = encrypt(input);
        String original = decrypt(encinput);
        System.out.println(encinput);
       // CaesarCipher cc2 = new CaesarCipher(mainkey);
        System.out.println(original);
    }
}
