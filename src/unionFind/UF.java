package unionFind;

public interface UF {

    int getSize();

    //查
    boolean isConnected(int p ,int q);

    //合
    void unionElements(int p,int q);
}
