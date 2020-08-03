package array.stack;

import linkedList.LinkedList;

public class LinkedListStack<E> implements Stack<E> {

    private LinkedList<E> list;
    public LinkedListStack(){
        list = new LinkedList<E>();
    }


    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(E e) {
        list.addFirst(e);
    }

    @Override
    public E pop() {
        return list.removeFirst();
    }

    @Override
    public E peek() {
        return list.getFirst();
    }
    @Override
    public String toString(){
        StringBuffer res = new StringBuffer();
        res.append("stack: top ");
        res.append(list);
        return res.toString();
    }

}
