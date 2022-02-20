package org.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * ClassName: MergeSort <br>
 * Description: 归并排序<br>
 * date: 2022/2/20 12:45<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class MergeSort {
  public static int[] mergeSort(int[] nums, int left, int right) {
    // 如果 left == right，表示数组只有一个元素，则不用递归排序
    if (left < right) {
      int mid = left + (right - left) / 2;
      // 递归对左半部分排序
      mergeSort(nums, left, mid);
      // 递归对右半部分排序
      mergeSort(nums, mid + 1, right);
      // 合并上面部分有序的子数组
      merge(nums, left, mid, right);
    }
    return nums;
  }

  /**
   * 归并函数，把两个有序的数组归并起来
   *
   * <p>arr[left..mif]表示一个数组，arr[mid+1 .. right]表示一个数组
   *
   * @param nums
   * @param left
   * @param mid
   * @param right
   */
  private static void merge(int[] nums, int left, int mid, int right) {
    int[] temp = new int[right - left + 1];
    int l = left, r = mid + 1, i = 0;
    while (l <= mid && r <= right) {
      if (nums[l] < nums[r]) {
        temp[i++] = nums[l++];
      } else {
        temp[i++] = nums[r++];
      }
    }

    while (l <= mid) {
      temp[i++] = nums[l++];
    }
    while (r <= right) {
      temp[i++] = nums[r++];
    }

    //      if (l <= mid) {
    //        System.arraycopy(nums, l, temp, i, mid - l + 1);
    //        i = i + mid - l + 1;
    //      }
    //      if (r <= right) {
    //        System.arraycopy(nums, r, temp, i, right - r + 1);
    //        i = i + mid - l + 1;
    //      }

    System.arraycopy(temp, 0, nums, left, i);
  }

  public static void main(String[] args) {
    int[] nums = new int[] {1, 4, 3, 2, 5, 7, 6, 8};
    int[] ret = mergeSort(nums, 0, nums.length - 1);
    System.out.println(Arrays.stream(ret).boxed().collect(Collectors.toList()));
  }
}
