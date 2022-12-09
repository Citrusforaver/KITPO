object Main2 {
  def main(args: Array[String]): Unit = {
    val factory = new MyFactory
    val userType: UserType = factory.getBuilderByName("Int")
    val tree: TreeScala = new TreeScala
    val gui = new GUI(tree)
    System.out.println("------ Добавить ------")
    tree.insertElement(userType.create(), userType.getTypeComparator())
    
    tree.insertElement(userType.create(), userType.getTypeComparator())
    
    tree.insertElement(userType.create(), userType.getTypeComparator())
    
    tree.insertElement(userType.create(), userType.getTypeComparator())
    tree.printTree()
  }
}
