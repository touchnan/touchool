package cn.touch.io.xls;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.touch.io.xls.XlsReader;
import cn.touch.io.xls.XlsWriteCellInvoker;
import cn.touch.io.xls.XlsWriteRowInvoker;
import cn.touch.io.xls.processor.XlsRowEndtrigger;
import cn.touch.io.xls.processor.XlsSheetValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by touchnan on 2016/3/13.
 */
public class XlsTest implements XlsReader {

    public static void main(String[] args) {



    }

    static void write() {
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFDataFormat df = wb.createDataFormat();
        final XSSFCellStyle dateCellStyle = wb.createCellStyle();
        dateCellStyle.setDataFormat(df.getFormat("yyyyMMdd"));

        XSSFSheet sheet = wb.createSheet("合同附件");


        AtomicInteger row = new AtomicInteger(0);
        final String[] titles = {"源系统_单据编号", "附件ID", "附件名称", "附件文件类型", "生成日期", "备注", "附件链接", "附件大小"};
        new XlsWriteRowInvoker() {
        }.write(sheet, row.getAndIncrement(), titles, new XlsWriteCellInvoker() {
        });

        Object[] values = {"code",
                1L,//id
                "mingcheng",
                "leixing",
                new Date(),//日期
                "beizhu",
                "lianjie",
                "daxiao"
        };

        new XlsWriteRowInvoker() {
        }.write(sheet, row.getAndIncrement(), values, new XlsWriteCellInvoker() {
            @Override
            public CellStyle getDateCellStyle(int cellIndex) {
                return dateCellStyle;
            }
        });
    }
    void read() {
        Workbook wb = this.workbook(FileUtils.getFile("name.xlsx"));
        final int rowBeginIdx = 3;
        this.processSheet(wb, "合同信息_录入", new XlsSheetValidator() {
        }, new XlsRowEndtrigger() {
            @Override
            public boolean end(Row row) {
                boolean r = true;
                if (row!=null) {
                    Cell cell = row.getCell(2);
                    //该列不为空且有值，则不end
                    r = !(cell!=null && StringUtils.isNotBlank(cell.getStringCellValue()));
                }
                return r;
            }
        }, (sIdex, rIdex, row) -> {

            try {
            	/*if (rIdex ==111) {
            		System.out.println("");
            	}*/

                String code = this.readCellValueString(row,2);//47
                String attachmentName = this.readCellValueString(row,63);//

                String companyName = this.readCellValueString(row, 10);//24
                String companyId = this.readCellValueString(row, 28);//28  56
                String departName = this.readCellValueString(row, 30);
                String departId = this.readCellValueString(row, 29);

//                ContractDataDto meijuShujuDto = new ContractDataDto(code,attachmentName,companyName,companyId,departName,departId);
//                Object obj = new Object();
//                map.put(obj,obj);
            } catch (Exception e) {
                String msg = String.format("第%s页第%s行数据有误或者格式不符合导入规则，请确认！", sIdex + 1, rIdex + 1);
                logger.error(msg, e);
                throw new RuntimeException(msg);
            }

        },rowBeginIdx);
    }
    void read2() {
        Workbook wb = this.workbook(FileUtils.getFile("name.xlsx"));
        final int rowBeginIdx = 1;

//        AtomicInteger count = new AtomicInteger(0);


        List<Object> list = new ArrayList<Object>();

        this.sheetEach(wb, (sIdex, sheet) -> {
            //sheet
            this.rowEach(sheet, rowBeginIdx, (rIdex, row) -> {
                //行
                Object fujian = new Object();

                this.cellEach(row, (cIdex, cell) -> {
                    //列
                    try {
                        switch (cIdex) {
                            case 0://"源系统_单据编号"
                                String code = (String) readCellValue(cell);
//                                fujian.setYuanxitongDangjuBianma(code);
                                break;
                            case 1:// "附件ID",
//                                fujian.setFujianId(parseDouble2Long((Double) readCellValue(cell)));
                                break;
                            case 2://"附件名称",
                                String name = (String) readCellValue(cell);
//                                fujian.setFujianMingchen(name);
                                break;
                            case 3: //"附件文件类型",
                                String type = (String) readCellValue(cell);
//                                fujian.setFujianWenjianLeixing(type);
                                break;
                            case 4: //""生成日期",
                                java.util.Date date = (java.util.Date) readCellValue(cell);
//                                fujian.setShengchengRiqi(date);
                                break;
                            case 5: //"备注",
                                String remark = (String) readCellValue(cell);
//                                fujian.setFujianBeizhu(remark);
                                break;
                            case 6: //"附件链接",
                                String linker = (String) readCellValue(cell);
//                                fujian.setFujianLianjie(linker);
                                break;
                            case 7: //"附件大小"
                                Double daxiao = (Double) readCellValue(cell);
                                if (daxiao != null) {
//                                    fujian.setFujianDaxiao(new BigDecimal(daxiao));
                                }
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

                list.add(fujian);
            });
        });
    }
}
