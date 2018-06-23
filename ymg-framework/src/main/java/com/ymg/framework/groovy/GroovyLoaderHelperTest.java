package com.ymg.framework.groovy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yumg
 * @create 2018/02/11
 * @desc
 **/
public class GroovyLoaderHelperTest {

    private static Log logger = LogFactory.getLog(GroovyLoaderHelperTest.class);

    private static Map<String,Class> scriptCache = new ConcurrentHashMap<String,Class>();

    public GroovyLoaderHelperTest() {
    }

    public static GroovyLoaderHelperTest getInstance(){
        return new GroovyLoaderHelperTest();
    }

    public Object excute(Map<String,Object> params,String scriptCode) {

        return null;
    }

}
