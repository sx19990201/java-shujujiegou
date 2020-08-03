package trie;

import java.util.TreeMap;

public class WordDictionary {

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

    private Node root;

    public void addWord(String word){

    }

    public  boolean search(String word){

        return match(root,word,0);
    }
    //递归函数
    private boolean match(Node node,String word,int index){

        //递归到底
        if (index == word.length()){
            return node.isWord;
        }

        char c = word.charAt(index);
        if (c!='.'){
            if (node.next.get(c) == null){
                return false;
            }
            return match(node.next.get(c),word,index+1);
        }else{
            for (char nextChar : node.next.keySet()){
                if (match(node.next.get(nextChar),word,index+1)){
                    return true;
                }
            }
            return false;
        }



    }

}
