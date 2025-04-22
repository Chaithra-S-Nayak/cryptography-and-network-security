import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;

public class DiffieHellman {
    static final BigInteger ONE = BigInteger.ONE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a prime number (q): ");
        BigInteger q = new BigInteger(sc.nextLine());

        System.out.print("Enter a primitive root (alpha): ");
        BigInteger alpha = new BigInteger(sc.nextLine());

        SecureRandom random = new SecureRandom();

        BigInteger Xa = new BigInteger(q.bitLength(), random)
                            .mod(q.subtract(ONE)).add(ONE);
        BigInteger Xb = new BigInteger(q.bitLength(), random)
                            .mod(q.subtract(ONE)).add(ONE);

        BigInteger Ya = alpha.modPow(Xa, q);
        BigInteger Yb = alpha.modPow(Xb, q);

        BigInteger Ka = Yb.modPow(Xa, q);
        BigInteger Kb = Ya.modPow(Xb, q);

        System.out.println("Prime (q): " + q);
        System.out.println("Primitive root (alpha): " + alpha);
        System.out.println("Alice's Private Key: " + Xa);
        System.out.println("Alice's Public Key: " + Ya);
        System.out.println("Bob's Private Key: " + Xb);
        System.out.println("Bob's Public Key: " + Yb);
        System.out.println("Alice's Shared Secret: " + Ka);
        System.out.println("Bob's Shared Secret: " + Kb);

        if (Ka.equals(Kb)) {
            System.out.println("Shared secret established successfully!");
        } else {
            System.out.println("Shared secret mismatch!");
        }

        sc.close();
    }
}
