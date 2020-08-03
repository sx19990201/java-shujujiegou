package array;

public class Main {
    public static void main(String[] args) {
        Array<Integer> arr = new Array(20);

        for (int i = 0 ; i<10;i++){
            arr.addLast(i);
        }
        System.out.println(arr.toString());

        arr.add(1,100);
        System.out.println(arr);

        arr.addFirst(-1);
        System.out.println(arr);

        arr.remove(2);
        System.out.println(arr);

        arr.removeFindValue(4);
        System.out.println(arr);

        arr.removeFirst();
        System.out.println(arr);
    }
}
