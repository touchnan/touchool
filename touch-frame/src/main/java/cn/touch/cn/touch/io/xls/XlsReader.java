package cn.touch.cn.touch.io.xls;

import cn.touch.cn.touch.io.xls.processor.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;

/**
 * Created by touchnan on 2016/3/10.
 * 
 * 海量数据处理
 * http://blog.csdn.net/jinshuaiwang/article/details/15499685
 * http://blog.csdn.net/kongls08/article/details/12169329
 * http://blog.csdn.net/lee_guang/article/details/8936178
 * http://blog.csdn.net/slience_perseverance/article/details/8228157
 */
public interface XlsReader {
    public static final Logger logger = LoggerFactory.getLogger(XlsReader.class);

    default Workbook workbook(File file) {
        try {
            return new XSSFWorkbook(new FileInputStream(file));
        } catch (Exception e) {
            try {
                return new HSSFWorkbook(new FileInputStream(file));
            } catch (Exception e1) {
            }
        }
        return null;
    }

    /**
     * 跳出循环
     *
     * @param rowBeginIdx
     * @param file
     * @param xlsSheetValidator
     * @param rowEndtrigger
     * @param reader
     */
    default void process(final int rowBeginIdx, File file, XlsSheetValidator xlsSheetValidator, XlsRowEndtrigger rowEndtrigger, XlsRowReader reader) {
        Workbook workbook = this.workbook(file);
        int sCount = workbook.getNumberOfSheets();
        for (int sIdex = 0; sIdex < sCount; sIdex++) {
            Sheet sheet = workbook.getSheetAt(sIdex); // sheet页
            processSheet(sIdex, sheet, xlsSheetValidator, rowEndtrigger, reader, rowBeginIdx);
        }
    }

    default void processSheet(Workbook workbook, String name, XlsSheetValidator xlsSheetValidator, XlsRowEndtrigger rowEndtrigger, XlsRowReader reader, final int rowBeginIdx) {
        Sheet sheet = workbook.getSheet(name);
        processSheet(workbook.getSheetIndex(sheet), sheet, xlsSheetValidator, rowEndtrigger, reader, rowBeginIdx);
    }

    /**
     * 跳出循环
     *
     * @param sIdex
     * @param sheet
     * @param xlsSheetValidator
     * @param rowEndtrigger
     * @param reader
     * @param rowBeginIdx
     */
    default void processSheet(final int sIdex, Sheet sheet, XlsSheetValidator xlsSheetValidator, XlsRowEndtrigger rowEndtrigger, XlsRowReader reader, final int rowBeginIdx) {
        //Sheet sheet = workbook.getSheetAt(sIdex); // sheet页
        if (sheet != null && xlsSheetValidator.validate(sheet)) {
            int rows = sheet.getLastRowNum();
            // 循环行
            for (int r = rowBeginIdx; r <= rows; r++) {
                Row row = (Row) sheet.getRow(r);
                if (row == null || rowEndtrigger.end(row)) {
                    break;//跳出row循环
                } else {
                    // 第r行
                    reader.forEach(sIdex, r, row);
                }
            }
        }
    }

    default void sheetEach(Workbook workbook, XlsReadSheetProcessor processor) {
        int sCount = workbook.getNumberOfSheets();
        for (int sIdex = 0; sIdex < sCount; sIdex++) {
            Sheet sheet = workbook.getSheetAt(sIdex); // sheet页
            processor.forEach(sIdex, sheet);
        }
    }

//    default  void sheet(Workbook workbook, String name,XlsReadRowProcessor processor,final int rowBeginIdx) {
//        Sheet sheet = workbook.getSheet(name);
//        this.rowEach(sheet,rowBeginIdx,processor);
//    }
//
//    default  void sheet(Workbook workbook,final int sIdx,XlsReadRowProcessor processor,final int rowBeginIdx) {
//        Sheet sheet = workbook.getSheetAt(sIdx);
//        this.rowEach(sheet,rowBeginIdx,processor);
//    }

    default void rowEach(Sheet sheet, XlsReadRowProcessor processor) {
        this.rowEach(sheet, 0, processor);
    }

    default void rowEach(Sheet sheet, final int rowBeginIdx, XlsReadRowProcessor processor) {
        int rows = sheet.getLastRowNum();
        // 循环行
        for (int r = rowBeginIdx; r <= rows; r++) {
            Row row = (Row) sheet.getRow(r);
            if (row != null) {
                processor.forEach(r, row);
            }
        }
    }

    default void cellEach(Row row, XlsReadCellProcessor processor) {
        // 循环列
        int columancountMax = row.getLastCellNum();
        for (int c = 0; c < columancountMax; c++) {
            Cell cell = row.getCell(c);
            if (cell != null) {
                processor.forEach(c, cell);
            }
        }
    }


    default Object readCellValue(Row row, int cIndx) {
        return readCellValue(row.getCell(cIndx));
    }

    /*-

    http://yl-fighting.iteye.com/blog/1726285


以上，我用
Double d = cell.getNumericCellValue();
DecimalFormat df = new DecimalFormat("#");
String value = df.format(d);
来处理，是可以做到把科学计数变成正常的数字，但如果有小数，小数部分会被忽略掉。

 所以，大家有木有什么好的办法和建议呀？ excel java poi
[解决办法]
上面写错了，是
DecimalFormat df = new DecimalFormat("#.#########");

[解决办法]
都当成字符串处理吧，String.valueOf(),然后想怎么弄怎么弄，我解析就是这样处理的
     */
    default Object readCellValue(Cell cell) {
        Object o = null;
        if (cell != null){
	        try {
	            //CellFormatType.NUMBER
	        	
	            switch (cell.getCellType()) {
	                case Cell.CELL_TYPE_BLANK:
	                    o = null;
	                    break;
	                case Cell.CELL_TYPE_BOOLEAN:
	                    o = cell.getBooleanCellValue();
	                    break;
	                case Cell.CELL_TYPE_ERROR:
	                    o = cell.getErrorCellValue();
	                    break;
	                case Cell.CELL_TYPE_FORMULA:
	                    o = cell.getNumericCellValue();
	                    //o = cell.getCellFormula();
	                    break;
	                case Cell.CELL_TYPE_NUMERIC:
	                    if (DateUtil.isCellDateFormatted(cell)) {
	                        o = cell.getDateCellValue();
	                    } else {
	                        o = cell.getNumericCellValue();
	                    }
	                    break;
	                case Cell.CELL_TYPE_STRING:
	                    o = cell.getStringCellValue();
	                    break;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
        }
        return o;
    }

    default Long readCellLongValue(Cell cell) {
        double val = cell.getNumericCellValue();
        return parseDouble2Long(val);
    }

    default Long parseDouble2Long(double val) {
        DecimalFormat df = new DecimalFormat("#");
        return Long.valueOf(df.format(val));
    }
}
