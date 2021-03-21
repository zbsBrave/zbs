package zbs.spel.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import zbs.spel.SpelUtil;

import java.lang.reflect.Method;

/**
 * 切莫。注意：必须将切面加入spring容器
 * @author zhangbaisen
 */
@Aspect
@Component
@Slf4j
public class SpelAop {
    
    @Around("@annotation(zbs.spel.aop.SpelAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        SpelAnnotation annotation = method.getAnnotation(SpelAnnotation.class);
        //获取注解SpelAnnotation的spel表达式
        String spelString = annotation.value();
        Object o = spelString;
        //如果spelString以#开头，代表它是一个spel表达式，需要计算
        if(!"".equals(spelString)){
            o = SpelUtil.parserSpelByJoinPoint(spelString, joinPoint);
        }
        log.info("{} : {}",spelString,o);
        

        log.info("SpelAop---方法准备执行");
        Object result = joinPoint.proceed();
        log.info("SpelAop---方法执行完成");
        
        return result;
    }
}
