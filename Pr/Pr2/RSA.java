import java.util.Scanner;
class RSA{
    public static long modPow(long num, long pow, long n){
        long result=1;
        for(int i=0;i<pow;i++){
            result=(num*result)%n;
        }
        return result;
    }
    public static long gcd(long a, long b){
        if(b==0){
            return a;
        }
        return gcd(b,a%b);
    }
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        long p,q,n,phi,e,d,C,M;
        System.out.println("Enter the p");
        p=sc.nextLong();
        System.out.println("Enter the q");
        q=sc.nextLong();
        n=p*q;
        phi=(p-1)*(q-1);
        for(e=2;e<phi;e++){
            if(gcd(e,phi)==1){
                break;
            }
        }
        d=1;
        for(long x=1;x<phi;x++){
            if((x*e)%phi==1){
                d=x;
            }
        }
        System.out.println("Enter the M");
        M=sc.nextLong();
        C=modPow(M,e,n);
        System.out.println("encrypted text "+ C);
        System.out.println("decrypted text "+ modPow(C,d,n));
    }
}