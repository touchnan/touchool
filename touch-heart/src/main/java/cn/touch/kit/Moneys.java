/*
 * cn.touch.kit.Moneys.java
 * May 27, 2012 
 */
package cn.touch.kit;

import java.math.BigDecimal;

/**
 * May 27, 2012
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class Moneys {
    public final static BigDecimal DIVISOR_WANG_YUAN = new BigDecimal(1000000);
    public final static BigDecimal MULTIPLICAND_PERCENT = new BigDecimal(100);

    /**
     * 把分转化成元格式
     * 
     * @param fen
     * @return yuan
     */
    public static String castMoney2Yuan(Long fen) {
        if (null == fen) {
            return "0.00";
        }
        return BigDecimal.valueOf(fen, 2).toString();
    }

    /**
     * 把分转化成万元格式
     * 
     * @param fen
     *            分
     * @return 万元
     */
    public static String castMoney2WangYuan(Long fen) {
        if (fen != null) {
            return BigDecimal.valueOf(fen).divide(DIVISOR_WANG_YUAN).toString();
        }
        return "0";
    }

    public static String cast2PercentPattern(Long div, Long divisor) {
        if (div != null && divisor != null && divisor > 0) {
            return BigDecimal.valueOf(div).divide(BigDecimal.valueOf(divisor), 5, BigDecimal.ROUND_HALF_UP)
                    .multiply(MULTIPLICAND_PERCENT).doubleValue()
                    + "%";
        }
        return "-";
    }

}
