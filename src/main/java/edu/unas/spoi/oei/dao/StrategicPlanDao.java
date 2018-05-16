/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import dn.core3.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.IStrategicPlanDao;
import edu.unas.spoi.oei.model.StrategicPlan;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

/**
 * The type Strategic plan dao.
 *
 * @author CORE i7
 */
@Repository("strategicPlanDao")
public class StrategicPlanDao extends GenericDao<StrategicPlan, Integer> implements IStrategicPlanDao {

    @Override
    public StrategicPlan getBy(Integer year) {
        return (StrategicPlan) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM StrategicPlan sp "
                        + "WHERE  :year BETWEEN sp.startYear AND sp.endYear ")
                .setParameter("year", year, IntegerType.INSTANCE)
                .uniqueResult();
    }

    @Override
    public Long countListLazyForMainView(Integer year) {
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(StrategicPlan.class)
                .setProjection(
                        Projections.count("id")
                );
        if (year != null) {
            criteria.add(Restrictions.ge("startYear", year));
            criteria.add(Restrictions.le("endYear", year));
        }
        return (Long) criteria.uniqueResult();
    }

    @Override
    public List getListLazyForMainView(int page, int rows, Integer year) {
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(StrategicPlan.class)
                .setProjection(
                        Projections.projectionList()
                                .add(Projections.property("id").as("id"))
                                .add(Projections.property("name").as("name"))
                                .add(Projections.property("startYear").as("startYear"))
                                .add(Projections.property("endYear").as("endYear")))
                .setMaxResults(rows)
                .setFirstResult((page - 1) * rows)
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        if (year != null) {
            criteria.add(Restrictions.ge("startYear", year));
            criteria.add(Restrictions.le("endYear", year));
        }
        return criteria.list();
    }

    @Override
    public boolean validateYear(Integer year, Integer currentId) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "1 "
                        + "FROM StrategicPlan sp "
                        + "WHERE  "
                        + "(:year BETWEEN sp.startYear AND sp.endYear) AND "
                        + "sp.id <> :id")
                .setParameter("year", year, IntegerType.INSTANCE)
                .setParameter("id", currentId == null ? -1 : currentId, IntegerType.INSTANCE)
                .uniqueResult() == null;

    }
}
