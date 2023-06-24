package main;


import java.io.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


class NTT {
    static final int P = 998244353, G = 3, Gi = 332748118;

    static int[] polyMul(int[] a, int[] b) {
        int N = a.length - 1, M = b.length - 1;
        int t = N + M;
        N = Math.max(N, M);
        int limit = 1, L = 0;
        while (limit <= (N << 1)) {
            limit <<= 1;
            ++L;
        }

        int[][] A = new int[2][limit + 1];
        int[] rev = new int[limit + 1];
        System.arraycopy(a, 0, A[0], 0, a.length);
        System.arraycopy(b, 0, A[1], 0, b.length);

        for(int i = 0; i < limit; i++) {
            rev[i] = (rev[i >> 1] >> 1) | ((i & 1) << (L - 1));
        }

        NTT(A[0], limit, 1, rev);
        NTT(A[1], limit, 1, rev);
        for(int i = 0; i < limit; i++) A[0][i] = (int) (((long)A[0][i] * (long)A[1][i]) % P);
        NTT(A[0], limit, -1, rev);

        int[] ans = new int[t + 1];
        long inv = fastpow(limit, P - 2);
        for(int i = 0; i <= t; i++) ans[i] = (int) (((long) A[0][i] * inv) % P);
        return ans;
    }

    private static void NTT(int[] A, int limit, int type, int[] rev) {
        for(int i = 0; i < limit; i++){
            if(i < rev[i]) {
                int temp = A[i];
                A[i] = A[rev[i]];
                A[rev[i]] = temp;
            }
        }
        for(int mid = 1; mid < limit; mid <<= 1) {
            long Wn = fastpow( type == 1 ? G : Gi , (P - 1) / (mid << 1));
            for(int j = 0; j < limit; j += (mid << 1)) {
                long w = 1;
                for(int k = 0; k < mid; k++, w = (w * Wn) % P) {
                    long x = A[j + k], y = w * (long) A[j + k + mid] % P;
                    A[j + k] = (int) ((x + y) % P);
                    A[j + k + mid] = (int) ((x - y + P) % P);
                }
            }
        }
    }

    private static long fastpow(long a, long k) {
        long base = 1;
        while(k != 0) {
            if((k & 1) != 0)
                base = (base * a) % P;
            a = (a * a) % P;
            k >>= 1;
        }
        return base % P;
    }
}
public class Main {

    public static void main(String[] args) throws IOException {
        int n=nextInt();
        int[] ar=new int[n];
        int mx = 0;
        for (int i = 0; i < n; ++i) {
            int v=nextInt();
            mx=Math.max(mx,v);
            ar[i]=v;
        }
        int[] a=new int[mx+1],b=new int[mx+1];
        for (int e : ar) {
            a[e]++;
            b[e]++;
        }
        int[] c=NTT.polyMul(a,b);
        for (int i = 0; i < c.length; i++) {
            if (c[i]>=4) {
                System.out.println("YES");
                return;
            }
        }
        System.out.println("NO");
        out.flush();
    }

    static PrintWriter out = new PrintWriter(System.out, true);
    static InputReader in = new InputReader(System.in);
    static String next() { return in.next(); }
    static int nextInt() { return Integer.parseInt(in.next()); }
    static long nextLong() { return Long.parseLong(in.next()); }
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
    }
}

