/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao.interfac;

import gkfire.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.security.model.User;
import java.util.List;

/**
 * The interface User dao.
 *
 * @author CORE i7
 */
public interface IUserDao extends IGenericDao<User, Integer> {

    /**
     * Login user.
     *
     * @param username the username
     * @param password the password
     * @return the user
     */
    public User login(String username, String password);

    /**
     * Gets next id.
     *
     * @return the next id
     */
    public Integer getNextId();

    /**
     * Gets permissions code.
     *
     * @param userId the user id
     * @return the permissions code
     */
    public List<String> getPermissionsCode(Integer userId);
}
