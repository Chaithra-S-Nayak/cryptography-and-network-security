import java.util.Scanner;

public class PlayfairCipher {

   public static char[][] generateKeyTable(String key) {
       boolean[] used = new boolean[26];
       char[][] keyTable = new char[5][5];
       int row = 0, col = 0;

       // removing duplicates and convert to uppercase
       key = key.toUpperCase().replace("J", "I");
       for (int i = 0; i < key.length(); i++) {
           char c = key.charAt(i);
           if (!used[c - 'A']) {
               used[c - 'A'] = true;
               keyTable[row][col++] = c;
               if (col == 5) {
                   col = 0;
                   row++;
               }
           }
       }

       // fill the remaining spaces with unused letters
       for (char c = 'A'; c <= 'Z'; c++) {
           if (!used[c - 'A'] && c != 'J') {
               keyTable[row][col++] = c;
               if (col == 5) {
                   col = 0;
                   row++;
               }
           }
       }

       return keyTable;
   }

   // pairing and filling
   public static String preprocessMessage(String message) {
       message = message.toUpperCase().replace("J", "I");
       StringBuilder result = new StringBuilder();
       for (int i = 0; i < message.length(); i++) {
           if (i + 1 < message.length() && message.charAt(i) == message.charAt(i + 1)) {
               result.append(message.charAt(i)).append('X');
           } else {
               result.append(message.charAt(i));
           }
       }
       if (result.length() % 2 != 0) {
           result.append('X');
       }
       return result.toString();
   }

   public static void printKeyTable(char[][] keyTable) {
       for (int i = 0; i < keyTable.length; i++) {
           for (int j = 0; j < keyTable[0].length; j++) {
               System.out.print(keyTable[i][j] + " ");
           }
           System.out.println();
       }
   }

   // find position of a char
   public static int[] findPosition(char c, char[][] keyTable) {
       for (int i = 0; i < 5; i++) {
           for (int j = 0; j < 5; j++) {
               if (keyTable[i][j] == c) {
                   return new int[]{i, j};
               }
           }
       }
       return null;
   }

   // encrypt
   public static String encrypt(String message, char[][] keyTable) {
       StringBuilder encryptedMessage = new StringBuilder();
       for (int i = 0; i < message.length(); i += 2) {
           char first = message.charAt(i);
           char second = message.charAt(i + 1);
           int[] firstPos = findPosition(first, keyTable);
           int[] secondPos = findPosition(second, keyTable);

           // same row case
           if (firstPos[0] == secondPos[0]) {
               encryptedMessage.append(keyTable[firstPos[0]][(firstPos[1] + 1) % 5]);
               encryptedMessage.append(keyTable[firstPos[0]][(secondPos[1] + 1) % 5]);
           } else if (firstPos[1] == secondPos[1]) { // same column case
               encryptedMessage.append(keyTable[(firstPos[0] + 1) % 5][firstPos[1]]);
               encryptedMessage.append(keyTable[(secondPos[0] + 1) % 5][firstPos[1]]);
           } else { // rectangle
               encryptedMessage.append(keyTable[firstPos[0]][secondPos[1]]);
               encryptedMessage.append(keyTable[secondPos[0]][firstPos[1]]);
           }
       }
       return encryptedMessage.toString();
   }

   // decrypt
   public static String decrypt(String message, char[][] keyTable) {
       StringBuilder decryptedMessage = new StringBuilder();
       for (int i = 0; i < message.length(); i += 2) {
           char first = message.charAt(i);
           char second = message.charAt(i + 1);
           int[] firstPos = findPosition(first, keyTable);
           int[] secondPos = findPosition(second, keyTable);


           if (firstPos[0] == secondPos[0]) {
               decryptedMessage.append(keyTable[firstPos[0]][(firstPos[1] + 4) % 5]);
               decryptedMessage.append(keyTable[firstPos[0]][(secondPos[1] + 4) % 5]);
           } else if (firstPos[1] == secondPos[1]) {
               decryptedMessage.append(keyTable[(firstPos[0] + 4) % 5][firstPos[1]]);
               decryptedMessage.append(keyTable[(secondPos[0] + 4) % 5][firstPos[1]]);
           } else {
               decryptedMessage.append(keyTable[firstPos[0]][secondPos[1]]);
               decryptedMessage.append(keyTable[secondPos[0]][firstPos[1]]);
           }
       }
       return decryptedMessage.toString();
   }

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
       System.out.print("Enter the key: ");
       String key = scanner.nextLine();
       char[][] keyTable = generateKeyTable(key);

       System.out.println("Key Table:");
       printKeyTable(keyTable);

       System.out.println("Choose an option:");
       System.out.println("1. Encrypt");
       System.out.println("2. Decrypt");
       int choice = scanner.nextInt();
       scanner.nextLine(); // consume the newline

       if (choice == 1) {
           System.out.print("Enter the message to encrypt: ");
           String message = scanner.nextLine();
           message = preprocessMessage(message);
           System.out.println("Processed Text: " + message);
           String encryptedMessage = encrypt(message, keyTable);
           System.out.println("Encrypted Message: " + encryptedMessage);
       } else if (choice == 2) {
           System.out.print("Enter the message to decrypt: ");
           String message = scanner.nextLine();
           String decryptedMessage = decrypt(message, keyTable);
           System.out.println("Decrypted Message: " + decryptedMessage);
       } else {
           System.out.println("Invalid choice.");
       }
   }
}
