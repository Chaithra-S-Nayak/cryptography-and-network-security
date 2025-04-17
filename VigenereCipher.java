import java.util.Scanner;
public class VigenereCipher {

   public static String encrypt(String text, String key) {
       StringBuilder result = new StringBuilder();

       // Convert key to match the length of the text
       key = keyRepeat(text, key);

       // Loop through each character in the text
       for (int i = 0; i < text.length(); i++) {
           char ch = text.charAt(i);

           // Encrypt only alphabetic characters
           if (Character.isLetter(ch)) {
               int encryptedChar = (ch - 'A' + (key.charAt(i) - 'A')) % 26;
               result.append((char) ('A' + encryptedChar));
           } else {
               result.append(ch); // Keep non-alphabetic characters unchanged
           }
       }
       return result.toString();
   }

   public static String decrypt(String text, String key) {
       StringBuilder result = new StringBuilder();

       // Convert key to match the length of the text
       key = keyRepeat(text, key);

       // Loop through each character in the text
       for (int i = 0; i < text.length(); i++) {
           char ch = text.charAt(i);
           // Decrypt only alphabetic characters
           if (Character.isLetter(ch)) {
               int decryptedChar = (ch - 'A' - (key.charAt(i) - 'A') + 26) % 26;
               result.append((char) ('A' + decryptedChar));
           } else {
               result.append(ch); // Keep non-alphabetic characters unchanged
           }
       }
       return result.toString();
   }

   // Helper method to repeat the key to match the length of the text
   private static String keyRepeat(String text, String key) {
       StringBuilder repeatedKey = new StringBuilder();
       for (int i = 0; repeatedKey.length() < text.length(); i++) {
           repeatedKey.append(key.charAt(i % key.length()));
       }
       return repeatedKey.toString();
   }

   public static void main(String[] args) {
       Scanner sc = new Scanner(System.in);
       System.out.print("Enter the plain text : ");
       String plaintext = sc.nextLine().toUpperCase();
       System.out.print("Enter the key: ");
       String key = sc.nextLine().toUpperCase();
       String encryptedText = encrypt(plaintext, key);
       System.out.println("Encrypted Text: " + encryptedText);
       String decryptedText = decrypt(encryptedText, key);
       System.out.println("Decrypted Text: " + decryptedText);
   }
}
