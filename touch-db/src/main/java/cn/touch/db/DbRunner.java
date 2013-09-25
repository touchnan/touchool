/*
 */
package cn.touch.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.nutz.lang.Lang;

import cn.touch.db.page.Pagination;
import cn.touch.db.page.parser.PageSQLQuery;
import cn.touch.db.page.parser.SQLParser;
import cn.touch.db.page.parser.SQLFlow;
import cn.touch.db.query.filter.FlexiRuleGroup;

/**
 * Sep 15, 2011
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class DbRunner implements Db {
    protected QueryRunner queryRunner;
    protected SQLParser sqlParser;

    /**
     * @param dataSource
     *            数据源
     */
    public DbRunner(DataSource dataSource) {
        // this.dataSource = dataSource;
        this.queryRunner = new QueryRunner(dataSource);
    }

    /**
     * @param dataSource
     *            数据源
     * @param sqlParser
     *            SQL解析器
     */
    public DbRunner(DataSource dataSource, SQLParser sqlParser) {
        // this.dataSource = dataSource;
        this.queryRunner = new QueryRunner(dataSource);
        this.sqlParser = sqlParser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(String sql, Object... params) {
        try {
            return queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw Lang.wrapThrow(e,
                    "Error occured while attempting to update data");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] batchUpdate(String sql, Object[][] params) {
        try {
            return queryRunner.batch(sql, params);
        } catch (SQLException e) {
            throw Lang.wrapThrow(e,
                    "Error occured while attempting to batch update data");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> find(String sql, Object... params) {
        return (List<Map<String, Object>>) this.query(sql,
                new MapListHandler(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> find(String sql, RowProcessor convert,
            Object... params) {
        return (List<Map<String, Object>>) this.query(sql, new MapListHandler(
                convert), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> find(Class<T> entityClass, String sql, Object... params) {
        return (List<T>) this.query(sql, new BeanListHandler<T>(entityClass),
                params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> find(Class<T> entityClass, String sql,
            RowProcessor convert, Object... params) {
        return (List<T>) this.query(sql, new BeanListHandler<T>(entityClass,
                convert), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T findFirst(Class<T> entityClass, String sql, Object... params) {
        return this.query(sql, new BeanHandler<T>(entityClass), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T findFirst(Class<T> entityClass, String sql,
            RowProcessor convert, Object... params) {
        return this
                .query(sql, new BeanHandler<T>(entityClass, convert), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> findFirst(String sql, Object... params) {
        return (Map<String, Object>) this.query(sql, new MapHandler(), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> findFirst(String sql, RowProcessor convert,
            Object... params) {
        return (Map<String, Object>) this.query(sql, new MapHandler(convert),
                params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object findBy(String sql, String columnName, Object... params) {
        return this.query(sql, new ScalarHandler<Object>(columnName), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object findBy(String sql, int columnIndex, Object... params) {
        return this.query(sql, new ScalarHandler<Object>(columnIndex), params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params) {
        try {
            return this.queryRunner.query(sql, rsh, params);
        } catch (SQLException e) {
            throw Lang.wrapThrow(e,
                    "Error occured while attempting to query data");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pagination findPage(SQLFlow sqlFlow, Pagination page) {
        PageSQLQuery pq = this.sqlParser.createPageSQLQuery(sqlFlow.page(page));
        page.setRows(this.find(pq.getPageSql(), pq.getPageParams()));
        if (page.isAutoCount()) {
            page.setTotal(((Long) this.findBy(pq.getCountSql(), 1,
                    pq.getCountParams())).intValue());
        }
        return page;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pagination findPage(SQLFlow sqlFlow, RowProcessor convert,
            Pagination page) {
        PageSQLQuery pq = this.sqlParser.createPageSQLQuery(sqlFlow.page(page));
        page.setRows(this.find(pq.getPageSql(), convert, pq.getPageParams()));
        if (page.isAutoCount()) {
            page.setTotal(((Long) this.findBy(pq.getCountSql(), 1,
                    pq.getCountParams())).intValue());
        }
        return page;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Pagination findPage(Class<T> entityClass, SQLFlow sqlFlow,
            Pagination page) {
        PageSQLQuery pq = this.sqlParser.createPageSQLQuery(sqlFlow.page(page));
        page.setRows(this.find(entityClass, pq.getPageSql(), pq.getPageParams()));
        if (page.isAutoCount()) {
            page.setTotal(((Long) this.findBy(pq.getCountSql(), 1,
                    pq.getCountParams())).intValue());
        }
        return page;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> Pagination findPage(Class<T> entityClass, SQLFlow sqlFlow,
            RowProcessor convert, Pagination page) {
        PageSQLQuery pq = this.sqlParser.createPageSQLQuery(sqlFlow.page(page));
        page.setRows(this.find(entityClass, pq.getPageSql(), convert,
                pq.getPageParams()));
        if (page.isAutoCount()) {
            page.setTotal(((Long) this.findBy(pq.getCountSql(), 1,
                    pq.getCountParams())).intValue());
        }
        return page;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> find(String sql, FlexiRuleGroup ruleGroup) {
        StringBuffer sbf = new StringBuffer(sql);
        List<Object> params = new ArrayList<Object>();
        ruleGroup.filterAnd(sbf, params);
        return this.find(sbf.toString(), params.toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> find(String sql, RowProcessor convert,
            FlexiRuleGroup ruleGroup) {
        StringBuffer sbf = new StringBuffer(sql);
        List<Object> params = new ArrayList<Object>();
        ruleGroup.filterAnd(sbf, params);
        return this.find(sbf.toString(), convert, params.toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> find(Class<T> entityClass, String sql,
            FlexiRuleGroup ruleGroup) {
        StringBuffer sbf = new StringBuffer(sql);
        List<Object> params = new ArrayList<Object>();
        ruleGroup.filterAnd(sbf, params);
        return this.find(entityClass, sbf.toString(), params.toArray());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> find(Class<T> entityClass, String sql,
            RowProcessor convert, FlexiRuleGroup ruleGroup) {
        StringBuffer sbf = new StringBuffer(sql);
        List<Object> params = new ArrayList<Object>();
        ruleGroup.filterAnd(sbf, params);
        return this.find(entityClass, sbf.toString(), convert, params.toArray());
    }
}
