public class SDES {
   // Permutation tables
   static int[] PERMUTATION_10 = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
   static int[] PERMUTATION_8 = {6, 3, 7, 4, 8, 5, 10, 9};
   static int[] INITIAL_PERMUTATION = {2, 6, 3, 1, 4, 8, 5, 7};
   static int[] EXPANSION_PERMUTATION = {4, 1, 2, 3, 2, 3, 4, 1};
   static int[] INVERSE_PERMUTATION = {4, 1, 3, 5, 7, 2, 8, 6};

   static int[][] SBOX_0 = {
           {1, 0, 3, 2},
           {3, 2, 1, 0},
           {0, 2, 1, 3},
           {3, 1, 3, 2}
   };
   static int[][] SBOX_1 = {
           {0, 1, 2, 3},
           {2, 0, 1, 3},
           {3, 0, 1, 0},
           {2, 1, 0, 3}
   };

   static int[] PERMUTATION_4 = {2, 4, 3, 1};

    // Permutation method
   public static String permute(String input, int[] table) {
       StringBuilder output = new StringBuilder();
       // Use permutation table to rearrange bits
       for (int index : table) {
           output.append(input.charAt(index - 1));
       }
       return output.toString();
   }

    // Left shift method
   public static String leftShift(String input, int shift) {
       // Perform circular left shift
       return input.substring(shift) + input.substring(0, shift);
   }

    // Subkey generation method
   public static String[] generateSubKeys(String key) {
       // Apply P10 permutation to the key
       String p10Permutation = permute(key, PERMUTATION_10);

       // Perform left shifts on both halves
       String leftShift1 = leftShift(p10Permutation.substring(0, 5), 1) +
                           leftShift(p10Permutation.substring(5, 10), 1);

       // Generate first subkey using P8 permutation
       String k1 = permute(leftShift1, PERMUTATION_8);

       // Perform second left shift
       String leftShift2 = leftShift(leftShift1.substring(0, 5), 2) +
                           leftShift(leftShift1.substring(5, 10), 2);

       // Generate second subkey using P8 permutation
       String k2 = permute(leftShift2, PERMUTATION_8);

       return new String[]{k1, k2};
   }

    // XOR operation method
   public static String xor(String a, String b) {
       StringBuilder result = new StringBuilder();

       // Perform bit-wise XOR
       for (int i = 0; i < a.length(); i++) {
           result.append(a.charAt(i) == b.charAt(i) ? '0' : '1');
       }

       return result.toString();
   }

    // S-box substitution method
   public static String sBox(String input) {
       // Split input into left and right 4-bit halves
       String leftHalf = input.substring(0, 4);
       String rightHalf = input.substring(4, 8);

       // S0 Box calculation
       // Extract row and column from left half
       int row0 = Integer.parseInt("" + leftHalf.charAt(0) + leftHalf.charAt(3), 2);
       int col0 = Integer.parseInt("" + leftHalf.charAt(1) + leftHalf.charAt(2), 2);
       String s0Output = Integer.toBinaryString(SBOX_0[row0][col0]);

       // S1 Box calculation
       // Extract row and column from right half
       int row1 = Integer.parseInt("" + rightHalf.charAt(0) + rightHalf.charAt(3), 2);
       int col1 = Integer.parseInt("" + rightHalf.charAt(1) + rightHalf.charAt(2), 2);
       String s1Output = Integer.toBinaryString(SBOX_1[row1][col1]);

       // Ensure 2-bit representation by padding
       s0Output = ("00" + s0Output).substring(s0Output.length());
       s1Output = ("00" + s1Output).substring(s1Output.length());

       // Combine S-box outputs
       return s0Output + s1Output;
   }

    // Feistel function
   public static String feistelRound(String left, String right, String key) {
       // Expand right half using expansion permutation
       String expandedRight = permute(right, EXPANSION_PERMUTATION);

       // XOR expanded right half with subkey
       String xorResult = xor(expandedRight, key);

       // Apply S-box substitution
       String sBoxOutput = sBox(xorResult);

       // Permute S-box output
       String permutedSBox = permute(sBoxOutput, PERMUTATION_4);

       // XOR left half with permuted S-box output
       String leftXOR = xor(left, permutedSBox);

       // Combine XOR result with original right half
       return leftXOR + right;
   }

    // Encryption method
   public static String encrypt(String plaintext, String k1, String k2) {
       // Apply initial permutation to the plaintext
       String permutedText = permute(plaintext, INITIAL_PERMUTATION);

       // Split the permuted text into left and right halves
       String leftHalf = permutedText.substring(0, 4);
       String rightHalf = permutedText.substring(4, 8);

       // First round of Feistel function
       String firstRoundResult = feistelRound(leftHalf, rightHalf, k1);
      
       // Extract left and right halves after first round
       leftHalf = firstRoundResult.substring(0, 4);
       rightHalf = firstRoundResult.substring(4, 8);

       // Swap halves before second round (classic Feistel network step)
       String swappedHalves = rightHalf + leftHalf;

       // Second round of Feistel function
       String finalRoundResult = feistelRound(swappedHalves.substring(0, 4),
                                    swappedHalves.substring(4, 8), k2);

       // Apply final inverse permutation
       return permute(finalRoundResult, INVERSE_PERMUTATION);
   }

   public static void main(String[] args) {
       String plaintext = "10010111";  // 8-bit plaintext
       String secretKey = "1010000010";  // 10-bit key

       // Generate subkeys using the secret key
       String[] subKeys = generateSubKeys(secretKey);

       // Print out the generated subkeys
       System.out.println("Subkey 1: " + subKeys[0]);
       System.out.println("Subkey 2: " + subKeys[1]);

       // Encrypt the plaintext
       String ciphertext = encrypt(plaintext, subKeys[0], subKeys[1]);
       System.out.println("Encrypted Text: " + ciphertext);
   }
}