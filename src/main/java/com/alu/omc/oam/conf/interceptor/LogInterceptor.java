package com.alu.omc.oam.conf.interceptor;

//import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogInterceptor 
{
	Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
	@Pointcut("execution(* com.alu.omc.oam..*.*(..))")
	public void logPointCut(){}
	
	@Around("logPointCut()")
	public Object logMthod(ProceedingJoinPoint pjp)
	{
		Object target = pjp.getTarget();
		boolean unLogable = target instanceof com.alu.omc.oam.conf.interceptor.intf.UnLogable;
		if(!unLogable)
		{
			logger.debug("****** "+pjp.getSignature()+" start ******");
			logger.debug("Invoker: "+pjp.getTarget());
			Object[] args = pjp.getArgs();
			if(args !=null && args.length != 0)
				logger.debug("Arguments: "+args.toString());
		}
		Object obj = null;  
        try {  
            obj = pjp.proceed();             
        } catch (Throwable e) {  
            logger.error(e.getMessage(),e);  
        }  
        if((!unLogable) && (obj!= null))
        {
        	logger.debug(" Return Value: "+obj.toString());
        	logger.debug("****** "+pjp.getSignature()+" end ******");
        	logger.debug("");
        }
       
        return obj;  
	}
}
