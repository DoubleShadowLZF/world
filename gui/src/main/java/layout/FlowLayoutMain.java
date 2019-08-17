package layout;

import java.awt.*;

/**
 * ClassName: layout.FlowLayoutMain <br/>
 * Description: 布局管理器<br/>
 * date: 2019/8/16 23:52<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class FlowLayoutMain {

    public static void main(String[] args) {
        Frame frame = new Frame("frame flow layout");
        Button b1 = new Button("open");
        Button b2 = new Button("close");
        Button b3 = new Button("ok");

        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 40));
        frame.add(b1);
        frame.add(b2);
        frame.add(b3);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
