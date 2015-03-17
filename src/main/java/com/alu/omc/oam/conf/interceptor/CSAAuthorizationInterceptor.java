package com.alu.omc.oam.conf.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CSAAuthorizationInterceptor 
{
	@Pointcut("execution(* com.alu.omc.oam..controller.*.*(..))")
	public void anyControllerMethod(){}
	
	@Around("anyControllerMethod()")
	public Object csaAuthentication(ProceedingJoinPoint pjp) throws Throwable
	{
		// FIXME CSA Interface invoke
		// Once the CSA interface is ready, we can start write the CSA intercepter.
		return pjp.proceed();
	}
}
