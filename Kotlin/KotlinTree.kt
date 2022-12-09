import java.util.*

class TreeKotlin : ITree {

    private var root // корневой узел
            : Element? = null

    private fun setRoot(root: Element) {
        this.root = root
    }

     private var size = 0

     override fun getSize(): Int {
         return size
     }


     override fun insertElement(value: Any, comparator: Comparator) // метод вставки нового элемента
    {
        val newElement: Element = Element() // создание нового узла
        newElement.value = value // вставка данных
        newElement.weight = 1
        if (root == null) // если корневой узел не существует
        {
            root = newElement // то новый элемент и есть корневой узел
            size++
        } else  // корневой узел занят
        {
            var currentElement = root // начинаем с корневого узла
            var parentElement: Element?
            while (true) // мы имеем внутренний выход из цикла
            {
                parentElement = currentElement
                if (value === currentElement!!.value) {   // если такой элемент в дереве уже есть, не сохраняем его
                    return  // просто выходим из метода
                } else {
                    //if (value < currentElement.getValue())  // движение влево?
                    //if (currentElement.getValue().compareTo(value) > 0)  // движение влево?
                    if (comparator.compare(currentElement!!.value, value) > 0) {
                        currentElement = currentElement.leftChild
                        if (currentElement == null) // если был достигнут конец цепочки,
                        {
                            parentElement!!.leftChild = newElement //  то вставить слева и выйти из методы
                            newElement.parent = parentElement
                            weightPlacement(root)
                            size++
                            return
                        }
                    } else { // Или направо?
                        currentElement = currentElement.getRightChild()
                        if (currentElement == null) // если был достигнут конец цепочки,
                        {
                            parentElement!!.setRightChild(newElement) //то вставить справа
                            newElement.parent = parentElement
                            weightPlacement(root)
                            size++
                            return  // и выйти
                        }
                    }
                }
            }
        }
    }

    private fun weightPlacement(element: Element?) {
        if (element != null) {
            weightPlacement(element.leftChild)
            weightPlacement(element.getRightChild())
            if (element.leftChild == null || element.getRightChild() == null) {
                if (element.leftChild == null && element.getRightChild() == null) {
                    element.weight = 1
                } else if (element.leftChild == null) {
                    element.weight = 1 + element.getRightChild()!!.weight
                } else if (element.getRightChild() == null) {
                    element.weight = 1 + element.leftChild!!.weight
                }
            } else element.weight = 1 + element.getRightChild()!!.weight + element.leftChild!!.weight
        }
    }

    // поиск по логическому номеру
    fun findElementByInd(index: Int): Element? {
        var index_element: Int
        var search_element = root
        val answer: Element?
        index_element = if (search_element!!.leftChild != null) search_element.leftChild!!.weight else 0
        while (true) {
            if (index == index_element) {
                //answer = search_element.getValue();
                answer = search_element
                break
            } else if (index > index_element) {
                search_element = search_element!!.getRightChild()
                index_element += try {
                    search_element?.leftChild!!.weight + 1
                } catch (e: NullPointerException) {
                    1
                }
            } else {
                search_element = search_element!!.leftChild
                index_element -= try {
                    search_element!!.getRightChild()!!.weight + 1
                } catch (e: NullPointerException) {
                    1
                }
            }
        }
        return answer
    }

    fun deleteElement(search_element: Element?): Boolean {
        val prev_element = search_element!!.parent
        if (search_element.leftChild == null && search_element.getRightChild() == null) //удаляемый узел - лист
        {
            if (prev_element == null) {
                root = null
            } else if (prev_element.leftChild === search_element) {
                prev_element.leftChild = null
            } else if (prev_element.getRightChild() === search_element) {
                prev_element.setRightChild(null)
            }
        } else if (search_element.leftChild != null && search_element.getRightChild() == null) //удаляемый узел имеет только левого потомка
        {
            if (prev_element == null) {
                root = search_element.leftChild
                root!!.parent = null
            } else if (prev_element.leftChild === search_element) {
                prev_element.leftChild = search_element.leftChild
                search_element.leftChild!!.parent = prev_element
            } else if (prev_element.getRightChild() === search_element) {
                prev_element.setRightChild(search_element.leftChild)
                search_element.leftChild!!.parent = prev_element
            }
        } else if (search_element.leftChild == null && search_element.getRightChild() != null) {
            if (prev_element == null) {
                root = search_element.getRightChild()
                root!!.parent = null
            } else if (prev_element.leftChild === search_element) {
                prev_element.leftChild = search_element.getRightChild()
                search_element.getRightChild()!!.parent = prev_element
            } else if (prev_element.getRightChild() === search_element) {
                prev_element.setRightChild(search_element.getRightChild())
                search_element.getRightChild()!!.parent = prev_element
            }
        } else {
            return false
        }
        return true
    }

    override fun deleteElemByInd(index: Int): Boolean {
        var search_element = root
        var index_element: Int
        index_element = try {
            search_element!!.leftChild!!.weight
        } catch (e: NullPointerException) {
            0
        }
        while (true) {
            if (index == index_element) {
                if (deleteElement(search_element) == true) {
                    ///+
                } else if (search_element!!.leftChild != null && search_element.getRightChild() != null) {
                    var del_element: Element? = search_element.getRightChild()
                    while (del_element!!.leftChild != null && del_element.getRightChild() != null) {
                        if (del_element.leftChild != null) {
                            del_element.weight = del_element.weight - 1
                            del_element = del_element.leftChild
                        } else {
                            del_element.weight = del_element.weight - 1
                            del_element = del_element.getRightChild()
                        }
                    }
                    search_element.value = del_element.value
                    search_element.weight = search_element.weight - 1
                    deleteElement(del_element)
                }
                size--
                return true
            } else if (index > index_element) {
                search_element!!.weight = search_element.weight - 1
                search_element = search_element.getRightChild()
                index_element += try {
                    search_element?.leftChild!!.weight + 1
                } catch (e: NullPointerException) {
                    1
                }
            } else {
                search_element!!.weight = search_element.weight - 1
                search_element = search_element.leftChild
                index_element -= try {
                    search_element!!.getRightChild()!!.weight + 1
                } catch (e: NullPointerException) {
                    1
                }
            }
        }
    }

    override fun printTree() { // метод для вывода дерева в консоль
        val globalStack: Stack<Any?> = Stack<Any?>() // общий стек для значений дерева
        globalStack.push(root)
        var gaps = 32 // начальное значение расстояния между элементами
        var isRowEmpty = false
        val separator = "-----------------------------------------------------------------"
        println(separator) // черта для указания начала нового дерева
        while (isRowEmpty == false) {
            val localStack: Stack<Any?> = Stack<Any?>() // локальный стек для задания потомков элемента
            isRowEmpty = true
            for (j in 0 until gaps) print(' ')
            while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                val temp = globalStack.pop() as Element? // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    //System.out.print(temp.getValue()); // выводим его значение в консоли
                    print(temp.value.toString() + ":" + temp.weight) // выводим его значение в консоли
                    localStack.push(temp.leftChild ) // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.rightChild )
                    if (temp.leftChild != null || temp.rightChild != null) isRowEmpty = false
                } else {
                    print("__") // - если элемент пустой
                    localStack.push(null)
                    localStack.push(null)
                }
                for (j in 0 until gaps * 2 - 2) print(' ')
            }
            println()
            gaps /= 2 // при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false) globalStack.push(localStack.pop() ) // перемещаем все элементы из локального стека в глобальный
        }
        println(separator) // подводим черту
    }


    override fun forEach(a: Action<Any>) {
        if (root!!.weight > 0) {
            var counter = 0
            var element = root
            do {
                a.toDo(element!!.value)
                element = findElementByInd(++counter)
            } while (element != null)
        } else {
            println("Нет элементов в массиве")
        }
    }

    operator fun iterator(): Iterator<Any?> {
        return object : Iterator<Any?> {
            var counter = 0
            var buf = root
            override fun hasNext(): Boolean {
                return counter < root!!.weight
            }

            override fun next(): Any {
                if (counter++ != 0) buf = findElementByInd(counter - 1)
                return buf!!.value!!
            }
        }
    }

    private fun rotateRight(grandParent: Element?, parent: Element, leftChild: Element): Element? {
        if (null != grandParent) {
            grandParent.setRightChild(leftChild)
        } else {
            setRoot(leftChild)
        }
        parent.leftChild = leftChild.getRightChild()
        leftChild.setRightChild(parent)
        return grandParent
    }

    private fun rotateLeft(grandParent: Element?, parent: Element?, rightChild: Element) {
        if (null != grandParent) {
            grandParent.setRightChild(rightChild)
        } else {
            setRoot(rightChild)
        }
        parent!!.setRightChild(rightChild.leftChild)
        rightChild.leftChild = parent
    }

    private fun makeRotations(bound: Int) {
        var bound = bound
        var grandParent: Element? = null
        var parent = root
        var child = root!!.getRightChild()
        while (bound > 0) {
            try {
                if (null != child) {
                    rotateLeft(grandParent, parent, child)
                    grandParent = child
                    parent = grandParent.getRightChild()
                    child = parent?.getRightChild()
                } else {
                    break
                }
            } catch (convenient: NullPointerException) {
                break
            }
            bound--
        }
    }// соохраняем в локальный стек, наследники текущего элемента

    override fun getStringTree(): Array<String?>? {
        val list = arrayOfNulls<String>(size)
        print(getSize())
        val globalStack: Stack<Any?> = Stack<Any?>() // общий стек для значений дерева
        globalStack.push(root)
        var gaps = 32 // начальное значение расстояния между элементами
        var isRowEmpty = false
        var i = 0
        while (isRowEmpty == false) {
            val localStack: Stack<Any?> = Stack<Any?>() // локальный стек для задания потомков элемента
            isRowEmpty = true
            for (j in 0 until gaps) while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                val temp = globalStack.pop() as Element? // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    list[i] = temp.value.toString()
                    i++
                    localStack.push(temp.leftChild) // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.rightChild )
                    if (temp.leftChild != null ||
                            temp.rightChild != null) isRowEmpty = false
                } else {
                    localStack.push(null)
                    localStack.push(null)
                }
            }
            gaps /= 2 // при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false) globalStack.push(localStack.pop()) // перемещаем все элементы из локального стека в глобальный
        }
        return list
    }


    override fun clear() {
        val globalStack: Stack<Any?> = java.util.Stack<Any?>() // общий стек для значений дерева
        globalStack.push(root )
        var gaps = 32 // начальное значение расстояния между элементами
        var isRowEmpty = false
        while (isRowEmpty == false) {
            val localStack: Stack<Any?> = java.util.Stack<Any?>() // локальный стек для задания потомков элемента
            isRowEmpty = true
            for (j in 0 until gaps) while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                val temp = globalStack.pop() as Element? // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    temp.value = null
                    localStack.push(temp.leftChild ) // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRightChild())
                    if (temp.leftChild != null ||
                            temp.getRightChild() != null) isRowEmpty = false
                } else {
                    localStack.push(null)
                    localStack.push(null)
                }
            }
            gaps /= 2 // при переходе на следующий уровень расстояние между элементами каждый раз уменьшается
            while (localStack.isEmpty() == false) globalStack.push(localStack.pop() ) // перемещаем все элементы из локального стека в глобальный
        }
        root = null
        size = 0
    }
}