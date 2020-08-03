package linkedList;

public class LinkedList<E> {

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

    //private Node head; //创建一个head 节点 指向链表中第一个节点
    private Node dummyHead;//创建一个虚拟头节点
    private int size;
    public LinkedList(){
        dummyHead = new Node(null,null);
        size=0;
    }
    //获取链表中的元素个数
    public int getSize(){
        return size;
    }
    //返回链表是否为空
    public boolean isEmpty(){
        return size==0;
    }

    /*/
    /在链表的头部添加一个元素
    public void addFirst(E e){
        Node node = new Node(e); 添加一个节点 节点的值为e
        node.next = head;         设置这个节点的指向为第一个元素 head 这个时候 e这个节点就成了第一个元素 他下一个节点指向原来的head
        head = node;             维护head  这个时候head指向的是node 新添加的这个节点

        //与上面的逻辑一样  创建一个节点 节点的值为e，他的下一个节点为head 这时这个节点为新创建的节点了
        //然后在维护head  head指向的位置就为新添加的节点
        head = new Node(e,head);
        size ++;
    }*/

    //在链表的index（0-based）位置添加新的元素e
    //在链表中不是一个常用的操作，练习用
    public void add(int index, E e){
        if (index<0 || index>size){
            throw new IllegalArgumentException("索引不合法");
        }
      /*  if (index ==0){
            addFirst(e);
        }else{
            //创建一个节点用来找到要添加的位置的上一个节点
            Node prev = head;       //遍历linkedlist节点只能从第一个节点开始找 所以必须为head
            //index是要添加节点的位置，所以他上一个节点的位置就为index-1
            for (int i=0;i<index-1;i++){
                prev = prev.next; //每次遍历prev的值都为下一个节点的值，直到遍历到，index-1这个节点 这个时候prev的值为index-1的值
            }
            //遍历完成后 prev的值为index-1的值 找打了index的上一个节点，然后可以添加了
         //   Node node = new Node(e);
          //  node.next = prev.next;  //将perv指向的下一个节点的值给node 这个时候node和prev他们下一个节点指向的是同一个节点
           // prev.next = node;       //将perv的下一个节点指向到node 这个时候node就插入进去了
            //prev找到指定节点的上一个节点后，直接创建一个节点，将prev的指向赋予给创建的节点
            //这个时候创建的节点指向的就是插入后指向的下一个节点了
            //然后维护一下prev.next 因为创建的节点以及找到位置了，所以他上一个节点可以直接指向node了 这样就添加进去了
            prev.next = new Node(e,prev.next);
            size++;
        }*/
      //创建一个虚拟头节点，用来找到index的前一个节点
        Node prev = dummyHead;
        //第一次遍历的时候，prev.next指向的是第一个节点  以此类推 找到index的时候perv.next指向的就是index这个节点
        for (int i=0;i<index;i++){
            prev=prev.next;
        }
        prev.next = new Node(e,prev.next);
        size++;
    }
    public void addFirst(E e){
        add(0,e);
    }
    //向链表末尾添加元素
    public void addLast(E e){
        add(size,e);
    }

    //获得链表第index（0-based）个位置的元素
    //在链表中不是一个常见的操作，练习用
    public E get(int index){
        if (index<0 || index>size){
            throw new IllegalArgumentException("索引不合法");
        }
        //遍历链表，创建一个虚拟头节点，每次遍历，虚拟头结点指向的下一个节点就是遍历的节点
        Node cur = dummyHead.next;
        for (int i = 0 ; i < index ; i ++){
            cur = cur.next;
        }
        return cur.e;
    }

    //获取第一个参数
    public E getFirst(){
        return get(0);
    }
    //获取最后一个参数
    public E getLast(){
        return get(size-1);
    }

    //修改链表中第index(0+based)个参数
    //在链表中不常用 练习用
    public  void set(int index,E e){
        if (index<0 || index>size){
            throw new IllegalArgumentException("索引不合法");
        }
        //创建一个虚拟头节点
        Node cur = dummyHead.next;
        for (int i=0;i<index;i++){
            cur = cur.next;
        }
        //找到这个节点后就可以修改值了
        cur.e =e;
    }

    //查看链表中是否有元素e
    public boolean contains(E e){

        Node cur = dummyHead.next;
        while (cur!=null){
            if (cur.e.equals(e)){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    //从链表中删除第index个元素，返回删除的元素
    //在链表中不是一个常用的操作 练习用
    public E remove(int index){
        if (index<0 || index>size){
            throw new IllegalArgumentException("索引不合法");
        }
        Node prev = dummyHead;
        for (int i = 0 ; i<index;i++){
            prev = prev.next;
        }
        //找到要删除的元素后，将循环后的节点指向要删除节点的下一个节点
        //创建一个节点，将要删除的下一个节点给创建的这个节店 这个时候retNode指向的节点就是要删除元素的后一个节点
        Node retNode = prev.next;
        //
        prev.next = retNode.next;
        retNode.next = null;
        size --;
        return retNode.e;
    }

    //删除链表中第一个元素
    public E removeFirst(){
        return remove(0);
    }
    //删除链表中最后一个元素
    public E removeLast(){
        return remove(size-1);
    }

    @Override
    public String toString(){
        StringBuffer res = new StringBuffer();
        for (Node cur = dummyHead.next ; cur!=null ; cur=cur.next){
            res.append(cur+"->");
        }
        res.append("null");
        return res.toString();
    }



}
