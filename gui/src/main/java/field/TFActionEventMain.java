package field;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClassName: TFActionEventMain <br/>
 * Description: TextField 类，用来创建文本框对象，敲回车触发事件<br/>
 * date: 2019/8/17 0:05<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class TFActionEventMain {
    public static void main(String[] args) {
        new TFFrame();
    }


    private static class TFFrame extends Frame {
        TFFrame() {
            TextField tf = new TextField();
            //将指定的组件附加到此容器的末尾。
            add(tf);
            //添加指定的动作侦听器以从此文本字段接收动作事件。 如果l为空，则不会抛出任何异常，也不会执行任何操作。
            tf.addActionListener(new TFActionListenner());
            // 使此窗口的大小适合其子组件的首选大小和布局。 如果尺寸小于上一次调用setMinimumSize方法规定的最小尺寸，窗口的宽度和高度将自动放大。
            // 如果窗口和/或其所有者不可显示，则在计算优选尺寸之前，它们都可显示。 窗口在其大小计算后验证。
            pack();
            setVisible(true);
        }

    }

    private static class TFActionListenner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TextField tf = (TextField) e.getSource();
            System.out.println(tf.getText());
            tf.setText("");
        }
    }
}
