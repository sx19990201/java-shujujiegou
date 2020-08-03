import java.io.File;

public class Test {

    //检查回文字符串
    public static void main(String[] args) {
        String str = "123321";
        char[] ary = new char[str.length()];
        //将字符串分隔，保存在数组中
        for (int i = 0; i <str.length();i++){
            ary[i]=str.charAt(i);
        }
        System.out.println(examine(ary));
    }
    //递归判断字符数组是否是回文字符串
    public static boolean examine(char[] ary){
        //递归终止条件，如果最中间的2个元素相等，则直接返回true
        if (ary[ary.length/2-1]==ary[(ary.length-1)/2+1]){
            return true;
        }
        //如果字符组数第一个元素与最后一个元素不相等，并且字符数组的长度不是偶数，则直接返回false，反之继续递归
        if (ary[0] == ary[ary.length-1-1] && ary.length%2==0){
            examine(ary);
        }else{
            return false;
        }
        return false;
    }
}
