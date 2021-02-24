package features;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 方法引用，双冒号
 *    要点：双冒号写法本质就是引用方法，所以应该函数式接口来接收，引用方法的参数和函数式接口的参数是相同的
 *    https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
 * @author zhangbaisen
 */
public class MethodReferences {
    private String name;

    public static void main(String[] args) {
        List<String> list = Arrays.asList("aaa","bbb","ccc");
        //1，静态方法调用
        list.forEach( MethodReferences::printStatic);
        
        //2，实例方法调用
        // this::print , super::print
        list.forEach(new MethodReferences()::print);
        
        //3，构造器方法调用。注意：双冒号写法其实返回的都是函数式接口，
        Supplier<MethodReferences> supplier = MethodReferences::new;//这里是无参构造方法，相当于 new MethodReferences()
        System.out.println(supplier.get().name);
        
        Function<String,MethodReferences> function = MethodReferences::new;//有参构造方法，相当于 new MethodReferences(String name)
        System.out.println(function.apply("名字").name);
        
        Supplier<HashSet<String>> s1 = HashSet<String>::new;//带泛型的构造方法，相当于 new HashSet<String>()
        
        //4，数组构造方法调用
        Function<Integer,String[]> f1 = String[]::new;//这里相当于new String[integer]
        String[] num = f1.apply(3);//创建一个长度为3的 string数组

    }

    public MethodReferences(){

    }
    public MethodReferences(String name){
        this.name = name;
    }
    public void print(String a){
        System.out.println(a);
    }
    public static void printStatic(String a){
        System.out.println(a);
    }
}
