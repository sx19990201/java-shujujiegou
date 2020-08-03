package array.queue;

import array.Array;

/**
 * 自己实现的队列(基于自己设计的动态数组实现的队列)
 */
public class ArrayQueue<E> implements Queue<E>{


    private Array<E> array ;

    public ArrayQueue(int capacity){
        array =new Array<E>(capacity);
    }
    public ArrayQueue(){
        array = new Array<>();
    }
    /**
     * 往队列里添加一个元素
     * @paramo
     */
    @Override
    public void enqueue(E e) {
        array.addLast(e);
    }

    @Override
    public E dequeue() {
        return array.removeFirst();
    }

    @Override
    public E getFront() {
        return array.getFirst();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public String toString(){
        StringBuilder rst = new StringBuilder();
        rst.append("Queue:");
        rst.append("front [");
        for (int i = 0 ; i<array.getSize() ; i++){
            rst.append(array.get(i));
            if (i!=array.getSize()-1){
                rst.append(",");
            }
        }
        rst.append("] tail");
        return rst.toString();
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        for (int i = 0; i<10 ;i++){
            queue.enqueue(i);
            System.out.println(queue);
            if (i % 3==2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }


}
