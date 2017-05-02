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
public interface SQLParser {
    SimpleSQLQuery createSQLQuery(SQLFlow sqlFlow);
    PageSQLQuery createPageSQLQuery(SQLFlow sqlFlow);
}
