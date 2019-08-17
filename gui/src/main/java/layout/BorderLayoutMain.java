package layout;

import java.awt.*;

/**
 * ClassName: layout.BorderLayoutMain <br/>
 * Description: <br/>
 * date: 2019/8/16 23:55<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class BorderLayoutMain {

    public static void main(String[] args) {
        Frame frame = new Frame("border layout");
        Button bn = new Button("BN");
        Button bs = new Button("BS");
        Button bw = new Button("BW");
        Button be = new Button("BE");
        Button bc = new Button("BC");
        frame.add(bn, BorderLayout.NORTH);
        frame.add(bs, BorderLayout.SOUTH);
        frame.add(bw, BorderLayout.WEST);
        frame.add(be, BorderLayout.EAST);
        frame.add(bc, BorderLayout.CENTER);

        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}
