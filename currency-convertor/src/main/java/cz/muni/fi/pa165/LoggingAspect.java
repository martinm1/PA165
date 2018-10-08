/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 *
 * @author martin
 */
@Aspect
public class LoggingAspect {
    @Around("execution(public * cz.muni.fi.pa165.currency..*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {

        System.err.println("Calling method: "
                + joinPoint.getSignature());

        Object result = joinPoint.proceed();

        System.err.println("Method finished: "
                + joinPoint.getSignature());

        return result;
    }
}
