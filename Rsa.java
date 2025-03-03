import java.math.BigInteger;
import java.util.Scanner;

class Rsa {
   // Function to compute base^expo mod m using BigInteger
   static BigInteger power(BigInteger base, BigInteger expo, BigInteger m) {
       return base.modPow(expo, m);
   }

   // Encrypt message using public key (e, n)
   static BigInteger encrypt(BigInteger m, BigInteger e, BigInteger n) {
       return power(m, e, n);
   }

   // Decrypt message using private key (d, n)
   static BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n) {
       return power(c, d, n);
   }

   public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);

       System.out.print("Enter prime number p: ");
       BigInteger p = new BigInteger(scanner.next());
       System.out.print("Enter prime number q: ");
       BigInteger q = new BigInteger(scanner.next());

       // Calculate n and Φ(n)
       BigInteger n = p.multiply(q); // n = p * q
       BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE)); // Φ(n) = (p - 1) * (q - 1)

       // Input public exponent e
       System.out.print("Enter public exponent e: ");
       BigInteger e = new BigInteger(scanner.next());

       // Validate that e and Φ(n) are coprime
       if (!e.gcd(phi).equals(BigInteger.ONE)) {
           System.out.println("Error: e and Φ(n) are not coprime. Exiting...");
           return;
       }

       // Calculate private key d using modular multiplicative inverse
       BigInteger d = e.modInverse(phi); // d * e ≡ 1 mod Φ(n)

       // Display calculated keys
       System.out.println("Calculated values:");
       System.out.println("Modulus n: " + n);
       System.out.println("Private Key d: " + d);

       // Provide menu for user choice
       System.out.println("Choose an operation:");
       System.out.println("1. Encrypt a message");
       System.out.println("2. Decrypt a message");
       System.out.print("Enter your choice (1 or 2): ");
       int choice = scanner.nextInt();

       if (choice == 1) {
           System.out.print("Enter Original Message (M): ");
           BigInteger M = new BigInteger(scanner.next());
           BigInteger C = encrypt(M, e, n);
           System.out.println("Encrypted Message: " + C);
       } else if (choice == 2) {
           System.out.print("Enter Encrypted Message (C): ");
           BigInteger C = new BigInteger(scanner.next());
           BigInteger decrypted = decrypt(C, d, n);
           System.out.println("Decrypted Message: " + decrypted);
       } else {
           System.out.println("Invalid choice. Exiting...");
       }
   }
}
