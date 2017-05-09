package cn.touch.examples;

import cn.touch.cn.touch.io.xls.XlsReader;
import cn.touch.cn.touch.io.xls.XlsWriteCellInvoker;
import cn.touch.cn.touch.io.xls.XlsWriteRowInvoker;
import cn.touch.cn.touch.io.xls.processor.XlsSheetValidator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by touchnan on 2016/3/21.
 */
public class XlsProcessExample implements XlsReader {

    public static void main(String[] args) {

    }

    private void load(File file) {
        Workbook wb = this.workbook(file);
        final int rowBeginIdx = 3;
        this.processSheet(wb, "sheetName", new XlsSheetValidator() {
                },
                row -> {//判断行结束
                    boolean r = true;
                    if (row != null) {
                        Cell cell = row.getCell(45);
                        //该列不为空且有值，则不end
                        r = !(cell != null && StringUtils.isNotBlank(this.readCellValueString(cell)));
                    }
                    return r;
                },
                (sIdex, rIdex, row) -> {//处理行
                    //this.cellEach(row, (cIdex, cell) -> {});
                    try {
                        String code = this.readCellValueString(row, 45);//
                        String attachmentName = this.readCellValueString(row, 63);
                        String companyName = this.readCellValueString(row, 10);//24
                        String companyId = this.readCellValueString(row, 28);
                        String departName = this.readCellValueString(row, 30);
                        String departId = this.readCellValueString(row, 29);

                        Date fileDate = this.readCellValueDate(row,40 );
                    } catch (Exception e) {
                        String msg = String.format("第%s页第%s行数据有误或者格式不符合导入规则，请确认！", sIdex + 1, rIdex + 1);
                        logger.error(msg, e);
                        throw new RuntimeException(msg);
                    }

                }, rowBeginIdx);
    }

    private void loadXls(File file) {
        Workbook wb = this.workbook(file);
        final int rowBeginIdx = 1;

        this.sheetEach(wb, (sIdex, sheet) -> {
            //sheet
            this.rowEach(sheet, rowBeginIdx, (rIdex, row) -> {
                //行
                String name = this.readCellValueString(row, 0);
                Date fileDate = this.readCellValueDate(row,1);

                this.cellEach(row, (cIdex, cell) -> {
                    //列
                    try {
                        switch (cIdex) {
                            case 0://String
                                String code = this.readCellValueString(cell);
                                break;
                            case 1:// Long
                                Long id = this.readCellValueLong(cell);
                                break;

                            case 2: //Date
                                Date date = readCellValueDate(cell);
                                break;
                            default:
                                break;
                        }

                    } catch (Exception e) {
                        Object cellValue = this.readCellValue(cell);
                        String msg = String.format("第%s页第%s行第%s列[%s]数据有误或者格式不符合导入规则，请确认！", sIdex + 1, rIdex + 1, cIdex + 1, cellValue);
                        logger.error(msg, e);
                        throw new RuntimeException(msg);
                    }
                });

            });
        });

    }


    public void writefile(String outFileName) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFDataFormat df = wb.createDataFormat();
        final XSSFCellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setDataFormat(df.getFormat("yyyyMMdd"));

        XSSFSheet sheet = wb.createSheet("sheetName");
//        sheet.setDefaultColumnWidth(13);

        final String[] titles = {"源系统_单据编号", "附件ID", "附件名称", "附件文件类型", "生成日期", "备注", "附件链接", "附件大小"};


        AtomicLong attachmentId = new AtomicLong(0);
        AtomicInteger row = new AtomicInteger(0);

        //写titles
        new XlsWriteRowInvoker() {
        }.write(sheet, row.getAndIncrement(), titles, new XlsWriteCellInvoker() {
        });


        Object[] values = {"code", attachmentId.getAndIncrement(),"name", "type", new Date(), "remark", "linker", 1234585};
        //写一行数据
        new XlsWriteRowInvoker() {
        }.write(sheet, row.getAndIncrement(), values, new XlsWriteCellInvoker() {
            @Override
            public CellStyle getDateCellStyle(int cellIndex) {
                return dateCellStyle;
            }
        });

        FileOutputStream fos = new FileOutputStream(FileUtils.getFile(outFileName));
        wb.write(fos);
        IOUtils.closeQuietly(fos);
    }

}
