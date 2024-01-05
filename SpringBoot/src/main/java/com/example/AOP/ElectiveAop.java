package com.example.AOP;

import com.example.pojo.Elective;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ElectiveAop {
    
    // @Before("execution(* com.example.service.impl.ElectiveServiceImpl.insert(*))")
    public void addElective(ProceedingJoinPoint joinPoint) throws Exception{
        // joinPoint.getClass();
    }
    
}
