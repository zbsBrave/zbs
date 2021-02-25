package features;

import java.util.Optional;

/**
 * Optional 是一个包装类。类中包装的对象可以为 NULL->Optional.of() ,或 非NULL->Optional.ofNullable()。
 *      public T orElse(T var1)：value 不为 NULL 则返回 value ；为 NULL 返回 var1。
 *      public T orElseGet(Supplier<? extends T> var1)：value 不为 NULL 则返回 value ；为 NULL 返回 var1.get()。
 *      public <X extends Throwable> T orElseThrow(Supplier<? extends X> var1) throws X：value 不为 NULL 则返回 value ；为 NULL 执行 var1。
 * @author zhangbaisen
 */
public class OptionalFeature {
    public String name = "default";
    public static void main(String[] args) throws Exception {
        OptionalFeature val = null;

        Optional.ofNullable(val).orElse( new OptionalFeature());//val 不为 NULL 则返回 val ；为 NULL 返回 new OptionalFeature()
        Optional.ofNullable(val).orElseGet(OptionalFeature::new);//val 不为 NULL 则返回 val ；为 NULL 返回 supplier.get()
        //Optional.ofNullable(val).orElseThrow(Exception::new);//val 不为 NULL 则返回 val ；为 NULL 返回 异常
        //Optional.ofNullable(val).orElseThrow( () -> new Exception("val=null") );

        //map 和 flatMap 最大的区别在于：flatMap的参数是 Function<? super T, Optional>，而map是Function<? super T, ? extends U>
        Optional.ofNullable(val).map(a -> a.getName());
        Optional.ofNullable(val).flatMap(a -> a.getOptionalName());
        Optional<String> s = Optional.ofNullable(val).map(OptionalFeature::getName);
        
        //最佳实践: 如果val != null，就返回val.getName()；否则返回null
        String name = Optional.ofNullable(val).map(OptionalFeature::getName).orElse(null);
        System.out.println(name);

    }
    
    
    public String getName(){return this.name;}
    public Optional<String> getOptionalName(){return Optional.ofNullable(this.name);}
}
