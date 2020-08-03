package linkedList;

public class test {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int  i= 0;i<5;i++){
            linkedList.addLast(i);
            System.out.println(linkedList.toString());
        }
        linkedList.add(2,20);
        System.out.println(linkedList);

        System.out.println( linkedList.contains(1));
        System.out.println(linkedList.toString());

    }
}
