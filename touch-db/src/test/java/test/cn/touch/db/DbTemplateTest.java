/*
 * cn.touch.db.MySQLdbTest.java
 * Sep 15, 2011 
 */
package test.cn.touch.db;

import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.touch.db.DbRunner;
import cn.touch.db.query.filter.FlexiRule;
import cn.touch.db.query.filter.FlexiRule.FilterSopt;
import cn.touch.db.query.filter.FlexiRuleGroup;

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
        
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        String driver = "org.hsqldb.jdbcDriver";
        String jdbcUrl = "jdbc:hsqldb:mem:mydb";
        //String jdbcUrl = "jdbc:hsqldb:E:/env/tomcat637\data3.4.2/database/confluencedb"
        
//        String driver = "com.mysql.jdbc.Driver";
//        String jdbcUrl = "jdbc:mysql://localhost:3306/mydb";
                
        String userName = "sa";
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
        
//        db.update("DROP TABLE IF EXISTS REAL_PORT");
//        String createTable="CREATE MEMORY  TABLE REAL_PORT(" +
//                "PORT_ID INTEGER NOT NULL PRIMARY KEY," +
//                "NE_ID INTEGER NOT NULL," +
//                "PORT_NO VARCHAR(10)," +
//                "TAKE_STATE VARCHAR(10)," +
//                "RACK VARCHAR(10)," +
//                "COLLECTDATE VARCHAR(10)," +
//                "EXPLAINDATE VARCHAR(10)" +
//                ")";
//        db.update(createTable);
        
        //db.update("DROP  TABLE IF EXISTS `tab_u`");
        //db.update("CREATE MEMORY TABLE `tab_u` (`id` INT NOT NULL ,`name` VARCHAR(45) NOT NULL, `state` INT(8),PRIMARY KEY (`id`) )");
//        db.update("drop table customer");
//        db.update("create table customer(id integer not null primary key,firstname VARCHAR(45),lastname VARCHAR(45) )");
        
//        db.update("CREATE SCHEMA PUBLIC AUTHORIZATION DBA");
//        
//        db.update("CREATE USER root PASSWORD ''");
//        db.update("GRANT DBA TO root");
//        db.update("SET WRITE_DELAY 10");
//        db.update("SET SCHEMA PUBLIC");
        
        
        db.update("DROP TABLE IF EXISTS tab_u");
        db.update("CREATE MEMORY TABLE tab_u(id INTEGER NOT NULL PRIMARY KEY,name VARCHAR(45),state INTEGER)");
    }

    @Test
    public void insert() {
        int count = 10;
        Object[][] params = new Object[count][2];
        for (int i=0; i<count; ) {
            params[i][1] = i%2==0? "红":"蓝";
            params[i][0] = ++i;
        }
        
        db.batchUpdate("INSERT INTO tab_u (id, name, state) VALUES (?, ?, 0)", params);
//         db.update("INSERT INTO tab_u (id, name, state) VALUES (1, '红', 0)");
    }
    
    @Test
    public void find() {
        insert();
        List<Map<String, Object>> a = db.find("select * from tab_u WHERE 1=1", new FlexiRuleGroup().addRule(new FlexiRule().rule(FilterSopt.cn,"S_name","红")));
//        List<Map<String, Object>> a= db.find("select * from tab_u WHERE 1=1 AND ( name LIKE ?)", new Object[]{"%红%"});
        Assert.assertEquals(5L,Long.valueOf(a.size()).longValue());
        
    }
}
