package array.stack;

import java.util.Stack;
class Solution {
    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();//[({[]
        for (int i = 0 ; i < s.length() ;i++){
            //将第i个元素转换为char类型
            char c = s.charAt(i);
            if (c =='(' || c=='[' || c=='{'){
                //如果这个c为这些左括号就将他放入栈里
                stack.push(c);
            }else{
                //如果栈里没有元素了就返回空 匹配失败
                if (stack.isEmpty()){
                    return false;
                }
                //取出栈顶的元素
                char topChar = stack.pop();
                if (c==')' && topChar !='('){
                    return false;
                }
                if (c==']' && topChar !='['){
                    return false;
                }
                if (c=='}' && topChar !='{'){
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }
}