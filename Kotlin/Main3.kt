fun main(){

        val factory = MyFactory()
        val userType: UserType = factory.getBuilderByName("Int")
        val tree = TreeKotlin()
        val gui = GUI(tree)
        println("------ Добавить ------")
        tree.insertElement(userType.create(), userType.getTypeComparator())
        tree.insertElement(userType.create(), userType.getTypeComparator())
        tree.insertElement(userType.create(), userType.getTypeComparator())
        tree.insertElement(userType.create(), userType.getTypeComparator())
        tree.insertElement(userType.create(), userType.getTypeComparator())
        tree.printTree()

}