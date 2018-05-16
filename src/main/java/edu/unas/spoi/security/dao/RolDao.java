/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao;

import edu.unas.spoi.security.dao.interfac.IRolDao;
import edu.unas.spoi.security.model.Rol;
import gkfire.hibernate.generic.GenericDao;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Rol dao.
 *
 * @author Jhoan Brayam
 */
@Repository("rolDao")
public class RolDao extends GenericDao<Rol, Integer> implements IRolDao {

    @Override
    public boolean validateCode(String code, Integer id) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "1 "
                        + "FROM Rol r "
                        + "WHERE "
                        + "r.code LIKE :code AND "
                        + "r.active = true AND "
                        + "r.id <> :id")
                .setParameter("code", code, StringType.INSTANCE)
                .setParameter("id", id == null ? -1 : id, IntegerType.INSTANCE)
                .uniqueResult() == null;
    }

    @Override
    public List<Map<String, Object>> getPermissionCategoriesBasicData(Integer rolId) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT DISTINCT new map("
                            + "p.permissionCategory.id,"
                            + "p.permissionCategory.icon,"
                            + "p.permissionCategory.name"
                        + ") "
                        + "FROM Rol r join r.permissions p "
                        + "WHERE "
                        + "r.id = :id")
                .setParameter("id", rolId, IntegerType.INSTANCE)
                .list();
    }

    @Override
    public List<Map<String, Object>> getPermissionsBasicData(Integer permissionCategoryId, Integer rolId) {
        
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "p.id,"
                            + "p.code,"
                            + "p.name"
                        + ") "
                        + "FROM Rol r join r.permissions p "
                        + "WHERE "
                        + "r.id = :id AND "
                        + "p.permissionCategory.id = :category")
                .setParameter("id", rolId, IntegerType.INSTANCE)
                .setParameter("category", permissionCategoryId, IntegerType.INSTANCE)
                .list();
    }

}
