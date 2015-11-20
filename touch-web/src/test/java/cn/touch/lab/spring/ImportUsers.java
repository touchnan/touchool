/*
 * test.cn.touch.zkytxl.vo.ImportUsers.java
 * Jul 27, 2014 
 */
package cn.touch.lab.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.touch.entity.User;
import cn.touch.entity.UserProperty;
import cn.touch.serv.ITxlService;

/**
 * Jul 27, 2014
 * 
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public class ImportUsers {

    private static ApplicationContext cx = new ClassPathXmlApplicationContext("spring/context.xml");
    protected static ITxlService txlService = (ITxlService) cx.getBean(ITxlService.class);
    private static String filename = "src/test/resources/aa.xls";

    public static void main(String[] args) {
        try {
            initUsers();
            System.out.println("Data init success.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("人员初始化失败");
        } finally {
            System.exit(0);
        }

    }

    /**
     * @throws Exception
     * @throws
     * 
     */
    private static void initUsers() throws Exception {
        File file = FileUtils.getFile(filename);
        System.out.println(file.getAbsolutePath());
        readExcel(file);

    }

    public static void readExcel(File file) throws IOException {
        Set<String> female= new HashSet<String>();
        
        FileInputStream fis = FileUtils.openInputStream(file);
        // XSSFWorkbook wb = new XSSFWorkbook(fis);
        HSSFWorkbook wb = new HSSFWorkbook(fis);

        Sheet sheet = wb.getSheetAt(0);
        Iterator<Row> rIter = sheet.rowIterator();

        Row row = rIter.next();
        short minColIx = row.getFirstCellNum();
        short maxColIx = row.getLastCellNum();
        String[] titles = new String[maxColIx - minColIx + 1];
        for (short colIx = minColIx; colIx < maxColIx; colIx++) {
            titles[colIx] = getCellValue(row.getCell(colIx));
        }
        titles[0] = "姓名";

        for (; rIter.hasNext();) {
            row = rIter.next();
            /*-
            short minColIx = row.getFirstCellNum();
            short maxColIx = row.getLastCellNum();
            for(short colIx=minColIx; colIx<maxColIx; colIx++) {
              Cell cell = row.getCell(colIx);
              if(cell == null) {
                continue;
              }
              //... do something with cell
              getCellValue(cell)
            }
             */

            User u = new User();
            Iterator<Cell> cIter = row.cellIterator();
            for (int i = 0; cIter.hasNext(); i++) {
                String val = getCellValue(cIter.next());
                switch (i) {
                case 0:
                    u.setLoginName(val);
                    u.setPasswd("20142014");
                    if (female.contains(val)) {
                        u.setType(1);
                    }
                default://
                    UserProperty prop = new UserProperty();
                    prop.setTitle(titles[i]);
                    prop.setValue(val);
                    prop.setType((i == 0) ? 1 : 0);
                    u.getProps().add(prop);
                    break;
                }
            }
            txlService.save(u);
        }
        // }
        IOUtils.closeQuietly(fis);
    }

    private static String getCellValue(Cell cell) throws UnsupportedEncodingException {
        String v = "";
        switch (cell.getCellType()) {
        case Cell.CELL_TYPE_NUMERIC:
            v = String.valueOf(new BigDecimal(cell.getNumericCellValue()));
            break;
        case Cell.CELL_TYPE_BOOLEAN:
            v = String.valueOf(cell.getBooleanCellValue());
            break;
        case Cell.CELL_TYPE_STRING:
            v = cell.getStringCellValue();
        case Cell.CELL_TYPE_BLANK:
        case Cell.CELL_TYPE_ERROR:
        case Cell.CELL_TYPE_FORMULA:
        default:
            break;
        }
        return full2HalfChange(v.trim().replace(" ", ""));
    }

    // /**
    // * @param cells
    // * @param i
    // * @return
    // * @throws UnsupportedEncodingException
    // */
    // private static String getCell(Cell[] cells, int i) throws UnsupportedEncodingException {
    // if (i >= cells.length)
    // return null;
    // if (Strings.isBlank(cells[i].getContents()))
    // return "";
    // return full2HalfChange(cells[i].getContents().trim());
    // }

    // 全角转半角的 转换函数

    public static final String full2HalfChange(String QJstr) throws UnsupportedEncodingException {
        StringBuffer outStrBuf = new StringBuffer("");
        String Tstr = "";
        byte[] b = null;
        for (int i = 0; i < QJstr.length(); i++) {
            Tstr = QJstr.substring(i, i + 1);
            // 全角空格转换成半角空格
            if (Tstr.equals("　")) {
                outStrBuf.append(" ");
                continue;
            }
            b = Tstr.getBytes("unicode");
            // 得到 unicode 字节数据

            if (b[2] == -1) {
                // 表示全角？
                b[3] = (byte) (b[3] + 32);
                b[2] = 0;
                outStrBuf.append(new String(b, "unicode"));
            } else {
                outStrBuf.append(Tstr);
            }
        } // end for.
        return outStrBuf.toString();
    }
}
