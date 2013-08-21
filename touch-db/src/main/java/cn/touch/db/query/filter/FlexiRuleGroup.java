/*
 * cn.touchin.page.flexi.FlexiFilter.java
 * Aug 3, 2012 
 */
package cn.touch.db.query.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.nutz.lang.Lang;

import cn.touch.util.Constants;

/**
 * Aug 3, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
/**
 * Aug 4, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class FlexiRuleGroup {
    public static final String NAME_GROUPOP_AND = "AND";
    public static final String NAME_GROUPOP_OR = "OR";

    private String groupOp;// 组连接符
    private List<FlexiRule> rules;
    private List<FlexiRuleGroup> groups;//

    public FlexiRuleGroup() {
        this(NAME_GROUPOP_AND);
    }

    public FlexiRuleGroup(String groupOp) {
        this.setGroupOp(groupOp);
    }

    public boolean isBlank() {
        return !isNotBlank();
    }

    public boolean isNotBlank() {
        return (rules != null && !rules.isEmpty())
                || (groups != null && !groups.isEmpty());
    }

    /**
     * 组装过滤条件，并以and连接返回
     * 
     * @param sql
     * @param params
     */
    public void filterAnd(StringBuffer sql, List<Object> params) {
        sql.append(" AND");
        filter(sql, params);
    }

    /**
     * 组装过滤条件，并以or连接返回
     * 
     * @param sql
     * @param params
     */
    public void filterOr(StringBuffer sql, List<Object> params) {
        sql.append(" OR");
        filter(sql, params);
    }

    /**
     * 组装过滤条件
     * 
     * @param sql
     * @param params
     */
    private void filter(StringBuffer sql, List<Object> params) {
        sql.append(" (");
        int count = 0;
        if (rules != null) {
            count = filterRule(sql, params, count);
        }

        if (groups != null) {
            count = filterGroup(sql, params, count);
        }

        sql.append(Constants.RIGHT_PARENTHESIS);
    }

    /**
     * 组装过滤规则
     * 
     * @param sql
     * @param params
     * @param count
     * @return
     */
    private int filterRule(StringBuffer sql, List<Object> params, int count) {
        for (Iterator<FlexiRule> iter = this.rules.iterator(); iter.hasNext(); count++) {
            FlexiRule rule = iter.next();
            if (count != 0) {
                sql.append(Constants.WHITE_SPACE).append(this.groupOp);
            }
            rule.flip().sql(sql, params);
        }
        return count;
    }

    /**
     * 组装过滤组
     * 
     * @param sql
     * @param params
     * @param count
     * @return
     */
    private int filterGroup(StringBuffer sql, List<Object> params, int count) {
        if (NAME_GROUPOP_AND.equalsIgnoreCase(this.groupOp)) {
            for (Iterator<FlexiRuleGroup> iter = this.groups.iterator(); iter
                    .hasNext(); count++) {
                FlexiRuleGroup filter = iter.next();
                if (count != 0) {
                    filter.filterAnd(sql, params);
                } else {
                    filter.filter(sql, params);
                }
            }
        } else if (NAME_GROUPOP_OR.equalsIgnoreCase(this.groupOp)) {
            for (Iterator<FlexiRuleGroup> iter = this.groups.iterator(); iter
                    .hasNext(); count++) {
                FlexiRuleGroup filter = iter.next();
                if (count != 0) {
                    filter.filterOr(sql, params);
                } else {
                    filter.filter(sql, params);
                }
            }
        }
        return count;
    }

    /**
     * @param groupOp
     *            the groupOp to set
     */
    public void setGroupOp(String groupOp) {
        if (!(NAME_GROUPOP_AND.equalsIgnoreCase(groupOp) || NAME_GROUPOP_OR
                .equalsIgnoreCase(groupOp))) {
            throw Lang.makeThrow("连接符(%s)无效", groupOp);
        }
        this.groupOp = groupOp;
    }

    /**
     * @param rules
     *            the rules to set
     */
    public void setRules(List<FlexiRule> rules) {
        this.rules = rules;
    }

    /**
     * @param rule
     * @return
     */
    public FlexiRuleGroup addRule(FlexiRule rule) {
        if (this.rules == null) {
            this.rules = new ArrayList<FlexiRule>();
        }
        this.rules.add(rule);
        return this;
    }

    /**
     * @param rules
     * @return
     */
    public FlexiRuleGroup addRules(List<FlexiRule> rules) {
        if (this.rules == null) {
            this.rules = new ArrayList<FlexiRule>();
        }
        this.rules.addAll(rules);
        return this;
    }

    /**
     * @param groups
     *            the groups to set
     */
    public void setGroups(List<FlexiRuleGroup> groups) {
        this.groups = groups;
    }

    /**
     * @param group
     * @return
     */
    public FlexiRuleGroup addGroup(FlexiRuleGroup group) {
        if (this.groups == null) {
            this.groups = new ArrayList<FlexiRuleGroup>();
        }
        this.groups.add(group);
        return this;
    }

    /**
     * @param groups
     * @return
     */
    public FlexiRuleGroup addGroups(List<FlexiRuleGroup> groups) {
        if (this.groups == null) {
            this.groups = new ArrayList<FlexiRuleGroup>();
        }
        this.groups.addAll(groups);
        return this;
    }
}
