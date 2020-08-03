package segmentTree;

public class Main {
    public static void main(String[] args) {
        Integer[] nums = {-2,0,1,5,8,6,7,3};
        SegmentTree<Integer> segTree = new SegmentTree<>(nums, new Merger<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {

                //通过匿名类，用户自己定义需要的业务场景所需要的逻辑
                return a+b;
            }
        });
        SegmentTree<Integer> segeTree = new SegmentTree<>(nums,(a,b)->a+b);

    }
}
