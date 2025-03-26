import java.util.Scanner;
class DiffieHellman{
    public static long modPow(long num, long pow, long n){
        long result=1;
        for(int i=0;i<pow;i++){
            result=(num*result)%n;
        }
        return result;
    }
     public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        long alpha,q,Xa,Xb,Ya,Yb,Ka,Kb;
        System.out.println("Enter the alpha");
        alpha=sc.nextLong();
        System.out.println("Enter the q");
        q=sc.nextLong();
        System.out.println("Enter the Xa");
        Xa=sc.nextLong();
        System.out.println("Enter the Xb");
        Xb=sc.nextLong();
        Ya=modPow(alpha,Xa,q);
        Yb=modPow(alpha,Xb,q);
        System.out.println("Ya "+Ya);
        System.out.println("Yb "+Yb);
        Ka=modPow(Yb,Xa,q);
        Kb=modPow(Ya,Xb,q);
        System.out.println("Ka "+Ka);
        System.out.println("Kb "+Kb);
        if(Ka==Kb){
            System.out.println("Success");
        }
     }
}