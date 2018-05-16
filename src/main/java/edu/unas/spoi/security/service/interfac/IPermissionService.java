/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.service.interfac;

import edu.unas.spoi.security.model.Permission;
import edu.unas.spoi.security.model.PermissionCategory;
import gkfire.hibernate.generic.interfac.IGenericService;
import java.util.List;

/**
 * The interface Permission service.
 *
 * @author Jhoan Brayam
 */
public interface IPermissionService extends IGenericService<Permission, Integer>{
    /**
     * Gets by.
     *
     * @param category the category
     * @return the by
     */
    public List<Permission> getBy(PermissionCategory category);
}
