package cn.touch.io.xls.processor;

import org.apache.poi.ss.usermodel.Row;

/**
 * Created by touchnan on 2016/3/10.
 */
@FunctionalInterface
public interface XlsRowReader {
    void forEach(final int sIdex, final int rIdex, Row row);
}
