package bst;

import java.util.LinkedList;
import java.util.Queue;

public class BST<E extends Comparable<E>> {

    private class Node {
        private E e;
        private Node left, right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

   /* //向二分搜索树添加元素
    public void add(E e){
        if (root == null){  //如果根节点为空，就直接插入到根节点里去
            root = new Node(e);
            size++;
        }else{
            add(root,e);
        }
    }
    private void add(Node node,E e){
        if (e.equals(node.e)){  //如果要插入的元素等于树中的其中一个元素，则直接返回出去
            return;
            //如果e小于树中的一个元素，而且他的左孩子为空 则吧e放到他的左孩子中
        }else if (e.compareTo(node.e)<0 &&  node.left ==null){
            node.left = new Node(e);
            size++;
            return;
        }else if (e.compareTo(node.e)>0 && node.right == null){
            node.right = new Node(e);
            size++;
            return;
        }
        //开始递归，将元素放入到树中，由递归函数处理
        if (e.compareTo(node.e)<0){
            add(node.left,e);
        }else{
            add(node.right,e);
        }
    }
*/


    /**
     * 向二分搜索树添加元素
     * 大致思路，添加元素需要传入一个元素，然后从root开始遍历整个树，遍历的时候让这个元素e与每次遍历的节点对比，如果小于节点的值
     * 而且他的左孩子为空就创建一个节点吧这个节点挂在他的左孩子上，反之大于同样与此，挂到树上以后就可以返回这个节点
     *
     * @param e
     */
    public void add(E e) {
        root = add(root, e);
    }

    private Node add(Node node, E e) {

        //如果找到一个节点为空，那么就创建一个节点添加元素e 并返回这个节点  这个时候就需要把这个节点挂到树上面去
        if (node == null) {
            size++;
            return new Node(e);
        }
        //开始递归，将元素放入到树中，由递归函数处理
        if (e.compareTo(node.e) < 0) {
            node.left = add(node.left, e);     //如果上面那个节点的元素小于传进来的那个节点的值则吧它挂到左孩子上去
        } else {
            node.right = add(node.right, e);   //如果上面那个节点的元素大于传进来的那个节点的值则吧它挂到右孩子上去
        }
        return node;
    }

    //查看二分搜索树中是否包含元素e
    public boolean contains(E e) {
        return contains(root,e);
    }

    //看以node为根的二分搜索树是否包含元素e，递归算法
    private boolean contains(Node node, E e) {
        if (node == null) {
            return false;
        }
        //如果这个元素当前根，则从这个跟的左孩子开始查找
        if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else  //如果当前根的元素等于传进来的元素e，说明找到了相同的，返回true；
            if (e.compareTo(node.e) == 0) {
                return true;
            } else {  //剩下的情况则是 当前根的元素大于传进来的元素e，就要从这个根的右孩子开始查找
                return contains(node.right, e);
            }
    }

    //二分搜索树的前序遍历
    public void perOrder(){
        perOrder(root);
    }
    //前序遍历以node为根的二分搜索树，递归算法
    private void perOrder(Node node){
        //如果node为空，则直接返回
        if (node == null){
            return;
        }
        System.out.println(node.e);
        //遍历node的左孩子
        perOrder(node.left);
        //遍历node的右孩子
        perOrder(node.right);
    }
    //二分搜索树的中序遍历
    public void inOrder(){
        inOrder(root);
    }
    private void inOrder(Node node){
        if (node == null){  //结束条件  如果为空则直接返回
            return ;
        }
        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }
    //二分搜索树的后序遍历
    public void postOrder(){
        postOrder(root);
    }
    public void postOrder(Node node){
        if (node == null){
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    //二分搜索树的层序遍历（广度优先遍历）
    public void levelOrder(){
        //创建一个队列 用于树节点的入队出队操作
        Queue<Node> q = new LinkedList<>();
        q.add(root);  //将树的跟节点加到队列里
        while (!q.isEmpty()){   //如果这个队列里还有元素 则进入循环
            Node cur = q.remove();      //将队首元素出队，并打印输出
            System.out.println(cur.e);

            if (cur.left != null){  //如果出队的那个节点有左孩子，就将他的左孩子加入到这个队列里
                q.add(cur.left);
            }
            if (cur.right!=null){ //如果出队的那个节点有右孩子，就将他的右孩子加入到这个队列里
                q.add(cur.right);
            }
        }
    }

    //寻找二分搜索树中的最小元素
    public E minimum(){
        if (size == 0){
            throw new IllegalArgumentException("BST is null");
        }
        return minimum(root).e;
    }
    //返回以node为根的二分搜索树的最小值所在的节点
    public Node minimum(Node node){
        if (node.left == null){ //如果该节点的左孩子为空，则该节点就是最小值所在的节点
            return node;
        }
        return minimum(node.left);
    }
    //从二分搜索树中删除最小值的节点，返回最小值
    public E removeMin(){
        E res = minimum();  //获取该树中的最小值
        removeMin(root);
        return res;
    }
    //删除掉以node为跟的二分搜索树的最小节点
    //返回删除节点后新的二分搜索树的根
    public Node removeMin(Node node){
        if (node.left==null){   //找到最小节点
            Node rightNode = node.right;    //创建一个节点，将最小节点的右孩子给他
            node.right=null;                //这个时候可以删除掉最小节点的右孩子了
            size--;                         //删除后维护一下树的元素个数
            return rightNode;               //删除后返回的就是最小节点的右孩子
        }
        node.left = removeMin(node.left);   //将之前最小节点的根重新指向他的左孩子，该左孩子就是删除后返回的右孩子
        return node;
    }
    //寻找二分搜索树的最大值
    public E maxmum(){
        if (size == 0){
            throw new IllegalArgumentException("BTS is bull");
        }
        return maxmum(root).e;
    }
    //返回以node为根的最大值所在的节点
    public Node maxmum(Node node){
        if (node.right==null){  //如果最小节点的右孩子为空，则该节点就是最大节点
            return node;
        }
        return maxmum(node.right);
    }
    //删除二分搜索树的最大值
    public E removeMax(){
        E rst = maxmum();   //获取最大值所在的节点
        size--;
        return removeMax(root).e;
    }
    public Node removeMax(Node node){
        if (node.right==null){
            Node leftNode = node.left;
            node.right = null;
            size--;
            return leftNode;
        }
        node.left = removeMax(node.right);
        return node;
    }

    /**
     * 大致逻辑
     *  先开始查找这个树中有没有这个节点，通过遍历这个左子树和右子树来对比
     *  如果没有找到则说明这个书中没有这个节点，返回空，如果找到了则执行一系列删除操作
     *  如果找到他了，但它的左子树为空 或者右子树为空，则把这个节点删除了，然后再把它的子树挂在删除节点的位置上
     * 但如果他的左子树和右子树都不为空，则找到比这个待删除节点小的最小节点，让他来代替这个待删除节点的位置
     * @param e
     */
    //从二分搜索树中删除元素为e的节点
    public void remove(E e){
        root = remove(root,e);
    }
    //删除以node为根的二分搜索树中值为e的节点，递归算法
    //返回删除节点后新的二分搜索树的根
    private Node remove(Node node,E e){
        if (node == null){  //如果node为空 ，则说明没有找到值为e的节点
            return null;
        }
        //如果带删除值小于该节点的值，则继续向左子树递归，直到找到该节点，并返回该删除的节点
        if (e.compareTo(node.e)<0){
            node.left = remove(node.left,e);
            return node;
        //如果带删除值大于该节点的值，则继续向右子树递归，直到找到最大值，然后删除，并返回该删除的节点
        }else if (e.compareTo(node.e)>0){
            node.right = remove(node.right,e);
            return node;
        }else{  //e == node.e
            //待删除节点为左子树为空的情况 用一个新节点代替待删除的节点，将新节点的右子树指向待删除节点的右子树
            //这样就实现了把待删除的节点删除后，他的右子树代替了他的位置挂上了这个树，然后把原来的右孩子删除掉
            if (node.left== null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            //待删除节点为右子树为空的情况
            if (node.right== null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
        }
        //待删除节点左右子树均不为空的情况
        //找到比待删除节点大的最小节点，即待删除节点右子树的最小节点
        //用这个节点顶替待删除节点的位置
        Node successor = minimum(node.right);   //找到这个节点
        //将待删除节点的右孩子给新创建的节点，因为新节点是要顶替待删除的接待的，所以直接用删除方法
        //把这个节点删除掉，把它原来的右孩子返回给新节点，让右孩子挂接上树
        successor.right = removeMin(node.right);
        //将之前待删除节点的左孩子指向新节点的左孩子
        successor.left = node.left;

        //这样新节点就成功顶替了删除的节点，再把之前删除节点的左右孩子都赋值为null 这样就成功实现了删除
        node.left= node.right = null;
        //删除完毕后返回现在的新节点 这个新节点所在的位置就是原来旧节点的位置
        return successor;
    }

    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        generateBSTtring(root,0,res);
        return res.toString();
    }  //生成以node我根节点，深度为depth的描述二叉树的字符串

    private void generateBSTtring(Node node,int depth , StringBuilder res){
        if (node ==null){   //如果遍历的时候遇到空节点就输出为空
            res.append(generateBSTtring(depth)+"null\n");
            return;
        }
        //如果不为空，没次遍历一个节点就输出一次该节点的值
        res.append(generateBSTtring(depth)+node.e+"\n");
        //然后继续遍历他的左孩子
        generateBSTtring(node.left,depth+1,res);
        //遍历他的右孩子
        generateBSTtring(node.right,depth+1,res);
    }
    private String generateBSTtring(int depth){
        StringBuilder res = new StringBuilder();
        for (int i = 0 ; i < depth;i++){
            res.append("--");
        }
        return res.toString();
    }
}
