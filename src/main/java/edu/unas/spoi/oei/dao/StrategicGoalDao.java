/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import dn.core3.hibernate.OrderFactory;
import dn.core3.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.IStrategicGoalDao;
import edu.unas.spoi.oei.model.StrategicGoal;
import edu.unas.spoi.oei.model.StrategicPlan;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Strategic goal dao.
 *
 * @author Jhoan Brayam
 */
@Repository("strategicGoalDao")
public class StrategicGoalDao extends GenericDao<StrategicGoal, Integer> implements IStrategicGoalDao {

    @Override
    public boolean validateCode(String code, Integer strategicPlanId, Integer id) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "1 "
                        + "FROM StrategicGoal sg "
                        + "WHERE "
                        + "sg.code LIKE :code AND "
                        + "sg.strategicAxis.strategicPlan = :idsa AND "
                        + "sg.id <> :id")
                .setParameter("code", code, StringType.INSTANCE)
                .setParameter("idsa", strategicPlanId, IntegerType.INSTANCE)
                .setParameter("id", id == null ? -1 : id, IntegerType.INSTANCE)
                .uniqueResult() == null;
    }

    @Override
    public List getListLazyForMainView(int page, int recordsPerPage, Integer strategicPlanId, String code, String name, Integer strategicAxisId, Boolean active, OrderFactory orderFactory) {
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(StrategicGoal.class)
                .createAlias("strategicAxis", "sa")
                .createAlias("sa.strategicPlan", "sp")
                .setProjection(
                        Projections.projectionList()
                                .add(Projections.property("id").as("id"))
                                .add(Projections.property("code").as("code"))
                                .add(Projections.property("name").as("name"))
                                .add(Projections.property("sa.code").as("strategicAxisCode"))
                                .add(Projections.property("sp.startYear").as("startYear"))
                                .add(Projections.property("sp.endYear").as("endYear")))
                .add(Restrictions.eq("sa.strategicPlan", new StrategicPlan(strategicPlanId)))
                .addOrder(Order.asc("code"))
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        if (active != null) {
            criteria.add(Restrictions.eq("active", active));
        }
        if (!StringUtils.isBlank(code)) {
            criteria.add(Restrictions.like("code", code.trim(), MatchMode.ANYWHERE));
        }
        if (!StringUtils.isBlank(name)) {
            criteria.add(Restrictions.like("name", name.trim(), MatchMode.ANYWHERE).ignoreCase());
        }
        if (strategicAxisId != null) {
            criteria.add(Restrictions.eq("sa.id", strategicAxisId));
        }
        return criteria.list();
    }

    @Override
    public Long countListForMainView(Integer strategicPlanId, String code, String name, Integer strategicAxisId, Boolean active) {
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(StrategicGoal.class)
                .createAlias("strategicAxis", "sa")
                .createAlias("sa.strategicPlan", "sp")
                .setProjection(Projections.count("id"))
                .add(Restrictions.eq("sa.strategicPlan", new StrategicPlan(strategicPlanId)));
        if (active != null) {
            criteria.add(Restrictions.eq("active", active));
        }
        if (!StringUtils.isBlank(code)) {
            criteria.add(Restrictions.like("code", code.trim(), MatchMode.ANYWHERE));
        }
        if (!StringUtils.isBlank(name)) {
            criteria.add(Restrictions.like("name", name.trim(), MatchMode.ANYWHERE).ignoreCase());
        }
        if (strategicAxisId != null) {
            criteria.add(Restrictions.eq("sa.id", strategicAxisId));
        }
        return ((Number)criteria.uniqueResult()).longValue();
    }

}
