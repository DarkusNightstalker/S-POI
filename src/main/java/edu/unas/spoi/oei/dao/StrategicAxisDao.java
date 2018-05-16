/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import dn.core3.hibernate.OrderFactory;
import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.IStrategicAxisDao;
import edu.unas.spoi.oei.model.StrategicAxis;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Strategic axis dao.
 *
 * @author CORE i7
 */
@Repository("strategicAxisDao")
public class StrategicAxisDao extends GenericDao<StrategicAxis, Integer> implements IStrategicAxisDao {

    @Override
    public boolean validateCode(String code, Integer idStrategicPlan, Integer id) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "1 "
                        + "FROM StrategicAxis sa "
                        + "WHERE "
                        + "sa.code LIKE :code AND "
                        + "sa.strategicPlan.id = :idsp AND "
                        + "sa.id <> :id")
                .setParameter("code", code, StringType.INSTANCE)
                .setParameter("idsp", idStrategicPlan, IntegerType.INSTANCE)
                .setParameter("id", id == null ? -1 : id, IntegerType.INSTANCE)
                .uniqueResult() == null;
    }

    @Override
    public List getListLazyForMainView(int page, int rows, Integer year, String code, String name, OrderFactory orderFactory) {
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(StrategicAxis.class)
                .createAlias("strategicPlan", "sp")
                .addOrder(Order.asc("code"))
                .setProjection(
                        Projections.projectionList()
                                .add(Projections.property("id").as("id"))
                                .add(Projections.property("code").as("code"))
                                .add(Projections.property("name").as("name"))
                                .add(Projections.property("sp.startYear").as("startYear"))
                                .add(Projections.property("sp.endYear").as("endYear"))
                                .add(Projections.property("description").as("description")))
                .add(Restrictions.eq("active", true))
                .add(Restrictions.ge("sp.startYear", year))
                .add(Restrictions.le("sp.endYear", year))
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        if (!StringUtils.isBlank(code)) {
            criteria.add(Restrictions.like("code", code.trim(), MatchMode.ANYWHERE));
        }
        if (!StringUtils.isBlank(name)) {
            criteria.add(Restrictions.like("name", name.trim(), MatchMode.ANYWHERE).ignoreCase());
        }
        return criteria.list();
    }

    @Override
    public List getListBasicData(Integer strategicPlanId) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "sa.id as id,"
                            + "sa.code as code,"
                            + "sa.name as name"                        
                        + ") "
                        + "FROM StrategicAxis sa "
                        + "WHERE "
                        + "sa.strategicPlan.id = :idsp AND "
                        + "sa.active = true "
                        + "ORDER BY sa.code")                
                .setParameter("idsp", strategicPlanId, IntegerType.INSTANCE)
                .list();
    }

}
