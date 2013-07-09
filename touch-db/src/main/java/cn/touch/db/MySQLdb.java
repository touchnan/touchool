/*
 * cn.touch.db.MySQLdb.java
 * Sep 15, 2011 
 */
package cn.touch.db;

import javax.sql.DataSource;

/**
 * Sep 15, 2011
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 *
 */
public class MySQLdb extends DbTemplate {

    /**
     * @param dataSource 数据源
     */
    public MySQLdb(DataSource dataSource) {
        super(dataSource);
    }

}
