/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao.interfac;

import edu.unas.spoi.security.model.Permission;
import edu.unas.spoi.security.model.PermissionCategory;
import gkfire.hibernate.generic.interfac.IGenericDao;
import java.util.List;

/**
 * The interface Permission dao.
 *
 * @author Jhoan Brayam
 */
public interface IPermissionDao extends IGenericDao<Permission, Integer>{
    /**
     * Gets by.
     *
     * @param category the category
     * @return the by
     */
    public List<Permission> getBy(PermissionCategory category);
}
