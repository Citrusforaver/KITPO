import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private int width;
    private int height;
    private Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    private ActionMenu actionMenu;

    public GUI (ITree tree) {
        actionMenu = new ActionMenu(tree);
        width = 1220;
        height = 700;
        setVisible(true);
        setLayout(null);
        setFocusable(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(dimension.width/2 - width/2, dimension.height/2 - height/2 , width, height);
        setTitle("Test List");
        add(actionMenu);
    }
}
