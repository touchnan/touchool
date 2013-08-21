/**
 * 
 */
package cn.touch.db.page.parser;

import java.util.List;

import cn.touch.db.page.Pagination;

/**
 * Aug 19, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class MysqlSQLParser extends BaseSQLParser {

    /* (non-Javadoc)
     * @see cn.touch.db.page.parser.SQLParser#createPageSQLQuery(cn.touch.db.page.parser.SQLFlow)
     */
    @Override
    public PageSQLQuery createPageSQLQuery(SQLFlow sqlFlow) {
        StringBuffer sql = new StringBuffer("SELECT ");
        StringBuffer countSql = new StringBuffer("SELECT count(0)");
        List<Object> params = createPageSql(sqlFlow, sql, countSql);
        sql.append(" LIMIT ?, ?");
        Object[] pageParams = null;
        Object[] countParams = null;
        if (params != null && !params.isEmpty()) {
            countParams = params.toArray(new Object[params.size()]);
            pageParams = params.toArray(new Object[params.size() + 2]);
        } else {
            pageParams = new Object[2];
        }
        pageParams[pageParams.length - 2] = first(sqlFlow.flexiPage);
        pageParams[pageParams.length - 1] = second(sqlFlow.flexiPage);

        return new PageSQLQuery(sql.toString(), pageParams,
                countSql.toString(), countParams);
    }

    private long first(Pagination page) {
        return (page.getPage() - 1) * page.getRp();
    }

    private long second(Pagination page) {
        return page.getRp();
    }
}
