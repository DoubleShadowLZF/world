package frame;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ClassName: frame.FrameMain <br/>
 * Description: 窗口测试demo <br/>
 * date: 2019/8/16 21:10<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class FrameMain {
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setBackground(Color.BLUE);
        frame.setSize(170, 100);
        frame.setLocation(100, 100);
        //是否隐藏
        frame.setVisible(true);
        //设置该框架是否可以由用户调整大小。
        frame.setResizable(true);
        //注册关闭事件
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
