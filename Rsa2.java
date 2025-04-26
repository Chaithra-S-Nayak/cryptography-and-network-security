import java.util.Scanner;

public class Rsa2 {
   public static long modPow(long num,long pow,long n){
       long result = 1;
       for(int i = 0;i < pow;i++){
           result = (num * result) %n;
       }
       return result;
   }
   public static long gcd(long a,long b){
       if (b == 0)  return a;
       else return gcd(b,a%b);
   }
    public static void main(String[] args) {
       long p,q,n,phi,e,d,C,M;
       Scanner sc = new Scanner(System.in);
       System.out.print("Enter p (Prime Number) : ");
       p = sc.nextLong();
       System.out.print("Enter q (Prime number) : ");
       q = sc.nextLong();
       n = p * q;
       System.out.println("n = " + n);
       phi = (p-1) * (q-1);
       System.out.println("phi = " + phi);
       //Generate e
       for(e = 2; e < phi;e++){
           if(gcd(e,phi) == 1){
               break;
           }
       }
       System.out.println("Public Key {e,n} : {" + e + "," + n + "}");
       //Generate d
       d = 1;
       for(long x = 1;x < phi;x++){
           if(((x*e) % phi) == 1){
               d = x;
           }
       }
       System.out.println("private Key {e,n} : {" + d + "," + n + "}");
       System.out.print("Enter the message : ");
       M = sc.nextLong();
       C = modPow(M,e,n);
       System.out.println("Cipher text : " + C);
       System.out.println("Decrypted ciphertext : " + modPow(C,d,n));
   }
}
