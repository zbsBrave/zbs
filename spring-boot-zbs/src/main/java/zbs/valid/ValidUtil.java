package zbs.valid;

import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.stereotype.Component;
import javax.validation.*;
import javax.validation.spi.ValidationProvider;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * Validator 使用了jdk的SPI原理，
 *      默认加载META-INF/services/javax.validation.spi.ValidationProvider接口。具体使用参考buildValidationProviderBySPI方法。
 *      可以通过jdk的api简单的获取Validator： Validation.buildDefaultValidatorFactory().getValidator()
 *          Validation.loadProviders()即是通过SPI来加载ValidationProvider。
 *
 * spring validator 底层使用的是hibernate-validator 的 ValidatorImpl
 *
 * @author zhangbaisen
 * @since 2021/1/15
 */
@Component
public class ValidUtil {
    public static Validator validator;//spring的 validator 实质使用的是hibernate的实现 ValidatorImpl
    public ValidUtil(ValidatorFactory validatorFactory){
        System.out.println(validatorFactory.getClass());//org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
        validator = validatorFactory.getValidator();
    }
    //参考BeanValidationPostProcessor
    public static <T> void valid(T bean, Class<?>... groups){
        Set<ConstraintViolation<T>> result = getValidator().validate(bean, groups);
        if(!result.isEmpty()){
            StringBuilder sb = new StringBuilder("Bean state is invalid: ");
            for(Iterator<ConstraintViolation<T>> it = result.iterator(); it.hasNext() ;){
                ConstraintViolation<T> next = it.next();
                sb.append(next.getPropertyPath()).append(" : ").append(next.getMessage());
                if (it.hasNext()) {
                    sb.append("; ");
                }
            }

            System.out.println(sb.toString());
            throw new BeanInitializationException(sb.toString());
        }
    }
    public static Validator getValidator(){
        if( validator != null) return validator;
        return Validation.buildDefaultValidatorFactory().getValidator();
    }



    /**
     * SPI的使用例子：或者参考Validation.loadProviders()
     *  通过SPI的方式加载 ValidationProvider
     *  SPI原理是：通过 ServiceLoader 加载 META-INF/services下的对应服务接口。
     *  这里加载的是hibernate.validator包下的 META-INF/services/javax.validation.spi.ValidationProvider
     *      即 org.hibernate.validator.HibernateValidator
     */
    public static void useSPIDemo(){
        ServiceLoader<ValidationProvider> providers = ServiceLoader.load(ValidationProvider.class);
        for( Iterator<ValidationProvider> it = providers.iterator() ; it.hasNext() ;){
            ValidationProvider next = it.next();
            System.out.println(next);
        }
    }
}
