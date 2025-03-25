import java.util.Scanner;
class DigitalSignature{
    public static long modPow(long num, long pow, long n){
        long result=1;
        for(int i=0;i<pow;i++){
            result=(num*result)%n;
        }
        return result;
    }
    public static long gcd(long a , long b){
        if(b==0){
            return a;
        }
        return gcd(b,a%b);
    }
    public static long simpleHash(String message){
        long h=0;
        for(char c : message.toCharArray()){
            h=(h+'c')%96;
        }
        return h;
    }
     public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        long p,q,n,phi,d,e;
        System.out.println("Enter the p");
        p=sc.nextLong();
        sc.nextLine();
        System.out.println("Enter the q");
        q=sc.nextLong();
        sc.nextLine();
        System.out.println("Enter the message");
        String message=sc.nextLine();
        n=p*q;
        phi=(n-1)*(q-1);
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
        long hashed=simpleHash(message);
        long signature=modPow(hashed,d,n);
        long decryptedHash=modPow(signature,e,n);
        System.out.println(" hashed "+ hashed);
        System.out.println("decrypted hash "+ decryptedHash);
        if(hashed==decryptedHash){
            System.out.println("Success");
        }
     }
}