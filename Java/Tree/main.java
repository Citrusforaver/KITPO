

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {



      MyFactory factory = new MyFactory();
      UserType userType = factory.getBuilderByName("Int");



      Tree tree = new Tree();
      GUI gui = new GUI(tree);
     System.out.println ("------ Добавить ------");


    }
}
