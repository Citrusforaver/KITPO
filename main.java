

import java.io.IOException;

public class main {
    public static void main(String[] args) throws IOException {



      MyFactory factory = new MyFactory();
      UserType userType = factory.getBuilderByName("Int");



      Tree tree = new Tree();
      GUI gui = new GUI(tree);
     System.out.println ("------ Добавить ------");
//     tree.insertElement(userType.create(), userType.getTypeComparator());
//        tree.insertElement(userType.create(), userType.getTypeComparator());
//        tree.insertElement(userType.create(), userType.getTypeComparator());
//        tree.insertElement(userType.create(), userType.getTypeComparator());
//        tree.insertElement(userType.create(), userType.getTypeComparator());
//        tree.printTree();
//        tree.insertElement(userType.create());
//        tree.insertElement(userType.create());
//        tree.insertElement(userType.create());
//        tree.insertElement(userType.create());
//        tree.insertElement(userType.create());
//        tree.insertElement(50);
//        tree.insertElement(66);
//        tree.insertElement(7);
//        tree.insertElement(1);
//        tree.insertElement(2);
//        tree.insertElement(3);
//        tree.insertElement(4);
//        tree.insertElement(8);
    //    tree.printTree();

     //   tree.deleteElement(12);
      //  tree.printTree();

      //  tree.clear();
       // tree.printTree();


//
//
//        tree.push(userType.create());
//        tree.push(userType.create());
//        tree.push(userType.create());
//        tree.push(userType.create());
//        tree.push(userType.create());
//
//
//
//        // отображение дерева:
//        tree.printTree();
//        System.out.println(tree.getSize());

//
//        // удаляем один узел и выводим оставшееся дерево в консоли
//        tree.deleteElement(5);
//        tree.printTree();
//        System.out.println(tree.getSize());
//
//        // находим узел по значению и выводим его в консоли
//        Tree.Element foundElement = tree.findElementByData(7);
//        foundElement.printElement();


    }
}
