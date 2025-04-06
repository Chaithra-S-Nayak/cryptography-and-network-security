import java.util.Scanner;
class Caesar{
    public static String encrypt(int shift, String text){
        StringBuilder sb= new StringBuilder();
        for(char c : text.toCharArray()){
            if(Character.isUpperCase(c)){
                sb.append((char)((c-'A'+shift)%26+'A'));
            }
            if(Character.isLowerCase(c)){
                sb.append((char)((c-'a'+shift)%26+'a'));
            }
        }
        return sb.toString();
    }
    public static String decrypt(int shift, String text){
        return encrypt(26-shift,text);
    }

    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter shift");
        int shift= sc.nextInt();
        sc.nextLine();
        System.out.println("Enter text");
        String text= sc.nextLine();
        System.out.println("Choose \n 1. Encrypt \n 2. Decrypt \n");
        int n = sc.nextInt();
        if(n==1){
            System.out.println(encrypt(shift,text));
        }else if(n==2){
            System.out.println(decrypt(shift,text));
        }else{
            System.out.println("Invalid");
        }
    }
}