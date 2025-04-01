public class ShaAk{
   // Initial SHA-1 buffer values (constants)
   private static final int[] INITIAL_HASHES = {
           0x67452301,
           0xEFCDAB89,
           0x98BADCFE,
           0x10325476,
           0xC3D2E1F0
   };
   // Left rotate a 32-bit integer
   private static int leftRotate(int value, int bits) {
       return (value << bits) | (value >>> (32 - bits));
   }
   // Main SHA-1 function
   public static String sha1(String input) {
       byte[] messageBytes = input.getBytes();
       // Use long to avoid overflow (important for large inputs)
       long messageBitLength = (long) messageBytes.length * 8;
       // --- Step 1: Padding ---
       int newLength = messageBytes.length + 1;
       while ((newLength * 8) % 512 != 448) {
           newLength++;
       }
       // Allocate space for padding + 64-bit length
       byte[] paddedMessage = new byte[newLength + 8];
       System.arraycopy(messageBytes, 0, paddedMessage, 0, messageBytes.length);
       // Append '1' bit (0x80 = 10000000)
       paddedMessage[messageBytes.length] = (byte) 0x80;
       // Append original message length in bits as 64-bit big-endian integer
       for (int i = 0; i < 8; i++) {
           paddedMessage[paddedMessage.length - 1 - i] = (byte) ((messageBitLength >>> (8 * i)) & 0xFF);
       }
       // Working buffer
       int[] hash = INITIAL_HASHES.clone();
       int[] w = new int[80];
       // --- Step 2: Process 512-bit chunks ---
       for (int i = 0; i < paddedMessage.length; i += 64) {
           // Break chunk into 16 big-endian 32-bit words
           for (int j = 0; j < 16; j++) {
               int index = i + j * 4;
               w[j] = ((paddedMessage[index] & 0xFF) << 24) |
                       ((paddedMessage[index + 1] & 0xFF) << 16) |
                       ((paddedMessage[index + 2] & 0xFF) << 8) |
                       (paddedMessage[index + 3] & 0xFF);
           }
           // Extend the 16 words into 80 words
           for (int j = 16; j < 80; j++) {
               w[j] = leftRotate(w[j - 3] ^ w[j - 8] ^ w[j - 14] ^ w[j - 16], 1);
           }
           // Initialize hash value for this chunk
           int a = hash[0];
           int b = hash[1];
           int c = hash[2];
           int d = hash[3];
           int e = hash[4];
           // Main loop (80 rounds)
           for (int j = 0; j < 80; j++) {
               int f, k;
               if (j < 20) {
                   f = (b & c) | ((~b) & d);
                   k = 0x5A827999;
               } else if (j < 40) {
                   f = b ^ c ^ d;
                   k = 0x6ED9EBA1;
               } else if (j < 60) {
                   f = (b & c) | (b & d) | (c & d);
                   k = 0x8F1BBCDC;
               } else {
                   f = b ^ c ^ d;
                   k = 0xCA62C1D6;
               }
               int temp = leftRotate(a, 5) + f + e + k + w[j];
               e = d;
               d = c;
               c = leftRotate(b, 30);
               b = a;
               a = temp;
           }
           // Add this chunk's hash to result
           hash[0] += a;
           hash[1] += b;
           hash[2] += c;
           hash[3] += d;
           hash[4] += e;
       }
       // --- Step 3: Produce final hex digest ---
       StringBuilder hexResult = new StringBuilder();
       for (int hPart : hash) {
           hexResult.append(String.format("%08x", hPart));
       }
       return hexResult.toString();
   }

   public static void main(String[] args) {
       String input1 = "hello";
       System.out.println("Input: " + input1);
       System.out.println("SHA-1 Hash: " + sha1(input1));
       String input2 = "secure hash";
       System.out.println("Input: " + input2);
       System.out.println("SHA-1 Hash: " + sha1(input2));
   }
}
