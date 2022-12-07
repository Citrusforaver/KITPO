
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
//import java.security.acl.Group;

public class ActionMenu extends JPanel implements ActionListener {
    private JButton buttonAddElement = new JButton("Add Element");

    private JButton buttonDeleteElement = new JButton("Delete Element");
    private JButton buttonSaveList = new JButton("Serialize List");
    private JButton buttonLoadList = new JButton("Deserialize List");
    private JButton buttonClearList = new JButton("Clear List");
    private JButton buttonCreateList = new JButton("Create List");
    private JTextField textFieldAddElement = new JTextField();
    private JTextField textFieldInsertElement = new JTextField();
    private JTextField textFieldIdInsert = new JTextField();
    private JTextField textFieldIdDelete = new JTextField();
    private JRadioButton radioButton1 = new JRadioButton("Int");
    private JRadioButton radioButton2 = new JRadioButton("Fraction");
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JLabel labelAddElement = new JLabel("value:");

    private JLabel labelidDeleteElement = new JLabel("value:");
    private JList jList = new JList();
    private MyFactory factory = new MyFactory();
    private DefaultListModel<String> model;
    TreeSerialization treeSerialization = new TreeSerialization();
    private ITree tree;
    String type = "Int";
    boolean isSerialize = false;

    public ActionMenu(ITree tree) {
        setLayout(null);

        setBounds(0, 0 , 1220, 700);

        buttonAddElement.addActionListener(this);
        buttonAddElement.setBounds(850, 80, 300, 50);
        buttonAddElement.setFocusable(false);
        buttonAddElement.setEnabled(false);
        buttonAddElement.setFont(new Font("Arial", Font.PLAIN, 20));
        add(buttonAddElement);


        buttonDeleteElement.addActionListener(this);
        buttonDeleteElement.setBounds(850, 210, 300, 50);
        buttonDeleteElement.setFocusable(false);
        buttonDeleteElement.setEnabled(false);
        buttonDeleteElement.setFont(new Font("Arial", Font.PLAIN, 20));
        add(buttonDeleteElement);

        buttonSaveList.addActionListener(this);
        buttonSaveList.setBounds(850, 270, 300, 50);
        buttonSaveList.setFocusable(false);
        buttonSaveList.setEnabled(false);
        buttonSaveList.setFont(new Font("Arial", Font.PLAIN, 20));
        add(buttonSaveList);

        buttonLoadList.addActionListener(this);
        buttonLoadList.setBounds(850, 330, 300, 50);
        buttonLoadList.setFocusable(false);
        buttonLoadList.setEnabled(false);
        buttonLoadList.setFont(new Font("Arial", Font.PLAIN, 20));
        add(buttonLoadList);

        buttonClearList.addActionListener(this);
        buttonClearList.setBounds(850, 390, 300, 50);
        buttonClearList.setFocusable(false);
        buttonClearList.setEnabled(false);
        buttonClearList.setFont(new Font("Arial", Font.PLAIN, 20));
        add(buttonClearList);

        buttonCreateList.addActionListener(this);
        buttonCreateList.setBounds(850, 520, 300, 50);
        buttonCreateList.setFocusable(false);

        buttonCreateList.setFont(new Font("Arial", Font.PLAIN, 20));
        add(buttonCreateList);

        buttonGroup.add(radioButton1);
        buttonGroup.add(radioButton2);

        radioButton1.addActionListener(this);
        radioButton1.setSelected(true);
        radioButton1.setBounds(850, 450, 300, 20);

        radioButton1.setFont(new Font("Arial", Font.PLAIN, 20));
        add(radioButton1);

        radioButton2.addActionListener(this);
        radioButton2.setBounds(850, 480, 300, 20);
        //textFieldIdInsert.setFocusable(false);
        radioButton2.setFont(new Font("Arial", Font.PLAIN, 20));
        add(radioButton2);

        textFieldAddElement.addActionListener(this);
        textFieldAddElement.setBounds(1050, 20, 100, 50);
        //textFieldAddElement.setFocusable(false);
        textFieldAddElement.setFont(new Font("Arial", Font.PLAIN, 15));
        add(textFieldAddElement);



        textFieldIdDelete.addActionListener(this);
        textFieldIdDelete.setBounds(1050, 150, 100, 50);
        //textFieldIdInsert.setFocusable(false);
        textFieldIdDelete.setFont(new Font("Arial", Font.PLAIN, 15));
        add(textFieldIdDelete);

        labelAddElement.setBounds(990, 20, 100, 50);
        //textFieldIdInsert.setFocusable(false);
        labelAddElement.setFont(new Font("Arial", Font.PLAIN, 18));
        add(labelAddElement);



        labelidDeleteElement.setBounds(990, 150, 100, 50);
        //textFieldIdInsert.setFocusable(false);
        labelidDeleteElement.setFont(new Font("Arial", Font.PLAIN, 18));
        add(labelidDeleteElement);

        jList.setBounds(50, 50, 750, 500);
        jList.setFont(new Font("Arial", Font.PLAIN, 25));
        add(jList);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == buttonCreateList) {
            isSerialize = false;
            buttonAddElement.setEnabled(true);
            buttonDeleteElement.setEnabled(true);
            buttonLoadList.setEnabled(false);
            buttonSaveList.setEnabled(true);
            buttonClearList.setEnabled(true);
            if(radioButton1.isSelected()) {
                type = "Int";
            }

            if(radioButton2.isSelected()) {
                type = "Fraction";
            }
            UserType userType = factory.getBuilderByName(type);
            tree = new Tree();

            if(type == "Int"){
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());

            }
            else {
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());
                tree.insertElement(userType.create(), userType.getTypeComparator());

            }


            model = new DefaultListModel<>();

            tree.printTree();
            for (String str: tree.getStringTree()) {
                model.addElement(str);
            }
            jList.setModel(model);

        }

        if(e.getSource() == buttonAddElement) {
           // UserType userType = null;
            UserType userType = factory.getBuilderByName(type);
            tree.insertElement(factory.getBuilderByName(type).parseValue(textFieldAddElement.getText()), userType.getTypeComparator());
            model = new DefaultListModel<>();
            tree.printTree();
            for (String str: tree.getStringTree()) {
                model.addElement(str);
            }
            jList.setModel(model);
        }



        if(e.getSource() == buttonDeleteElement) {
            tree.deleteElemByInd(Integer.parseInt(textFieldIdDelete.getText()));
            model = new DefaultListModel<>();
            tree.printTree();
            for (String str: tree.getStringTree()) {
                model.addElement(str);
            }
            jList.setModel(model);
        }

        if(e.getSource() == buttonSaveList) {
            try {
                treeSerialization.serialize(factory.getBuilderByName(type), tree);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            tree.printTree();
            isSerialize = true;
            buttonLoadList.setEnabled(true);
        }



        if(e.getSource() == buttonLoadList) {
            try {
                tree = treeSerialization.deserialize(factory.getBuilderByName(type), tree);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            model = new DefaultListModel<>();
            tree.printTree();
            for (String str: tree.getStringTree()) {
                model.addElement(str);
            }
            jList.setModel(model);
        }


        if(e.getSource() == buttonClearList) {
            tree.clear();
            model = new DefaultListModel<>();
            tree.printTree();
            for (String str: tree.getStringTree()) {
                model.addElement(str);
            }
            jList.setModel(model);
        }
    }

}
