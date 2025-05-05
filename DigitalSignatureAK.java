import java.util.Scanner;

public class DigitalSignatureAK {
   public static long gcd(long a, long b) {
       if (b == 0) return a;
       else return gcd(b,a % b);
   }
   public static long modPow(long num, long pow, long n) {
       long result = 1;
       for(int i = 0;i < pow;i++){
           result = (num * result) % n;
       }
       return result;
   }
   public static long simpleHash(String message) {
       long hash = 0;
       for (char c : message.toCharArray()) {
           hash = (hash + c) % 97;
       }
       return hash;
   }

   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       System.out.print("Enter the value for p (prime number): ");
       long p = sc.nextInt();
       System.out.print("Enter the value for q (prime number): ");
       long q = sc.nextInt();
       sc.nextLine();
       long n = p * q;
       long phi = (p - 1) * (q - 1);
       //Generate e
       long e;
       for(e = 2; e < phi;e++){
           if(gcd(e,phi) == 1){
               break;
           }
       }
       //Generate d
       long d = 1;
       for(long x = 1;x < phi;x++){
           if(((x*e) % phi) == 1){
               d = x;
           }
       }
       System.out.print("Enter the message : ");
       String message = sc.nextLine();
       long hashed = simpleHash(message);
       long signature = modPow(hashed, d, n);
       System.out.println("Signature: " + signature);
       long decryptedHash = modPow(signature, e, n);
       System.out.println("Decrypted Hash: " + decryptedHash);
       System.out.println("Original Hash : " + hashed);
       if (decryptedHash == hashed) {
           System.out.println("Signature verified successfully!");
       } else {
           System.out.println("Signature verification failed!");
       }
   }
}
