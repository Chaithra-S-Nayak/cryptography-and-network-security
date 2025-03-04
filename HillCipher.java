import java.util.Scanner;

public class HillCipher {
    
    // Function to pad text with 'X' to make its length a multiple of the matrix size
    public static String padText(String text, int size) {
        while (text.length() % size != 0)
            text += 'X';  // Padding with 'X'
        return text;
    }

    // Function to compute the determinant of an n x n matrix using recursion
    public static int determinant(int[][] matrix, int n) {
        // Base case: If the matrix is 2x2, directly compute determinant using the formula
        if (n == 2)
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        int det = 0; // Initialize determinant value

        // Loop through the first row to expand along it (Laplace expansion)
        for (int x = 0; x < n; x++) {
            int[][] subMatrix = new int[n - 1][n - 1]; // Create submatrix for minors

            // Construct the submatrix by excluding the current row (0) and column (x)
            for (int i = 1; i < n; i++) { // Start from row index 1 (skip first row)
                for (int j = 0, col = 0; j < n; j++) { 
                    if (j != x) // Skip the current column
                        subMatrix[i - 1][col++] = matrix[i][j]; // Store remaining elements
                }
            }

            // Apply recursive determinant calculation (Laplace Expansion)
            det += (x % 2 == 0 ? 1 : -1) * matrix[0][x] * determinant(subMatrix, n - 1);
            // The (x % 2 == 0 ? 1 : -1) term ensures correct sign based on column index
        }

        return det; // Return computed determinant
    }

    // Function to calculate the modular inverse of a number under modulo m
    public static int modInverse(int a, int m) {
        a = (a % m + m) % m; // Ensure non-negative value
        for (int x = 1; x < m; x++)
            if ((a * x) % m == 1)
                return x;
        return 1; // If no modular inverse found, return 1 as a fallback
    }

    // Function to compute the adjoint (cofactor matrix transposed)
    public static int[][] adjoint(int[][] matrix, int n) {
        int[][] adj = new int[n][n];

        // Special case for 2x2 matrix
        if (n == 2) {
            adj[0][0] = matrix[1][1];
            adj[0][1] = -matrix[0][1];
            adj[1][0] = -matrix[1][0];
            adj[1][1] = matrix[0][0];
            return adj;
        }

        // Compute cofactors for general case
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int[][] subMatrix = new int[n - 1][n - 1];
                for (int row = 0, subRow = 0; row < n; row++) {
                    if (row == i) continue;
                    for (int col = 0, subCol = 0; col < n; col++) {
                        if (col == j) continue;
                        subMatrix[subRow][subCol++] = matrix[row][col];
                    }
                    subRow++;
                }
                adj[j][i] = (int) Math.pow(-1, i + j) * determinant(subMatrix, n - 1);
            }
        }
        return adj;
    }

    // Function to compute the inverse of the key matrix modulo 26
    public static int[][] inverseKeyMatrix(int[][] keyMatrix, int n) {
        int det = (determinant(keyMatrix, n) % 26 + 26) % 26; // Ensure positive determinant
        int detInverse = modInverse(det, 26);
        int[][] adj = adjoint(keyMatrix, n);
        int[][] inverseMatrix = new int[n][n];

        // Multiply adjoint matrix by determinant inverse (mod 26)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                inverseMatrix[i][j] = (adj[i][j] * detInverse % 26 + 26) % 26;
            }
        }
        return inverseMatrix;
    }

    // Function to multiply a matrix with a vector
    public static int[] matrixMultiply(int[][] keyMatrix, int[] vector, int size) {
        int[] result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = 0;
            for (int j = 0; j < size; j++)
                result[i] += keyMatrix[i][j] * vector[j];

            result[i] = (result[i] % 26 + 26) % 26;  // Ensure non-negative mod 26
        }
        return result;
    }

    // Function to encrypt or decrypt text
    public static String processText(String text, int[][] keyMatrix, int size, boolean isEncryption) {
        if (!isEncryption)
            keyMatrix = inverseKeyMatrix(keyMatrix, size); // Use inverse for decryption

        text = padText(text, size); // Ensure text is a multiple of matrix size
        StringBuilder result = new StringBuilder();

        // Process text in blocks of size 'size'
        for (int i = 0; i < text.length(); i += size) {
            int[] textVector = new int[size];
            for (int j = 0; j < size; j++)
                textVector[j] = text.charAt(i + j) - 'A'; // Convert to numeric values

            int[] transformedVector = matrixMultiply(keyMatrix, textVector, size);

            for (int j = 0; j < size; j++)
                result.append((char) (transformedVector[j] + 'A')); // Convert back to letters
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter text: ");
        String text = scanner.nextLine().toUpperCase().replaceAll("\\s", ""); // Convert to uppercase and remove spaces

        System.out.print("Enter the size of key: ");
        int size = scanner.nextInt();

        int[][] keyMatrix = new int[size][size];
        System.out.println("Enter key matrix: ");
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                keyMatrix[i][j] = scanner.nextInt();

        System.out.println("\nChoose the operation: ");
        System.out.println("1. Encryption\n2. Decryption");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 1)
            System.out.println("\nEncrypted Message: " + processText(text, keyMatrix, size, true));
        else
            System.out.println("\nDecrypted Message: " + processText(text, keyMatrix, size, false));

        scanner.close();
    }
}
