package com.ymg.groovy;

import com.ymg.framework.groovy.GroovyLoaderHelper;
import com.ymg.framework.groovy.model.User;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.net.URISyntaxException;

/**
 * @author yumg
 * @create 2018/02/09
 * @desc
 **/
public class GroovyTest {
    public static void main(String[] args) throws URISyntaxException {
        File scriptFile = new File(GroovyTest.class.getClassLoader().getResource("YumgTest.groovy").toURI());
        User user = new User();
        user.setName("按时打算");
        user.setPassword("阿萨德");
        user.setEmail("阿萨德");
        try {
            Object yumg = GroovyLoaderHelper.getInstance().executeMethod("yumg_test",scriptFile,null,user,"aasda");
            System.out.println();
            Object result = GroovyLoaderHelper.getInstance().executeMethod("yumg_test",scriptFile,null,user,"aasda");
            System.out.println();
            System.out.println("====yumg====="+yumg);
            System.out.println();
            System.out.println("====result====="+result);
        } catch (UnsupportedDataTypeException e) {
            System.out.println("asdsadas"+e);
            e.printStackTrace();
        }
    }
}
