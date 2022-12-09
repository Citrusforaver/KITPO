import java.io.IOException;

public interface ITree {
    void insertElement(Object value, Comparator comparator);

    boolean deleteElemByInd(int index);
    void printTree();
    boolean deleteElement(Element search_element);
    public void forEach(Action<Object> a) throws IOException;
    public int getSize();
    public String[] getStringTree();
    public void clear();
}
