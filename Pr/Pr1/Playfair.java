import java.util.Scanner;
class Playfair{

    public static char[][] generatetable(String key){
        key=key.toUpperCase().replaceAll("J","I");
         char [][] keyTable= new char[5][5];
         boolean vis[]= new boolean[26];
         int row=0, col=0;
         for(char c : key.toCharArray()){
            keyTable[row][col++]=c;
            vis[c-'A']=true;
            if(col==5){
                row++;
                col=0;
            }
         }
         for(char c='A'; c<='Z';c++){
            if( vis[c-'A']!=true && c!='J'){
                keyTable[row][col++]=c;
                vis[c-'A']=true;
                if(col==5){
                    row++;
                    col=0;
                }
            }
         }
         return keyTable;
    }
    public static void printTable(char [][] keyTable){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                System.out.print(keyTable[i][j]+ " ");
            }
            System.out.println();
        }
    }

    public static String preprocessText(String text){
        text=text.toUpperCase().replaceAll("J","I");
        StringBuilder sb= new StringBuilder();
        for(int i=0;i<text.length();i++){
            if(i+1<text.length() && text.charAt(i)==text.charAt(i+1)){
                sb.append(text.charAt(i)).append('X');
            }else{
                sb.append(text.charAt(i));
            }
        }
        if(sb.length()%2!=0){
            sb.append('X');
        }
        return sb.toString();
    }

    public static int[] findPos(char c,char [][] keyTable ){
        for(int i=0;i<5;i++){
            for(int j=0;j<5;j++){
                if(keyTable[i][j]==c){
                    return new int[] {i,j};
                }
            }
        }
        return null;
    }

    public static String encrypt(String text, char [][] keyTable){
        StringBuilder sb= new StringBuilder();
        for(int i=0;i<text.length();i+=2){
            char first=text.charAt(i);
            char second=text.charAt(i+1);
            // System.out.println("first "+first);
            // System.out.println("second "+second);
            int[] firstPos= findPos(first,keyTable);
            int[] secondPos= findPos(second,keyTable);
            // System.out.println("firstPos "+firstPos[0]+ " "+firstPos[1]);
            // System.out.println("secondPos "+secondPos[0]+ " "+secondPos[1]);

            //same row
            if(firstPos[0]==secondPos[0]){
                sb.append(keyTable[firstPos[0]][(firstPos[1]+1 )%5]);
                sb.append(keyTable[secondPos[0]][(secondPos[1]+1 )%5]);
            }else 
            //same row
            if(firstPos[1]==secondPos[1]){
                sb.append(keyTable[(firstPos[0]+1) %5][firstPos[1]]);
                sb.append(keyTable[(secondPos[0]+1) %5][secondPos[1]]);
            }
            //rectangle
            else{
                sb.append(keyTable[firstPos[0]][secondPos[1]]);
                sb.append(keyTable[secondPos[0]][firstPos[1]]);
            }
        }
        return sb.toString();
    }

        public static String decrypt(String text, char [][] keyTable){
        StringBuilder sb= new StringBuilder();
        for(int i=0;i<text.length();i+=2){
            char first=text.charAt(i);
            char second=text.charAt(i+1);
            // System.out.println("first "+first);
            // System.out.println("second "+second);
            int[] firstPos= findPos(first,keyTable);
            int[] secondPos= findPos(second,keyTable);
            // System.out.println("firstPos "+firstPos[0]+ " "+firstPos[1]);
            // System.out.println("secondPos "+secondPos[0]+ " "+secondPos[1]);

            //same row
            if(firstPos[0]==secondPos[0]){
                sb.append(keyTable[firstPos[0]][(firstPos[1]+4 )%5]);
                sb.append(keyTable[secondPos[0]][(secondPos[1]+4 )%5]);
            }else 
            //same row
            if(firstPos[1]==secondPos[1]){
                sb.append(keyTable[(firstPos[0]+4) %5][firstPos[1]]);
                sb.append(keyTable[(secondPos[0]+4) %5][secondPos[1]]);
            }
            //rectangle
            else{
                sb.append(keyTable[firstPos[0]][secondPos[1]]);
                sb.append(keyTable[secondPos[0]][firstPos[1]]);
            }
        }
        return sb.toString();
    }

    public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter Key");
        String key=sc.nextLine();
        char [][] keyTable=generatetable(key);
        printTable(keyTable);
        System.out.println("Choose \n 1.Encrypt \n 2.Decrypt");
        int n= sc.nextInt();
        sc.nextLine();
        System.out.println("Enter text");
        String text= sc.nextLine();
        if(n==1){
            String processedText= preprocessText(text);
            System.out.println("processedText "+processedText);
            System.out.println(encrypt(processedText,keyTable));
        }else if(n==2){
            System.out.println(decrypt(text,keyTable));
        }else{
            System.out.println("Invalid");
        }
    }
}