package template.math;

/**
 * @Author Create by jiaxiaozheng
 * @Date 2023/4/14
 */
class Comb {

    long[] p;
    long[] pi;
    long mod;

    public Comb(int n, long mod) {
        this.mod=mod;
        p=new long[n+1];
        pi=new long[n+1];
        p[0] = 1;
        pi[0] = 1;
        for (int i = 1; i <= n; i++) {
            p[i] = p[i - 1] * i % mod;
        }
        pi[n] = modinv(p[n], (int) mod);
        for (int i = n; i > 1; i--) {
            pi[i-1] = pi[i] * (long) i % mod;
        }
    }

    public long comb(int n, int r) {
        if (n < r) return 0;
        return p[n] * pi[r] % mod * pi[n - r] % mod;
    }

    public long perm(int n, int r) {
        if (n < r) return 0;
        return p[n] * pi[n - r] % mod;
    }

    static long modinv(long a, long m) {
        long b = m;
        long u = 1;
        long v = 0;
        long tmp = 0;

        while (b > 0) {
            long t = a / b;
            a -= t * b;
            tmp = a;
            a = b;
            b = tmp;

            u -= t * v;
            tmp = u;
            u = v;
            v = tmp;
        }

        u %= m;
        if (u < 0) u += m;
        return u;
    }

    public static void main(String[] args) {
        System.out.println(modinv(2, 998244353));
    }
}
