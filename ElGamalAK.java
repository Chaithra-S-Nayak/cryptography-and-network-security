import java.util.Random;
import java.util.Scanner;

public class ElGamalAK {
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       Random rand = new Random();
       System.out.print("Enter a prime number(q) : ");
       long q = sc.nextLong();
       System.out.print("Enter primitive root of q (alpha) : ");
       long alpha = sc.nextLong();
       //Key generation by Alice
       long Xa = 1 + rand.nextLong(q-2);
       long Ya = modPow(alpha,Xa,q);
       System.out.println("\nKey Generation By Alice");
       System.out.println("Public Key : {" + q +", " + alpha + ", " + Ya + "}");
       System.out.println("Private Key Xa: " + Xa);
       //Encryption by Bob with Alice's Public Key
       System.out.print("Enter the plain text (M) : ");
       long M = sc.nextLong();
       long k = 1 + rand.nextLong(q-1);
       long Kb = modPow(Ya,k,q);
       long C1 = modPow(alpha,k,q);
       long C2 = (Kb * M) % q;
       System.out.println("\nEncryption by Bob with Alice's Public Key");
       System.out.println("Cipher text (C1,C2) : (" + C1 + ", " + C2 + ")");
       //Decryption by Alice with Alice's Private Key
       long Ka = modPow(C1,Xa,q);
       long decrypted = (C2 * inverse(Ka,q)) % q;
       System.out.println("\nDecryption by Alice with Alice's Private Key");
       System.out.println("Decrypted text (M) = " + decrypted);
   }

   public static long modPow(long num,long pow,long n){
       long result = 1;
       for(int i = 0;i < pow;i++){
           result = (num * result) %n;
       }
       return result;
   }

   public static long inverse(long k, long q){
       for(long x = 1;x < q;x++){
           if((k*x)%q == 1){
               return x;
           }
       }
       return 0;
   }
}
