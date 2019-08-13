package org.prime;

/**
 * @author Double
 * @Function 确认素数
 */
public class Prime {
    public static boolean isPrime(int n) {
        if (n < 1) {
            return false;
        }
        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int n = 13;
        boolean isPrime = Prime.isPrime(n);
        System.out.println(n + " 是否质数："+isPrime);
    }
}
