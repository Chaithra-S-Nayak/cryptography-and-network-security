import java.util.Scanner;

public class HillCipher{
   public static void main(String[] args) {
       Scanner s = new Scanner(System.in);
       System.out.print("Text: ");
       String text = s.nextLine().toUpperCase().replaceAll("\\s", "");
       System.out.print("Size: ");
       int size = s.nextInt(), key[][] = new int[size][size];
       System.out.println("Key: ");
       for (int i = 0; i < size; i++)
           for (int j = 0; j < size; j++)
               key[i][j] = s.nextInt();
       System.out.print("1. Encrypt 2. Decrypt: ");
       int choice = s.nextInt();
       System.out.println((choice == 1 ? "Encrypted: " : "Decrypted: ") + process(text, key, size, choice == 1));
   }


   private static int modInv(int a, int m) {
       for (int x = 1; x < m; x++) if ((a * x) % m == 1) return x;
       throw new ArithmeticException("Modular inverse does not exist");
   }


   private static int det(int[][] m, int n) {
       if (n == 2) return (m[0][0] * m[1][1] - m[0][1] * m[1][0]) % 26;
       int d = 0;
       for (int x = 0; x < n; x++)
           d = (d + (x % 2 == 0 ? 1 : -1) * m[0][x] * det(subMatrix(m, 0, x, n), n - 1)) % 26;
       return (d + 26) % 26;
   }


   private static int[][] inv(int[][] k, int n) {
       int d = det(k, n), di = modInv(d, 26), inv[][] = new int[n][n];
       for (int i = 0; i < n; i++)
           for (int j = 0; j < n; j++)
               inv[j][i] = ((det(subMatrix(k, i, j, n), n - 1) * (1 - 2 * ((i + j) % 2)) * di) % 26 + 26) % 26;
       return inv;
   }


   private static int[][] subMatrix(int[][] m, int r, int c, int n) {
       int[][] sub = new int[n - 1][n - 1];
       for (int i = 0, si = 0; i < n; i++) {
           if (i == r) continue;
           for (int j = 0, sj = 0; j < n; j++)
               if (j != c) sub[si][sj++] = m[i][j];
           si++;
       }
       return sub;
   }


   private static String process(String m, int[][] k, int s, boolean enc) {
       while (m.length() % s != 0) m += 'X';
       StringBuilder r = new StringBuilder();
       int[][] keyMatrix = enc ? k : inv(k, s);
       for (int i = 0; i < m.length(); i += s) {
           int[] v = new int[s];
           for (int j = 0; j < s; j++) v[j] = m.charAt(i + j) - 'A';
           for (int j = 0; j < s; j++) {
               int sum = 0;
               for (int l = 0; l < s; l++) sum += v[l] * keyMatrix[l][j];
               r.append((char) ((sum % 26 + 26) % 26 + 'A'));
           }
       }
       return r.toString();
   }
}
