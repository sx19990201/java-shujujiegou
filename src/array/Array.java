package array;

import javax.xml.crypto.Data;

public class Array<E> {

    /*
     *数组是一个承载元素的工具
     * 数组就是把数据码成一排进行存放
     *索引可以有语意 也可以没有语意
     *
     * 数组最大的有点，可以快速查询
     * 数组最好应用于有语意的情况下
     * */

    private E[] data; //声明数组
    private int size;   //声明数组中最后的一个元素的下一个的下标

    //构造函数，用户自己定义数组的容积
    public Array(int capacity) {
        //
        data =(E[])(new Object[capacity]);
        size = 0;   //这个时候数组中没有元素 所以size的下标指向为0
    }
    //默认为10 如果用户调用无参构造函数  默认给他一个容积为10的数组
    public Array() {
        this(10);
    }
    public Array(E[] arr){
        data = (E[])new Object[arr.length];
        for (int i = 0 ; i < arr.length ; i++){
            data[i] = arr[i];
            size = arr.length;
        }
    }
    //数组中元素的个数  就是获得最后一个元素的位置如果有5个元素这个时候size=4+1 length=4 size=4+1 0 1 2 3 4
    public int getSize() {
        return size;
    }
    //获取数组的容量  数组的容量就是数组的长度  长度为10 容量就为10 但是实际储存的元素个数为11 下标是从0开始的
    public int getCapacity() {
        return data.length;
    }
    //数组是否为空    size是指向最后一个元素一下个的下标 如果没有元素这个时候size==0 所以为空
    public boolean isEmpty() {
        return size == 0;
    }

    //向数组中添加元素
    public void add(int index, E value) {
        //判断传进来的下标是否合法
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("下标不合法");
        }
        //先判断数组中是否还有位置
        if (size == data.length) {   //当数组中元素的个数等于数组的长度时
            resize(2*data.length);
        }
        //如果都没有问题就在数组中插入这个值
        //从数组中最后一个元素开始循环  一直循环到传进来的这个下标然后停止 最后一个元素为size-1
        //比如数组中由5个元素这个时候size指向第六个 这个时候数组中最后一个元素就为 size -1 第六个元素 - 1
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = value;    //当循环结束以后 index以后的元素都向后挪动了一位，这个时候位于index的位置的元素就空出来了。再将value插入进去
        size++;     //插入进去以后 这个时候数组中元素就增加了一个 所以size要++
    }

    //为数组的末尾添加一个元素
    public void addLast(E index) {
        add(size, index);
    }

    //为数组的最前端添加一个元素
    public void addFirst(E index) {
        add(0, index);
    }

    //按照下标获取元素
    public E get(int index) {
        //先判断传进来的下标是否合法
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("下标不合法");
        }
        return data[index];
    }

    public E getLast(){
        return get(size-1);
    }
    public E getFirst(){
        return get(0);
    }

    //修改某个元素
    public E set(int index, E value) {
        //先判断传进来的下标是否合法
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("下标不合法");
        }

        return data[index] = value;
    }

    //查询数组中的某个元素
    public int find(E value){
        for (int i=0;i<size;i++){
            if (data[i].equals(value)){
                return i;
            }
        }
        return -1;
    }
    //按下标删除某个元素 返回删除的元素
    public E remove(int index){
        if (index<0 ||index>size){
            throw new IllegalArgumentException("下标不合法");
        }

        E rst = data[index];  //记录要删除的元素
        for (int i = index+1 ; i<size;i++){
            data[i-1]=data[i];
        }
        size--; //删除过后size--
        /**
         * 由于没有使用泛型的时候存储的int 删除后其实size还是指向这个空间
         *  但是由于他永远也不会被访问到而且会被再次添加的元素给覆盖掉所以也不会产生什么
         * 由于使用泛型的时候存储的是对象的引用，删除了空间还是在内存中存在着的
         * 再次添加后也不会被覆盖，而是在内存中再次引用一个空间在指向他，
         * 对于引用会有一个自动垃圾回收机制，由于被删除后这个size还是会指向这个引用
         * 所以java的自动回收机制不会去回收他  这样就不会造成闲散数据的产生了
         */
        data[size]=null;    //闲散数据  ！=内存泄漏

        if (size == data.length/4 && data.length/2!=0){
            resize(data.length/2);
        }

        return rst;
    }
    //删除最后一个元素
    public E removeLast(){
        return remove(size-1);
    }
    //删除第一个元素
    public E removeFirst(){
        return remove(0);
    }
    //查看数组中是否包含某个元素
    public boolean findValue(E value){
        for (int i = 0 ; i <size;i++){
            if (data[i].equals(value)){
                return true;
            }
        }
        return false;
    }

    //查看数组中是否包含某个元素 如果有就删除 没有就返回-1
    public void removeFindValue(E value){
        int v = find(value);    //调用查询方法
        if (v!=-1){ //如果查到这个值
            remove(v);
        }
    }

    private void resize(int newCapacity){
        E[] newData = (E[])(new Object[newCapacity]);
        for (int i = 0 ;i<size;i++){
            newData[i] = data[i];
        }
        data = newData;
    }

    public void swap(int i ,int j){
        if (i<0 || i>=size || j<0 || j>=size){
            throw new IllegalArgumentException("索引不合法");
        }
        E t = data[i];
        data[i]=data[j];
        data[j]=t;

    }

    @Override
    public String toString(){
        StringBuilder rst = new StringBuilder();
        rst.append("Array: size = "+size+", capacity ="+data.length+"\n");
        rst.append('[');
        for (int i = 0 ; i<size ; i++){
            rst.append(data[i]);
            if (i!=size-1){
                rst.append(",");
            }
        }
        rst.append(']');
        return rst.toString();
    }

}
