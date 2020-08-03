import array.queue.ArrayQueue;
import array.queue.LoopQueue;
import array.queue.Queue;
import array.stack.ArrayStack;
import array.stack.LinkedListStack;
import array.stack.Stack;

import java.util.Random;

public class 时间复杂度 {


    //测试使用q运行opCount个enqueue和dequeue操作所需要的时间，单位秒
    private static double testQueue(Queue<Integer>q,int opCount){
        long startTime = System.nanoTime();
        Random random = new Random();
        for (int i = 0 ;i<opCount;i++){
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for (int i = 0 ;i<opCount;i++){
            q.dequeue();
        }

        long endTime = System.nanoTime();
        //由于nanotime单位为纳米 转换为秒要除以1000000000
        return (endTime-startTime)/1000000000.0;
    }

    //测试使用q运行opCount个enqueue和dequeue操作所需要的时间，单位秒
    private static double testStack(Stack<Integer> stack, int opCount){
        long startTime = System.nanoTime();
        Random random = new Random();
        for (int i = 0 ;i<opCount;i++){
            stack.push(random.nextInt(Integer.MAX_VALUE));

        }
        for (int i = 0 ;i<opCount;i++){
            stack.pop();
        }

        long endTime = System.nanoTime();
        //由于nanotime单位为纳米 转换为秒要除以1000000000
        return (endTime-startTime)/1000000000.0;
    }


    public static void main(String[] args) {
        int opCount = 10000000;
        ArrayStack<Integer> arrayStack = new ArrayStack<>();
        double time1 = testStack(arrayStack,opCount);
        System.out.println("ArrayStack ，time："+time1+"s");

        LinkedListStack<Integer> linkedListStack = new LinkedListStack<>();
        double time2 = testStack(linkedListStack,opCount);
        System.out.println("链表队列 LinkedListStack，time："+time2+"s");
    }
}
