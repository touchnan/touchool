/**
 * 
 */
package cn.touch.db.page.parser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.lang.Strings;

import cn.touch.db.page.Orderby;
import cn.touch.util.Constants;

/**
 * Aug 19, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public abstract class BaseSQLParser implements SQLParser {

    protected List<Object> createPageSql(SQLFlow sqlFlow, StringBuffer sql,
            StringBuffer countSql) {
        return create(sqlFlow, sql, true, countSql);
    }

    protected List<Object> createSql(SQLFlow sqlFlow, StringBuffer sql) {
        return create(sqlFlow, sql, false, null);
    }

    protected List<Object> create(SQLFlow sqlFlow, StringBuffer sql,
            boolean count, StringBuffer countSql) {
        List<Object> params = new ArrayList<Object>(10);

        if (sqlFlow.columns != null) {
            sql = append(sql, sqlFlow.columns);
        }

        StringBuffer _sql = new StringBuffer();

        if (sqlFlow.tabs != null) {
            _sql.append(" FROM");
            _sql = append(_sql, sqlFlow.tabs);
        }

        if (sqlFlow.conditions != null
                || (sqlFlow.flexiPage.getRuleGroup() != null && sqlFlow.flexiPage
                        .getRuleGroup().isNotBlank())) {
            _sql.append(" WHERE");
            if (sqlFlow.conditions != null) {
                _sql = append(_sql, sqlFlow.conditions);
            } else {
                _sql.append(" 1=1");
            }

            if (sqlFlow.params != null) {
                for (Object obj : sqlFlow.params) {
                    params.add(obj);
                }
            }

            if (sqlFlow.flexiPage.getRuleGroup() != null
                    && sqlFlow.flexiPage.getRuleGroup().isNotBlank()) {
                sqlFlow.flexiPage.getRuleGroup().filterAnd(_sql, params);
            }
        }

        if (sqlFlow.groups != null) {
            _sql.append(" GROUP BY");
            _sql = append(_sql, sqlFlow.groups);
        }

        if (count) {// 统计sql连接完成
            countSql.append(_sql);
        }

        if (sqlFlow.orders != null || sqlFlow.flexiPage.hasOrderBy()) {
            _sql.append(" ORDER BY");
            if (sqlFlow.orders != null) {
                _sql = append(_sql, sqlFlow.orders);
            }

            if (sqlFlow.flexiPage.hasOrderBy()) {
                Orderby orderby = sqlFlow.flexiPage.getOrderby();
                if (orderby != null && orderby.isOrderBySetted()) {
                    String[] sidxs = StringUtils.split(orderby.getSortname(),
                            Constants.COMMA);
                    int maxSordLen = 0;
                    String[] sords = null;
                    if (!Strings.isBlank(orderby.getSortorder())) {
                        sords = StringUtils.split(orderby.getSortorder(),
                                Constants.COMMA);
                        maxSordLen = sords.length;

                        // if (sidxs.length != sords.length) {
                        // throw
                        // Lang.makeThrow("分页多重排序参数中,排序字段个数[%s]与升降序个数[%s]不匹配",
                        // sidxs.length,sords.length);
                        // }

                    }

                    for (int i = 0; i < sidxs.length; i++) {
                        if (i != 0 || (i == 0 && sqlFlow.orders != null)) {
                            // 不是第一个或者是第一个但前面已经有排序字段
                            _sql.append(Constants.COMMA);
                        }
                        _sql.append(Constants.WHITE_SPACE).append(sidxs[i]);// 排序字段
                        if (i < maxSordLen) {
                            _sql.append(Constants.WHITE_SPACE).append(sords[i]);// 升降序
                        }
                    }
                }
            }
        }

        // SQL连接完成
        sql.append(_sql);

        return params;
    }

    private StringBuffer append(StringBuffer sql, String... params) {
        for (int i = 0; i < params.length; i++) {
            sql.append(i == 0 ? Constants.WHITE_SPACE : Constants.COMMA)
                    .append(params[i]);
        }
        return sql;
    }

     /* (non-Javadoc)
     * @see
     cn.touch.db.parse.SQLParser#createSQLQuery(cn.touch.db.parse.SQLRunner)
     */
     @Override
     public SimpleSQLQuery createSQLQuery(SQLFlow sqlFlow) {
         StringBuffer sql = new StringBuffer("SELECT ");
         List<Object> params = createSql(sqlFlow, sql);
         Object[] queryParams = null;
         if (params != null && !params.isEmpty()) {
             queryParams = params.toArray(new Object[params.size()]);
         }
         return new SimpleSQLQuery(sql.toString(), queryParams);    
     }

}
