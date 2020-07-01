package org.dbl.springstudy;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ClassName: TransactionTest <br/>
 * Description: <br/>
 * date: 2020/4/5 17:02<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
public class TransactionTest {


    @Test
    public void TxTest01() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("SpringTransaction.xml");

    }

}
