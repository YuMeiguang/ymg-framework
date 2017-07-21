package com.ymg.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * Created by yumg on 2017/7/18.
 */
@Aspect
public class AspectJTest {

    @Pointcut("execution(* *.test(..))")
    public void test(){
        System.out.println("*.test(..)");
    }

    @Before("test()")
    public void beforeTest(){
        System.out.println("beforeTest");
    }

    @After("test()")
    public void afterTest(){
        System.out.println("afterTest");
    }

    @Around("test()")
    public Object arountTest(ProceedingJoinPoint p){
        System.out.println("before1");
        Object o = null;
        try {
            o = p.proceed();

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("after 1");
        return o;
    }




}
