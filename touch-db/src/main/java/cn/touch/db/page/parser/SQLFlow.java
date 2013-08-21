/**
 * 
 */
package cn.touch.db.page.parser;

import cn.touch.db.page.Pagination;

/**
 * Aug 19, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class SQLFlow {
    protected String[] columns = null;
    protected String[] tabs = null;
    protected String[] conditions = null;
    protected String[] orders = null;
    protected String[] groups = null;
    protected Object[] params = null;

    protected Pagination flexiPage;

    public SQLFlow() {
        super();
    }

    public SQLFlow select(String... columns) {
        this.columns = columns;
        return this;
    }

    public SQLFlow from(String... tabs) {
        this.tabs = tabs;
        return this;
    }

    public SQLFlow where(String... conditions) {
        this.conditions = conditions;
        return this;
    }

    public SQLFlow orderBy(String... orders) {
        this.orders = orders;
        return this;
    }

    public SQLFlow groupBy(String... groups) {
        this.groups = groups;
        return this;
    }

    public SQLFlow setParameters(Object... params) {
        this.params = params;
        return this;
    }
    
    public SQLFlow page(Pagination flexiPage) {
        this.flexiPage = flexiPage;
        return this;
    }

}
