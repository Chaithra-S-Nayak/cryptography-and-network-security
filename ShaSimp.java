import java.util.Scanner;

public class ShaSimp {
    static int K[] = {0x5A827999,0x6ED9EBA1,0x8F1BBCDC,0xCA62C1D6}, H[] = {0x67452301,0xEFCDAB89,0x98BADCFE,0x10325476,0xC3D2E1F0};

    static int L(int x, int n) { return (x << n) | (x >>> (32 - n)); }

    static byte[] pad(byte[] m) {
        int l = m.length, t = (l + 9 + 63) / 64 * 64;
        byte[] r = new byte[t];
        System.arraycopy(m, 0, r, 0, l);
        r[l] = (byte) 0x80;
        long bits = l * 8L;
        for (int i = 0; i < 8; i++) r[t - 1 - i] = (byte) (bits >>> (i * 8));
        return r;
    }

    static int toInt(byte[] b, int i) {
        return ((b[i] & 255) << 24) | ((b[i + 1] & 255) << 16) | ((b[i + 2] & 255) << 8) | (b[i + 3] & 255);
    }
 
    static String h(String s) {
        byte[] m = pad(s.getBytes());
        int[] h = H.clone();
        for (int i = 0; i < m.length; i += 64) {
            int[] w = new int[80];
            for (int j = 0; j < 16; j++) w[j] = toInt(m, i + j * 4);
            for (int j = 16; j < 80; j++) w[j] = L(w[j-3] ^ w[j-8] ^ w[j-14] ^ w[j-16], 1);
            int a = h[0], b = h[1], c = h[2], d = h[3], e = h[4];
            for (int t = 0; t < 80; t++) {
                int f = t < 20 ? (b & c) | (~b & d) : t < 40 ? b ^ c ^ d : t < 60 ? (b & c) | (b & d) | (c & d) : b ^ c ^ d;
                int temp = L(a,5) + f + e + K[t/20] + w[t];
                e = d; d = c; c = L(b,30); b = a; a = temp;
            }
            h[0] += a; h[1] += b; h[2] += c; h[3] += d; h[4] += e;
        }
        StringBuilder sb = new StringBuilder();
        for (int v : h) for (int i = 28; i >= 0; i -= 4) sb.append("0123456789abcdef".charAt((v >> i) & 15));
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println(h(sc.nextLine()));
    }
}
