package org.string;

/**
 * 去重
 */
public class Removal {

    public static int[] remove(int[] a) {
        int index = 0;
        int[] ret = new int[a.length];
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i + 1]) {
                ret[index++] = a[i++];
            } else {
                ret[index++] = a[i];
            }
        }
        if (a[a.length - 1] != a[a.length - 2]) {
            ret[index] = a[a.length - 1];
        }
        return ret;
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 2, 3, 3, 4};
        int[] remove = Removal.remove(array);
        for (int i = 0; i < remove.length; i++) {
            System.out.println(remove[i]);
        }
    }

}
