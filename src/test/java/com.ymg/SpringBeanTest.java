package com.ymg;

import com.ymg.bean.DisplayJobBean;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yumg on 2017/7/16.
 */
public class SpringBeanTest {

    @Test
    public void testSimpleLoand(){
//        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("ActiveMQ.xml"));
        BeanFactory bf = new XmlBeanFactory(new ClassPathResource("job/testbean1.xml"));
        DisplayJobBean displayJobBean=  bf.getBean(DisplayJobBean.class);
        System.out.println(displayJobBean);
    }

    @Test
    public void testApp(){
        ApplicationContext bf = new ClassPathXmlApplicationContext("job/testbean1.xml");

        DisplayJobBean bean = bf.getBean(DisplayJobBean.class);
        System.out.println(bean.getName());

    }


    @Test
    public void testFiles() throws FileNotFoundException {
        File files = ResourceUtils.getFile("classpath:job/");
        List<DisplayJobBean> list = new ArrayList<DisplayJobBean>();
        if (files.isDirectory()){
            String[] fileStrs = files.list();
            for (int i=0;i<fileStrs.length;i++){
                ApplicationContext bf = new ClassPathXmlApplicationContext("job/"+fileStrs[i]);
                DisplayJobBean bean = bf.getBean(DisplayJobBean.class);
                list.add(bean);
            }
        }

        for (DisplayJobBean displayJobBean:list){
            System.out.println(displayJobBean);

            System.out.println(displayJobBean.getPersonName().get(0));
        }

    }



}
