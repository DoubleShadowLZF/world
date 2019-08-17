package event;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ClassName: ActionEventMain <br/>
 * Description: 事件模型<br/>
 * date: 2019/8/17 0:01<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class ActionEventMain {
    public static void main(String[] args) {
        Frame frame = new Frame("Test");
        Button b1 = new Button("start");
        Button b2 = new Button("stop");
        EventMonitor em = new EventMonitor();
        b1.addActionListener(em);
        b2.addActionListener(em);
        b2.setActionCommand("game over");
        frame.add(b1, "North");
        frame.add(b2, "Center");
        frame.pack();
        frame.setVisible(true);
    }

    private static class EventMonitor implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("进来了" + e.getActionCommand());
        }
    }
}
