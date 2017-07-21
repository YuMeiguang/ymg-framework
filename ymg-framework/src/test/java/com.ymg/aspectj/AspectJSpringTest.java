package com.ymg.aspectj;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yumg on 2017/7/18.
 */
public class AspectJSpringTest {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("aspectTest.xml");
        TestBean testBean = (TestBean) context.getBean(TestBean.class);
        testBean.test();
//        AopNamespaceHandler


    }
}
