import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeSerialization {

    public void serialize(UserType userType, ITree tree) throws IOException {
        OutputStream os = new FileOutputStream("tree.xml");
        OutputStreamWriter osr = new OutputStreamWriter(os);
        Writer w = osr;
        w.write("<list size=\""+tree.getSize()+"\">\n");
        tree.forEach(f -> {userType.writeValue(osr, f);});
        w.write("</list>");
        w.close();
        osr.close();
        os.close();
    }

    public ITree deserialize(UserType userType, ITree tree) throws IOException {
        InputStream os = new FileInputStream("tree.xml");
        InputStreamReader osr = new InputStreamReader(os);
        Reader r = osr;
        int data = r.read();
        boolean checkSize = false;
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
        int size = 0;
        while(m.find()) {
            size = Integer.parseInt(m.group());
        }
        for(int i = 1; i < size; i++) {
            Object a = userType.readValue(osr);
           tree.insertElement(a,userType.getTypeComparator());
        }

        r.close();
        osr.close();
        os.close();
        return tree;
    }
}
