package cn.touch.io.xls.processor;

import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by touchnan on 2016/3/10.
 */
@FunctionalInterface
public interface XlsCellEndtrigger {
    boolean end(Cell cell);
//    default  boolean end(Cell cell) {
//        return true;
//    }
}
