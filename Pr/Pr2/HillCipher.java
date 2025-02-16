import java.util.Scanner;
class HillCipher{
    public static String padText(String text,int size){
        while(text.length()%size!=0){
            text+='X';
        }
        return text;
    }
    public static int[] matrixMultiply(int[][] key, int[] vec,int size){
        int[] res= new int[size];
        for(int i=0;i<size;i++){
            res[i]=0;
            for(int j=0;j<size;j++){
                res[i]+=vec[j]*key[i][j]; // C=kay * plaintext 
                //res[i]+=vec[j]*key[j][i]; c= plaintext * key
            }
            res[i]=(res[i]%26+26)%26;
            }
            return res;
    }
    public static String process(String text, int[][] key, int size){
        StringBuilder res= new StringBuilder();
        text=padText(text,size);
        for(int i=0;i<text.length();i+=size){
            int[] v= new int[size];;
            for(int j=0;j<size;j++){
                v[j]=text.charAt(i+j)-'A';
            }
            int[] trans= matrixMultiply(key,v,size);
            for(int j=0;j<size;j++){
                res.append((char)(trans[j]+'A'));
            }
        }
        return res.toString();
    }
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the text");
        String text=sc.nextLine().toUpperCase();
        System.out.println("Enter the size");
        int size=sc.nextInt();
        int[][] key=new int[size][size];
        System.out.println("Enter the key");
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                key[i][j]=sc.nextInt();
            }
        }
        System.out.println(process(text,key,size));
    }
}