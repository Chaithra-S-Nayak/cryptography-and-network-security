import java.util.Scanner;

public class ElGamal {

    // Modular exponentiation: (base^exp) % mod
    public static int modPow(int base, int exp, int mod) {
        int result = 1;
        base = base % mod;
        while (exp > 0) {
            if ((exp & 1) == 1)
                result = (result * base) % mod;
            exp = exp >> 1;
            base = (base * base) % mod;
        }
        return result;
    }

    // Modular inverse using Fermat's Little Theorem (only when mod is prime)
    public static int modInverse(int a, int mod) {
        return modPow(a, mod - 2, mod);  // a^(mod-2) % mod
    }

    // Key generation
    public static int[] generateKeys(int p, int g, int x) {
        int h = modPow(g, x, p); // h = g^x mod p
        return new int[]{p, g, h}; // Public key
    }

    // Encryption
    public static int[] encrypt(int m, int p, int g, int h, int y) {
        int c1 = modPow(g, y, p);         // c1 = g^y mod p
        int s = modPow(h, y, p);          // s = h^y mod p
        int c2 = (m * s) % p;             // c2 = m * s mod p
        return new int[]{c1, c2};
    }

    // Decryption
    public static int decrypt(int c1, int c2, int p, int x) {
        int s = modPow(c1, x, p);         // s = c1^x mod p
        int sInv = modInverse(s, p);      // s^(-1) mod p
        return (c2 * sInv) % p;           // m = c2 * s^(-1) mod p
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter value of p: ");
        int p = sc.nextInt();

        System.out.print("Enter value of g: ");
        int g = sc.nextInt();

        System.out.print("Enter private key: ");
        int x = sc.nextInt();

        System.out.print("Enter Random number: ");
        int y = sc.nextInt();

        System.out.print("Enter Plain Text: ");
        int m = sc.nextInt();

        // Key generation
        int[] publicKey = generateKeys(p, g, x);
        int h = publicKey[2];

        // Encrypt
        int[] cipher = encrypt(m, p, g, h, y);
        System.out.println("Encrypted: c1 = " + cipher[0] + ", c2 = " + cipher[1]);

        // Decrypt
        int decrypted = decrypt(cipher[0], cipher[1], p, x);
        System.out.println("Decrypted Message: " + decrypted);

    }
}
