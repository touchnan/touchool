package cn.touch.io.xls.processor;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by touchnan on 2016/3/10.
 */
@FunctionalInterface
public interface XlsReadSheetProcessor {
    void forEach(final int sIdex, Sheet sheet);
}
