package com.joung45387.EveryAuction.Aop;

import com.joung45387.EveryAuction.Security.Auth.PrincipalDetails;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.lang.reflect.Method;

@Aspect
@Component
public class LoginCheckAop {
    @Pointcut("execution(* com.joung45387.EveryAuction.Controller..*.*(..))")
    private void cut() {}

    @Before("cut()")
    public void before(JoinPoint joinPoint) {

        PrincipalDetails principalDetails = null;
        Model model = null;

        //메서드에 들어가는 매개변수 배열을 읽어옴
        Object[] args = joinPoint.getArgs();
        //매개변수 배열의 종류와 값을 출력
        for(Object obj : args) {
            if(obj instanceof PrincipalDetails){
                principalDetails = (PrincipalDetails) obj;
            } else if (obj instanceof BindingAwareModelMap) {
                model = (Model) obj;
            }
        }
        System.out.println(principalDetails != null);
        if(model!=null){
            model.addAttribute("login", principalDetails != null);
        }
    }
}
