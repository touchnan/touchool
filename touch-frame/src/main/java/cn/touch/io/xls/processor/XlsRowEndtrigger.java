package cn.touch.io.xls.processor;

import org.apache.poi.ss.usermodel.Row;

/**
 * Created by touchnan on 2016/3/10.
 */
@FunctionalInterface
public interface XlsRowEndtrigger {
    boolean end(Row row);
//    default boolean end(Row row) {
//        return true;
//    }
}
