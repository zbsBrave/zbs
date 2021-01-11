package common.util;

import java.math.BigDecimal;

/**
 * 参考 https://www.jianshu.com/p/683b2406342f
 * 1，java.math.BigDecimal定义：
 *      不可变的、任意精度的有符号十进制数。BigDecimal 由任意精度的整数非标度值(unscaledValue)和32位的整数标度(scale)组成。
 * 其值为该数的非标度值乘以10的负scale次幂，即为(unscaledValue * 10-scale)。
 *      与之相关的还有两个类：
 *      1，java.math.MathContext
 *          该对象是封装上下文设置的不可变对象，它描述数字运算符的某些规则，如数据的精度，舍入方式等。
 *      2，java.math.RoundingMode
 *          这是一种枚举类型，定义了很多常用的数据舍入方式。
 *  2，增删改查：
 *      add：加法。
 *          bigDecimal1.add( bigDecimal2 )
 *      subtract：减法。
 *          bigDecimal1.subtract( bigDecimal2 );
 *      multiply：乘法。
 *          bigDecimal1.multiply( bigDecimal2 );
 *      divide：除法。
 *          divide(BigDecimal divisor除数, int scale精确小数位, int roundingMode舍入模式)
 *          舍入模式：
 *              ROUND_UP：舍弃小数位并加1。比如 1.1 => 2
 *              ROUND_DOWN：舍弃小数位。比如 1.1 => 1
 *              ROUND_CEILING：如果 BigDecimal 为正，则舍入行为与 ROUND_UP 相同；如果为负，则舍入行为与 ROUND_DOWN 相同
 *              ROUND_FLOOR：如果 BigDecimal 为正，则舍入行为与 ROUND_DOWN 相同；如果为负，则舍入行为与 ROUND_UP 相同
 *              ROUND_HALF_UP：如果舍弃部分 >= 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同。四舍五入
 *              ROUND_HALF_DOWN：如果舍弃部分 > 0.5，则舍入行为与 ROUND_UP 相同;否则舍入行为与 ROUND_DOWN 相同(五舍六入)
 *              ROUND_HALF_EVEN：
 *              ROUND_UNNECESSARY：
 * 3，注意
 *      该类有多种构造方法，建议使用String构造方式public BigDecimal(String val)。
 *
 * @author zbs
 * @since 2021/1/11
 */
public class BigDecimalUtil {
    /**
     * 计算百分比
     * @param count
     * @param scale 百分比 例如 20%
     * @return 返回值保留2位小数点
     */
    public static BigDecimal compute(BigDecimal count , String scale){
        BigDecimal a = new BigDecimal(scale.replace("%","")).
                divide(new BigDecimal("100"),2,BigDecimal.ROUND_DOWN);//除数100，精确小数位2，舍入模式

        return count.multiply(a).setScale(2,BigDecimal.ROUND_DOWN);
    }

    public static void main(String[] args) {
        System.out.println(compute(new BigDecimal("0.14"),"20%"));
    }
}
