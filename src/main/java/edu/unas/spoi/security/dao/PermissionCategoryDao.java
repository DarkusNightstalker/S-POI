/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao;

import edu.unas.spoi.security.dao.interfac.IPermissionCategoryDao;
import edu.unas.spoi.security.model.PermissionCategory;
import gkfire.hibernate.generic.GenericDao;
import org.springframework.stereotype.Repository;

/**
 * The type Permission category dao.
 *
 * @author Jhoan Brayam
 */
@Repository("permissionCategoryDao")
public class PermissionCategoryDao extends GenericDao<PermissionCategory, Integer> implements IPermissionCategoryDao{
    
}
