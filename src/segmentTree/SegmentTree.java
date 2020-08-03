package segmentTree;
/**
 * 首先创建一个线段树的类，要支持泛型
 * @param <E>
 */
public class SegmentTree<E> {

    //定义一个数组树，用于将用户传进来的数组，以树结构的形式保存
    private E[] tree;
    //定义一个数组，用来接收用户传进来的数组，在此类调用的时候则调用这个数组
    private E[] data;
    //定义一个融合器，这个是由用户的业务场景需要定义的
    private Merger<E> merger;

    //构造函数，首先将传进来的就是整个区间的数,进行初始化
    public SegmentTree(E[] arr,Merger<E> merger) {
        //用户在构造线段树的时候，同时定义好了对于这个线段树两个区间是如何融合的
        this.merger = merger;

        //接收用户传进来的数组
        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            data[i] = arr[i];
        }
        //由平衡二叉树可以得到，arr有n个元素，则这颗树需要4n个节点，才能保证arr里的元素在树中的正确位置上（树中最后一层节点能为空）
        tree = (E[]) new Object[4 * arr.length];
        /**
         * 在开创了tree的相应的数组空间之后，接着具体创建线段树
         * 需要三个参数
         * 1.第一个，当前要创建的线段树，他的根节点所对应的索引，这里为0
         * 2，接下来2个参数对于这个节点，他所表示的那个线段的左右断点是什么
         * 在这里是 l 和 r
         * 在初始的时候，对于0这个节点也是就线段的根节点来说，它对应的区间就是data
         * 这个数组的从头到尾，也是就0-data.length-1
         */
        buildSegmentTree(0,0,data.length-1);
    }

    //在treeindex的位置创建表示区间[l...r]的线段树的具体逻辑
    private void buildSegmentTree(int treeIndex,int l,int r) {
        //由于它是递归函数，首先考虑递归到底的情况
        //当l==r  也就是此时这个区间的长度为1,只有一个元素
        if (l==r){
            //这个时候这个节点所存储的信息就是元素本身
            tree[treeIndex] = data[l];
            return ;
        }
        //表示区间，首先计算出对于要表示一个区间的这个节点，这节点一定会有左右孩子
        //左孩子对应的这个数组中的这个索引
        int leftTreeIndex = leftChild(treeIndex);
        //右孩子对应的这个数组中的这个索引
        int rightTreeIndex= rightChild(treeIndex);

        //先创建好这个节点的左右子树，现在知道了左右子树对应数组中所在的索引，
        //还需要知道，对于这个左右子树来说，他需要表示的区间范围，
        //那么选择区间则可以用 左边界+有边界再除以2表示中间的位置，如下
        //int mid = (l+r)/2;，但是，在这里（l+r）都特别大时候，会造成整形溢出的问题
        //所以为了避免这个问题，也是就，左边界+左右边界之间的距离再除以2，得到的位置也是这个中间的位置
        int mid = l+(r-l)/2;
        //有了中间的位置，就可以确定对于当前的treeIndex所在的这个节点，他表示从l到r这个区间，
        // 他的左右子树表示的是，左区间为 l到mid，即左边界到中间点，
        //右区间为mid+1 到r，即中间点到右区间 ，
        //所以要基于这2个区间要再创建线段树，那么这就是一个递归的过程

        //左区间，先创建左子树，这个leftTreeIndex是上一个节点的左孩子对应的索引
        //即创建这个索引的左子树，在leftTreeIndex这个索引下创建的从l到mid的区间所对应的的线段树
        buildSegmentTree(leftTreeIndex,l,mid);
        //右区间，创建这个索引的右子树，在leftTreeIndex这个索引下创建的从mid+1到r的区间所对应的线段树
        buildSegmentTree(rightTreeIndex,mid+1,r);
        //这样就创建好了该索引的左右子树之后，我们就可以非常容易的来看，tree[treeIndex]也是就当前所考虑的这个节点他的值是什么
        //这个值是多少，其实是和业务相关的，如果我们对于这些数据其中某一个区间相应的数据和，那么他就是左右子树相应的值的和
        //如果要求某个区间中最大值 max[treeIndex]和max[treeIndex],最小值反之
        //总之这个过程，就是综合左右两个线段相应的信息得到我当先这个更大的相应的信息，那么怎么综合是由业务逻辑决定的
        //这里会报错，因为我们这个类型E，在他身上不一定定义了加法，所以，不能保证这个加法一定是合法的
        //如果只写成加法，那么这个线段树的作用就被局限住了，我们不希望线段树只能处理加法或者某个区间的最大值或者最小值
        //我们希望，用户可以根据自己的业务场景自由场景自由的组合自己的逻辑，来使用我们的线段树
        //在这种情况下，可以设置一个新的接口，在这里创建了Merger接口，融合器的意思
        //tree[treeIndex] =tree[leftTreeIndex] + tree[rightTreeIndex];
        //用户通过自己的业务场景定义好了融合器后，就可以调用这个融合器，将这2个区间融合得到自己想要的数据
        tree[treeIndex] = merger.merge(tree[leftTreeIndex] , tree[rightTreeIndex]);
    }

    public int getSize() {
        return data.length;
    }

    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("不合法");
        }
        return data[index];
    }

    //辅助函数，由完全二叉树数组表示中，一个索引所表示的元素的左孩子节点的索引
    private int leftChild(int index) {
        return 2 * index + 1;
    }
    //辅助函数，由完全二叉树数组表示中，一个索引所表示的元素的右孩子节点的索引
    private int rightChild(int index) {
        return 2 * index + 2;
    }

    //查询区间，用户需要传入2个参数，也就是用户期望查询这个区间的左右两个边界
    public E query(int queryL,int queryR){
        //边界检查，判断参数是否合法，是否是有效区间
        if (queryL<0|| queryL>=data.length||queryR<0||
        queryR>=data.length){
            throw new IllegalArgumentException("不合法");
        }
        //递归函数
        return query(0,0,data.length-1,queryL,queryR);
    }

    /**
     * 递归函数，
     * 在以treeId为根的线段树的范围里中[l...r]，搜索区间[queryL...quertR]的值
     * 从线段树的某一个根节点开始 treeindex，相应的这个节点表示的是一个区间，表示为l和r
     * 还需要查询一个区间 queryL和queryR
     * 前三个参数表示的是，这个节点的相关信息，
     * 后两个参数表示在这个节点中需要查找的区间
     * @param treeIndex 从线段的某一个根节点开始
     * @param l 该根节点的左区间
     * @param r 该跟节点的右区间
     * @param queryL    查询的左区间
     * @param queryR    查询的右区间
     * @return
     */
    private E query(int treeIndex,int l,int r,int queryL,int queryR){
        //递归到底的情况，当节点的左边界和用户想要查询的queryL重合的时候
        //或者节点的右边界和用户想要查询的queryR重合的时候，那么这个节点的信息，
        //就是用户想要的信息
        if (l==queryL && r == queryR){
            //找到用户想要的信息后，直接返回这个节点
            return tree[treeIndex];
        }
        //当这个节点，不是用户关心的区间
        //先计算节点的中间值，然后去该节点的左右孩子去查找
        int mid= l+(r-l)/2;
        //找到左孩子所对应的索引
        int leftTreeIndex = leftChild(treeIndex);
        //找到右孩子所对应的索引
        int rightTreeIndex = rightChild(treeIndex);

        //如果用户想要查找的queryL比mid+1大，
        //也就是用户查询的区间中的左边界，比改节点的中间值+1还要大
        //则说明，用户查询区间中的左边界要比该节点的左子树要大
        //这个时候就可以直接忽略它的左子树，直接去查右子树
        if (queryL>=mid+1){
            //查找rightTreeIndex该节点，从它的右子树开始查，而右子树对应的区间是mid+1一直到r
            //在这个区间查找queryL到queryR所对应的区间
            return query(rightTreeIndex,mid+1,r,queryL,queryR);
        }
        //同样，用户查询区间的有边界比该节点中间值要小，那么可以直接忽略该节点的右子树
        //直接去左子树查询，用户需要的区间
        else if (queryR<=mid){
            return query(leftTreeIndex,l,mid,queryL,queryR);
        }
        //如果这2种情况都不是的话，意味着，用户关注的区间，
        // 既没有落在当前treeIndex这个节点的左孩子所代表的节点中，
        //也没有落在右孩子所代表的节点中，而是有一部分落在左孩子那边，有一部分落在了右孩子那边
        //所以先查找左孩子和右孩子中用户所需要的区间

        //首先在左孩子的区间也就是l到mid，
        //找用户需要的区间也就是queryL到mid，这个mid是该节点的中间点
        E leftResult = query(leftTreeIndex,l,mid,queryL,mid);
        //再在右孩子的区间mid+1到r，查询mid+1到queryR
        E rightResult = query(rightTreeIndex,mid+1,r,mid+1,queryR);
        //找完以后，开始融合，将融合后的结果返回回去
        return merger.merge(leftResult,rightResult);
    }

    //线段树更新操作，将index位置的值，更新为e
    public void set(int index,E e){
        //检查合法性
        if (index <0 || index >= data.length){
            throw new IllegalArgumentException("不合法");
        }
        //将树中index所在的索引值换成e
        data[index] = e;
        //具体更新递归方法
        //从根节点开始，从左边界到右边界的区间内，更新index位置的元素改为e
        set(0,0,data.length-1,index,e);
    }

    /**
     * 更新递归函数
     * @param treeIndex 根节点
     * @param l 根节点的左边界
     * @param r 根节点的右边界
     * @param index 需要更新的索引
     * @param e 需要更新的值
     */
    private void set(int treeIndex,int l,int r,int index,E e){
        //由于是递归函数，先考虑递归到底的情况
        //如果l==r，也就是找到的最底层的叶子节点，则说明找到了那个节点
        if (l==r){
            //更新操作
            tree[treeIndex] = e;
            return;
        }
        //划分中间值，将一个区间从中间开始分割成2个区间
        int mid = l + (r-l)/2;
        //再找到该节点的左右孩子所在的节点
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        //如果要修改的索引>=mid+1，也就是这个索引>=该节点的右子树的左边界，那么就可以直接去右子树下继续查找，而不用关左子树了
        if (index>= mid+1){
            //在右子树下继续递归
            set(rightTreeIndex,mid+1,r,index,e);
        }
        //反之，如果这个索引<= mid，则说明这个索引，小于该节点的中间值，说明该索引在节点的左子树上，所以在左子树上继续递归
        else{ //index<=mid
            //在左子树下继续递归
            set(leftTreeIndex,l,mid,index,e);
        }

        //对于线段树来说，更新完值之后，由于index节点发生了改变，它的上层节点也也会受到影响
        //所以需要，重新融合一下，将修改后的线段树，重新拼接起来
        //这样修改前的线段树，就变成了修改之后的线段树了
        tree[treeIndex] = merger.merge(tree[leftTreeIndex],tree[rightTreeIndex]);

    }


/*    @Override
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append('[');
        for (int i = 0 ; i <tree.length;i++){
            if (tree[i] !=null){
                res.append(tree[i]);
            }else{
                res.append("null");
            }
            if (i!=tree.length -)
        }
    }*/

}
