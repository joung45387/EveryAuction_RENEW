package com.joung45387.EveryAuction.Aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoginCheckAop {
    @Pointcut("execution(* com.joung45387.EveryAuction.Controller..*.*(..))")
    private void cut() {}

    @Before("cut()")
    public void before(JoinPoint joinPoint) {

        //메서드에 들어가는 매개변수 배열을 읽어옴
        Object[] args = joinPoint.getArgs();

        //매개변수 배열의 종류와 값을 출력
        for(Object obj : args) {

            if(obj != null){
                System.out.println("type : "+obj.getClass().getSimpleName());
                System.out.println("value : "+obj);
            }
        }
    }
}
