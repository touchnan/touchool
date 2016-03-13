package cn.touch.examples.xls.processor;

import org.apache.poi.ss.usermodel.Row;

/**
 * Created by touchnan on 2016/3/10.
 */
public interface XlsRowEndtrigger {
    default boolean end(Row row) {
        return true;
    }
}
