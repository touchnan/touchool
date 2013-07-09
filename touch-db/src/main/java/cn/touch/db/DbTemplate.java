/*
 * cn.touch.db.DbTemplate.java
 * Sep 15, 2011 
 */
package cn.touch.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.nutz.lang.Lang;

/**
 * Sep 15, 2011
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class DbTemplate implements Db {

    // private static final Log LOG = Logs.getLog(DbTemplate.class);

    private QueryRunner queryRunner;

    // private DataSource dataSource;
    /**
     * @param dataSource
     *            数据源
     */
    public DbTemplate(DataSource dataSource) {
        // this.dataSource = dataSource;
        this.queryRunner = new QueryRunner(dataSource);
    }

    // private DataSource dataSource;
    // private QueryRunner queryRunner;
    // public void setDataSource(BasicDataSource dataSource) {
    // this.dataSource = dataSource;
    // }

    /**
     * {@inheritDoc}
     */
    public QueryRunner getQueryRunner() {
        return queryRunner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(String sql, Object... params) {
        // queryRunner = new QueryRunner(dataSource);
        int affectedRows = 0;
        try {
            if (params == null) {
                affectedRows = queryRunner.update(sql);
            } else {
                affectedRows = queryRunner.update(sql, params);
            }
        } catch (SQLException e) {
            throw Lang.wrapThrow(e, "Error occured while attempting to update data");
        }
        return affectedRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] batchUpdate(String sql, Object[][] params) {
        // queryRunner = new QueryRunner(dataSource);
        int[] affectedRows = new int[0];
        try {
            affectedRows = queryRunner.batch(sql, params);
        } catch (SQLException e) {
            throw Lang.wrapThrow(e, "Error occured while attempting to batch update data");
        }
        return affectedRows;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Map<String, Object>> find(String sql, Object... params) {
        // queryRunner = new QueryRunner(dataSource);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            if (params == null) {
                list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler());
            } else {
                list = (List<Map<String, Object>>) queryRunner.query(sql, new MapListHandler(), params);
            }
        } catch (SQLException e) {
            throw Lang.wrapThrow(e, "Error occured while attempting to query data");
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> List<T> find(Class<T> entityClass, String sql, Object... params) {
        // queryRunner = new QueryRunner(dataSource);
        List<T> list = new ArrayList<T>();
        try {
            if (params == null) {
                list = (List<T>) queryRunner.query(sql, new BeanListHandler<T>(entityClass));
            } else {
                list = (List<T>) queryRunner.query(sql, new BeanListHandler<T>(entityClass), params);
            }
        } catch (SQLException e) {
            throw Lang.wrapThrow(e, "Error occured while attempting to query data");
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T findFirst(Class<T> entityClass, String sql, Object... params) {
        // queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new BeanHandler<T>(entityClass));
            } else {
                object = queryRunner.query(sql, new BeanHandler<T>(entityClass), params);
            }
        } catch (SQLException e) {
            throw Lang.wrapThrow(e, "Error occured while attempting to query data");
            // LOG.error("Error occured while attempting to query data", e);
        }
        return (T) object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Object> findFirst(String sql, Object... params) {
        // queryRunner = new QueryRunner(dataSource);
        Map<String, Object> map = null;
        try {
            if (params == null) {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler());
            } else {
                map = (Map<String, Object>) queryRunner.query(sql, new MapHandler(), params);
            }
        } catch (SQLException e) {
            throw Lang.wrapThrow(e, "Error occured while attempting to query data");
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object findBy(String sql, String columnName, Object... params) {
        // queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler(columnName));
            } else {
                object = queryRunner.query(sql, new ScalarHandler(columnName), params);
            }
        } catch (SQLException e) {
            throw Lang.wrapThrow(e, "Error occured while attempting to query data");
        }
        return object;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object findBy(String sql, int columnIndex, Object... params) {
        // queryRunner = new QueryRunner(dataSource);
        Object object = null;
        try {
            if (params == null) {
                object = queryRunner.query(sql, new ScalarHandler(columnIndex));
            } else {
                object = queryRunner.query(sql, new ScalarHandler(columnIndex), params);
            }
        } catch (SQLException e) {
            throw Lang.wrapThrow(e, "Error occured while attempting to query data");
        }
        return object;
    }

}
