/**
 * 
 */
package cn.touch.db.page;

import org.apache.commons.lang3.StringUtils;
import org.nutz.lang.Strings;

import cn.touch.db.query.filter.FlexiRule;
import cn.touch.db.query.filter.FlexiRule.FilterSopt;
import cn.touch.db.query.filter.FlexiRuleGroup;
import cn.touch.db.util.Utils;
import cn.touch.util.Constants;

/**
 * Aug 17, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class Pagination extends Page {

    public static final String FLEXI_SEARCH_TYPE_MUTIL = "flexi";

    private FlexiRuleGroup ruleGroup;// jqgrid条件

    // -- 排序条件 --//
    private Orderby orderby = new Orderby();
//    private boolean distinct;

    public Pagination() {
        super();
    }

    /**
     * @param page
     * @param rp
     * @param total
     */
    public Pagination(int page, int rp, int total) {
        super(page, rp, total);
    }

    /**
     * @param page
     * @param total
     */
    public Pagination(int page, int total) {
        super(page, total);
    }

    /**
     * @param rp
     */
    public Pagination(int rp) {
        super(rp);
    }

    public Pagination(int rp, int page, FlexiRuleGroup ruleGroup) {
        this(rp, page, null, null, null, null, ruleGroup);
    }

    public Pagination(int rp, int page, String qtype, String query,
            String sortname, String sortorder, FlexiRuleGroup ruleGroup) {
        this(page, rp);
        this.ruleGroup = ruleGroup;
        if (!Strings.isBlank(qtype) && !Strings.isBlank(query)) {
            if (FLEXI_SEARCH_TYPE_MUTIL.equals(qtype)) {
                String[] pairs = StringUtils.split(query, Constants.AND);
                for (int i = 0; i < pairs.length; i++) {
                    String[] ps = StringUtils.split(pairs[i], Constants.EQUAL);
                    if (ps.length == 2) {
                        this.addFlexiRule(new FlexiRule().rule(FilterSopt.eq,
                                ps[0], ps[1]));
                    }
                }
            } else {
                this.addFlexiRule(new FlexiRule().rule(FilterSopt.eq, qtype,
                        query));
            }
        }
        if (!Strings.isBlank(sortname) && !Strings.isBlank(sortorder)) {
            Utils.sqlFieldFilter(sortname);
            try {// 带有类型说明的的字段,截取最后的字段
                sortname = new FlexiRule().rule(FilterSopt.bn, sortname, null)
                        .getField();
            } catch (Exception e) {// 普通正常字段
                // pass
            }
            this.setOrderby(new Orderby(sortname, sortorder));
        }
    }

    public boolean hasOrderBy() {
        return getOrderby() != null && getOrderby().isOrderBySetted();
    }

    /**
     * @return the orderby
     */
    public Orderby getOrderby() {
        return orderby;
    }

    /**
     * @param orderby
     *            the orderby to set
     */
    public void setOrderby(Orderby orderby) {
        this.orderby = orderby;
    }

    /**
     * @return the ruleGroup
     */
    public FlexiRuleGroup getRuleGroup() {
        return ruleGroup;
    }

    /**
     * @param ruleGroup
     *            the ruleGroup to set
     */
    public void setRuleGroup(FlexiRuleGroup ruleGroup) {
        this.ruleGroup = ruleGroup;
    }

    public Pagination addFlexiRule(FlexiRule rule) {
        if (this.ruleGroup == null) {
            this.ruleGroup = new FlexiRuleGroup();
        }
        this.ruleGroup.addRule(rule);
        return this;
    }

}
