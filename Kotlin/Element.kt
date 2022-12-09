class Element {

    var value: Any? = null

    var weight = 0

    var leftChild: Element? = null
    internal var rightChild: Element? = null
    var parent // Родитель узла
            : Element? = null

    fun Element() {
        leftChild = null
        rightChild = null
        parent = null
    }

    @JvmName("setWeight1")
    fun setWeight(weight: Int) {
        this.weight = weight
    }

    @JvmName("getLeftChild1")
    fun getLeftChild(): Element? {
        return leftChild
    }

    @JvmName("setLeftChild1")
    fun setLeftChild(leftChild: Element?) {
        this.leftChild = leftChild
    }

    fun getRightChild(): Element? {
        return rightChild
    }

    fun setRightChild(rightChild: Element?) {
        this.rightChild = rightChild
    }

    override fun toString(): String {
        return "Узел{" +
                "Значение=" + value +
                '}'
    }
}
