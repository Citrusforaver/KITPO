import java.util.Stack;
import java.util.Iterator;
import java.io.*;


class Tree implements ITree {

    public class Element {

        private Object value; // ключ узла
        private int weight; // вес узла
        private Element leftChild; // Левый узел потомок
        private Element rightChild; // Правый узел потомок
        private Element parent; // Родитель узла

        Element() {
            leftChild = null;
            rightChild = null;
            parent = null;
        }

        public Element getParent() {
            return this.parent;
        }

        public void setParent(Element element) {
            this.parent = element;
        }

        public Object getValue() {
            return this.value;
        }

        public void setValue(final Object value) {
            this.value = value;
        }

        public int getWeight() {
            try {
                int weight = this.weight;
                return weight;
            } catch (NullPointerException e) {
                return 0;
            }
        }

        public void setWeight(final int weight) {
            this.weight = weight;
        }

        public Element getLeftChild() {
            return this.leftChild;
        }

        public void setLeftChild(final Element leftChild) {
            this.leftChild = leftChild;
        }

        public Element getRightChild() {
            return this.rightChild;
        }

        public void setRightChild(final Element rightChild) {
            this.rightChild = rightChild;
        }

        @Override
        public String toString() {
            return "Узел{" +
                    "Значение=" + value +
                    '}';
        }
    }

    private Element root; // корневой узел

    public Tree() { // Пустое дерево
        root = null;
    }

    private Element getRoot() {
        return this.root;
    }

    private void setRoot(Element root) {
        this.root = root;
    }

    private int size = 0;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void insertElement(Object value, Comparator comparator) // метод вставки нового элемента
    {
        Element newElement = new Element(); // создание нового узла
        newElement.setValue(value); // вставка данных
        newElement.setWeight(1);

        if (root == null) // если корневой узел не существует
        {
            root = newElement;// то новый элемент и есть корневой узел
            size++;

        } else // корневой узел занят
        {
            Element currentElement = root; // начинаем с корневого узла
            Element parentElement;
            while (true) // мы имеем внутренний выход из цикла
            {
                parentElement = currentElement;
                if (value == currentElement.getValue()) {   // если такой элемент в дереве уже есть, не сохраняем его
                    return;    // просто выходим из метода
                } else {
                    //if (value < currentElement.getValue())  // движение влево?
                    //if (currentElement.getValue().compareTo(value) > 0)  // движение влево?
                    if (comparator.compare(currentElement.getValue(), value) > 0) {
                        currentElement = currentElement.getLeftChild();
                        if (currentElement == null)// если был достигнут конец цепочки,
                        {
                            parentElement.setLeftChild(newElement); //  то вставить слева и выйти из методы
                            newElement.setParent(parentElement);
                            weightPlacement(root);
                            size++;
                            return;
                        }
                    } else { // Или направо?
                        currentElement = currentElement.getRightChild();
                        if (currentElement == null) // если был достигнут конец цепочки,
                        {
                            parentElement.setRightChild(newElement);  //то вставить справа
                            newElement.setParent(parentElement);
                            weightPlacement(root);
                            size++;
                            return; // и выйти
                        }
                    }
                }
            }
        }

    }

    private void weightPlacement(Element element) {
        if (element != null) {
            weightPlacement(element.getLeftChild());
            weightPlacement(element.getRightChild());
            if (element.getLeftChild() == null || element.getRightChild() == null) {
                if (element.getLeftChild() == null && element.getRightChild() == null) {
                    element.setWeight(1);
                } else if (element.getLeftChild() == null) {
                    element.setWeight(1 + element.getRightChild().getWeight());
                } else if (element.getRightChild() == null) {
                    element.setWeight(1 + element.getLeftChild().getWeight());
                }
            } else
                element.setWeight(1 + element.getRightChild().getWeight() + element.getLeftChild().getWeight());
        }
    }

    // поиск по логическому номеру
    public Element findElementByInd(int index) {
        int index_element;
        Element search_element = this.root,  answer;

        if (search_element.getLeftChild() != null)
            index_element = search_element.getLeftChild().getWeight();
        else
            index_element = 0;

        while (true) {
            if (index == index_element) {
                answer = search_element;
                break;
            } else if (index > index_element) {
                search_element = search_element.getRightChild();
                try {
                    index_element += search_element.getLeftChild().getWeight() + 1;
                } catch (NullPointerException e) {
                    index_element += 1;
                }
            } else {
                search_element = search_element.getLeftChild();
                try {
                    index_element -= search_element.getRightChild().getWeight() + 1;
                } catch (NullPointerException e) {
                    index_element -= 1;
                }
            }
        }

        return answer;
    }


    public Object findValueByInd(int index) {
        Element search_element = this.findElementByInd(index);
        if(search_element == null) {
            return null;
        } else {
            return search_element.getValue();
        }
    }

    public boolean deleteElement(Element search_element) {
        Element prev_element = search_element.getParent();

        if (search_element.getLeftChild() == null && search_element.getRightChild() == null) //удаляемый узел - лист
        {
            if (prev_element == null) {
                this.root = null;
            } else if (prev_element.getLeftChild() == search_element) {
                prev_element.setLeftChild(null);
            } else if (prev_element.getRightChild() == search_element) {
                prev_element.setRightChild(null);
            }
        } else if (search_element.getLeftChild() != null && search_element.getRightChild() == null) //удаляемый узел имеет только левого потомка
        {
            if (prev_element == null) {
                this.root = search_element.getLeftChild();
                this.root.setParent(null);
            } else if (prev_element.getLeftChild() == search_element) {
                prev_element.setLeftChild(search_element.getLeftChild());
                search_element.getLeftChild().setParent(prev_element);
            } else if (prev_element.getRightChild() == search_element) {
                prev_element.setRightChild(search_element.getLeftChild());
                search_element.getLeftChild().setParent(prev_element);
            }
        } else if (search_element.getLeftChild() == null && search_element.getRightChild() != null) {
            if (prev_element == null) {
                this.root = search_element.getRightChild();
                this.root.setParent(null);
            } else if (prev_element.getLeftChild() == search_element) {
                prev_element.setLeftChild(search_element.getRightChild());
                search_element.getRightChild().setParent(prev_element);
            } else if (prev_element.getRightChild() == search_element) {
                prev_element.setRightChild(search_element.getRightChild());
                search_element.getRightChild().setParent(prev_element);
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteElemByInd(int index) {
        Element search_element = this.root;
        int index_element;
        try {
            index_element = search_element.getLeftChild().getWeight();
        } catch (NullPointerException e) {
            index_element = 0;
        }
        while (true) {
            if (index == index_element) {
                if (deleteElement(search_element) == true) {
                    ///+
                } else if (search_element.getLeftChild() != null && search_element.getRightChild() != null) {

                    Element del_element = search_element.getRightChild();

                    while (del_element.getLeftChild() != null && del_element.getRightChild() != null) {
                        if (del_element.getLeftChild() != null) {
                            del_element.setWeight(del_element.getWeight() - 1);
                            del_element = del_element.getLeftChild();
                        } else {
                            del_element.setWeight(del_element.getWeight() - 1);
                            del_element = del_element.getRightChild();
                        }
                    }

                    search_element.setValue(del_element.getValue());
                    search_element.setWeight(search_element.getWeight() - 1);

                    deleteElement(del_element);
                }
                size--;
                return true;
            } else if (index > index_element) {
                search_element.setWeight(search_element.getWeight() - 1);
                search_element = search_element.getRightChild();
                try {
                    index_element += (search_element.getLeftChild().getWeight() + 1);
                } catch (NullPointerException e) {
                    index_element += 1;
                }
            } else {
                search_element.setWeight(search_element.getWeight() - 1);
                search_element = search_element.getLeftChild();
                try {
                    index_element -= (search_element.getRightChild().getWeight() + 1);
                } catch (NullPointerException e) {
                    index_element -= 1;
                }
            }
        }
    }

    @Override
    public void printTree() { // метод для вывода дерева в консоль
        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(root);
        int gaps = 32; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        String separator = "-----------------------------------------------------------------";
        System.out.println(separator);// черта для указания начала нового дерева
        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;
            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                Element temp = (Element) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    //System.out.print(temp.getValue()); // выводим его значение в консоли
                    System.out.print(temp.getValue() + ":" + temp.getWeight()); // выводим его значение в консоли
                    localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRightChild());
                    if (temp.getLeftChild() != null || temp.getRightChild() != null)
                        isRowEmpty = false;
                } else {
                    System.out.print("__");// - если элемент пустой
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 - 2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
        System.out.println(separator);// подводим черту
    }

    public void forEach(Action a) throws IOException {
        if (this.root.getWeight() > 0) {
            int counter = 0;
            Element element = this.root;
            do {
                a.toDo(element.value);
                element = findElementByInd(++counter);
            } while (element != null);
        } else {
            System.out.println("Нет элементов в массиве");
        }
    }

    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            int counter = 0;
            Element buf = root;

            @Override
            public boolean hasNext() {
                return this.counter < root.getWeight();
            }

            @Override
            public Object next() {
                if (counter++ != 0)
                    buf = findElementByInd(counter - 1);
                return buf.value;
            }
        };
    }



    private Element rotateRight(Element grandParent, Element parent, Element leftChild) {
        if (null != grandParent) {
            grandParent.setRightChild(leftChild);
        } else {
            setRoot(leftChild);
        }
        parent.setLeftChild(leftChild.getRightChild());
        leftChild.setRightChild(parent);
        return grandParent;
    }

    private void rotateLeft(Element grandParent, Element parent, Element rightChild) {
        if (null != grandParent) {
            grandParent.setRightChild(rightChild);
        } else {
            setRoot(rightChild);
        }
        parent.setRightChild(rightChild.getLeftChild());
        rightChild.setLeftChild(parent);
    }




    private void makeRotations(int bound) {
        Element grandParent = null;
        Element parent = root;
        Element child = root.getRightChild();
        for (; bound > 0; bound--) {
            try {
                if (null != child) {
                    rotateLeft(grandParent, parent, child);
                    grandParent = child;
                    parent = grandParent.getRightChild();
                    child = parent.getRightChild();
                } else {
                    break;
                }
            } catch (NullPointerException convenient) {
                break;
            }
        }
    }
    private void createVine()
    {
        Element grandParent = null;
        Element parent = root;
        Element leftChild;

        while (null != parent)
        {
            leftChild = parent.getLeftChild();
            if (null != leftChild)
            {
                grandParent = rotateRight(grandParent, parent, leftChild);
                parent = leftChild;
            } else
            {
                grandParent = parent;
                parent = parent.getRightChild();
            }
        }
    }


    public Tree Balance()
    {
        this.createVine();
        int n = 0;
        for (Element tmp = root; null != tmp; tmp = tmp.getRightChild())
        {
            n++;
        }
        int m = greatestPowerOf2LessThanN(n + 1) - 1;
        makeRotations(n - m);

        while (m > 1)
        {
            makeRotations(m /= 2);
        }

        weightPlacement(root);
        return null;
    }

    private int greatestPowerOf2LessThanN(int n)
    {
        int ndx = 0;
        while (1 < n)
        {
            n = (n >> 1);
            ndx++;
        }
        return (1 << ndx);//2^x
    }
    public String[] getStringTree() {
        String[] list = new String[size];
        System.out.print(getSize());

        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(root);
        int gaps = 32; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        int i=0;

        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)

                while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                    Element temp = (Element) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                    if (temp != null) {
                        list[i] = temp.getValue().toString();
                        i++;
                        localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                        localStack.push(temp.getRightChild());
                        if (temp.getLeftChild() != null ||
                                temp.getRightChild() != null)
                            isRowEmpty = false;
                    }
                    else {
                        localStack.push(null);
                        localStack.push(null);
                    }

                }
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }



        return list;
    }

    public void clear() {
        Stack globalStack = new Stack(); // общий стек для значений дерева
        globalStack.push(root);
        int gaps = 32; // начальное значение расстояния между элементами
        boolean isRowEmpty = false;
        while (isRowEmpty == false) {
            Stack localStack = new Stack(); // локальный стек для задания потомков элемента
            isRowEmpty = true;

            for (int j = 0; j < gaps; j++)

                while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                    Element temp = (Element) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                    if (temp != null) {

                        temp.setValue(null);
                        localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                        localStack.push(temp.getRightChild());
                        if (temp.getLeftChild() != null ||
                                temp.getRightChild() != null)
                            isRowEmpty = false;
                    }
                    else {
                        localStack.push(null);
                        localStack.push(null);
                    }

                }
            gaps /= 2;// при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop()); // перемещаем все элементы из локального стека в глобальный
        }
        root = null;
        size = 0;
    }

}
