/**
 * 使用 mybatis的PageHelper 进行分页
 * 1，引入
 * 2，使用
 *      Page page= PageHelper.startPage(startPage, pageSize);
 *      PageHelper.orderBy("id ASC"); ASC是根据id 正向排序，DESC是反向排序
 *      List<User> allUser = userService.getAllUser();//从数据库查询，这里返回的的allUser就已经分页成功了
 *      long total = page.getTotal();//获取查询记录总数，必须位于从数据库查询数据的语句之后，否则不生效
 *
 */
package zbs.page;
