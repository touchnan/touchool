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
public class PageSQLQuery {
    private String pageSql;
    private Object[] pageParams;

    private String countSql;
    private Object[] countParams;

    public PageSQLQuery(String pageSql, Object[] pageParams, String countSql,
            Object[] countParams) {
        super();
        this.pageSql = pageSql;
        this.countSql = countSql;
        this.pageParams = pageParams;
        this.countParams = countParams;
    }

    /**
     * @return the pageSql
     */
    public String getPageSql() {
        return pageSql;
    }

    /**
     * @return the pageParams
     */
    public Object[] getPageParams() {
        return pageParams;
    }

    /**
     * @return the countSql
     */
    public String getCountSql() {
        return countSql;
    }

    /**
     * @return the counmtParams
     */
    public Object[] getCountParams() {
        return countParams;
    }
}
