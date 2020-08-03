package segmentTree;

public class NumArray2 {
    //sum[i]存储前i个元素和，sum[0]=0
    //sum[i]存储nums[0...i-1]的和
    private int[] sum;

    public NumArray2(int[] nums){
        //有一个偏移所以+1，因为sum[0]不是nums-0，而是0个元素，所以sum[1]才是有1个元素，所以要+1
        sum= new int[nums.length+1];
        sum[0]=0;
        for (int i = 1 ; i < sum.length ; i++){
            //sum数组保存nums数组中i个元素的和
            sum[i] = sum[i-1]+nums[i-1];
        }
    }

    public int sumRange(int i,int j){
        //sum[j+1]存储的是nums从[0...j]的元素的和
        //sum[i]存储的是num[0...i-1]
        //相减后就得i到j之间的和了
        return sum[j+1] - sum[i];
    }

}
