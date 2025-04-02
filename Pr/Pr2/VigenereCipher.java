import java.util.Scanner;
class VigenereCipher{
    public static StringBuilder encrypt(String text, String key){
        key=keyFormat(key,text);
        StringBuilder result=new StringBuilder();
        for(int i=0;i<text.length();i++){
            int encrypted=((text.charAt(i)-'A')+ (key.charAt(i)-'A'))%26;
            result.append((char)(encrypted + 'A'));
        }
        return result;
    }
    public static StringBuilder decrypt(String text, String key){
    key=keyFormat(key,text);
    StringBuilder result=new StringBuilder();
    for(int i=0;i<text.length();i++){
        int decrypted=((text.charAt(i)-'A')-(key.charAt(i)-'A')+26)%26;
        result.append((char)(decrypted + 'A'));
    }
    return result;
    }
    public static String keyFormat(String key, String text){
        StringBuilder result=new StringBuilder();
        for(int i=0;result.length()<text.length();i++){
            result.append(key.charAt(i%key.length()));
        }
        return result.toString();
    }
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the String");
        String word=sc.nextLine();
        word=word.toUpperCase();
        System.out.println("Enter the key");
        String key=sc.nextLine();
        key=key.toUpperCase();
        StringBuilder encrypted=encrypt(word,key);
        System.out.println("encrypted text "+ encrypted);
        System.out.println("decrypted text "+ decrypt(encrypted.toString(),key));
    }
}