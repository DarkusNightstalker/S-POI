/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao;

import dn.core3.hibernate.OrderFactory;
import dn.core3.hibernate.generic.GenericDao;
import edu.unas.spoi.poi.dao.interfac.IPOIActivityDao;
import edu.unas.spoi.poi.model.POI;
import edu.unas.spoi.poi.model.POIActivity;
import edu.unas.spoi.security.model.User;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

/**
 * The type Poi activity dao.
 *
 * @author Jhoan Brayam
 */
@Repository("poiActivityDao")
public class POIActivityDao extends GenericDao<POIActivity, Long> implements IPOIActivityDao {

    @Override
    public String getNextCode(Long poiId) {
        String result = (String) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "COALESCE(MAX(pa.code),'0') "
                        + "FROM POIActivity pa "
                        + "WHERE pa.poi.id = :poi")
                .setParameter("poi", poiId, LongType.INSTANCE)
                .uniqueResult();

        String code = StringUtils.leftPad(
                String.valueOf(
                        Integer.parseInt(result) + 1
                ), 4, "0");

        return code;
    }

    @Override
    public List<Map<String, Object>> getListLazyForMainView(
            int page,
            int rows,
            Long poiId,
            Integer strategicAxisId,
            Long activityBudgetProgramId,
            Integer strategicActivityId,
            Integer poiUnityId, Boolean active, OrderFactory orderFactory) {
        //<editor-fold defaultstate="collapsed" desc="Construcción criteria">
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(POIActivity.class)
                .createAlias("poiUnity", "pu")
                .createAlias("activityBudgetProgram", "abp")
                .createAlias("specificActivity", "sac")
                .createAlias("sac.specificGoal", "sg")
                .createAlias("sg.strategicGoal", "st")
                .createAlias("st.strategicAxis", "sa")
                .setProjection(
                        Projections.projectionList()
                                .add(Projections.property("id").as("id"))
                                .add(Projections.property("code").as("code"))
                                .add(Projections.property("detail").as("name"))
                                .add(Projections.property("createDate").as("createDate"))
                                .add(Projections.property("editDate").as("editDate"))
                                .add(Projections.property("id").as("schedule"))
                                .add(Projections.property("id").as("total"))
                                .add(Projections.property("sa.code").as("strategicAxisCode"))
                                .add(Projections.property("sa.name").as("strategicAxisName"))
                                .add(Projections.property("st.code").as("strategicGoalCode"))
                                .add(Projections.property("st.name").as("strategicGoalName"))
                                .add(Projections.property("sac.code").as("strategicActivityCode"))
                                .add(Projections.property("sac.name").as("strategicActivityName"))
                                .add(Projections.property("abp.code").as("activityBudgetProgramCode"))
                                .add(Projections.property("abp.name").as("activityBudgetProgramName"))
                                .add(Projections.property("abp.functionalSequence").as("functionalSequence"))
                                .add(Projections.property("pu.code").as("poiUnityCode"))
                                .add(Projections.property("pu.name").as("poiUnityName"))
                                .add(Projections.property("active").as("active"))
                )
                .add(Restrictions.eq("poi", new POI(poiId)))
                .setMaxResults(rows)
                .setFirstResult((page - 1) * rows)
                .setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

        //</editor-fold>
//        if (active != null) {
//            criteria.add(Restrictions.eq("active", true));
//        }
        criteria.add(Restrictions.eq("active", true));
        if (strategicAxisId != null) {
            criteria.add(Restrictions.eq("sa.id", strategicAxisId));
            if (activityBudgetProgramId != null) {
                criteria.add(Restrictions.eq("abp.id", activityBudgetProgramId));
            }
            if (strategicActivityId != null) {
                criteria.add(Restrictions.eq("sac.id", strategicActivityId));
            }
        }
        if (poiUnityId != null) {
            criteria.add(Restrictions.eq("pu.id", poiUnityId));
        }
        return criteria.list();
    }

    @Override
    public Long countListForMainView(Long poiId, Integer strategicAxisId, Long activityBudgetProgramId, Integer strategicActivityId, Integer poiUnityId, Boolean active) {
        //<editor-fold defaultstate="collapsed" desc="Construcción criteria">
        Criteria criteria = getSessionFactory()
                .getCurrentSession()
                .createCriteria(POIActivity.class)
                .createAlias("poiUnity", "pu")
                .createAlias("activityBudgetProgram", "abp")
                .createAlias("specificActivity", "sac")
                .createAlias("sac.specificGoal", "sg")
                .createAlias("sg.strategicGoal", "st")
                .createAlias("st.strategicAxis", "sa")
                .setProjection(Projections.count("id"))
                .add(Restrictions.eq("poi", new POI(poiId)));

        //</editor-fold>
        if (active != null) {
            criteria.add(Restrictions.eq("active", active));
        }
        if (strategicAxisId != null) {
            criteria.add(Restrictions.eq("sa.id", strategicAxisId));
            if (activityBudgetProgramId != null) {
                criteria.add(Restrictions.eq("abp.id", activityBudgetProgramId));
            }
            if (strategicActivityId != null) {
                criteria.add(Restrictions.eq("sac.id", strategicActivityId));
            }
        }
        if (poiUnityId != null) {
            criteria.add(Restrictions.eq("pu.id", poiUnityId));
        }
        return ((Number) criteria.uniqueResult()).longValue();
    }

    @Override
    public void setActive(Long id, boolean active, User user) {
        getSessionFactory()
                .getCurrentSession()
                .createQuery("UPDATE POIActivity p SET "
                        + "p.active = :active,"
                        + "p.editUser = :user,"
                        + "p.editDate = :date "
                        + "WHERE p.id = :id")
                .setParameter("active", active, BooleanType.INSTANCE)
                .setParameter("date", new Date(), DateType.INSTANCE)
                .setParameter("id", id, LongType.INSTANCE)
                .setParameter("user", user)
                .executeUpdate();
    }

}
