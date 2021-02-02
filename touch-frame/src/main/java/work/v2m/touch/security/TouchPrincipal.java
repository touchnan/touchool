package work.v2m.touch.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.security.Principal;


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
	private String principal;
	private String name;

	@JsonIgnore
	private transient String credential;

	
	public TouchPrincipal(Serializable id, String principal, String name, String credential){
		this.id = id;
		this.name = name;
		this.principal = principal;
		this.credential = credential;
	}
	
	public TouchPrincipal(Object o) {
        if (o != null) {
            try {
                BeanUtils.copyProperties(o,this);
            } catch (Throwable e) {
				LoggerFactory.getLogger(getClass()).error("Fail to copy properties!", e);
            }
        }
	}
	
	/**
	 * 
	 */
	public void clearSensitivity() {
		credential = null;
	}

	/**
	 * @return the id
	 */
	public Serializable getId() {
		return id;
	}

	/**
	 * @return
	 */
	public String getPrincipal() {
		return principal;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String getCredential() {
		return credential;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Serializable id) {
		this.id = id;
	}

	/**
	 * @param principal
	 */
	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}
}
