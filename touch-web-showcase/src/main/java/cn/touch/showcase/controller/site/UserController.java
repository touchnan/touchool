/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.touch.showcase.controller.site;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.touch.security.shiro.TouchPrincipal;
import cn.touch.showcase.entity.User;
import cn.touch.showcase.entity.UserProperty;
import cn.touch.showcase.serv.ITxlService;
import cn.touch.web.controller.BaseController;

/**
 *
 * @author touchnan
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    
    @Autowired
    private ITxlService txlService;
    
//    @PostConstruct
//    public void init() {
//    	URL url = Thread.currentThread().getContextClassLoader().getResource("aa.xls");
//        File file = FileUtils.toFile(url);
//        System.out.println(file.getAbsolutePath());
//        try {
//			readExcel(file);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//    }
    
    public void readExcel(File file) throws IOException {
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

    private String getCellValue(Cell cell) throws UnsupportedEncodingException {
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

    // 全角转半角的 转换函数

    public final String full2HalfChange(String QJstr) throws UnsupportedEncodingException {
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
    
    @RequestMapping(value = "/user", method = {RequestMethod.GET})
    public String editUser(HttpSession session, Model model) {
    	Subject subject = SecurityUtils.getSubject();
    	TouchPrincipal principal = (TouchPrincipal)subject.getPrincipal();
        User u = txlService.findUser((Long)principal.getId());
        model.addAttribute("user", u);
        return "user/edit";
    }
        
   @RequestMapping(value = "/user", method = {RequestMethod.POST})
    public String saveUser(@ModelAttribute("user") User user, BindingResult result, HttpSession session, RedirectAttributes redirectAttrs) {
       //ValidationUtils.rejectIfEmptyOrWhitespace(result.geR, "userName","required.username", "用户名必须填写");
	   //new TypeValidator().validate(p, result);
       if (StringUtils.isBlank(user.getLoginName())) {
             ObjectError e = new ObjectError("user.loginName", "登录名不能为空");
             result.addError(e);
             return "user/edit";
       }
       user = txlService.save(user);
       return "redirect:/txl";
//       return "user/edit";
    }    
    
}
