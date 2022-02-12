package org.select;

/**
 * ClassName: FindMissingNumber <br>
 * Description: 寻找丢失的数<br>
 * date: 2022/2/12 10:59<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class FindMissingNumber {

  /**
   * 根据出现的次数的奇偶性，可以使用按位异或运算得到丢失的数字。按位异或运算 \oplus⊕ 满足交换律和结合律，且对任意整数 xx 都满足
   *
   * <p>x^x = 0
   *
   * <p>x^0 = x。
   *
   * @param A
   * @return
   */
  private static int findMissingNumber(int A[]) {
    int xor = 0;

    for (int j = 0; j < A.length; j++) {
      xor ^= A[j];
      System.out.println(String.format("%s:%s", j, A[j]));
      System.out.println(String.format("xor(%s):%s", xor, Integer.toBinaryString(xor)));
      xor ^= j;
      System.out.println(String.format("j(%s):%s", j, Integer.toBinaryString(j)));
      System.out.println(String.format("xor(%s):%s", xor, Integer.toBinaryString(xor)));
      System.out.println();
    }
    xor ^= A.length;
    System.out.println(String.format("xor(%s):%s", xor, Integer.toBinaryString(xor)));
    return xor;
  }

  public static void main(String[] args) {
    //    int A[] = new int[] {0, 1, 2, 4, 6, 3};
    int A[] = new int[] {0, 1, 2, 3, 4, 5, 7};
    //    6 : 0110
    //    5 : 0101
    int missingNumber = findMissingNumber(A);
    System.out.println(missingNumber);
  }
}
