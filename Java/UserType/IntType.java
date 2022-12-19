import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntType implements UserType{
    int value;
    IntComparator intComparator = new IntComparator();

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public String typeName() {
        return "Int";
    }

    @Override
    public Object create() {
        value = getRandomNumber(1223, 100000000);
        return value;
    }

    @Override
    public Object clone() {
        return value;
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
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(line);
        int i = 0;
        while(m.find()) {
            i = (int)parseValue(m.group());
        }
        return i;
    }

    @Override
    public void writeValue(OutputStreamWriter out, Object o) throws IOException {
        int i = (int) o;
        Writer w = out;
        w.write("\t<int>"+i+"</int>\n");
    }

    @Override
    public Object parseValue(String ss) {
        return Integer.parseInt(ss);
    }

    @Override
    public Comparator getTypeComparator() {
        return intComparator;
    }
}
