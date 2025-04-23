import java.util.Scanner;

public class DiffieHellman2{
   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);

       System.out.print("Enter a prime number (q) : ");
       long q = sc.nextLong();
       System.out.print("Enter a primitive root (alpha) : ");
       long alpha = sc.nextLong();
       System.out.print("Enter a private key for user A : ");
       long Xa = sc.nextLong();
       System.out.print("Enter a private key for user B : ");
       long Xb = sc.nextLong();

       long Ya = modPow(alpha,Xa,q);
       System.out.println("Public key of user A : " + Ya);
       long Yb = modPow(alpha,Xb,q);
       System.out.println("Public key of user B : " + Yb);
       
       long Ka = modPow(Yb,Xa,q);
       long Kb = modPow(Ya,Xb,q);

       if(Ka == Kb){
           System.out.println("Secret key is shared successfully");
           System.out.println("A's secret Key : " + Ka);
           System.out.println("B's secret Key : " + Kb);
       }else{
           System.out.println("Unsuccessful sharing of secret key");
       }
   }

   public static long modPow(long num,long pow,long n){
       long result = 1;
       for(int i = 0;i < pow;i++){
           result = (result * num) % n;
       }
       return result;
   }
}
