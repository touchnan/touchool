/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.touch.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author touchnan
 */
@Entity
@Table(name = "txl_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class User extends BaseEntity {

    @Column(name = "col_login", length = 20)
    private String loginName;

    @Column(name = "col_passwd", length = 128)
    private String passwd;

    @Column(name = "col_type")
    private int type;
    
    @Column(name="col_u_time")
    private Date updateDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "col_uid")
    //@Where(clause=("state<>-1"))
    @OrderBy("sort DESC, id ASC")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<UserProperty> props = new LinkedHashSet<UserProperty>();
    private List<UserProperty> props = new ArrayList<UserProperty>();

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

//    public Set<UserProperty> getProps() {
//        return props;
//    }
//
//    public void setProps(Set<UserProperty> props) {
//        this.props = props;
//    }
    
    /**
	 * @return the props
	 */
	public List<UserProperty> getProps() {
		return props;
	}

	/**
	 * @param props the props to set
	 */
	public void setProps(List<UserProperty> props) {
		this.props = props;
	}

	/**
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

	/**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}
