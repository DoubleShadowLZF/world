package org.adt.heap;

/**
 * ClassName: Heap <br>
 * Description: 堆<br>
 * date: 2022/1/2 11:13<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public interface Heap {
  /**
   * 返回堆的大小
   *
   * @return
   */
  int size();

  /**
   * 是否为空
   *
   * @return
   */
  boolean isEmpty();

  /**
   * 插入元素
   *
   * @param ele
   */
  void push(int ele);

  /**
   * 删除第一个元素
   *
   * @return
   */
  int pop();

  /**
   * 返回第一个元素
   *
   * @return
   */
  int peek();
}
