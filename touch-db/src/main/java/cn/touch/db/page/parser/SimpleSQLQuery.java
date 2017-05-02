/**
 * 
 */
package cn.touch.db.page.parser;

/**
 * Aug 19, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class SimpleSQLQuery {
    private String sql;
    private Object[] params;

    public SimpleSQLQuery(String sql, Object[] params) {
        super();
        this.sql = sql;
        this.params = params;
    }

    /**
     * @return the sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @return the params
     */
    public Object[] getParams() {
        return params;
    }
}
