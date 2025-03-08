import java.util.Scanner;

public class CaesarCipher {

    // Method to encrypt text
    public static String encrypt(String text, int shift) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isUpperCase(c)) {
                encrypted.append((char) ((c - 'A' + shift) % 26 + 'A'));
            } else if (Character.isLowerCase(c)) {
                encrypted.append((char) ((c - 'a' + shift) % 26 + 'a'));
            }
        }
        return encrypted.toString();
    }

    // Method to decrypt text
    public static String decrypt(String text, int shift) {
        return encrypt(text, 26 - shift); // Decryption is the reverse of encryption
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an option:");
        System.out.println("1. Encrypt");
        System.out.println("2. Decrypt");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        if (choice == 1 || choice == 2) {
            // Get input text
            System.out.print("Enter text: ");
            String text = scanner.nextLine();

            // Get shift value
            System.out.print("Enter shift value: ");
            int shift = scanner.nextInt();

            if (choice == 1) {
                // Encrypt the text
                String encryptedText = encrypt(text, shift);
                System.out.println("Encrypted text: " + encryptedText);
            } else {
                // Decrypt the text
                String decryptedText = decrypt(text, shift);
                System.out.println("Decrypted text: " + decryptedText);
            }
        } else {
            System.out.println("Invalid choice!");
        }

        scanner.close();
    }
}
