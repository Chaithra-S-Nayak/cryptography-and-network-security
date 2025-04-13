import java.util.Scanner;
class DiffieHellman{
    public static long modPow(long num, long pow, long n){
        long res=1;
        for(int i=0;i<pow;i++){
            res= (res*num) % n;
        }
        return res;
    }
    public static void main(String args[]){
        long q, alpha, Xa, Xb, Ya, Yb, Ka, Kb;
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter q");
        q=sc.nextLong();
        System.out.println("Enter alpha");
        alpha=sc.nextLong();
        System.out.println("Enter Xa");
        Xa=sc.nextLong();
        System.out.println("Enter Xb");
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
            System.out.println("sucess");
        }
    }
}