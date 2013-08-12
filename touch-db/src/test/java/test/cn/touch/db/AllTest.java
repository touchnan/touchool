/*
 * cn.touch.AllTest.java
 * Sep 15, 2011 
 */
package test.cn.touch.db;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import test.cn.touch.db.query.filter.FlexiFilterTest;


/**
 * Sep 15, 2011
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({DbTest.class,FlexiFilterTest.class})
public class AllTest {

}
