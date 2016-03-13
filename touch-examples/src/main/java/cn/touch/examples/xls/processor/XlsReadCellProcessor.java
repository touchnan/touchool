package cn.touch.examples.xls.processor;


import org.apache.poi.ss.usermodel.Cell;

/**
 * Created by touchnan on 2016/3/10.
 */
@FunctionalInterface
public interface XlsReadCellProcessor {
    void forEach(final int cIdex, Cell cell);
}
