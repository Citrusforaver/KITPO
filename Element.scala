case class Element(var value: AnyRef)
{
  var leftChild: Element = null
  var rightChild: Element = null
  var parent: Element = null
  var weight: Int = 0

  def printElement() = println("Значение выбранного узла: " + value);

  def getParent() = this.parent;

  def setParent(element: Element) = this.parent = element;

  def getValue() = this.value;

  def setValue(value: AnyRef): Unit = this.value = value;

  def getWeight() =
  {
    try this.weight
    catch
    {
      case e: NullPointerException =>
        0
    }
  };

  def setWeight(weight: Int) = this.weight = weight;

  def getLeftChild() = this.leftChild;

  def setLeftChild(left: Element) = this.leftChild=left;

  def getRightChild() = this.rightChild;

  def setRightChild(right: Element) = this.rightChild = right;

  override def toString: String = "Узел{" + "Значение=" + value + '}'
}