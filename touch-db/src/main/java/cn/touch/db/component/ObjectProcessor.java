/*
 * cn.touch.db.component.ObjectProcessor.java
 * Mar 7, 2012 
 */
package cn.touch.db.component;

import java.sql.ResultSet;

/**
 * Mar 7, 2012
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
public interface ObjectProcessor<T extends Object> {
    T invoke(ResultSet rs);
}
