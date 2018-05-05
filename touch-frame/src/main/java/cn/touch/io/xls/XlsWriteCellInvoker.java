package cn.touch.io.xls;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by touchnan on 2016/3/10.
 */
public interface XlsWriteCellInvoker {
    default void write(Cell cell, final int cellIndex, Object value) {
        if (value != null) {
			if (value instanceof String) {
				CellStyle wrapCellStyle = getWrapCellStyle(cellIndex);
				if (wrapCellStyle != null) {
					cell.setCellStyle(wrapCellStyle);
					// cell.setCellValue(new XSSFRichTextString((String)
					// value));
				}
				cell.setCellValue((String) value);
			} else if (value instanceof Date) {
				CellStyle cellStyle = getDateCellStyle(cellIndex);
				if (cellStyle != null) {
					cell.setCellStyle(cellStyle);
				}
				cell.setCellValue((Date) value);
			} else if (value instanceof Double) {
				cell.setCellValue((Double) value);
			} else if (value instanceof Long) {
				cell.setCellValue((Long) value);
			} else if (value instanceof Byte) {
				cell.setCellValue((Byte) value);
			} else if (value instanceof Integer) {
				cell.setCellValue((Integer) value);
			} else if (value instanceof BigDecimal) {
				cell.setCellValue(((BigDecimal) value).doubleValue());
			} else if (value instanceof Boolean) {
				cell.setCellValue((Boolean) value);
			} else if (value instanceof RichTextString) {
				cell.setCellValue((RichTextString) value);
			} else if (value instanceof Calendar) {
				cell.setCellValue((Calendar) value);
			} else {
                throw new RuntimeException(String.format("未匹配到第列[%s]数据类型[%s]", cellIndex + 1, value));
            }
        }
    }

    default CellStyle getDateCellStyle(final int cellIndex) {
        return null;
    }

    default CellStyle getWrapCellStyle(final int cellIndex) {
        return null;
    }
}
