package cn.touch.controller.site;

import java.io.ByteArrayOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.touch.controller.BaseController;
import cn.touch.serv.ITxlService;

/**
 * 
 * @author touchnan
 */
@Controller
@RequestMapping("/txl")
public class TxlController extends BaseController{
//    private static final Logger logger = LoggerFactory.getLogger(TxlController.class);

    @Autowired
    private ITxlService txlService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("users", txlService.findAll());
        return "txl/txl";
    }

//    @RequestMapping(value = "/exp", method = RequestMethod.GET)
//    public String list(HttpServletResponse resp) {
//        // resp.setContentType("application/binary;charset=ISO8859_1");
//        resp.setContentType("application/vnd.ms-excel;charset=UTF-8");
//        try {
//
//            // <param name="contentType">*/*;charset=UTF-8</param>
//            // <param name="contentType">*/*;charset=UTF-8</param
//            // <param name="contentDisposition">attachment;filename="${showName}";filename*=UTF-8''${showName}</param>
//
//            String fileName = "00计A通讯录" + ".xlsx";
//            try {
//                fileName = java.net.URLEncoder.encode(fileName,"UTF-8");
//            } catch (Exception e) {
//                logger.error("导出通讯录URLEncoder.encode(fileName,\"UTF-8\")出错", e);
//            }
//            resp.setHeader("Content-disposition", "attachment;filename=\"" + fileName + "\";filename*=UTF-8''"
//                    + fileName);// 组装附件名称和格式
//            ServletOutputStream outputStream = resp.getOutputStream();
//            txlService.exportTxl(outputStream);
//        } catch (IOException e) {
//            logger.error("导出通讯录出错", e);
//            throw new RuntimeException(e);
//        }
//        return null;
//    }
    
    @RequestMapping(value = "/exp", method = RequestMethod.GET)
    public ResponseEntity<byte[]> list() {
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          txlService.exportTxl(bos);
          return down("通讯录.xlsx", MediaType.parseMediaType("application/vnd.ms-excel;charset=UTF-8"),bos.toByteArray());
    }    	
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model) {
        // for (int i=0; i<50; i++) {
        // UserDto u = new UserDto();
        // u.setLoginName("u"+i);
        // u.setPasswd(Long.toString(i));
        // for (int j=0; j<7; j++) {
        // UserPropertyDto p = new UserPropertyDto();
        // p.setTitle("哈哈:"+i);
        // p.setValue("从来不知道你说"+i);
        // if (j==0) {
        // p.setType(1);
        // }
        // u.getPropDtos().add(p);
        // }
        // txlService.save(u);
        // }
        return "txl/edit";
    }

}