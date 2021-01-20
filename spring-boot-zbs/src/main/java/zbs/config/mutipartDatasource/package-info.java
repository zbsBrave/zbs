/**
 * 多数据源
 * 1，通过dynamic-datasource-spring-boot-starter管理多数据源
 *      1，引入 dynamic-datasource-spring-boot-starter 依赖
 *      2，配置 yml。spring.datasource.dynamic 。参考application.yml
 *      3，在需要切换数据源的类或方法上添加 @DS() 注解
 *
 * 2，自定义实现多数据源
 *
 *      原理：继承AbstractRoutingDataSource。该类的重要属性
 *          defaultTargetDataSource：默认数据源
 *          targetDataSources：所有目标数据源的map
 *          resolvedDataSources：所有目标数据源的map
 *          afterPropertiesSet方法：将targetDataSources里面的key和value包装存入resolvedDataSources
 *          determineCurrentLookupKey方法：该方法返回一个key，可以通过该key从resolvedDataSources中取出datasource
 *      1，编写MyAbstractRoutingDataSource 继承AbstractRoutingDataSource，实现 determineCurrentLookupKey 方法
 *          通过在该方法中动态的返回key来实现多数据源的效果。比如将key保存在ThreadLocal中，在该方法使用threadLocal.get()动态返回key
 *      2，将我们自己的MyAbstractRoutingDataSource加入spring容器，同时设置：
 *          setDefaultTargetDataSource() 设置默认数据源
 *          setTargetDataSources() 设置目标数据源的map<key,datasource>
 *      3，动态改变key。
 *          以下是比较常见的通过aop动态改变key的流程：
 *          3.1 方法执行前，通过aop，在设置threadLocal中的key(这个key可以通过在方法上自定义注解得到)
 *          3.2 方法执行中，abstractRoutingDataSource.determineCurrentLookupKey()通过threadLocal获取到上述设置的key，进而达到切换数据源的效果
 *          3.3 方法执行后，通过aop，在方法执行后删除threadLocal中的key
 *
 *       缺点：
 *          1，无法在运行时动态改变数据源。因为resolvedDataSources是一个私有属性，我们无法修改这个map<key,datasource>
 *             可以参考dynamic-datasource-spring-boot-starter框架中的设计，它是重写了AbstractRoutingDataSource类。
 *          2，事务。
 * @author zhangbaisen
 * @since 2021/1/12
 */
package zbs.config.mutipartDatasource;