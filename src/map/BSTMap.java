package map;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {

    private class Node {
        public K key;
        public V value;
        public Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    @Override
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {
        //递归到底
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        //没有递归到底
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else if (key.compareTo(node.key) == 0) {  //如果添加的节点已经存在该root树中
            node.value = value;
        }
        return node;
    }

    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }
        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else {
            return getNode(node.right, key);
        }
    }

    @Override
    public V remove(K key) {
        Node node = getNode(root,key);
        if (node!=null){
            root= remove(root,key);
            return node.value;
        }
        return null;
    }
    //删除二分搜索树中键为key的节点，递归算法
    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        //待删除节点的key小于该节点的左子树的key，则继续想左子树遍历
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
            //待删除节点的key小于该节点的右子树的key，则继续想右子树遍历
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {
            //待删除节点的左子树为空，右子树不为空的情况下
            if (node.left == null) {
                //定义一个节点，指向该节点的右子树
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            //待删除节点的右子树为空，左子树不为空的情况下
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            //待删除节点的左右子树都不为空的情况下
            //定义一个节点，从节点的左子树一直找，找到它最底层的右节点，则这个节点为他的最后一个节点，
            //右比左大，找到了该root的左子树中做小的右子节点，则这个定义的节点为root树中，左子树的最底层节点的父节点
            Node success = minimun(node.right);

            success.right = removeMin(node.right);
            success.left = node.left;

            node.left = node.right = null;
            return success;
        }
    }
    //返回以node为根的二分搜索树的最小值所在的节点
    private Node minimun(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimun(node.left);
    }
    //删除掉以node为根的二分搜索树中只为key的节点，递归算法
    //返回删除节点后新的二叉搜索树的跟
    private Node removeMin(Node node) {
        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V value) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException();
        }
        node.value = value;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
