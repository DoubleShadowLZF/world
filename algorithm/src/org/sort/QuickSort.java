package org.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * ClassName: QuickSort <br>
 * Description: 快速排序<br>
 * date: 2020/4/8 23:09<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class QuickSort {

  public static void quickSort(int[] nums, int left, int right) {
    if (left > right) {
      return;
    }
    // base 中存放基准数
    int base = nums[left];
    int l = left, r = right;
    while (l != r) {
      // 一定要从右边开始往左找，直到找到比base值小的数
      // 假设从左边开始，那么起始值一定满足 nums[l = left] = base = nums[left],在交换基准值时，就不一定满足nums[l] < base < nums[r]
      while (nums[r] >= base && l < r) {
        r--;
      }
      // 再从左往右找，知道找到比base值大的数
      while (nums[l] <= base && l < r) {
        l++;
      }
      // 上面的循环结束表示找到了位置或者(l>=r)了，交换两个数组中的位置
      if (l < r) {
        int tmp = nums[l];
        nums[l] = nums[r];
        nums[r] = tmp;
      }
    }
    // 将基准数放在中间的位置(基准数归位)
    nums[left] = nums[l];
    nums[l] = base;
    // 递归，继续向基准的左右边执行和上面同样的操作
    // i的索引处为上面的已确定的基准值的位置，无需再处理
    quickSort(nums, left, l - 1);
    quickSort(nums, l + 1, right);
  }

  public static void main(String[] args) {
    int[] arr = new int[] {1, 4, 3, 2, 5, 7, 6, 8};
    quickSort(arr, 0, arr.length - 1);
    System.out.println(Arrays.stream(arr).boxed().collect(Collectors.toList()));
    //    arr = new int[] {1, 4};
    //    quickSort(arr, 0, arr.length - 1);
    //    System.out.println(Arrays.stream(arr).boxed().collect(Collectors.toList()));
    //    arr = new int[] {1};
    //    quickSort(arr, 0, arr.length - 1);
    //    System.out.println(Arrays.stream(arr).boxed().collect(Collectors.toList()));
    //    arr = new int[] {3, 2};
    //    quickSort(arr, 0, arr.length - 1);
    //    System.out.println(Arrays.stream(arr).boxed().collect(Collectors.toList()));
    //    arr = new int[] {1, 3, 4};
    //    quickSort(arr, 0, arr.length - 1);
    //    System.out.println(Arrays.stream(arr).boxed().collect(Collectors.toList()));
    //    arr = new int[] {2, 1, 4};
    //    quickSort(arr, 0, arr.length - 1);
    //    System.out.println(Arrays.stream(arr).boxed().collect(Collectors.toList()));
  }
}
