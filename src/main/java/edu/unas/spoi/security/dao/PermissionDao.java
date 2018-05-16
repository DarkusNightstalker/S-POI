/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao;

import edu.unas.spoi.security.dao.interfac.IPermissionDao;
import edu.unas.spoi.security.model.Permission;
import edu.unas.spoi.security.model.PermissionCategory;
import gkfire.hibernate.generic.GenericDao;
import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * The type Permission dao.
 *
 * @author Jhoan Brayam
 */
@Repository("permissionDao")
public class PermissionDao extends GenericDao<Permission, Integer> implements IPermissionDao {

    @Override
    public List<Permission> getBy(PermissionCategory category) {
        Query query = getSessionFactory()
                .getCurrentSession()
                .createQuery("SELECT p FROM Permission p WHERE " + (category != null ? "p.permissionCategory = :category" : "p.permissionCategory is null"));
        if (category != null) {
            query.setEntity("category", category);
        }
        return query.list();
    }

}
