/*
 * cn.touch.db.MySQLdbTest.java
 * Sep 15, 2011 
 */
package test.cn.touch.db;

import java.beans.PropertyVetoException;

import org.junit.BeforeClass;
import org.junit.Test;

import cn.touch.db.DbRunner;

import com.jolbox.bonecp.BoneCPDataSource;

/**
 * Sep 15, 2011
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class DbTemplateTest extends DbTest {
    @BeforeClass
    public static void beforeClass() throws PropertyVetoException {
        
        String driver = "org.hsqldb.jdbcDriver";
        String jdbcUrl = "jdbc:hsqldb:mem:touchdb";
        //String jdbcUrl = "jdbc:hsqldb:E:/env/tomcat637\data3.4.2/database/confluencedb"
        
//        String driver = "com.mysql.jdbc.Driver";
//        String jdbcUrl = "jdbc:mysql://localhost:3306/mydb";
                
        String userName = "root";
        String passwd = "";
        
//        BasicDataSource bds = new BasicDataSource();
//        bds.setDriverClassName(driver);
//        bds.setUrl(jdbcUrl);
//        bds.setUsername(userName);
//        bds.setPassword(passwd);
//        DbTemplateTest.db = new DbTemplate(bds);
//        
//        ComboPooledDataSource c3p0 = new ComboPooledDataSource();
//        c3p0.setDriverClass(driver);
//        c3p0.setJdbcUrl(jdbcUrl);
//        c3p0.setUser(userName);
//        c3p0.setPassword(passwd);
//        DbTemplateTest.db = new DbTemplate(c3p0);
        
        
        BoneCPDataSource boneCP = new BoneCPDataSource();
        boneCP.setDriverClass(driver);
        boneCP.setJdbcUrl(jdbcUrl);
        boneCP.setUsername(userName);
        boneCP.setPassword(passwd);
        DbTemplateTest.db = new DbRunner(boneCP);
        
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.touch.db.DbTest#init()
     */
    @Override
    protected void init() {
        //db.update("DROP  TABLE IF EXISTS `tab_u`");
        //db.update("CREATE MEMORY TABLE `tab_u` (`id` INT NOT NULL ,`name` VARCHAR(45) NOT NULL, `state` INT(8),PRIMARY KEY (`id`) )");
        
        db.update("CREATE SCHEMA PUBLIC AUTHORIZATION DBA");
        
        db.update("CREATE USER root PASSWORD ''");
        db.update("GRANT DBA TO root");
        db.update("SET WRITE_DELAY 10");
        db.update("SET SCHEMA PUBLIC");
        
        
//        db.update("DROP  TABLE tab_u");
        db.update("CREATE MEMORY TABLE tab_u(id BIGINT NOT NULL PRIMARY KEY,name VARCHAR(45),state BIGINT)");
    }

    @Test
    public void insert() {
         init();
         db.update("INSERT INTO tab_u (id, name, state) VALUES (1, '红', 0)");
    }
}
