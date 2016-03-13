package cn.touch.cn.touch.io.xls.processor;

import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by touchnan on 2016/3/10.
 */
public interface XlsSheetValidator {
    default boolean validate(Sheet sheet) {
        return true;
    }
}
