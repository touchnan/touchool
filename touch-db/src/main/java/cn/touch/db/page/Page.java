/**
 * 
 */
package cn.touch.db.page;

import java.util.ArrayList;
import java.util.List;

/**
 * Aug 11, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class Page {

    protected int rp = 20;// 每页多少条纪录
    protected int page = 1;// 页码(当前页)
    protected int total = 0;// 纪录总条数

    protected boolean autoCount = true;

    // -- 返回结果 --//
    protected List<?> rows = new ArrayList<Object>();

    public Page() {
        super();
    }

    public Page(int rp) {
        this.setRp(rp);
    }

    public Page(int page, int rp) {
        this.setPage(page);
        this.setRp(rp);
    }

    public Page(int page, int rp, Integer total) {
        this.setPage(page);
        this.setRp(rp);
        this.setTotal(total);
    }

    // -- 访问查询参数函数 --//
    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPage() {
        return page;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPage(final int pageNo) {
        this.page = pageNo;
        if (pageNo < 1) {
            this.page = 1;
        }
    }

    public Page page(final int thePageNo) {
        setPage(thePageNo);
        return this;
    }

    /**
     * 获得每页的记录数量,默认为1.
     */
    public int getRp() {
        return rp;
    }

    /**
     * 设置每页的记录数量,低于1时自动调整为1.
     */
    public void setRp(final int rp) {
        this.rp = rp;

        if (rp < 1) {
            this.rp = 1;
        }
    }

    public Page pageSize(final int thePageSize) {
        setRp(thePageSize);
        return this;
    }

    /**
     * 根据pageNo和pageSize计算当前页第一条记录在总结果集中的位置,序号从1开始.
     */
    public int getFirst() {
        return ((page - 1) * rp);
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数, 默认为false.
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 查询对象时是否自动另外执行count查询获取总记录数.
     */
    public void setAutoCount(final boolean autoCount) {
        this.autoCount = autoCount;
    }

    public Page autoCount(final boolean theAutoCount) {
        setAutoCount(theAutoCount);
        return this;
    }

    // -- 访问查询结果函数 --//

    /**
     * 取得页内的记录列表.
     */
    public List<?> getRows() {
        return rows;
    }

    /**
     * 设置页内的记录列表.
     */
    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    /**
     * 取得总记录数, 默认值为-1.
     */
    public int getTotal() {
        return total;
    }

    /**
     * 设置总记录数.
     */
    public void setTotal(final int totalCount) {
        this.total = totalCount;
    }

    /**
     * 根据pageSize与totalCount计算总页数, 默认值为-1.
     */
    public long getTotalPages() {
        if (total < 0) {
            return 0;
        }
        long count = total / rp;
        if (total % rp > 0) {
            count++;
        }
        return count;
    }

}
