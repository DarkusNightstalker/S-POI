/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.IActivityBudgetProgramDao;
import edu.unas.spoi.oei.model.ActivityBudgetProgram;
import java.util.List;
import java.util.Map;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Activity budget program dao.
 *
 * @author CORE i7
 */
@Repository("activityBudgetProgramDao")
public class ActivityBudgetProgramDao extends GenericDao<ActivityBudgetProgram, Long> implements IActivityBudgetProgramDao {

    @Override
    public boolean existCode(String code, Long id) {
        //<editor-fold defaultstate="collapsed" desc="Construcción de HQL">
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "1 "
                        + "FROM ActivityBudgetProgram abp "
                        + "WHERE "
                        + "abp.code LIKE :code AND "
                        + "abp.id <> :id")
                .setParameter("code", code, StringType.INSTANCE)
                .setParameter("id", id == null ? -1 : id, LongType.INSTANCE)
                .uniqueResult() != null;
        
//</editor-fold>
    }

    @Override
    public List<Map<String, Object>> getListForAssigmentStrategicActivity(Integer strategicActivityId) {
        //<editor-fold defaultstate="collapsed" desc="Construcción de HQL">
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "abp.id as id,"
                            + "abp.code as code,"
                            + "abp.functionalSequence as functionalSequence,"
                            + "abp.name as name,"
                            + "abp.goal as goal,"
                            + "uom.code as uomCode,"
                            + "uom.name as uomName,"
                            + "abp.active as active"
                        + ") "
                        + "FROM ActivityBudgetProgram abp "
                            + "left join abp.uom uom "
                            + "join abp.strategicActivities sta "
                        + "WHERE "
                            + "sta.id = :idsta "
                        + "ORDER BY abp.code")
                .setParameter("idsta", strategicActivityId, IntegerType.INSTANCE)
                .list();
        
//</editor-fold>
    }

    @Override
    public List getListForGiveStrategicActivity(List<Long> idsForExclude) {
        return idsForExclude.isEmpty()
                ? getListForGiveStrategicActivity()
                : getListForGive(idsForExclude);
    }
    @Override
    public List getListForGiveDependency(List<Long> idsForExclude) {        
        return idsForExclude.isEmpty()
                ? getListForGiveDependency()
                : getListForGive(idsForExclude);
    }
    
    @Override
    public List getListForGiveStrategicActivity() {
        return getListForGive();
    }

    @Override
    public List getListForGiveDependency() {        
        return getListForGive();
    }

    @Override
    public List getListForAssigmentDependency(Integer dependencyId) {
        //<editor-fold defaultstate="collapsed" desc="Construcción de HQL">
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "abp.id as id,"
                            + "abp.code as code,"
                            + "abp.functionalSequence as functionalSequence,"
                            + "abp.name as name,"
                            + "abp.goal as goal,"
                            + "uom.code as uomCode,"
                            + "uom.name as uomName,"
                            + "abp.active as active"
                        + ") "
                        + "FROM ActivityBudgetProgram abp "
                            + "left join abp.uom uom "
                            + "join abp.dependencies d "
                        + "WHERE "
                            + "d.id = :dependencyId "
                        + "ORDER BY abp.code")
                .setParameter("dependencyId", dependencyId, IntegerType.INSTANCE)
                .list();
        
//</editor-fold>
    }
    
    private List getListForGive() {        
        //<editor-fold defaultstate="collapsed" desc="Construcción de HQL">
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "abp.id as id,"
                            + "abp.code as code,"
                            + "abp.functionalSequence as functionalSequence,"
                            + "abp.name as name,"
                            + "abp.goal as goal,"
                            + "uom.code as uomCode,"
                            + "uom.name as uomName,"
                            + "abp.active as active"
                        + ") "
                        + "FROM ActivityBudgetProgram abp "
                            + "left join abp.uom uom "
                        + "WHERE abp.active = true  "
                        + "ORDER BY abp.code")
                .list();
        
//</editor-fold>
    }
    private List getListForGive(List<Long> idsForExclude) {  
        //<editor-fold defaultstate="collapsed" desc="Construcción de HQL">
        return getSessionFactory()
                        .getCurrentSession()
                        .createQuery(""
                                + "SELECT new map("
                                    + "abp.id as id,"
                                    + "abp.code as code,"
                                    + "abp.functionalSequence as functionalSequence,"
                                    + "abp.name as name,"
                                    + "abp.goal as goal,"
                                    + "uom.code as uomCode,"
                                    + "uom.name as uomName,"
                                    + "abp.active as active"
                                + ") "
                                + "FROM ActivityBudgetProgram abp "
                                    + "left join abp.uom uom "
                                + "WHERE "
                                    + "abp.active = true AND "
                                    + "abp.id NOT IN (:idsta) "
                                + "ORDER BY abp.code")
                        .setParameterList("idsta", idsForExclude, LongType.INSTANCE)
                        .list();
        
//</editor-fold>
    }
}
