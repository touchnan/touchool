/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.touch.showcase.serv;

import java.io.OutputStream;
import java.util.List;

import cn.touch.showcase.entity.User;

/**
 *
 * @author touchnan
 */
public interface ITxlService {

    public List<User> findAll();

    public User save(User u);

    public User login(User user);

    public User findUser(Long id);

    /**
     * @param sos
     */
    public void exportTxl(OutputStream sos);
}
