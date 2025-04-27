import java.util.Scanner;
class VigenereCipher{
    public static String repeat(String key, String text){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<text.length();i++){
            sb.append(key.charAt(i % key.length()));
        }
        return sb.toString();
    }
    public static String encrypt(String key, String text){
        StringBuilder sb= new StringBuilder();
        for(int i=0;i<text.length();i++){
            int encrypted= ((text.charAt(i)-'A' )+ (key.charAt(i)-'A')) %26;
            sb.append((char)('A'+encrypted));
        }  
        return sb.toString();
    }
    public static String decrypt(String key, String text){
        StringBuilder sb= new StringBuilder();
        for(int i=0;i<text.length();i++){
            int encrypted= ((text.charAt(i)-'A' )- (key.charAt(i)-'A')+26) %26;
            sb.append((char)('A'+encrypted));
        }  
        return sb.toString();
    }
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter text");
        String text=sc.nextLine();
        text= text.toUpperCase();
        System.out.println("Enter key");
        String key=sc.nextLine();
        String repeatKey=repeat(key,text);
        repeatKey= repeatKey.toUpperCase();
        System.out.println("repeatKey "+repeatKey);
        System.out.println("Choose\n 1. encrypt \n 2. decrypt");
        int n=sc.nextInt();
        sc.nextLine();
        if(n==1){
            System.out.println("Encypt "+encrypt(repeatKey,text));
        }else if(n==2){
            System.out.println("Decrypt "+decrypt(repeatKey,text));
        }else{
            System.out.println("invalid");
        }
    }
}