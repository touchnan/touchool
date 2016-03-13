package cn.touch.cn.touch.io.xls.processor;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by touchnan on 2016/3/10.
 */
public interface XlsCellEndtrigger {
    default  boolean end(Cell cell) {
        return true;
    }
}
