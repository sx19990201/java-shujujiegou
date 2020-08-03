package map;

public class LinkedListMap<K,V> implements Map<K,V> {

    private class Node{
        public K key;
        public V value;
        public Node next;
        public Node(K key,V value,Node next){
            this.key=key;
            this.value=value;
            this.next=next;
        }
        public Node(K key){
            this(key,null,null);
        }
        public Node(){
            this(null,null,null);
        }
    }
    private Node dummyHead;
    private int size;

    public LinkedListMap() {
        dummyHead = new Node();
        size=0;
    }

    /**
     *遍历整个链表
     * @param key
     * @return
     */
    private Node getNode(K key){
        //从第一个节点开始找
        Node cur = dummyHead.next;
        while (cur!=null){
            //如果找到了就返回找到的节点
            if (cur.key.equals(key)){
                return cur;
            }
            cur=cur.next;
        }
        //没有则返回空
        return null;
    }
    @Override
    public void add(K key, V value) {
        //首先，不能有重复键
        Node node  =getNode(key);
        if (node==null){
            dummyHead.next=new Node(key,value,dummyHead);
            size++;
        }else{
            node.value=value;
        }
    }

    @Override
    public V remove(K key) {
        //先找到虚拟头节点,定义待删除节点的前一个节点
        Node prev = dummyHead;
        //从虚拟头节点开始遍历整个链表
        while (prev.next !=null){
            //找到需要删除的节点，如果找到就结束
            if (prev.next.key.equals(key)){
                break;
            }
            //找到以后，新节点的下一个节点指向链表内需要删除的节点的下一个节点
            prev = prev.next;
        }
        //如果找到了节点
        if (prev.next!=null){
            //定义需要删除的节点，也就是找到的节点，他指向的下一个节点为待删除节点的下一个节点
            Node delNode = prev.next;
            /**
             * 将待删除节点的下一个节点指向该删除节点的下一个节点
             * 1->2->3->4   如果待删除节点为3 即找到3后，他的前一个节点2指向4 2->4 delnode = 3->
             * 1->2->3->4   3->4 将3置空，并将他上一个节点指向下一个节点
             * 1->2->4
             */
            prev.next = delNode.next;
            delNode.next=null;
            size--;
            return delNode.value;
        }
        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key)!=null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null:node.value;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(key);
        if (node==null){
            throw new IllegalArgumentException(key+"doesn't exist");
        }
        node.value=value;
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
