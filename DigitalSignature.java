import java.util.Scanner;

public class DigitalSignature {
    // Method to compute modular exponentiation (a^b mod n)
    public static long modPow(long base, long exponent, long modulus) {
        long result = 1;
        base = base % modulus;
        
        while (exponent > 0) {
            // If exponent is odd, multiply result with base
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            
            // Exponent must be even now
            exponent = exponent >> 1; // Divide by 2
            base = (base * base) % modulus; // Square the base
        }
        
        return result;
    }
    
    // Method to find GCD of two numbers
    public static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }
    
    // Method to generate digital signature
    public static long generateSignature(long message, long d, long n) {
        // Signature = message^d mod n
        return modPow(message, d, n);
    }
    
    // Method to verify digital signature
    public static boolean verifySignature(long message, long signature, long e, long n) {
        // Verification: (signature^e mod n) should equal message
        long verification = modPow(signature, e, n);
        return verification == message;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Get RSA key components
        System.out.print("Enter p (prime number): ");
        long p = scanner.nextLong();
        
        System.out.print("Enter q (prime number): ");
        long q = scanner.nextLong();
        
        long n = p * q;
        long phi = (p - 1) * (q - 1);
        
        System.out.println("n = " + n);
        System.out.println("phi(n) = " + phi);
        
        // Find public exponent e (commonly 65537, but we'll find a simpler one)
        long e = 2;
        while (e < phi) {
            if (gcd(e, phi) == 1) {
                break;
            }
            e++;
        }
        
        System.out.println("Public exponent (e) = " + e);
        
        // Find private exponent d
        long d = 1;
        while ((e * d) % phi != 1) {
            d++;
        }
        
        System.out.println("Private exponent (d) = " + d);
        
        // Get message (numerical value)
        System.out.print("\nEnter message (numerical value): ");
        long message = scanner.nextLong();
        
        // Generate signature
        long signature = generateSignature(message, d, n);
        System.out.println("Digital signature: " + signature);
        
        // Verify signature
        boolean verified = verifySignature(message, signature, e, n);
        
        if (verified) {
            System.out.println("Signature is valid");
        } else {
            System.out.println("Signature is invalid");
        }
        
        scanner.close();
    }
}