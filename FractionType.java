import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FractionType implements UserType {
    Fraction fraction = null;
    FractionComparator fractionComporator = new FractionComparator();

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public String typeName() {
        return "Fraction";
    }

    @Override
    public Object create() {
        fraction = new Fraction(getRandomNumber(0, 100), getRandomNumber(1, 100), getRandomNumber(1, 100));
        return fraction;
    }

    @Override
    public Object clone() {
        Fraction clone = new Fraction(fraction);
        return clone;
    }

    @Override
    public Object readValue(InputStreamReader in) throws IOException {
        Reader r = in;
        int data = r.read();
        String line = "";
        while (data != -1) {
            if((char)data == '\n') {
                break;
            }
            line += (char)data;
            data = r.read();
        }
        Pattern p = Pattern.compile("[(][0-9]+[)][0-9]+[/][0-9]+");
        Matcher m = p.matcher(line);
        Fraction f = null;
        while(m.find()) {
            f = (Fraction)parseValue(m.group());
        }
        return f;
    }

    @Override
    public void writeValue(OutputStreamWriter out, Object o) throws IOException {
        Fraction f = (Fraction) o;
        Writer w = out;
        w.write("\t<fraction>"+f.toString()+"</fraction>\n");
    }

    @Override
    public Object parseValue(String ss) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(ss);
        ArrayList<Integer> list = new ArrayList<Integer>();
        while(m.find()) {
            list.add(Integer.parseInt(m.group()));
        }
        return new Fraction(list.get(0), list.get(1), list.get(2));
    }

    @Override
    public Comparator getTypeComparator() {
        return fractionComporator;
    }
}

