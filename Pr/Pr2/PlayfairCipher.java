import java.util.Scanner;
class PlayfairCipher{
        public static char[][] processKey(String key,char[][] keyMat){
            boolean used[]= new boolean[26];
            int row=0,col=0;
            for(int i=0;i<key.length();i++){
                char c=key.charAt(i);
                if(!used[c-'A']){
                    used[c-'A']=true;
                    keyMat[row][col++]=c;
                    if(col==5){
                        row++;
                        col=0;
                    }
                }
                
            }
            for(char i='A';i<='Z';i++){
                if(!used[i-'A'] && i!='J'){
                    used[i-'A']=true;
                    keyMat[row][col++]=i;
                    if(col==5){
                        row++;
                        col=0;
                    }
                }
            }
            return keyMat;
        }
        public static String processText(String text){
            StringBuilder sb= new StringBuilder();
            for(int i=0;i<text.length();i++){
                if(i+1<text.length() && text.charAt(i)==text.charAt(i+1)){
                    sb.append(text.charAt(i));
                    sb.append('X');
                }else{
                    sb.append(text.charAt(i));
                }
            }
            if(sb.length()%2!=0){
                sb.append('X');
            }
            return sb.toString();
        }
        public static int[] findPos(char c, char[][] keyMat){
            for(int i=0;i<5;i++){
                for(int j=0;j<5;j++){
                    if(keyMat[i][j]==c){
                        return new int[] {i,j};
                    }
                }
            }
            return null;
        }
        public static StringBuilder encrypt(String word, char[][] keyMat){
            StringBuilder encrypted= new StringBuilder();
            for(int i=0;i<word.length();i+=2){
                char first=word.charAt(i);
                char second=word.charAt(i+1);

                int[] firstPos=findPos(first,keyMat);
                int[] secondPos=findPos(second,keyMat);

                if(firstPos[0]==secondPos[0]){
                    encrypted.append(keyMat[firstPos[0]][(firstPos[1]+1)%5]);
                    encrypted.append(keyMat[firstPos[0]][(secondPos[1]+1)%5]);
                }else if(firstPos[1]==secondPos[1]){
                    encrypted.append(keyMat[(firstPos[0]+1)%5][firstPos[1]]);
                    encrypted.append(keyMat[(secondPos[0]+1)%5][firstPos[1]]);
                }else{
                    encrypted.append(keyMat[firstPos[0]][secondPos[1]]);
                    encrypted.append(keyMat[secondPos[0]][firstPos[1]]);
                }
            }
            return encrypted;
        }
        public static StringBuilder decrypt(String word, char[][] keyMat){
            StringBuilder decrypted= new StringBuilder();
            for(int i=0;i<word.length();i+=2){
                char first=word.charAt(i);
                char second=word.charAt(i+1);

                int[] firstPos=findPos(first,keyMat);
                int[] secondPos=findPos(second,keyMat);

                if(firstPos[0]==secondPos[0]){
                    decrypted.append(keyMat[firstPos[0]][(firstPos[1]+4)%5]);
                    decrypted.append(keyMat[firstPos[0]][(secondPos[1]+4)%5]);
                }else if(firstPos[1]==secondPos[1]){
                    decrypted.append(keyMat[(firstPos[0]+4)%5][firstPos[1]]);
                    decrypted.append(keyMat[(secondPos[0]+4)%5][firstPos[1]]);
                }else{
                    decrypted.append(keyMat[firstPos[0]][secondPos[1]]);
                    decrypted.append(keyMat[secondPos[0]][firstPos[1]]);
                }
            }
            return decrypted;
        }
        public static void main(String args[]){
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter the String");
        String word=sc.nextLine();
        word=word.toUpperCase().replaceAll("J","I");
        word=processText(word);
        System.out.println("Enter the key");
        char keyMat[][]=new char[5][5];
        String key=sc.nextLine();
        key=key.toUpperCase().replaceAll("J","I");
        keyMat=processKey(key,keyMat);
        System.out.println("Enter 1.encryption or 2. decryption");
        int n=sc.nextInt();
        sc.nextLine();
        if(n==1){
            System.out.println("encrypted text "+ encrypt(word,keyMat));
        }else if(n==2){
            System.out.println("decrypted text "+ decrypt(word,keyMat));
        }else {
            System.out.println("invalid");
        }
    }
}