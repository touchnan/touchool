/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.touch.showcase.serv.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.hibernate.Hibernate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.touch.security.crypto.Encoder;
import cn.touch.security.shiro.ITouchSubjectDao;
import cn.touch.security.shiro.TouchPrincipal;
import cn.touch.security.shiro.TouchUsernamePasswordToken;
import cn.touch.showcase.db.ITxlDao;
import cn.touch.showcase.entity.User;
import cn.touch.showcase.entity.UserProperty;
import cn.touch.showcase.serv.ITxlService;
import cn.touch.util.ExportUtil;

/**
 * 
 * @author touchnan
 */
@Service
@Transactional(readOnly = true)
public class TxlService implements ITxlService,ITouchSubjectDao {

	@Autowired()
    private ITxlDao txlDao;

    @Autowired()
    private Encoder encode;
    
    @Override
    public List<User> findAll() {
        return txlDao.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User u) {
        if (u.getId() != null) {
            return update(u);
        } else {
            return insert(u);
        }
    }

    private User update(User uDto) {
        User dbUser = this.txlDao.find(uDto.getId());
        if (StringUtils.isNotBlank(uDto.getPasswd())) {
            dbUser.setPasswd(encode.encode(uDto.getPasswd().trim()));
            //dbUser.setPasswd(uDto.getPasswd());
        }
        dbUser.setLoginName(uDto.getLoginName());
        dbUser.setUpdateDate(new Date());
        if (uDto.getProps() != null && !uDto.getProps().isEmpty()) {
            for (UserProperty propDto : uDto.getProps()) {
                if (propDto.getId() != null) {
                    for (UserProperty dbProp : dbUser.getProps()) {
                        if (dbProp.getId().equals(propDto.getId())) {
                            dbProp.setValue(propDto.getValue());
                            if (StringUtils.isNotBlank(propDto.getTitle())) {
                                dbProp.setTitle(propDto.getTitle());
                            }
                        }
                    }
                }
            }
            this.txlDao.save(dbUser);
        }
        return dbUser;
    }

    // private User update(User uDto) {
    // User dbUser = this.txlDao.find(uDto.getId());
    // if (StringUtils.isNotBlank(uDto.getPasswd())) {
    // dbUser.setPasswd(uDto.getPasswd());
    // }
    // dbUser.setLoginName(uDto.getLoginName());
    // dbUser.setUpdateDate(new Date());
    //
    // if (uDto.getPropDtos()!=null && !uDto.getPropDtos().isEmpty()) {
    // Set<UserProperty> updateSets = new HashSet<UserProperty>();
    // Set<UserProperty> delSets = new HashSet<UserProperty>();
    // Set<UserProperty> newSets = new HashSet<UserProperty>();
    // for (UserPropertyDto propDto : uDto.getPropDtos()) {
    // if (propDto.getId()!=null) {
    // for (UserProperty dbProp : dbUser.getProps()) {
    // if (dbProp.getId().equals(propDto.getId())) {
    // if (StringUtils.isNotBlank(propDto.getTitle())&& StringUtils.isNotBlank(propDto.getValue())) {
    // dbProp.setTitle(propDto.getTitle());
    // dbProp.setValue(propDto.getValue());
    // updateSets.add(dbProp);
    // } else {
    // delSets.add(dbProp);
    // }
    // }
    // }
    // } else if (StringUtils.isNotBlank(propDto.getTitle())&& StringUtils.isNotBlank(propDto.getTitle())) {
    // UserProperty newProp = new UserProperty();
    // newProp.setTitle(propDto.getTitle());
    // newProp.setValue(propDto.getTitle());
    // newSets.add(newProp);
    // }
    // }
    //
    // for (UserProperty dbProp : dbUser.getProps()) {
    // boolean match = false;
    // for (UserPropertyDto propDto : uDto.getPropDtos()) {
    // if (propDto.getId()!=null) {
    // if (dbProp.getId().equals(propDto.getId())) {
    // match = true;
    // break;
    // }
    // }
    // }
    // if (!match) {
    // delSets.add(dbProp);
    // }
    // }
    //
    // dbUser.getProps().clear();
    // dbUser.getProps().addAll(updateSets);
    // dbUser.getProps().addAll(newSets);
    // this.txlDao.save(dbUser);
    // this.txlDao.deletes(UserProperty.class,delSets);
    // } else {
    // Set<UserProperty> ups = dbUser.getProps();
    // dbUser.getProps().clear();
    // //this.txlDao.delete(ups);
    // this.txlDao.deletes(UserProperty.class, ups);
    // }
    //
    // return new User(dbUser);
    // }

    private User insert(User uDto) {
        User dbUser = new User();
        if (StringUtils.isNotBlank(uDto.getPasswd())) {
            dbUser.setPasswd(encode.encode(uDto.getPasswd().trim()));
        }
        dbUser.setLoginName(uDto.getLoginName());
        dbUser.setType(uDto.getType());
        // dbUser.setUpdateDate(new Date());
        if (uDto.getProps() != null && !uDto.getProps().isEmpty()) {
            for (UserProperty propDto : uDto.getProps()) {
                UserProperty up = new UserProperty();
                up.setTitle(propDto.getTitle());
                up.setValue(propDto.getValue());
                up.setType(propDto.getType());
                dbUser.getProps().add(up);
            }
        }
        this.txlDao.save(dbUser);
        return dbUser;
    }

    @Override
    public User login(User user) {
        User u = txlDao.findByLoginName(user.getLoginName());
        if (u != null) {
            if (encode.canDecode(u.getPasswd())) {
                if (encode.isValid(user.getPasswd().trim(), u.getPasswd())) {
                    return u;
                }
            }
        }
        return null;
    }

    @Override
    public User findUser(Long id) {
    	 User u = txlDao.find(id);
    	 Hibernate.initialize(u);
         return u;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.zkytxl.serv.ITxlService#exportTxl(javax.servlet.ServletOutputStream)
     */
    @Override
    public void exportTxl(OutputStream sos) {

        List<String> labels = this.txlDao.findLabels();

        // 创建一个workbook 对应一个excel应用文件
        XSSFWorkbook workBook = new XSSFWorkbook();
        // 在workbook中添加一个sheet,对应Excel文件中的sheet
        XSSFSheet sheet = workBook.createSheet("通讯录");
        ExportUtil exportUtil = new ExportUtil(workBook, sheet);
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
        // 构建表头
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;
        for (int i = 0, s = labels.size(); i < s; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(labels.get(i));
        }

        List<User> us = this.txlDao.findAll();

        // 构建表体数据
        if (us != null && !us.isEmpty()) {

            for (int i = 0, s = us.size(); i < s; i++) {
                XSSFRow bodyRow = sheet.createRow(1 + i);
                User u = us.get(i);
                List<UserProperty> ups = u.getProps();
                for (int j = 0, sx = labels.size(); j < sx; j++) {
                    cell = bodyRow.createCell(j);
                    cell.setCellStyle(bodyStyle);
                    UserProperty up = find(ups, labels.get(j));
                    if (up != null) {
                        if (StringUtils.isNumeric(up.getValue())) {
                            cell.setCellValue(new BigDecimal(up.getValue()).doubleValue());
                        } else {
                            cell.setCellValue(up.getValue());
                        }
                    }
                }
            }
        }
        try {
            workBook.write(sos);
            sos.flush();
            IOUtils.closeQuietly(sos);
        } catch (IOException e) {
            LoggerFactory.getLogger(TxlService.class).error("导出通讯录", e);
        } finally {
            IOUtils.closeQuietly(sos);
        }

    }

    /**
     * @param ups
     * @param string
     * @return
     */
    private UserProperty find(List<UserProperty> ups, String title) {
        if (StringUtils.isNotBlank(title) && ups != null) {
            for (UserProperty up : ups) {
                if (StringUtils.equals(up.getTitle(), title)) {
                    return up;
                }
            }
        }
        return null;
    }

	/* (non-Javadoc)
	 * @see cn.touch.security.shiro.ITouchSubjectDao#getAuthorizationInfo(cn.touch.security.shiro.TouchPrincipal)
	 */
	@Override
	public SimpleAuthorizationInfo getAuthorizationInfo(TouchPrincipal principal) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.touch.security.shiro.ITouchSubjectDao#getAuthorizationInfo(cn.touch.security.shiro.TouchUsernamePasswordToken)
	 */
	@Override
	public TouchPrincipal getAuthenticationInfo(TouchUsernamePasswordToken userToken) {
		User u = txlDao.findByLoginName(userToken.getUsername());
		return new TouchPrincipal(u);
	}
}
