import java.util.Scanner;
class HillCipher{
    public static String padText(String text, int n){
        while(text.length()%n!=0){
            text+='X';
        }
        return text;
    }
    public static int determinant(int[][] matrix, int n){
        if(n==2){
            return (matrix[0][0]*matrix[1][1])-(matrix[0][1]*matrix[1][0]);    
        }
        int det=0;
        for(int x=0;x<n;x++){
            int[][] subMatrix=new int[n-1][n-1];
            for(int i=1;i<n;i++){
                for(int j=0,col=0;j<n;j++){
                    if(j!=x){
                        subMatrix[i-1][col++]=matrix[i][j];
                    }
                }
            }
            det+= (x%2==0?1:-1)*matrix[0][x]*determinant(subMatrix,n-1);
        }
        return det;
    }
    public static int modInverse(int a, int m){
        a=(a%m+m)%m;
        for(int x=1;x<m;x++){
            if((a*x)%m==1){
                return x;
            }
        }
        return 1;
    }
    public static int[][] adjoint(int[][]keyMat,int n){
        int[][] adj= new int[n][n];
        if(n==2){
            adj[0][0]=keyMat[1][1];
            adj[1][1]=keyMat[0][0];
            adj[0][1]=-keyMat[0][1];
            adj[1][0]=-keyMat[1][0];
            return adj;
        }
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                int[][] subMatrix= new int[n-1][n-1];
                for(int row=0,subRow=0;row<n;row++){
                    if(i==row) continue;
                    for(int col=0,subCol=0;col<n;col++){
                        if(j==col) continue;
                        subMatrix[subRow][subCol++]=keyMat[row][col];
                    }
                    subRow++;
                }
                adj[j][i]=(int) Math.pow(-1,i+j)*determinant(subMatrix,n-1);
            }
        }
        return adj;
    }
    public static int[][] inverseKeyMatrix(int[][] key,int size){
        int det=(determinant(key,size)%26+26)%26;
        int detInverse=modInverse(det,26);
        int[][] adj=adjoint(key,size);
        int[][] inverseKeyMatrix= new int[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                inverseKeyMatrix[i][j]=(adj[i][j]*detInverse % 26+26)%26;
            }
        }
        return inverseKeyMatrix;
    }
    public static int[] matrixMultiply(int[][] keyMat, int size,int[] vector){
        int[] res= new int[size];
        for(int i=0;i<size;i++){
            res[i]=0;
            for(int j=0;j<size;j++){
                res[i]+=keyMat[i][j]*vector[j];
            }
            res[i]=(res[i]%26+26)%26;
        }
        return res;
    }
    public static String processText(int[][] keyMat,int size, String message, boolean isEncryption){
        if(!isEncryption){
            keyMat=inverseKeyMatrix(keyMat,size);
        }
        StringBuilder res= new StringBuilder();
        for(int i=0;i<message.length();i+=size){
            int[] vector=new int[size];
            for(int j=0;j<size;j++){
                vector[j]=message.charAt(i+j)-'A';
            }
            int transformedVector[]= matrixMultiply(keyMat,size,vector);
            for(int j=0;j<size;j++){
                res.append((char)(transformedVector[j]+'A'));
            }
        }
        return res.toString();

    }
    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        System.out.println("enter key size ");
        int n= sc.nextInt();
        sc.nextLine();
        System.out.println("enter keymat ");
        int [][] key= new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                key[i][j]=sc.nextInt();
            }
        }
        sc.nextLine();
        System.out.println("enter message");
        String message= sc.nextLine();
        message=message.toUpperCase().replaceAll("\\s","");
        String paddedText=padText(message,n);
        System.out.println("choose \n 1. encrypt \n 2.decrypt");
        int choice=sc.nextInt();
        sc.nextLine();
        if(choice==1){
            System.out.println(processText(key,n,paddedText,true));
        }else{
            System.out.println(processText(key,n,paddedText,false));
        }
    }
}