package field;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClassName: TFPasswordMain <br/>
 * Description: 输入密码 <br/>
 * date: 2019/8/17 0:12<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class TFPasswordMain {
    public static void main(String[] args) {
        new TFFrame();
    }

    private static class TFFrame extends Frame {
        TFFrame() {
            TextField tf = new TextField();
            add(tf);
            //密码不显示
            tf.setEchoChar('*');
            tf.addActionListener(new TFActionListener());
            pack();
            setVisible(true);
        }

        private class TFActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextField tf = (TextField) e.getSource();
                System.out.println(tf.getText());
                tf.setText("");
            }
        }
    }
}
