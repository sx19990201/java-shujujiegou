package maxHeap;

import array.Array;

/**
 * 最大堆 需要有可比较性 继承 extends Comparable<E>
 *
 * @param <E>
 */
public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap() {
        data = new Array<>();
    }

    //返回堆中的元素个数
    public int size() {
        return data.getSize();
    }

    //返回一个布尔值，表示堆中是否为空
    public boolean isEmpty() {
        return data.isEmpty();
    }

    //返回完全二叉树的数组表示中，一个索引所表示的元素的父节点的索引
    private int parent(int index) {
        if (index == 0) {
            throw new IllegalArgumentException("不合法");
        }
        return (index - 1) / 2;
    }

    //返回完全二叉树的数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    //返回完全二叉树的数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index) {
        return index * 2 + 2;
    }

    //向堆中添加元素
    public void add(E e) {
        data.addLast(e);    //添加进数组
        siftUp(data.getSize() - 1);
    }

    private void siftUp(int i) {
        /**
         * 满足两个条件的时候，停止条件
         * 1.传进来的索引不能是根节点所有需要它得>0 即i>0
         * 2.这个节点的父节点要比这个节点的值大
         */
        while (i > 0 && data.get(parent(i)).compareTo(data.get(i)) < 0) {
            //交换位置(父节点与子节点换位置)
            data.swap(i,parent(i));
            //交换之后，下一轮循环，就从新的位置开始循环，即到交换位置后的i的位置继续循环
            i=parent(i);
        }
    }
    //查看堆中最大元素
    public E findMax(){
        if (data.getSize()==0){
            throw new IllegalArgumentException("不合法");
        }
        return data.get(0);
    }

    //取出堆中最大元素
    public E extractMax(){
        E ret = findMax();
        data.swap(0,data.getSize()-1);
        data.removeLast();
        siftDown(0);
        return ret;
    }
    //下沉操作
    private void siftDown(int i) {
        while (leftChild(i)<data.getSize()){
            int j = leftChild(i);
            //j+1为i右节点所在的节点
            if (j+1<data.getSize()
                    &&data.get(j+1).compareTo(data.get(j))>0){
                //j++;
                j=rightChild(i);
                //data[j]是laftChild和rightChild中的最大值
            }
            if (data.get(i).compareTo(data.get(j))>=0){
                break;
            }
            data.swap(i,j);
            i=j;
        }
    }

    //取出堆中的最大元素，并且替换成元素e   replace
    public E replace(E e){
        E ret = findMax();
        data.set(0,e);
        siftDown(0);
        return ret;
    }

    //heapify
    public MaxHeap(E[] arr){
        data = new Array<>(arr);
        for (int i = parent(arr.length-1);i>=0;i--){
            siftDown(i);
        }
    }






}
