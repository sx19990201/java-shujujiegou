package array.stack;

public interface Stack<E> {

    int getSize();      //查看栈中元素多少个
    boolean isEmpty();  //是否为空
    void push(E e);     //添加元素
    E pop();            //取出栈顶元素
    E peek();           //查看栈顶元素



}
