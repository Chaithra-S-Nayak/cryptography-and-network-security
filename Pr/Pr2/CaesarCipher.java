import java.util.Scanner;
class CaesarCipher{
    public static StringBuilder encrypt(String word, int shift){
        StringBuilder encrypted=new StringBuilder();
        for(char c: word.toCharArray()){
            encrypted.append((char)((c-'A'+shift)%26 + 'A'));
        }
        return encrypted;
    }
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the String");
        String word=sc.nextLine();
        word=word.toUpperCase();
        System.out.println("Enter 1.encryption or 2. decryption");
        int n=sc.nextInt();
        sc.nextLine();
        System.out.println("Enter shift");
        int shift=sc.nextInt();
        sc.nextLine();
        if(n==1){
            System.out.println("encrypted text "+ encrypt(word,shift));
        }else if(n==2){
            System.out.println("decrypted text "+ encrypt(word,26-shift));
        }else {
            System.out.println("invalid");
        }
    }
}