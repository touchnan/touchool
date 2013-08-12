/**
 * 
 */
package cn.touch.lab.spring.db.impl;

import org.springframework.stereotype.Repository;

import cn.touch.lab.spring.db.IUserDao;

/**
 * May 9, 2013
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
@Repository
public class UserDao implements IUserDao {

	/* (non-Javadoc)
	 * @see cn.touch.lab.spring.db.IUserDao#validate()
	 */
	@Override
	public boolean validate() {
		System.out.println("invoke dao");
		return false;
	}
	
}
