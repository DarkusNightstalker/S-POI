/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao.interfac;

import edu.unas.spoi.security.model.Rol;
import gkfire.hibernate.generic.interfac.IGenericDao;
import java.util.List;
import java.util.Map;

/**
 * The interface Rol dao.
 *
 * @author Jhoan Brayam
 */
public interface IRolDao extends IGenericDao<Rol, Integer>{

    /**
     * Validate code boolean.
     *
     * @param code the code
     * @param id   the id
     * @return the boolean
     */
    public boolean validateCode(String code,Integer id);

    /**
     * Gets permission categories basic data.
     *
     * @param rolId the rol id
     * @return the permission categories basic data
     */
    public List<Map<String, Object>> getPermissionCategoriesBasicData(Integer rolId);

    /**
     * Gets permissions basic data.
     *
     * @param permissionCategoryId the permission category id
     * @param rolId                the rol id
     * @return the permissions basic data
     */
    public List<Map<String, Object>> getPermissionsBasicData(Integer permissionCategoryId, Integer rolId);
    
}
