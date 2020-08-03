package array.stack;

import array.Array;

/**
 * 栈
 * @param <E>
 */
public class ArrayStack<E> implements  Stack<E> {

    Array<E> array;

    public ArrayStack(int capacity){
        array = new Array<>(capacity);
    }
    public ArrayStack(){
        array = new Array<>();
    }



    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public void push(E e) {
        array.addLast(e);
    }

    @Override
    public E pop() {
        return array.removeLast();
    }

    @Override
    public E peek() {
        return array.getLast();
    }
}
