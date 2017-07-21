package com.ymg.aspectj;

/**
 * Created by yumg on 2017/7/18.
 */
public class TestBean {
    private String testStr = "testStr";

    public String getTestStr() {
        return testStr;
    }

    public void setTestStr(String testStr) {
        this.testStr = testStr;
    }

    public void test(){
        System.out.println("this is a test");
    }

}
