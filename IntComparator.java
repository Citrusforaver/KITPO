public class IntComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        int a = (int) o1;
        int b = (int) o2;
        if(a == b) {
            return 0;
        }
        if(a > b) {
            return 1;
        }
        if(a < b) {
            return -1;
        }
        return 2;
    }
}