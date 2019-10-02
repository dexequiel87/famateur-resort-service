package com.degg.famateur.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	Log LOGGER = LogFactory.getLog(LoggingAspect.class);

	@Pointcut("execution(* com.degg.famateur.service.*.*(..))")
	private void services() {}
	
	@Before("services()")
	private void logMethodCall(JoinPoint joinPoint) {
		StringBuilder methodCall = new StringBuilder("Calling: "  + joinPoint.getSignature().getName() + "(");
		for (int i = 0; i < joinPoint.getArgs().length; i++) {
			Object arg = joinPoint.getArgs()[i];
			methodCall.append(String.valueOf(arg));
			if (i < joinPoint.getArgs().length - 1) {
				methodCall.append(", ");
			}
		}
		methodCall.append(")");
		LOGGER.info(methodCall.toString());
	}
	
	@AfterReturning(
			pointcut = "services()",
			returning = "result"
			)
	private void logMethodReturn(JoinPoint joinPoint, Object result) {
		LOGGER.info(joinPoint.getSignature().getName() + " method returning: " + String.valueOf(result));
	}
}
