/**
 * 
 */
package cn.touch.db.page;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.nutz.lang.Strings;

import cn.touch.util.Constants;

/**
 * Aug 17, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class Orderby implements Serializable {
    private static final long serialVersionUID = 6014558851699403433L;
    
    public static final String ASC = "ASC";
    public static final String DESC = "DESC";

    private String sortname = "";// 排序字段,无默认值.多个排序字段时用','分隔.
    private String sortorder = "";// 升降序 asc or desc ，多個之間用","分隔

    public Orderby() {
        super();
    }

    /**
     * 
     * @param sortname
     * @param sortorder
     */
    public Orderby(String sortname, String sortorder) {
        super();
        setSortname(sortname);
        setSortorder(sortorder);
    }

    public Orderby sortname(final String sortname) {
        setSortname(sortname);
        return this;
    }

    public Orderby sortorder(final String sortorder) {
        setSortorder(sortorder);
        return this;
    }

    /**
     * 是否已设置排序字段,无默认值.
     * 
     * @return boolean
     */
    public boolean isOrderBySetted() {
//        return !Strings.isBlank(sortname) && !Strings.isBlank(sortorder);
        return !Strings.isBlank(sortname);
    }

    /**
     * @return the sortname
     */
    public String getSortname() {
        return sortname;
    }

    /**
     * @param sortname
     *            the sortname to set
     */
    public void setSortname(String sortname) {
        this.sortname = sortname;
    }

    /**
     * @return the sortorder
     */
    public String getSortorder() {
        return sortorder;
    }

    /**
     * 设置排序方式向.
     * 
     * @param sortorder
     *            可选值为desc或asc,多个排序字段时用','分隔.
     */
    public void setSortorder(final String sortorder) {
        // 检查order字符串的合法值
        String caseOrder = StringUtils.upperCase(sortorder);
        String[] orders = StringUtils.split(caseOrder, Constants.COMMA);
        for (String orderStr : orders) {
            if (!StringUtils.equalsIgnoreCase(DESC, orderStr)
                    && !StringUtils.equalsIgnoreCase(ASC, orderStr)) {
                throw new IllegalArgumentException("排序方向" + orderStr + "不是合法值");
            }
        }

        this.sortorder = caseOrder;
    }
}
