/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import dn.core3.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.IDependencyDao;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.DependencyHasABP;
import gkfire.hibernate.OrderFactory;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Dependency dao.
 *
 * @author CORE i7
 */
@Repository("dependencyDao")
public class DependencyDao extends GenericDao<Dependency, Integer> implements IDependencyDao {

    /**
     * Get parent info object [ ].
     *
     * @param dependencyId the dependency id
     * @return the object [ ]
     */
    public Object[] getParentInfo(Integer dependencyId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT d.id,d.path,d.parent.id,d.name FROM Dependency d WHERE d.id = :id");
        query.setInteger("id", dependencyId);
        Object[] result = (Object[]) query.uniqueResult();
        return result;
    }

    @Override
    public Dependency getParent(Integer dependencyId) {
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT d.parent FROM Dependency d WHERE d.id = :id");
        query.setInteger("id", dependencyId);
        Dependency result = (Dependency) query.uniqueResult();
        return result;
    }

    @Override
    public List<DependencyHasABP> getABPInYear(Integer dependencyId, Integer year) {
        if (dependencyId == null) {
            return Collections.EMPTY_LIST;
        }
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "d "
                        + "FROM DependencyHasABP d "
                        + "WHERE d.dependency.id = :id")
                .setInteger("id", dependencyId).list();
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void saveOrUpdate(DependencyHasABP dependencyHasSpecificGoal) {
        getSessionFactory().getCurrentSession().saveOrUpdate(dependencyHasSpecificGoal);
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void delete(DependencyHasABP dependencyHasSpecificGoal) {
        getSessionFactory().getCurrentSession().delete(dependencyHasSpecificGoal);
    }

    @Override
    public List getListLazyForMainView(int page, int rows, String path, String name, Boolean operational, Integer operationYear, Boolean active, OrderFactory orderObject) {
        //<editor-fold defaultstate="collapsed" desc="Construcción criteria">
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(Dependency.class)
                .setProjection(
                        Projections.projectionList()
                                .add(Projections.alias(Projections.property("id"), "id"))
                                .add(Projections.property("path").as("path"))
                                .add(Projections.property("name").as("name"))
                                .add(Projections.property("operational").as("operational"))
                                .add(Projections.property("active").as("active"))
                )
                .add(Restrictions.eq("operationYear", operationYear))
                .addOrder(Order.asc("path"))
                .setMaxResults(rows)
                .setFirstResult((page - 1) * rows)
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        
//</editor-fold>
        if (active != null) {
            criteria.add(Restrictions.eq("active", active));
        }
        if (operational != null) {
            criteria.add(Restrictions.eq("operational", operational));
        }
        if (!StringUtils.isBlank(path)) {
            criteria.add(Restrictions.like("path", path, MatchMode.START));
        }
        if (!StringUtils.isBlank(name)) {
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }
        return criteria.list();
    }

    @Override
    public Long countListForMainView(String path, String name, Boolean operational, Integer operationYear, Boolean active) {
        //<editor-fold defaultstate="collapsed" desc="Construcción de criteria">
        
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(Dependency.class)
                .setProjection(Projections.count("id"))
                .add(Restrictions.eq("operationYear", operationYear));
//</editor-fold>
        if (active != null) {
            criteria.add(Restrictions.eq("active", active));
        }
        if (operational != null) {
            criteria.add(Restrictions.eq("operational", operational));
        }
        if (!StringUtils.isBlank(path)) {
            criteria.add(Restrictions.like("path", path, MatchMode.START));
        }
        if (!StringUtils.isBlank(name)) {
            criteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE).ignoreCase());
        }
        return ((Number) criteria.uniqueResult()).longValue();
    }

    @Override
    public List getListForUserAssigment(List<Integer> dependenciesId, Integer currentDependencyId,Integer operationYear) {
        //<editor-fold defaultstate="collapsed" desc="Contrucción de HQL">
        Query query = getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "d.id,"
                        + "d.path,"
                        + "d.name "
                        + "FROM Dependency d "
                        + "WHERE "
                        + "d.operational = true AND "
                        + "d.operationYear = :year AND "
                        + "d.active = true "
                        + (dependenciesId.isEmpty() ? "" : "AND (d.id = :id OR d.id NOT IN (:list)) ")
                        + "ORDER BY d.path ")
                .setParameter("year", operationYear,IntegerType.INSTANCE);
        
//</editor-fold>
        if (!dependenciesId.isEmpty()) {
            query
                    .setParameter("id", currentDependencyId, IntegerType.INSTANCE)
                    .setParameterList("list", dependenciesId);
        }
        return query.list();
    }

    @Override
    public List getListForCopyPreviousYear(Integer operationYear) {
        //<editor-fold defaultstate="collapsed" desc="Contrucción de HQL">
      return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "d.id as id,"
                            + "d.path as path,"
                            + "d.operational as operational,"
                            + "d.name as name"
                        + ") "
                        + "FROM Dependency d "
                        + "WHERE "
                            + "d.active = true AND "
                            + "d.operationYear = :year "
                        + "ORDER BY d.path")
                .setParameter("year", operationYear, IntegerType.INSTANCE)
                .list();
        
//</editor-fold>
    }

    @Override
    public List<ActivityBudgetProgram> getActivityBudgetProgramsIds(Integer dependencyId) {
        //<editor-fold defaultstate="collapsed" desc="Contrucción de HQL">
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new ActivityBudgetProgram(abp.id) "
                        + "FROM Dependency d  join d.activityBudgetPrograms abp "
                        + "WHERE "
                            + "d.id = :dependencyId")
                .setParameter("dependencyId", dependencyId, IntegerType.INSTANCE)
                .list();
        
//</editor-fold>
    }

    @Override
    public List getListOperationalBasicData(Integer operationYear) {
        //<editor-fold defaultstate="collapsed" desc="Contrucción de HQL">
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "d.id as id,"
                            + "d.path as path,"
                            + "d.name as name"
                        + ") "
                        + "FROM Dependency d "
                        + "WHERE "
                            + "d.operational = true AND "
                            + "d.active = true AND "
                            + "d.operationYear = :year")
                .setParameter("year", operationYear,IntegerType.INSTANCE)
                .list();
        
//</editor-fold>    
    }

    @Override
    public boolean validatePath(String path, Integer operationYear, Integer currentId) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "1 "
                        + "FROM Dependency d "
                        + "WHERE "
                            + "d.operationYear = :year AND "
                            + "d.path LIKE :path AND "
                            + "d.active = true AND "
                            + "d.id <> :id")
                .setParameter("year", operationYear,IntegerType.INSTANCE)
                .setParameter("path", path,StringType.INSTANCE)
                .setParameter("id", currentId == null ? -1 : currentId,IntegerType.INSTANCE)
                .uniqueResult() == null;
    }

    @Override
    public Dependency getBy(String path, Integer operationYear) {
        return (Dependency) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM Dependency d "
                        + "WHERE "
                            + "d.operationYear = :year AND "
                            + "d.path LIKE :path AND "
                            + "d.active = true")
                .setParameter("year", operationYear,IntegerType.INSTANCE)
                .setParameter("path", path,StringType.INSTANCE)
                .uniqueResult();
    }

    @Override
    public boolean getAllowCopy(Integer operationYear) {
        return  getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "DISTINCT 1 "
                        + "FROM Dependency d "
                        + "WHERE "
                            + "d.operationYear = :year AND "
                            + "d.previousYearDependency.operationYear = :previous AND "
                            + "d.active = true")
                .setParameter("year", operationYear, IntegerType.INSTANCE)
                .setParameter("previous", operationYear - 1, IntegerType.INSTANCE)
                .uniqueResult() == null;
    
    }

    @Override
    public List getChildrenDependenciesBasicData(String path, Integer operationYear) {
       return  getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "d.id,"
                            + "d.path,"
                            + "d.name,"
                            + "d.active "
                        + "FROM Dependency d "
                        + "WHERE "
                            + "d.operationYear = :year AND "
                            + "d.path LIKE :path AND "
                            + "d.active = true "
                        + "ORDER BY d.path")
                .setParameter("year", operationYear, IntegerType.INSTANCE)
                .setParameter("path", MatchMode.START.toMatchString(path), StringType.INSTANCE)
                .list();    
    }

    @Override
    public void deleteParent(Integer parentId) {
        getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "UPDATE Dependency d SET "
                        + "d.parent = (SELECT p.parent.id FROM Dependency p WHERE p.id = :parentId),"
                        + "d.code = (SELECT p.code FROM Dependency p WHERE p.id = :parentId)||d.code "
                        + "WHERE "
                            + "d.parent = :parent")
                .setParameter("parentId", parentId, IntegerType.INSTANCE)
                .setParameter("parent", new Dependency(parentId))
                .executeUpdate();    
    }

    
}
