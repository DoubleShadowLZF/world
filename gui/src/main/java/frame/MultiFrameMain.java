package frame;

import java.awt.*;

/**
 * ClassName: frame.MultiFrameMain <br/>
 * Description: 多窗体 main<br/>
 * date: 2019/8/16 21:24<br/>
 *
 * @author Double<br       />
 * @since JDK 1.8
 */
public class MultiFrameMain {

    public static void main(String[] args) {
        MyFrame frame1 = new MyFrame(100, 100, 200, 200, Color.BLUE);
        MyFrame frame2 = new MyFrame(300, 100, 200, 200, Color.yellow);
        MyFrame frame3 = new MyFrame(100, 300, 200, 200, Color.green);
        MyFrame frame4 = new MyFrame(300, 300, 200, 200, Color.yellow);
    }

    private static class MyFrame extends Frame {
        static int id = 0;

        public MyFrame(int x, int y, int w, int h, Color color) {
            super(String.format("MyFrame %s", ++id));
            setVisible(true);
            setLayout(null);
            setBounds(x, y, w, h);
            setBackground(color);
        }
    }
}
