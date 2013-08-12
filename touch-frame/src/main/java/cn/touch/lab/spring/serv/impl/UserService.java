/**
 * 
 */
package cn.touch.lab.spring.serv.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import cn.touch.lab.spring.db.IUserDao;
import cn.touch.lab.spring.serv.IUserService;

/**
 * May 9, 2013
 * @author <a href="mailto:touchnan@gmail.com">chegnqiang.han</a>
 * 
 */
@Service
public class UserService implements IUserService {
	
	@Inject
	private IUserDao userDao;
	
	/* (non-Javadoc)
	 * @see cn.touch.lab.spring.serv.IUserService#validate()
	 */
	@Override
	public boolean validate() {
		System.out.println("invoke service");
		return userDao.validate();
	}

}
