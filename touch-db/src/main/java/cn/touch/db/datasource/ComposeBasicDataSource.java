/*
 * cn.touch.db.datasource.ComposeBasicDataSource.java
 * Feb 11, 2012 
 */
package cn.touch.db.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.nutz.log.Logs;

/**
 * Feb 11, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class ComposeBasicDataSource extends BasicDataSource {

    private Map<Object, BasicDataSource> dbs = new HashMap<Object, BasicDataSource>();
    private Map<Object, DataSourceConfig> cfigs = new HashMap<Object, DataSourceConfig>();
    private static ThreadLocal<Object> current = new ThreadLocal<Object>();

    public ComposeBasicDataSource add(DataSourceConfig dsCfig) {
        return add(dsCfig, newDataSourceInstance(dsCfig));
    }

    public ComposeBasicDataSource add(DataSourceConfig dsCfig, BasicDataSource ds) {
        remove(dsCfig.getKey());
        cfigs.put(dsCfig.getKey(), dsCfig);
        dbs.put(dsCfig.getKey(), ds);
        return this;
    }

    public ComposeBasicDataSource remove(DataSourceConfig dsCfig) {
        return remove(dsCfig.getKey());
    }

    public ComposeBasicDataSource remove(Object key) {
        cfigs.remove(key);
        BasicDataSource b = dbs.get(key);
        if (b != null)
            try {
                b.close();
            } catch (SQLException e) {
                Logs.getLog(ComposeBasicDataSource.class).error(e);
            }
        dbs.remove(key);
        return this;
    }

    public ComposeBasicDataSource clear() {
        cfigs.clear();
        dbs.clear();
        return this;
    }
    
    public static void setIdentity(Object identity) {
        current.set(identity);
    }

    protected BasicDataSource getDataSourceTarget() {
        return getDataSourceTarget(current.get());
    }

    protected BasicDataSource getDataSourceTarget(Object identity) {
        if (identity == null) {
            return this;
        }
        BasicDataSource ds = dbs.get(identity);
        if (ds == null) {
            DataSourceConfig cfig = cfigs.get(identity);
            if (cfig != null) {
                ds = newDataSourceInstance(cfig);
                dbs.put(identity, ds);
            } else {// 找不到匹配的
                return this;
            }
        }
        return ds;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.commons.dbcp.BasicDataSource#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        BasicDataSource ds = getDataSourceTarget();
        if (this == ds) {
            return super.getConnection();
        }
        return ds.getConnection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.apache.commons.dbcp.BasicDataSource#getConnection(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        BasicDataSource ds = getDataSourceTarget();
        if (this == ds) {
            return super.getConnection();
        }
        return ds.getConnection(username, password);
    }

    /**
     * @param cfig
     * @return
     */
    protected BasicDataSource newDataSourceInstance(DataSourceConfig cfig) {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(cfig.getUrl());
        ds.setUsername(cfig.getUserName());
        ds.setDriverClassName(cfig.getDriverClassName());
        ds.setPassword(cfig.getPasswd());
        ds.setDefaultAutoCommit(this.defaultAutoCommit);
        ds.setDefaultCatalog(this.defaultCatalog);
        ds.setDefaultReadOnly(this.defaultReadOnly == null ? false : this.defaultReadOnly.booleanValue());
        ds.setDefaultTransactionIsolation(this.defaultTransactionIsolation);
        ds.setInitialSize(this.initialSize);
        ds.setLogAbandoned(this.getLogAbandoned());
        ds.setMaxActive(this.maxActive);
        ds.setMaxIdle(this.maxIdle);
        ds.setMaxOpenPreparedStatements(this.maxOpenPreparedStatements);
        ds.setMaxWait(this.maxWait);
        ds.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
        ds.setMinIdle(this.minIdle);
        ds.setNumTestsPerEvictionRun(this.numTestsPerEvictionRun);
        ds.setRemoveAbandoned(this.getRemoveAbandoned());
        ds.setRemoveAbandonedTimeout(this.getRemoveAbandonedTimeout());
        ds.setTestOnBorrow(this.getTestOnBorrow());
        ds.setTestOnReturn(this.getTestOnReturn());
        ds.setTestWhileIdle(this.getTestWhileIdle());
        ds.setTimeBetweenEvictionRunsMillis(this.getTimeBetweenEvictionRunsMillis());
        ds.setValidationQuery(this.getValidationQuery());
        return ds;
    }

}
