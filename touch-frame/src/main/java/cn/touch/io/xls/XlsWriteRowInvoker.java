package cn.touch.io.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Created by touchnan on 2016/3/10.
 */
public interface XlsWriteRowInvoker {
    default void write(Sheet sheet, int rowIndex, Object[] values, XlsWriteCellInvoker xlsWriteCellInvoker) {
        Row row = sheet.createRow(rowIndex);
        Cell cell = null;
        int i = 0;
        for (Object value : values) {
            cell = row.createCell(i);
            xlsWriteCellInvoker.write(cell,i,value);
            i++;
        }
    };
}
