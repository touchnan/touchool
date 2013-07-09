/*
 * cn.touch.db.Db.java
 * Sep 15, 2011 
 */
package cn.touch.db;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

/**
 * Sep 15, 2011
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public interface Db {
    /**
     * 得到QueryRunner
     * 
     * @return
     */
    QueryRunner getQueryRunner();

    /**
     * 执行sql语句
     * 
     * @param sql
     *            sql语句
     * @param params
     *            参数数组
     * @return 受影响的行数
     */
    int update(String sql, Object... params);

    /**
     * 执行批量sql语句
     * 
     * @param sql
     *            sql语句
     * @param params
     *            二维参数数组
     * @return 受影响的行数的数组
     */
    int[] batchUpdate(String sql, Object[][] params);

    /**
     * 执行查询，将每行的结果保存到一个Map对象中，然后将所有Map对象保存到List中
     * 
     * @param sql
     *            sql语句
     * @param params
     *            参数数组
     * @return 查询结果
     */
    List<Map<String, Object>> find(String sql, Object... params);

    /**
     * 执行查询，将每行的结果保存到Bean中，然后将所有Bean保存到List中
     * 
     * @param <T>
     *            泛型
     * @param entityClass
     *            类名
     * @param sql
     *            sql语句
     * @param params
     *            参数数组
     * @return 查询结果
     */
    <T> List<T> find(Class<T> entityClass, String sql, Object... params);

    /**
     * 查询出结果集中的第一条记录，并封装成对象
     * 
     * @param <T>
     *            泛型
     * @param entityClass
     *            类名
     * @param sql
     *            sql语句
     * @param params
     *            参数数组
     * @return 对象
     */
    <T> T findFirst(Class<T> entityClass, String sql, Object... params);

    /**
     * 查询出结果集中的第一条记录，并封装成Map对象
     * 
     * @param sql
     *            sql语句
     * @param params
     *            参数数组
     * @return 封装为Map的对象
     */
    Map<String, Object> findFirst(String sql, Object... params);

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * 
     * @param sql
     *            sql语句
     * @param columnName
     *            列名
     * @param params
     *            参数数组
     * @return 结果对象
     */
    Object findBy(String sql, String columnName, Object... params);

    /**
     * 查询某一条记录，并将指定列的数据转换为Object
     * 
     * @param sql
     *            sql语句
     * @param columnIndex
     *            列索引
     * @param params
     *            参数数组
     * @return 结果对象
     */
    Object findBy(String sql, int columnIndex, Object... params);
}
