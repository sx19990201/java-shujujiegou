package trie;

import java.util.TreeMap;

/**
 * 字典树，由于trie中定义每一个node，都要使用映射，在这里用java标准库中的treemap
 *
 * 之前所设计的所有数据结构都是泛型类，但是这个trie不是
 * 这相当于，我默认了，使用这个trie在每一个节点中，存储的每一个字母都是Character这个类的对象，
 * 这其实是一个假设，trie这个数据结构完全可以不仅仅使用英文环境中，比如说汉语，韩语等，都可以使用，只要他能够分割成一个一个的单元
 * 在其他不同的语言中，就不是一个一个的字母了，比如在中国，是一个一个的字符了
 * 在这种时候，在一些业务场景中，你要用trie的话，就不能用这种charater对象的trie树了
 * 这是因为对于汉语，一个一个的字符不是character对象，可以根据汉语等语言，创建属于这些语言体系的单独的字符的类
 *
 * 在这种情况下，其实将trie设置成一个泛型类，也是可以的。
 * 在这里使用character，是因为之后象trie中存储元素基本单元，都是一个一个的字符串
 * 在这种情况下，字符串都是由一个一个character组成的，所以整个程序的设计会比较简单
 *
 *  另一种情况，对于中国等语言中，对于什么是单词这样的定义是比较模糊的，
 *  不是像英语那样由空格就可以区分一个单词了
 */
public class Trie {

    private class Node{
        //标识当前节点是否是一个单词的结尾
        public boolean isWord;
        //定义映射，到下一个节点的映射
        public TreeMap<Character,Node> next;

        //构造函数
        public Node(boolean isWord){
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node(){
            this(false);
        }
    }

    //定义根节点
    private Node root;

    private int size;

    public Trie(){
        root = new Node();
        size = 0;
    }

    //获得trie中存储的单词数量
    public int getSize(){
        return size;
    }

    //添加元素，他的逻辑和二叉树是非常像的，不过trie指向下个节点是多个映射
    //在这里采用非递归写法
    //在这里是添加的是一个新的字符串，要把这个字符串拆成一个一个的字符，然后做成一个一个的节点，添加进树结构中
    //你也可以实现一遍递归的写法
    public void add(String word){
        //假定初始在根节点上
        Node cur = root;
        for (int i = 0 ; i < word.length();i++){
            //拆分字符串
            char c = word.charAt(i);
            //接下来c这个字符做成一个节点插入到整个trie中，在这之前要进行检查一下
            //对于当前的cur节点的next映射中是否已经有了指向c这个字符的相应的节点，如果为空的话才能新创建一个节点
            if (cur.next.get(c)==null){
                //创建节点，treemap添加映射为put，对应的字符为c，node为新的节点
                cur.next.put(c,new Node());
            }
            //如果如果cur节点的next映射中已经包含了从c下一个节点的映射，那么直接走到相应的节点就行了
            cur = cur.next.get(c);
            //这样以此类推，就把字符串所有的字母都分割添加到trie树中了
        }
        //整个for循环运行结束后，那么来到了整个字符串最后的那个字符所在的节点
        //这个节点不一定是叶子节点，比如说 pan最后一个单词不是panda的结尾
        //所有现在要标识一下，这个节点已经表示一个单词的末尾了
        //但是这里有隐秘的逻辑的错误，如果添加的单词已经在trie中了，所有再标识之前要进行一个判断
        //判断这个添加的单词是否在trie中存在 也就是isWord为false，这样代表不存在，如果siword为true则说明它存在了，不用添加
        if (!cur.isWord){
            cur.isWord = true;
            size++;
        }
    }

    //查询单词word是否在Trie中
    //整体逻辑和添加是差不多的，
    //只不过在添加的时候要判断是否存在不存在创建节点，
    //而查询的话，直接判断是否有这个单词，没有直接返回false就可以了
    //这里也是非递归实现的，也可以用递归实现
    public boolean contains(String word){
        //假设cur指向的是根节点
        Node cur = root;
        //从根节点开始循环
        for (int i = 0 ; i < word.length();i++){
            //每一个字符
            char c = word.charAt(i);
            //判断当前的节点的next的映射中是否包含从c这个字符到下一个节点的映射
            //如果等于null，则说明这个true没有这个word，那么直接返回false
            if (cur.next.get(c)==null){
                return false;
            }
            //否则cur节点就进行更新，这个时候当前的位置为c这个节点
            cur = cur.next.get(c);
        }
        //然后继续循环，一直插下去，直到出了循环，相当于这个cur来到了字符串最后的这个节点
        //但是这个时候不能直接返回true，比如说有panda这个单词，要查pan，这个时候他会来到pan 中 n这个字母
        //但是trie是没有pan这个单词的，只是panda中包含了pan，
        //那么这个时候应该返回，cur下到pan单词末尾的节点isWore属性
        //如果isword为true则有这个单词，如果为false则说明没有
        //刚好可以直接将这个isword的属性给返回出去
        return cur.isWord;
    }

    //查询是否在trie中有单词是否以prefix为潜在
    //这个逻辑和上面contans的逻辑非常像
    //都是从根节点开始，逐渐向下搜索，
    //搜索到最后整个前缀都找了个遍，在trie都有相应的节点就可以直接返回true
    public boolean isPrefix(String prefix){
        //从根节点开始遍历
        Node cur = root;
        for (int i = 0 ; i < prefix.length() ; i++){
            char c = prefix.charAt(i);
            if (cur.next.get(c)==null){
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }

}
