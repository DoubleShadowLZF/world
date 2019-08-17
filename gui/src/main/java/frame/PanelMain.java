package frame;

import java.awt.*;

/**
 * ClassName: frame.PanelMain <br/>
 * Description: <br/>
 * date: 2019/8/16 21:32<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class PanelMain {
    public static void main(String[] args) {
        Frame frame = new Frame("java with panel");
        //设置此容器的布局管理器
        frame.setLayout(null);
        frame.setBounds(300, 300, 500, 500);
        frame.setBackground(new Color(0, 0, 102));
        //最简单的容器类：面板提供应用程序可以附加任何其他组件（包括其他面板）的空间。
        Panel panel = new Panel(null);
        //移动并调整此组件的大小。
        panel.setBounds(50, 50, 400, 400);
        panel.setBackground(new Color(204, 204, 255));
        frame.add(panel);
        frame.setVisible(true);
    }
}
