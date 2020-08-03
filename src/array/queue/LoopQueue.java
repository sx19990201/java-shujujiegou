package array.queue;


/**
 * 循环队列  每次修改队列的长度时都要维护队列的队首元素和队尾元素 公式为(x+1)%data.length  x可以为front也可以为tail
 */
public class LoopQueue<E> implements Queue<E> {
    private E[] data;   //定义一个数组
    private int front,tail; //定义队首和队尾指向的位置
    private int size;       //定义队列的大小

    public LoopQueue(int capacity){
        data= (E[]) new Object[capacity+1];
        front=0;
        tail=0;
        size=0;
    }
    public LoopQueue(){
        this(10);
    }
    //查看队列的容量
    public int getCapacity(){
        return data.length-1;
    }
    //查看队列里有多少个元素
    @Override
    public int getSize() {
        return size;
    }

    //查看队列是否为空
    @Override
    public boolean isEmpty() {
        return front==tail;
    }

    @Override
    public void enqueue(E e) {
        //队尾指向的下标+1 取余队列的长度 如果等于队首下标 则队列为满，所以就要进行扩容
        if ((tail+1)%data.length == front){
            resize(getCapacity()*2);
        }
        data[tail] = e;
        //tail指向的位置
        tail = (tail+1)%data.length;
        size++;
    }

    /**
     * 出队
     * @return
     */
    @Override
    public E dequeue() {
        if (isEmpty()){
            System.out.println("队列里没有元素");
        }
        E rst = data[front];
        data[front] = null;
        size--;
        if (size == getCapacity()/4 && getCapacity()/2!=0){
            resize(getCapacity()/2);
        }
        return rst;
    }

    @Override
    public E getFront() {
        return null;
    }
    private void resize(int newCapacity){
        E[] newData = (E[]) new Object[newCapacity+1];
        for (int i=0;i<size;i++){
            //将之前的队列循环放入新的队列中  由于是循环队列 所以在放入的时候要进行一些额外的操作
            //旧队列的队首元素可能指向的不是队列里的第一个位置，而放入新的队列中时要将front指向的元素放入新队列的第一个位置
            //新队列里的第一个元素不一定对应旧队列里第一个元素 而是对应front指向的元素 第二个元素则是front+1指向的元素
            // 所以放入的时候是有一个front的偏移量的 所以在添加的时候就要加上front这个偏移量  则为front+i
            //如果i+front的长度大于了队列的长度 这时就会出现下标越界了，所以要进行取余队列的长度
            //（i+front）%data.length  (2+8)%20 = 10   下标就为10  这个公式计算的是front指向的位置
            //如果队列长度为5 front指向的是3  tail 指向的是 1     0 1 2 3 4  里面有4个元素
            //（0+3）%5 = 3   newdata[0]=data[3]
            //（1+3）%5 = 4   newdata[1]=data[4]
            //（2+3）%5 = 0   newdata[2]=data[0]
            //（3+3）%5 = 1   newdata[3]=data[1]
            //循环结束 新数组的空间为  0 1 2 3 4  其中 01234 对应着旧队列从front一直到tail对应的值
            //(i+front)%data.length 这个公式就是 获取当前循环所指向的下标
            newData[i]=data[(i+front)%data.length];
        }
        data = newData;
        front=0;    //新队列的队首元素则指向了0
        tail = size; //新队列的队尾元素指向队列中最后一个元素下一个元素 也就是size size 012 size=3 tail=3111

    }

    @Override
    public String toString(){
        StringBuilder rst = new StringBuilder();
        rst.append("Queue: size  =%d ,capacity= %d\n",size,getCapacity());
        rst.append("front [");
        for (int i = front ; i<tail ; i=(i+1)%data.length){
            rst.append(data[i]);
            if ((i+1)%data.length!=size-1){
                rst.append(",");
            }
        }
        rst.append("] tail");
        return rst.toString();
    }


}
