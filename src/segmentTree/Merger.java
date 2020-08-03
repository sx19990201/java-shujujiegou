package segmentTree;

/**
 * 业务包装接口，（融合器）
 * @param <E>
 */
public interface Merger<E> {
    //将2个参数通过merge这个操作转换成一个元素E给返回回去
    E merge(E a, E b);
}
