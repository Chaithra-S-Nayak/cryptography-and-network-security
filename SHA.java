import java.util.Scanner;

public class SHA {
    // Constants used in each round
    public static final int[] K = {
        0x5A827999, 0x6ED9EBA1, 0x8F1BBCDC, 0xCA62C1D6
    };

    // Initial hash values (standard in SHA-1)
    public static final int[] H0 = {
        0x67452301, 0xEFCDAB89, 0x98BADCFE, 0x10325476, 0xC3D2E1F0
    };

    // Left rotate function (circular left shift)
    public static int leftRotate(int x, int n) {
        return ((x << n) | (x >>> (32 - n)));
    }

    // Main SHA-1 hashing function
    public static String hash(String message) {
        byte[] bytes = message.getBytes();
        long originalLengthBits = bytes.length * 8L;
        
        int paddingBytes = (56 - (bytes.length % 64)) % 64;
        if (paddingBytes == 0) paddingBytes = 64;
        
        byte[] paddedMessage = new byte[bytes.length + paddingBytes + 8];
        System.arraycopy(bytes, 0, paddedMessage, 0, bytes.length);
        paddedMessage[bytes.length] = (byte) 0x80;
        
        for (int i = 0; i < 8; i++) {
            paddedMessage[paddedMessage.length - 8 + i] = (byte) (originalLengthBits >>> (56 - 8 * i));
        }
        
        int[] h = H0.clone();
        int numBlocks = paddedMessage.length / 64;
        
        for (int block = 0; block < numBlocks; block++) {
            int[] w = new int[80];
            
            for (int i = 0; i < 16; i++) {
                w[i] = ((paddedMessage[block * 64 + i * 4] & 0xFF) << 24) |
                       ((paddedMessage[block * 64 + i * 4 + 1] & 0xFF) << 16) |
                       ((paddedMessage[block * 64 + i * 4 + 2] & 0xFF) << 8) |
                       (paddedMessage[block * 64 + i * 4 + 3] & 0xFF);
            }
            
            for (int i = 16; i < 80; i++) {
                w[i] = leftRotate(w[i-3] ^ w[i-8] ^ w[i-14] ^ w[i-16], 1);
            }
            
            int a = h[0], b = h[1], c = h[2], d = h[3], e = h[4];
            
            for (int i = 0; i < 80; i++) {
                int f, k;
                
                if (i < 20) {
                    f = (b & c) | ((~b) & d);
                    k = K[0];
                } else if (i < 40) {
                    f = b ^ c ^ d;
                    k = K[1];
                } else if (i < 60) {
                    f = (b & c) | (b & d) | (c & d);
                    k = K[2];
                } else {
                    f = b ^ c ^ d;
                    k = K[3];
                }
                
                int temp = leftRotate(a, 5) + f + e + k + w[i];
                e = d;
                d = c;
                c = leftRotate(b, 30);
                b = a;
                a = temp;
            }
            
            h[0] += a; h[1] += b; h[2] += c; h[3] += d; h[4] += e;
        }
        
        StringBuilder result = new StringBuilder();
        for (int value : h) {
            result.append(String.format("%08x", value));
        }
        
        return result.toString();
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a message to hash: ");
        String message = scanner.nextLine();
        System.out.println("SHA-1 Hash: " + hash(message));
        scanner.close();
    }
}