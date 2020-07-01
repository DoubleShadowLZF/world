package org.dbl.springstudy;

import org.dbl.springstudy.bean.MyTestBean;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import static org.junit.Assert.assertEquals;

/**
 * ClassName: BeanFactoryTest <br/>
 * Description: <br/>
 * date: 2020/4/3 21:56<br/>
 *
 * @author Double <br/>
 * @since JDK 1.8
 */
public class BeanFactoryTest {

    @Test
    public void testSimpleLoad() {
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("SpringBeanFactory.xml"));
        MyTestBean bean = (MyTestBean) bf.getBean("myTestBean");
        assertEquals("testStr", bean.getTestStr());
    }


}
