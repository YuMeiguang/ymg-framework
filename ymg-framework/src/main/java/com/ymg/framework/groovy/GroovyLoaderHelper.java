package com.ymg.framework.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.Script;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.runtime.InvokerHelper;

import javax.activation.UnsupportedDataTypeException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yumg
 * @create 2018/02/09
 * @desc
 **/
public class GroovyLoaderHelper {
    private static Log logger = LogFactory.getLog(GroovyLoaderHelper.class);

    private static Map<String, Class> scriptCache = new ConcurrentHashMap();

    private GroovyLoaderHelper() {
    }

    public static GroovyLoaderHelper getInstance() {
        return new GroovyLoaderHelper();
    }

    /**
     * 执行groovy脚本
     * @param params        脚本需要的参数值
     * @param scriptCode    脚本内容
     * @return              脚本执行的结果
     */
    public Object execute(Map<String, Object> params, String scriptCode) throws UnsupportedDataTypeException {
        return getScript(params, scriptCode).run();
    }

    public Object execute(Map<String, Object> params, File scriptFile) throws UnsupportedDataTypeException {
        return getScript(params, scriptFile).run();
    }

    /**
     * 执行脚本中的一个方法
     * @param method        执行的方法名字
     * @param scriptCode    脚本文件
     * @param args          方法的参数，按照顺序
     * @return              fang发返回的结果
     */
    public Object executeMethod(String method, String scriptCode, Map<String, Object> params, Object... args) throws UnsupportedDataTypeException {
        return getScript(params, scriptCode).invokeMethod(method, args);
    }

    public Object executeMethod(String method, File scriptFile, Map<String, Object> params, Object... args) throws UnsupportedDataTypeException {
        return getScript(params, scriptFile).invokeMethod(method, args);
    }

    /**
     * 得到groovy script 对象
     * @param params        脚本所需的参数
     * @param script    脚本源代码
     * @return
     */
    public Script getScript(Map<String, Object> params, Object script) throws UnsupportedDataTypeException {

        Script scriptObject = null;
        String cacheKey = generateKey(script);
        try {

            Binding binding = new Binding();
            if (params!=null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    binding.setVariable(entry.getKey(), entry.getValue());
                }
            }

            Class shell = null;
            if (isCached(cacheKey)) {
                shell = getCaches().get(cacheKey);
            } else {
                shell = cache(cacheKey, script);
            }

            scriptObject = InvokerHelper.createScript(shell, binding);

        } catch (Throwable t) {
            logger.error("groovy script eval error. script: " + script, t);
            return null;
        }

        return scriptObject;
    }


    private boolean isCached(String cacheKey) {
        return scriptCache.containsKey(cacheKey);
    }

    private Map<String, Class> getCaches() {
        return scriptCache;
    }

    private Class cache(String cacheKey, Object script) throws IOException {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class scriptClass = null;
        if (script instanceof File){
            scriptClass = groovyClassLoader.parseClass((File)script);
        } else if (script instanceof String){
            scriptClass = groovyClassLoader.parseClass((String)script);
        } else {
            throw new UnsupportedDataTypeException("only support string or file!");
        }

        scriptCache.put(cacheKey, scriptClass);
        return scriptClass;
    }

    private String generateKey(Object source) throws UnsupportedDataTypeException {
        if (source instanceof String){
            return Md5String((String) source);
        } else if (source instanceof File){
            return Md5File((File) source);
        } else {
            throw new UnsupportedDataTypeException("only support string or file!");
        }
    }

    private String Md5String(String scriptCode) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(scriptCode.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0){
                    i += 256;
                }
                if (i < 16){
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }

            return buf.toString();

        } catch (NoSuchAlgorithmException e) {
            logger.error("error to generic groovy key!", e);
            return null;
        }
    }

    private String Md5File(File file) {
        InputStream inputStream = null;
        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest digest = MessageDigest.getInstance("md5");
            int len = -1;
            byte[] buffer = new byte[1024];//设置输入流的缓存大小 字节
            //将整个文件全部读入到加密器中
            inputStream = new FileInputStream(file);
            while ((len = inputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            //对读入的数据进行加密
            byte[] bytes = digest.digest();
            for (byte b : bytes) {
                // 数byte 类型转换为无符号的整数
                int n = b & 0XFF;
                // 将整数转换为16进制
                String s = Integer.toHexString(n);
                // 如果16进制字符串是一位，那么前面补0
                if (s.length() == 1) {
                    sb.append("0" + s);
                } else {
                    sb.append(s);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("error to generic groovy key!", e);
            return null;
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
