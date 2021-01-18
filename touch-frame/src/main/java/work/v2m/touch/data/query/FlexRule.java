package work.v2m.touch.data.query;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by <a href="mailto:88052350@qq.com">chengqiang.han</a> on 2021/1/19.
 */
public class FlexRule {
    private String field;// 比较字段
    private String data;// 比较值
    private FilterSopt op;// 比较符


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

    /*-
     * ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
     * ['equal','not equal', 'less', 'less or equal','greater','greater or equal', 'begins with','does not begin with','is in','is not in','ends with','does not end with','contains','does not contain']
     */
    public enum FilterSopt {
        eq("="), ne("<>"), lt("<"), le("<="), gt(">"), ge(">="), bw("LIKE"), bn(
                "NOT LIKE"), in("IN"), ni("NOT IN"), ew("LIKE"), en("NOT LIKE"), cn(
                "LIKE"), nc("NOT LIKE"), nu("IS NULL"), nn("IS NOT NULL");

        FilterSopt(String sopt) {
            this.sopt = sopt;
        }

        private String sopt;

//        public String sopt() {
//            return this.sopt;
//        }
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
