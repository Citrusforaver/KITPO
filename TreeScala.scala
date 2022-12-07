import scala.collection.mutable

class TreeScala extends ITree {
  var root: Element = null

  def getRoot() = this.root

  def setRoot(root: Element) = this.root = root

  private var size = 0

  override def getSize: Int = size

  override def insertElement(value: AnyRef, comparator: Comparator) = {
    val newElem = Element(value);
    newElem.weight = 1;

    if (root == null) {
      root = newElem;
    }
    else {
      var curElement = root;
      var parElement = Element(value);
      var check = true
      while (check == true) {
        parElement = curElement;
        if (value == curElement.value) {
          check = false
        }
        else {
          if (comparator.compare(curElement.value, value) > 0) {
            curElement = curElement.leftChild;
            if (curElement == null) {
              parElement.leftChild = newElem;
              newElem.parent = parElement;
              weightPlace(root);
              check = false
            }
          }
          else {
            curElement = curElement.rightChild;
            if (curElement == null) {
              parElement.rightChild = newElem;
              newElem.parent = parElement;
              weightPlace(root);
              check = false
            }
          }
        }
      }
    }
  }

  def weightPlace(elem: Element): Unit = {
    if (elem != null) {
      weightPlace(elem.leftChild);
      weightPlace(elem.rightChild);
      if (elem.leftChild == null || elem.rightChild == null) {
        if (elem.leftChild == null && elem.rightChild == null)
          elem.weight = 1;
        else if (elem.leftChild == null)
          elem.weight = 1 + elem.rightChild.weight;
        else if (elem.rightChild == null)
          elem.weight = 1 + elem.leftChild.weight;
      }
      else
        elem.weight = 1 + elem.leftChild.weight + elem.rightChild.weight;
    }
  }

  def findElemByInd(index: Int): Element = {
    var index_elem = 0
    var search_elem = this.root
    var answer = Element(search_elem.value)

    index_elem = search_elem.leftChild.getWeight()

    var check = true

    while (check)
      if (index == index_elem) {
        answer = search_elem
        check = false
      }
      else if (index > index_elem) {
        search_elem = search_elem.rightChild
        try index_elem += search_elem.leftChild.weight + 1
        catch {
          case e: NullPointerException =>
            index_elem += 1
        }
      }
      else {
        search_elem = search_elem.leftChild
        try index_elem -= search_elem.rightChild.weight + 1
        catch {
          case e: NullPointerException =>
            index_elem -= 1
        }
      }
    answer
  }
  override def deleteElement(search_element: Element): Boolean = {
    val prev_elem = search_element.parent
    if (search_element.leftChild == null && search_element.rightChild == null) { //удаляемый узел - лист
      if (prev_elem == null)
        this.root = null
      else if (prev_elem.leftChild == search_element)
        prev_elem.leftChild = null
      else if (prev_elem.rightChild == search_element) prev_elem.rightChild = null
    }
    else if (search_element.leftChild != null && search_element.rightChild == null) { //удаляемый узел имеет только левого потомка
      if (prev_elem == null) {
        this.root = search_element.leftChild
        this.root.parent = null
      }
      else if (prev_elem.leftChild == search_element) {
        prev_elem.leftChild = search_element.leftChild
        search_element.leftChild.parent = prev_elem
      }
      else if (prev_elem.rightChild == search_element) {
        prev_elem.rightChild = search_element.leftChild
        search_element.leftChild.parent = prev_elem
      }
    }
    else if (search_element.leftChild == null && search_element.rightChild != null) {
      if (prev_elem == null) {
        this.root = search_element.rightChild
        this.root.parent = null
      }
      else if (prev_elem.leftChild == search_element) {
        prev_elem.leftChild = search_element.rightChild
        search_element.rightChild.parent = prev_elem
      }
      else if (prev_elem.rightChild == search_element) {
        prev_elem.rightChild = search_element.rightChild
        search_element.rightChild.parent = prev_elem
      }
    }
    else
      return false
    true
  }

  override def deleteElemByInd(index: Int): Boolean = {
    if (this.root.getWeight() - 1 < index)
      return false

    var search_elem = this.root
    var index_elem = search_elem.leftChild.getWeight()

    var check = true
    while (check)
      if (index == index_elem) {
        if (deleteElement(search_elem) == true) {}
        else if (search_elem.leftChild != null && search_elem.rightChild != null) {
          var del_elem = search_elem.rightChild
          while (del_elem.leftChild != null && del_elem.rightChild != null)
            if (del_elem.leftChild != null) {
              del_elem.weight = del_elem.weight - 1
              del_elem = del_elem.leftChild
            }
            else {
              del_elem.weight = del_elem.weight - 1
              del_elem = del_elem.rightChild
            }
          search_elem.value = del_elem.value
          search_elem.weight = search_elem.weight - 1
          deleteElement(del_elem)
        }
        check = false
      }
      else if (index > index_elem) {
        search_elem.weight = search_elem.getWeight() - 1
        search_elem = search_elem.rightChild
        try index_elem += (search_elem.leftChild.weight + 1)
        catch {
          case e: NullPointerException =>
            index_elem += 1
        }
      }
      else {
        search_elem.weight = search_elem.weight - 1
        search_elem = search_elem.leftChild
        //index_elem -= search_elem.right.getWeight() + 1
        try index_elem -= (search_elem.rightChild.weight + 1)
        catch {
          case e: NullPointerException =>
            index_elem -= 1
        }
      }
    true
  }

  override def printTree(): Unit = {
    val globalStack = mutable.Stack[Element]()
    globalStack.push(root)
    var gaps = 32
    var isRowEmpty = false
    val separator = "-----------------------------------------------------------------"
    println(separator) // черта для указания начала нового дерева

    while (isRowEmpty == false) {
      val localStack = mutable.Stack[Element]() // локальный стек для задания потомков элемента
      isRowEmpty = true
      var j = 0
      while (j < gaps) {
        print(' ')
        j += 1
      }
      while (globalStack.isEmpty == false) { // покуда в общем стеке есть элементы
        val temp = globalStack.pop.asInstanceOf[Element] // берем следующий, при этом удаляя его из стека
        if (temp != null) {
          print(temp.value + ":" + temp.weight) // выводим его значение в консоли
          localStack.push(temp.leftChild) // соохраняем в локальный стек, наследники текущего элемента
          localStack.push(temp.rightChild)
          if (temp.leftChild != null || temp.rightChild != null) isRowEmpty = false
        }
        else {
          print("__") // - если элемент пустой

          localStack.push(null)
          localStack.push(null)
        }
        var j = 0
        while (j < gaps * 2 - 2) {
          print(' ')
          j += 1
        }
      }
      println()
      gaps /= 2 // при переходе на следующий уровень расстояние между элементами каждый раз уменьшается

      while (localStack.isEmpty == false)
        globalStack.push(localStack.pop) // перемещаем все элементы из локального стека в глобальный
    }
    println(separator) // подводим черту

  }

  private def createVine(): Unit = {
    var grandParent = Element(root.value)
    grandParent = null
    var parent = root
    var leftChild = Element(root.value)
    while (null != parent) {
      leftChild = parent.leftChild
      if (null != leftChild) {
        grandParent = rotateRight(grandParent, parent, leftChild)
        parent = leftChild
      }
      else {
        grandParent = parent
        parent = parent.rightChild
      }
    }
  }

  private def rotateRight(grandParent: Element, parent: Element, leftChild: Element) = {
    if (null != grandParent) grandParent.rightChild = leftChild
    else this.root = leftChild
    parent.leftChild = leftChild.rightChild
    leftChild.rightChild = parent
    grandParent
  }

  private def rotateLeft(grandParent: Element, parent: Element, rightChild: Element): Unit = {
    if (null != grandParent)
      grandParent.rightChild = (rightChild)
    else
      setRoot(rightChild)
    parent.rightChild = rightChild.leftChild
    rightChild.leftChild = parent
  }

  private def makeRotations(bounds: Int): Unit = {
    var grandParent = Element(root.value)
    grandParent = null
    var parent = root
    var child = root.rightChild
    var bound = bounds

    while (bound > 0) {
      try
        if (null != child) {
          rotateLeft(grandParent, parent, child)
          grandParent = child
          parent = grandParent.rightChild
          child = parent.rightChild
        }
        else
          bound = -1
      catch {
        case convenient: NullPointerException =>
          bound = -1
      }

      bound -= 1
    }
  }

  def iterator(): Iterator[AnyRef] = new Iterator[AnyRef] {
    var counter = 0
    var buf: Element = root

    def hasNext: Boolean = return this.counter < root.getWeight

    def next:AnyRef = {
      if (counter != 0) {
        counter += 1;
        buf = findElemByInd(counter - 1)
      }
      buf.value
    }
  }

  override def getStringTree: Array[String] = {
    val list = new Array[String](size)
    System.out.print(getSize)
    val globalStack = new mutable.Stack[Element] // общий стек для значений дерева
    globalStack push root
    var gaps = 32 // начальное значение расстояния между элементами
    var isRowEmpty = false
    var i = 0
    while ( {
      isRowEmpty == false
    }) {
      val localStack = new mutable.Stack[Element] // локальный стек для задания потомков элемента
      isRowEmpty = true
      for (j <- 0 until gaps) {
        while ( {
          globalStack.isEmpty == false
        }) { // покуда в общем стеке есть элементы
          val temp = globalStack.pop.asInstanceOf[Element] // берем следующий, при этом удаляя его из стека
          if (temp != null) {
            list(i) = temp.getValue.toString
            i += 1
            localStack push temp.getLeftChild // соохраняем в локальный стек, наследники текущего элемента

            localStack push temp.getRightChild
            if (temp.getLeftChild != null || temp.getRightChild != null) isRowEmpty = false
          }
          else {
            localStack push null
            localStack push null
          }
        }
      }
      gaps /= 2 // при переходе на следующий уровень расстояние между элементами каждый раз уменьшается

      while ( {
        localStack.isEmpty == false
      }) globalStack push localStack.pop // перемещаем все элементы из локального стека в глобальный
    }
    list
  }

  override def forEach(a: Action[AnyRef]): Unit = {
    if (this.root.getWeight > 0) {
      var counter = 0
      var node = findElemByInd(counter)
      do {
        a.toDo(node.value)
        counter += 1
        node = findElemByInd(counter)
      } while (node != null)
    }
    else println("Нет элементов в массиве")
  }

  def clear(): Unit = {
    val globalStack = new mutable.Stack[Element] // общий стек для значений дерева
    globalStack push root
    var gaps = 32 // начальное значение расстояния между элементами
    var isRowEmpty = false
    while ( {
      isRowEmpty == false
    }) {
      val localStack = new mutable.Stack[Element] // локальный стек для задания потомков элемента
      isRowEmpty = true
      for (j <- 0 until gaps) {
        while ( {
          globalStack.isEmpty == false
        }) { // покуда в общем стеке есть элементы
          val temp = globalStack.pop.asInstanceOf[Element] // берем следующий, при этом удаляя его из стека
          if (temp != null) {
            temp.setValue(null)
            localStack push temp.getLeftChild // соохраняем в локальный стек, наследники текущего элемента

            localStack push temp.getRightChild
            if (temp.getLeftChild != null || temp.getRightChild != null) isRowEmpty = false
          }
          else {
            localStack push null
            localStack push null
          }
        }
      }
      gaps /= 2 // при переходе на следующий уровень расстояние между элементами каждый раз уменьшается

      while ( {
        localStack.isEmpty == false
      }) globalStack push localStack.pop // перемещаем все элементы из локального стека в глобальный
    }
    root = null
    size = 0
  }

  }
