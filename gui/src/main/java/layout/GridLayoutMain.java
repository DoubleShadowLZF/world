package layout;

import java.awt.*;

/**
 * ClassName: layout.GridLayoutMain <br/>
 * Description: <br/>
 * date: 2019/8/16 23:57<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class GridLayoutMain {
    public static void main(String[] args) {
        Frame frame = new Frame("GridLayout example");
        Button b1 = new Button("Button");
        Button b2 = new Button("Button");
        Button b3 = new Button("Button");
        Button b4 = new Button("Button");
        Button b5 = new Button("Button");
        Button b6 = new Button("Button");
        frame.setLayout(new GridLayout(3, 2));
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.add(b4);
        frame.add(b5);
        frame.add(b6);
        frame.pack();
        frame.setVisible(true);
    }
}
