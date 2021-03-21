package zbs.spel;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 这里每次解析都需要创建EvaluationContext，有没有必要？
 * @author zhangbaisen
 */
@ConditionalOnExpression
@Slf4j
public class SpelUtil {
    /** 用于spel表达式解析 */
    private static final SpelExpressionParser parser = new SpelExpressionParser();
    /** 用于获取方法参数定义名字 */
    private static final ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();
    
    public static Object parserSpelByJoinPoint(String spelString,ProceedingJoinPoint joinPoint){
        //1,通过joinPoint获取被注解方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //2,使用spring的DefaultParameterNameDiscoverer获取方法形参名数组
        String[] parameterNames = nameDiscoverer.getParameterNames(method);
        log.info(Arrays.toString(parameterNames));
        //String[] strings = Optional.ofNullable(parameterNames).orElse(new String[0]);

        //3,解析过后的Spring表达式对象
        Expression expression = parser.parseExpression(spelString);
        // 创建表达式上下文对象,可以使用Spring的ApplicationContext容器，或者像下面一样创建一个虚拟容器EvaluationContext
        EvaluationContext context = new StandardEvaluationContext();
        // 通过joinPoint获取被注解方法的形参
        Object[] args = joinPoint.getArgs();
        // 给上下文赋值
        for(int i=0;i<args.length;i++){
            context.setVariable(parameterNames[i],args[i]);
        }

        return expression.getValue(context);
    }
}
