package cn.touch.security;

import java.io.Serializable;
import java.security.Principal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


/**
 * Jan 6, 2015
 *
 * @author <a href="mailto:88052350@qq.com">chengqiang.han</a>
 */
public class TouchPrincipal implements Serializable,Principal {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8512868579864102029L;
	private Serializable id;
	private String loginName;
	private String name;

	@JsonIgnore
	private transient String passwd;

	
	public TouchPrincipal(Serializable id, String loginName, String name, String passwd){
		this.id = id;
		this.name = name;
		this.loginName = loginName;
		this.passwd = passwd;
	}
	
	public TouchPrincipal(Object o) {
        if (o != null) {
            try {
                BeanUtils.copyProperties(o,this);
            } catch (Throwable e) {
                Logger log = LoggerFactory.getLogger(getClass());
                if (log.isErrorEnabled()) {
                    log.error("Fail to copy properties!", e);
                }
            }
        }
	}
	
	/**
	 * 
	 */
	public void clearSensitivity() {
		passwd = null;
	}

	/**
	 * @return the id
	 */
	public Serializable getId() {
		return id;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the passwd
	 */
	public String getPasswd() {
		return passwd;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Serializable id) {
		this.id = id;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param passwd the passwd to set
	 */
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
