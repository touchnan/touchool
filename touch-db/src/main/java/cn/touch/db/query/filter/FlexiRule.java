/*
 * cn.touchin.page.flexi.FlexiRule.java
 * Aug 3, 2012 
 */
package cn.touch.db.query.filter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nutz.castor.Castors;
import org.nutz.lang.Strings;

import cn.touch.db.util.Utils;
import cn.touch.kit.Dates;
import cn.touch.util.Constants;

/**
 * Aug 3, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class FlexiRule {
    private String field;// 比较字段
    private String data;// 比较值
    private FilterSopt op;// 比较符

    private String _field;// 比较字段
    private boolean _flip;// 准备完毕
    private Object _data;// 比较值对象
    private PropertyType _fieldType;// 比较值对象类型

    public FlexiRule() {
        super();
    }

    /**
     * 有SQL注入危险,禁止在外来参数的情景下使用
     * 
     * @param op
     * @param q_field
     * @param q_data
     */
    public FlexiRule rule_beCare(FilterSopt op, String q_field, Object q_data) {
        this._setOp(op);
        this._field = q_field;
        this._data = q_data;
        this._flip = true;// 无需再解析
        return this;
    }

    /**
     * 
     * @param op
     * @param field
     * @param data
     * @return
     */
    public FlexiRule rule(FilterSopt op, String field, String data) {
        this._setOp(op);
        this.setField(field);
        this.setData(data);
        return this;
    }

    private FlexiRule flipField() {
        String typeCode = StringUtils.substringBefore(field, "_");
        _fieldType = PropertyType.valueOf(typeCode);
        _field = StringUtils.substringAfter(field, "_");
        return this;
    }

    private FlexiRule flipDataValue() {
        if (!Strings.isBlank(this.data)) {
            if ((this.op == FilterSopt.nu || this.op == FilterSopt.nn)) {
                // 空和非空
                return this;
            }
            if (_fieldType == PropertyType.D) {
                this._data = Castors.me().cast(this.data, this.data.getClass(),
                        _fieldType.getValue(),
                        cn.touch.db.util.Constants.CASTORS_FORMAT_DATE);
                /*-特殊小于等于开区间日期, 取第二天*/
                if (this.op == FilterSopt.le) {
                    this._data = Dates.getNextDay((Date) this._data);
                }
            } else if (_fieldType == PropertyType.T) {
                this._data = Castors.me().cast(this.data, this.data.getClass(),
                        _fieldType.getValue(),
                        cn.touch.db.util.Constants.CASTORS_FORMAT_DATETIME);
                /*-特殊小于等于开区间时间，取下一秒*/
                if (this.op == FilterSopt.le) {
                    this._data = Dates.getNextSecond((Date) this._data);
                }
            } else if (_fieldType == PropertyType.M) {
                /*-月份特殊处理*/
                this._data = Dates.parse4DATE_YYYY_MM(this.data);
                /*-特殊小于等于开区间月份, 取下月的第一天*/
                if (this.op == FilterSopt.le) {
                    this._data = Dates
                            .getFirstDayOnNextMonth((Date) this._data);
                }
            } else {
                if (_fieldType == PropertyType.S
                        && (this.op == FilterSopt.bn
                                || this.op == FilterSopt.bw
                                || this.op == FilterSopt.cn
                                || this.op == FilterSopt.nc
                                || this.op == FilterSopt.en || this.op == FilterSopt.ew)) {
                    /*- 模糊查询 */
                    switch (this.op) {
                    case bn:
                    case bw:
                        this._data = this.data + "%";
                        break;
                    case cn:
                    case nc:
                        this._data = "%" + this.data + "%";
                        break;
                    case en:
                    case ew:
                        this._data = "%" + this.data;
                        break;
                    default:
                        throw new RuntimeException("no match filtersopt");
                    }

                } else if (this.op == FilterSopt.in || this.op == FilterSopt.ni) {
                    this._data = Castors.me().castTo(_data,
                            getArrayType(_fieldType.getValue()));
                } else {
                    this._data = Castors.me().castTo(this.data,
                            _fieldType.getValue());
                }
            }
        }
        return this;
    }

    /**
     * 解析传入字符，准备完毕
     * 
     * @return
     */
    public FlexiRule flip() {
        if (!this._flip) {
            this.flipField().flipDataValue();
            this._flip = true;
        }
        return this;
    }

    public void sql(StringBuffer sql, List<Object> params) {
        flip();
        switch (this.op) {
        case nu:
        case nn:
            sql.append(Constants.WHITE_SPACE).append(this.getField()).append(Constants.WHITE_SPACE)
                    .append(this.getOp());
            return;
        default:
            sql.append(Constants.WHITE_SPACE).append(this.getField()).append(Constants.WHITE_SPACE)
                    .append(this.getOp()).append(" ?");
            params.add(this.getValue());
        }

    }

    private Class<?> getArrayType(Class<?> type) {
        if (Long.class.equals(type)) {
            return Long[].class;
        } else if (Integer.class.equals(type)) {
            return Integer[].class;
        } else if (String.class.equals(type)) {
            return String[].class;
        } else if (Double.class.equals(type)) {
            return Double[].class;
        } else if (Date.class.equals(type)) {
            return Date[].class;
        } else if (Boolean.class.equals(type)) {
            return Boolean[].class;
        } else if (BigDecimal.class.equals(type)) {
            return BigDecimal[].class;
        } else {
            return Object[].class;
        }
    }

    /**
     * 设置比较符
     */
    private void _setOp(FilterSopt op) {
        this.op = op;
    }

    /**
     * 设置比较符
     * 
     * @param op
     *            the op to set
     */
    public void setOp(String op) {
        _setOp(FilterSopt.valueOf(op));
    }

    /**
     * @param data
     *            the data to set
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 转入符合规则的查询字段,并排除注入危险
     * 
     * @param field
     *            the field to set
     */
    public void setField(String field) {
        this.field = Utils.sqlFieldFilter(field);
    }

    /**
     * @return the field
     */
    public String getField() {
        flip();
        return _field;
    }

    /**
     * @return the op
     */
    public String getOp() {
        flip();
        return op.sopt;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        flip();
        return _data;
    }

    /*-
     * ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
     * ['equal','not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in','ends with','does not end with','contains','does not contain']
     */
    public enum FilterSopt {
        eq("="), ne("<>"), lt("<"), le("<="), gt(">"), ge(">="), bw("LIKE"), bn(
                "NOT LIKE"), in("IN"), ni("NOT IN"), ew("LIKE"), en("NOT LIKE"), cn(
                "LIKE"), nc("NOT LIKE"), nu("IS NULL"), nn("IS NOT NULL");

        private FilterSopt(String sopt) {
            this.sopt = sopt;
        }

        private String sopt;

        public String sopt() {
            return this.sopt;
        }
    }

    public enum PropertyType {
        S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(
                Date.class), T(Date.class), B(Boolean.class), O(Object.class), Q(
                BigDecimal.class), M(Date.class);

        private Class<?> clazz;

        PropertyType(Class<?> clazz) {
            this.clazz = clazz;
        }

        public Class<?> getValue() {
            return clazz;
        }
    }
}
