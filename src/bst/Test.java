package bst;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        Random random = new Random();
        int n = 1000;
        for (int i = 0; i<n;i++){
            bst.add(random.nextInt(10000));
        }
        System.out.println(bst.maxmum());
        System.out.println(bst.minimum());

    }
}
