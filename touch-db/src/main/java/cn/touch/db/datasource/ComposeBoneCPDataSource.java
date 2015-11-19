/*
 * cn.touch.db.datasource.ComposeBoneCPDataSource.java
 * Feb 11, 2012 
 */
package cn.touch.db.datasource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Feb 11, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class ComposeBoneCPDataSource extends BoneCPDataSource {
    private static final long serialVersionUID = 249824529705135115L;

    private Map<Object, BoneCPDataSource> dbs = new HashMap<Object, BoneCPDataSource>();
    private Map<Object, DataSourceConfig> cfigs = new HashMap<Object, DataSourceConfig>();
    private static ThreadLocal<Object> current = new ThreadLocal<Object>();

    public ComposeBoneCPDataSource add(DataSourceConfig dsCfig) {
        return add(dsCfig, newDataSourceInstance(dsCfig));
    }

    public ComposeBoneCPDataSource add(DataSourceConfig dsCfig, BoneCPDataSource ds) {
        remove(dsCfig.getKey());
        cfigs.put(dsCfig.getKey(), dsCfig);
        dbs.put(dsCfig.getKey(), ds);
        return this;
    }

    public ComposeBoneCPDataSource remove(DataSourceConfig dsCfig) {
        return remove(dsCfig.getKey());
    }

    public ComposeBoneCPDataSource remove(Object key) {
        cfigs.remove(key);
        BoneCPDataSource b = dbs.get(key);
        if (b != null)
            b.close();
        dbs.remove(key);
        return this;
    }

    public ComposeBoneCPDataSource clear() {
        cfigs.clear();
        dbs.clear();
        return this;
    }

    public static void setIdentity(Object identity) {
        current.set(identity);
    }

    protected BoneCPDataSource getDataSourceTarget() {
        return getDataSourceTarget(current.get());
    }

    protected BoneCPDataSource getDataSourceTarget(Object identity) {
        if (identity == null) {
            return this;
        }
        BoneCPDataSource ds = dbs.get(identity);
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
     * @see com.jolbox.bonecp.BoneCPDataSource#getConnection()
     */
    @Override
    public Connection getConnection() throws SQLException {
        BoneCPDataSource ds = getDataSourceTarget();
        if (this == ds) {
            return super.getConnection();
        }
        return ds.getConnection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jolbox.bonecp.BoneCPDataSource#getConnection(java.lang.String,
     * java.lang.String)
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        BoneCPDataSource ds = getDataSourceTarget();
        if (this == ds) {
            return super.getConnection();
        }
        return ds.getConnection(username, password);
    }

    /**
     * @param dsCfig
     * @return
     */
    protected BoneCPDataSource newDataSourceInstance(DataSourceConfig dsCfig) {
        try {
            BoneCPConfig boneCfig = this.getConfig().clone();
            boneCfig.setUsername(dsCfig.getUserName());
            boneCfig.setPassword(dsCfig.getPasswd());
            boneCfig.setJdbcUrl(dsCfig.getUrl());
            BoneCPDataSource ds = new BoneCPDataSource(boneCfig);
            ds.setDriverClass(dsCfig.getDriverClassName());
            return ds;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
