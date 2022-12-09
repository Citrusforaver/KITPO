public class FractionComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Fraction a = new Fraction((Fraction) o1);
        Fraction b = new Fraction((Fraction) o2);
        a.toIncorrect();
        b.toIncorrect();
        int aNum = a.getNumerator() * b.getDenominator();
        int bNum = b.getNumerator() * a.getDenominator();
        if(aNum == bNum) {
            return 0;
        }
        if(aNum > bNum) {
            return 1;
        }
        if(aNum < bNum) {
            return -1;
        }
        return 2;
    }
}
