/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.touch.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 
 * @author touchnan
 */
@Entity
@Table(name = "txl_uprop")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProperty extends BaseEntity {

    @Column(name = "col_title", length = 20)
    private String title;

    @Column(name = "col_value", length = 100)
    private String value;

    @Column(name = "col_show_type")
    private int showType;

    @Column(name = "col_type")
    private int type;

    @Column(name = "col_sort")
    private int sort;

//    @ManyToOne()
//    @JoinColumn(name = "col_uid", insertable = false, updatable = false)
//    private User user;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

}
