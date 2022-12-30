//import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Test;

public class TestIntType {

    private MyFactory factory = new MyFactory();
    private UserType userType = factory.getBuilderByName("Int");
    private int size = 10;

    public void InsertRandomElement(Tree tree) {;
        for(int i = 0; i < size; i++) {
            tree.insertElement(userType.create(), userType.getTypeComparator());
        }
    };

//Добавление элементов
    @Test
    public void testInsertElement() {
        Tree tree = new Tree();
        tree.insertElement(10, userType.getTypeComparator());
        tree.insertElement(5, userType.getTypeComparator());
        Assert.assertEquals(2, tree.getSize());
    };
    //Удаление элементов
    @Test
    public void testDeleteElement() {
        Tree tree = new Tree();
        tree.insertElement(43, userType.getTypeComparator());
        tree.insertElement(20, userType.getTypeComparator());
        tree.insertElement(32, userType.getTypeComparator());
        tree.insertElement(11, userType.getTypeComparator());
        tree.insertElement(1, userType.getTypeComparator());
        tree.insertElement(13, userType.getTypeComparator());
        tree.insertElement(100, userType.getTypeComparator());
        tree.printTree();
        Object check = tree.findElementByInd(3);
        tree.deleteElemByInd(3);
        tree.printTree();
        Assert.assertEquals(check, tree.findElementByInd(3));
    };
    //Очищение дерева
    @Test
    public void testClearTree() {
        Tree tree = new Tree();
        InsertRandomElement(tree);
        tree.printTree();
        tree.clear();
        tree.printTree();
        Assert.assertEquals(0, tree.getSize());
    };
    //Проверка на ввод одинаковых значений
    @Test
    public void testIdencial() {
        Tree tree = new Tree();
        for(int i = 0; i < size - 1; i++) {
            tree.insertElement(13, userType.getTypeComparator());
        }
        tree.printTree();
        Assert.assertEquals(1,tree.getSize());
    };
    //Проверка на ввод групп одинаковых значений
    @Test
    public void testIdencialGroup() {
        Tree tree = new Tree();
        for(int i = 0; i < size - 1; i++) {
            tree.insertElement(3, userType.getTypeComparator());
            tree.insertElement(2, userType.getTypeComparator());
            tree.insertElement(1, userType.getTypeComparator());
            tree.insertElement(1, userType.getTypeComparator());
            tree.insertElement(2, userType.getTypeComparator());
            tree.insertElement(3, userType.getTypeComparator());
        }
        tree.printTree();
        Assert.assertEquals(3,tree.getSize());
    };


    // Исходный набор неупорядочен
    @Test
    public void testDisordered() {
        Tree tree = new Tree();
        for(int i = 0; i < size - 1; i++) {
           InsertRandomElement(tree);
        }
        tree.Balance();
        for(int i = 0; i < size - 3; i++) {
            int first = (int) tree.findValueByInd( i+1);
            int second = (int) tree.findValueByInd( i+2);
            Assert.assertTrue(first <= second);
        }
    };
    // Исходный набор упорядочен
    @Test
    public void testDirect() {
        Tree tree = new Tree();
        for(int i = 0; i < size - 1; i++) {
            tree.insertElement(i, userType.getTypeComparator());

        }
        tree.Balance();
        for(int i = 0; i < size - 3; i++) {
            int first = (int) tree.findValueByInd( i+1);
            int second = (int) tree.findValueByInd( i+2);
            Assert.assertTrue(first <= second);
        }
    };
    // Исходный набор упорядочен в обратном порядке
    @Test
    public void testReverse() {
        Tree tree = new Tree();
        for(int i = size; i > 0; i--) {
            tree.insertElement(i, userType.getTypeComparator());
        }
        tree.Balance();
        for(int i = 0; i < size - 3; i++) {
            int first = (int) tree.findValueByInd( i+1);
            int second = (int) tree.findValueByInd( i+2);
            Assert.assertTrue(first <= second);
        }
    };
    // Экстремальное значение вводится в середине
    @Test
    public void testExtrimMiddle() {
        Tree tree = new Tree();
        for(int i = size; i > 0; i--) {
            int check;
            if (size % 2 == 0)
                check = size/2;
            else
                check = size/2+1;

            if (i == check)
                tree.insertElement(100, userType.getTypeComparator());
            else
                tree.insertElement(i, userType.getTypeComparator());
        }
        tree.Balance();
        for(int i = 0; i < size - 2; i++) {
            int first = (int) tree.findValueByInd( i+1);
            int second = (int) tree.findValueByInd( i+2);
            Assert.assertTrue(first <= second);
        }
    };

    // Экстремальное значение вводится в начале
    @Test
    public void testExtrimFront() {
        Tree tree = new Tree();
        for(int i = size; i > 0; i--) {
            if (i == size)
                tree.insertElement(100, userType.getTypeComparator());
            else
                tree.insertElement(i, userType.getTypeComparator());
        }
        tree.Balance();
        for(int i = 0; i < size - 2; i++) {
            int first = (int) tree.findValueByInd( i+1);
            int second = (int) tree.findValueByInd( i+2);
            Assert.assertTrue(first <= second);
        }
    };
    // Экстремальное значение вводится в конце
    @Test
    public void testExtrimEnd() {
        Tree tree = new Tree();
        for(int i = size; i > 0; i--) {
            if (i == 1)
                tree.insertElement(100, userType.getTypeComparator());
            else
                tree.insertElement(i, userType.getTypeComparator());
        }
        tree.Balance();
        for(int i = 0; i < size - 2; i++) {
            int first = (int) tree.findValueByInd( i+1);
            int second = (int) tree.findValueByInd( i+2);
            Assert.assertTrue(first <= second);
        }
    };
    // несколько экстремальных значений
    @Test
    public void testExtrimIdencial() {
        Tree tree = new Tree();
        InsertRandomElement(tree);
        tree.insertElement(-100, userType.getTypeComparator());
        tree.insertElement(100, userType.getTypeComparator());
        tree.insertElement(-100, userType.getTypeComparator());
        tree.insertElement(100, userType.getTypeComparator());
        tree.insertElement(-100, userType.getTypeComparator());
        for(int i = 0; i < size - 1; i++) {
            int first = (int) tree.findValueByInd( i+1);
            int second = (int) tree.findValueByInd( i+2);
            Assert.assertTrue(first <= second);
        }
    };
   // Проверка скорости удаления на разных значениях с шагом в 10к
      @Test
    public void testBalanced() {
        Tree tree = new Tree();

        for (int i = 10000; i <= 310000; i = i + 10000 ) {
           tree.clear();
            for(int j = 0; j < i; j++) {
                tree.insertElement(userType.create(), userType.getTypeComparator());

            }
            tree.Balance();
            long startTime = System.nanoTime();
            tree.deleteElemByInd(tree.getSize()-1);
            long endTime = System.nanoTime();
            double timeElapsed = (endTime - startTime) * 1.0 / 1_000_000;
             System.out.println("N = " + i +  ". Time = " + timeElapsed + " ms.");

            tree.clear();
        }

    }


}
