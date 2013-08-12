/**
 * 
 */
package test.cn.touch.copy;

import net.sf.cglib.beans.BeanCopier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Aug 11, 2013
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 *
 */
public class CopierTest {
    @Test
    public void testCopy() {
      String name = "韩成强";
      int age = 32;
      CopyObject src = new CopyObject();
      src.setName(name);
      src.setValue(age);
      BeanCopier copy = BeanCopier.create(CopyObject.class, CopyObject.class, false);
      
      CopyObject b = new CopyObject();
      copy.copy(src, b, null);
      Assert.assertEquals("年纪", age, b.getValue());
      Assert.assertEquals("名字", name, b.getName());
      
//      org.apache.commons.beanutils.BeanUtils.copyProperties(targetObj, page.getWhere());
    }
    
    public class CopyObject {
        private String name;
        private int value;
        /**
         * @return the name
         */
        public String getName() {
            return name;
        }
        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }
        /**
         * @return the value
         */
        public int getValue() {
            return value;
        }
        /**
         * @param value the value to set
         */
        public void setValue(int value) {
            this.value = value;
        }
        
    }
}
