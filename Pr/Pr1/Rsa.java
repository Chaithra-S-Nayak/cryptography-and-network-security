import java.util.Scanner;
class Rsa{
    public static long modPow(long num, long pow, long n){
        long res=1;
        for(int i=0;i<pow;i++){
            res=(res*num)%n;
        }
        return res;
    }
    public static long gcd(long a, long b){
        if(b==0){
            return a;
        }
        return gcd(b, b%a);
    }
    public static void main(String args[]){
        long p,q, n, phi,M,C, e, d;
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter p");
        p=sc.nextLong();
        System.out.println("Enter q");
        q=sc.nextLong();
        n=p*q;
        System.out.println("n "+n);
        phi=(n-1)*(p-1);
        System.out.println("phi "+phi);
        //generate e
        for(e=2;e<phi;e++){
            if(gcd(e,phi)==1){
                break;
            }
        }
        System.out.println("e "+e);
        //generate d
        d=1;
        for(long x=1;x<phi;x++){
            if(((x*e)%phi) ==1){
                d=x;
            }
        }
        System.out.println("d "+d);
        System.out.println("Enter Message");
        M=sc.nextLong();
        C=modPow(M,e,n);
        System.out.println("cipher "+C);
        System.out.println("Plain "+modPow(C,d,n));

    }
}