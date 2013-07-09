/*
 * cn.touch.db.component.NativeQueryTemplate.java
 * Mar 7, 2012 
 */
package cn.touch.db.component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.ResultSetHandler;

import cn.touch.db.Db;

/**
 * Mar 7, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public abstract class NativeQueryTemplate {
    protected <T extends Object> T nativeQueryObject(final ObjectProcessor<T> processor, String sql, Object... params) {
        try {
            T obj = db().getQueryRunner().query(sql, new ResultSetHandler<T>() {
                @Override
                public T handle(ResultSet rs) throws SQLException {
                    if (rs.next()) {
                        return processor.invoke(rs);
                    }
                    return null;
                }
            }, params);
            return obj;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected <T extends Object> List<T> nativeQueryList(final ObjectProcessor<T> processor, String sql,
            Object... params) {
        try {
            List<T> list = db().getQueryRunner().query(sql, new ResultSetHandler<List<T>>() {
                @Override
                public List<T> handle(ResultSet rs) throws SQLException {
                    List<T> l = new ArrayList<T>();
                    while (rs.next()) {
                        l.add(processor.invoke(rs));
                    }
                    return l;
                }
            }, params);

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Db db();
}
