package array.queue;


public interface Queue<E> {
    void enqueue(E e);  //添加一个元素
    E dequeue();        //取出一个元素
    E getFront();       //看队首的元素
    int getSize();      //队列的大小
    boolean isEmpty();  //队列里是否为空


}
