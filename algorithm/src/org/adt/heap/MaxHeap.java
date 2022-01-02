package org.adt.heap;

import org.adt.ConstructTree;
import org.adt.TreeNode;
import org.adt.TreeOperation;

/**
 * 《数据结构与算法经典问题解析：Java语言描述(原书第2版）》 第7章 优先队列和堆<br>
 * ClassName: MaxHeap <br>
 * Description: 最大堆<br>
 * date: 2022/1/2 11:17<br>
 *
 * @author Double <br>
 * @since JDK 1.8
 */
public class MaxHeap implements Heap {

  private Integer[] data = new Integer[16];

  private int size = 0;

  @Override
  public int size() {
    return this.size;
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public void push(int ele) {
    if (this.size == this.data.length) {
      resize();
    }
    this.size++;
    int i = this.size - 1;
    while (i > 0 && ele > this.data[(i - 1) / 2]) {
      this.data[i] = this.data[(i - 1) / 2];
      i = (i - 1) / 2;
    }
    this.data[i] = ele;
  }

  /** 重新分配大小 */
  private void resize() {
    Integer[] data = new Integer[this.data.length * 2];
    System.arraycopy(this.data, 0, data, 0, this.data.length);
    this.data = data;
  }

  @Override
  public int pop() {
    if (this.size == 0) {
      return -1;
    }
    int ele = this.data[0];
    this.data[0] = this.data[this.size - 1];
    this.size--;
    percolateDown(0);
    return ele;
  }

  /**
   * 堆化当前元素
   *
   * @param idx
   */
  private void percolateDown(int idx) {
    int maxIdx = 0;
    int l = leftChildIdx(idx);
    int r = rightChildIdx(idx);
    if (l != -1 && this.data[l] > this.data[idx]) {
      maxIdx = l;
    } else {
      maxIdx = idx;
    }

    if (r != -1 && this.data[r] > this.data[maxIdx]) {
      maxIdx = r;
    }

    if (maxIdx != idx) {
      int temp = this.data[idx];
      this.data[idx] = this.data[maxIdx];
      this.data[maxIdx] = temp;
      percolateDown(maxIdx);
    }
  }

  /**
   * 当前序号的左孩子结点
   *
   * @param idx
   * @return
   */
  private int leftChildIdx(int idx) {
    int leftChildIdx = idx * 2 + 1;
    if (leftChildIdx >= this.size) {
      return -1;
    }
    return leftChildIdx;
  }

  /**
   * 当前序号的右孩子结点
   *
   * @param idx
   * @return
   */
  private int rightChildIdx(int idx) {
    int rightChildIdx = (idx + 1) * 2;
    if (rightChildIdx >= this.size) {
      return -1;
    }
    return rightChildIdx;
  }

  /**
   * 节点的父亲节点
   *
   * @param idx 当前节点
   * @return
   */
  @Override
  public int parentIdx(int idx) {
    if (idx <= 0 || idx >= this.size) {
      return -1;
    }
    return (idx - 1) / 2;
  }

  /** @return */
  @Override
  public int peek() {
    return this.data[0];
  }

  public static void main(String[] args) {
    MaxHeap maxHeap = new MaxHeap();
    int count = 20;
    System.out.println("添加 0 - " + count);
    for (int i = 0; i < count; i++) {
      maxHeap.push(i);
    }
    Integer[] ret = maxHeap.data;
    TreeNode root = ConstructTree.constructTree(ret);
    TreeOperation.show(root);

    int pop = maxHeap.pop();
    System.out.printf("删除元素：%s\r\n", pop);
    root = ConstructTree.constructTree(ret);
    TreeOperation.show(root);
  }
}
