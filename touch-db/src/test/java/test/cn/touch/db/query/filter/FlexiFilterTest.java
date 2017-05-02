/**
 * 
 */
package test.cn.touch.db.query.filter;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cn.touch.db.query.filter.FlexiRule;
import cn.touch.db.query.filter.FlexiRule.FilterSopt;
import cn.touch.db.query.filter.FlexiRuleGroup;

/**
 * Aug 11, 2013
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class FlexiFilterTest {
    @Test
    public void test() {
        FlexiRuleGroup ff = new FlexiRuleGroup();
        
        ff.addRule(new FlexiRule().rule(FilterSopt.cn, "S_aa#a.id", "1"));
        StringBuffer sbf = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        ff.filterAnd(sbf, params);
        System.out.println(sbf.toString());
    }
}
