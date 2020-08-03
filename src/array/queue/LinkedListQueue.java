package array.queue;

/**
 * 用链表实现队列
 * @param <E>
 */
public class LinkedListQueue<E> implements Queue<E>{

    private class Node{
        public E e;
        public Node next;

        public Node(E e,Node next){
            this.e=e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }
        public Node(){
            this(null,null);
        }
        @Override
        public String toString(){
            return e.toString();
        }
    }
    private Node head,tail;
    private int size;

    public LinkedListQueue(){
        head=null;
        tail=null;
        size=0;
    }
    @Override
    public void enqueue(E e) {
        if (tail==null){   //如果队尾元素为空 ，则整个队列为空
            tail=new Node(e);       //这时就添加一个节点，队尾和队首同时指向这一个节点
            head = tail;
        }else{  //如果队尾不为空，则添加元素的时候 在队尾添加一个节点，原先队尾元素的下一个节点为添加的节点
            tail.next = new Node(e);
            tail= tail.next;    //维护tail 添加节点后 队尾元素就是添加后的节点
        }
    }

    @Override
    public E dequeue() {
        if (isEmpty()){
            throw new IllegalArgumentException("队列为空");
        }
        Node retNode =head;
        head =head.next;
        retNode.next = null;
        if (head == null){
            tail =null;
        }
        size--;
        return retNode.e;
    }

    @Override
    public E getFront() {
        if (isEmpty()){
            throw new IllegalArgumentException("队列为空");
        }
        return head.e;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }



}
