/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.touch.showcase.db;

import java.util.List;
import java.util.Set;

import cn.touch.showcase.entity.User;

/**
 *
 * @author touchnan
 */
public interface ITxlDao {
    public List<User> findAll();
    
    public User save(User u);
    
    public User find(Long id);
    
    public User find(String propValue);

    public User findByLoginName(String loginName);

    /**
     * @param clazz
     * @param entities
     */
    public void deletes(Class<?> clazz, Set<?> entities);

    /**
     * @return
     */
    public List<String> findLabels();
}
