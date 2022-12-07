import java.io.InputStreamReader;

public class Fraction  {
    private int intFraction;
    private int numerator;
    private int denominator;

    public Fraction(int intFraction, int numerator, int denominator) {
        this.intFraction = intFraction;
        this.numerator = numerator;
        this.denominator = denominator;
        toCorrect();
    }

    public Fraction(Fraction other) {
        this.intFraction = other.intFraction;
        this.numerator = other.numerator;
        this.denominator = other.denominator;
        toCorrect();
    }

    public String toString() {
        return "("+intFraction+")"+numerator+"/"+denominator;
    }

    private int gcd(int a, int b){
        return b == 0 ? a : gcd(b,a % b);
    }

    private void toCorrect() {
        int value = gcd(numerator, denominator);
        if(value != 1 && value != 0) {
            numerator /= value;
            denominator /= value;
        }

        if(numerator == 1 && denominator == 1) {
            intFraction++;
            numerator = 0;
            denominator = 0;
        }

        if(numerator > denominator) {
            int temp = numerator / denominator;
            intFraction += temp;
            numerator = numerator - (temp * denominator);
            denominator = numerator == 0 ? 0 : denominator;
        }
    }

    public void toIncorrect() {
        if(intFraction > 0) {
            if(numerator == 0 && denominator == 0) {
                numerator = intFraction;
                denominator = 1;
            } else {
                numerator = numerator + intFraction * denominator;
            }
            intFraction = 0;
        }
    }




    public int getIntFraction() {
        return intFraction;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }
}
