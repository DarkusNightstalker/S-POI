/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import dn.core3.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.ISpecificGoalDao;
import edu.unas.spoi.oei.model.SpecificGoal;
import java.util.List;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Specific goal dao.
 *
 * @author CORE i7
 */
@Repository("specificGoalDao")
public class SpecificGoalDao extends GenericDao<SpecificGoal, Integer> implements ISpecificGoalDao {

    @Override
    public boolean existCode(String code, Integer idStrategicPlan, Integer id) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "1 "
                        + "FROM SpecificGoal sg "
                        + "WHERE sg.code LIKE :code AND "
                            + "sg.strategicGoal.strategicAxis.strategicPlan = :idsa AND "
                            + "sg.id != :id")
                .setParameter("code", code,StringType.INSTANCE)
                .setParameter("idsa", idStrategicPlan,IntegerType.INSTANCE)
                .setParameter("id", id == null ? -1 : id,IntegerType.INSTANCE)
                .uniqueResult() != null;
    }

    @Override
    public SpecificGoal getByCode(String code,Integer strategicGoalId) {
         return (SpecificGoal) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM SpecificGoal sg "
                        + "WHERE sg.code LIKE :code AND sg.strategicGoal.id = :sgid")
                .setParameter("code", code,StringType.INSTANCE)
                .setParameter("sgid", strategicGoalId,IntegerType.INSTANCE)
                .uniqueResult();
    }

    @Override
    public List getListBasicDataByStrategicAxis(Integer strategicAxisId) {
         return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "sg.id as id, "
                            + "sg.code as code, "
                            + "sg.name as name "
                        + ") "
                        + "FROM SpecificGoal sg "
                        + "WHERE "
                            + "sg.strategicGoal.strategicAxis.id = :idsa AND "
                            + "sg.active = true "
                        + "ORDER BY sg.code")
                .setParameter("idsa", strategicAxisId,IntegerType.INSTANCE)
                .list();
    }

}
