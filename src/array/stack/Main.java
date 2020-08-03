package array.stack;

public class Main {

    public static void main(String[] args) {
        LinkedListStack<Integer> list = new LinkedListStack<>();
        for (int i = 0; i<5;i++){
            list.push(i);
            System.out.println(list);
        }
        list.pop();
        System.out.println(list);

    }
}
