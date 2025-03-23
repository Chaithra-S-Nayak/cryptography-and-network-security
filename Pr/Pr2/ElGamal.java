import java.util.Scanner;
import java.util.Random;
class ElGamal{
    public static long modPow(long num, long pow, long n){
        long result=1;
        for(int i=0;i<pow;i++){
            result=(num*result)%n;
        }
        return result;
    }
    public static long inverse(long k,long q){
        for(long x=1;x<q;x++){
            if((x*k)%q==1){
                return x;
            }
        }
        return 0;
    }
    public static void main(String args[]){
        Random rand= new Random();
        Scanner sc= new Scanner(System.in);
        long q,Xa,Xb,Ya,Yb,K,k,alpha,C1,C2,M,Ka,plain;
        System.out.println("Enter the q");
        q=sc.nextLong();
        System.out.println("Enter the alpa");
        alpha=sc.nextLong();
        System.out.println("Enter the M");
        M=sc.nextLong();
        Xa=1+rand.nextLong(q-2);
        Ya=modPow(alpha,Xa,q);
        System.out.println("Alice public key {"+q+","+alpha+","+Ya+"}");
        System.out.println("Alice private key "+ Xa);
        k=1+rand.nextLong(q-1);
        K=modPow(Ya,k,q);
        C1=modPow(alpha,k,q);
        C2=(K*M)%26;
        System.out.println("Cipher Text "+C1+","+C2);
        Ka=modPow(C1,Xa,q);
        plain=C2*inverse(Ka,q)%q;
        System.out.println("Plain Text "+plain);
    }
}