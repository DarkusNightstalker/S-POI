/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao;

import edu.unas.spoi.poi.dao.interfac.IPOIDao;
import edu.unas.spoi.poi.model.POI;
import gkfire.hibernate.generic.GenericDao;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

/**
 * The type Poi dao.
 *
 * @author Jhoan Brayam
 */
@Repository("poiDao")
public class POIDao extends GenericDao<POI, Long> implements IPOIDao{

    @Override
    public POI getBy(Integer operationYear, Integer dependencyId) {
          return (POI) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM POI poi "
                        + "WHERE "
                            + "poi.year = :operationYear AND "
                            + "poi.dependency.id = :dependencyId")
                .setParameter("operationYear", operationYear, IntegerType.INSTANCE)
                .setParameter("dependencyId", dependencyId,IntegerType.INSTANCE)
                .uniqueResult();
    
    }
    @Override
    public List<Map<String, Object>> getListFullData(Integer operationYear) {
        Session session =getSessionFactory()
                .getCurrentSession();
        return  session.createQuery(""
                        + "SELECT new map("
                            + "d.path as dependencyPath,"
                            + "d.name as dependencyName,"
                            + "d.operationYear as year,"
                            + "poia.id as poiActivityId,"
                            + "poia.code as poiActivityCode,"
                            + "poia.detail as poiActivityName,"
                            + "poia.priority as poiActivityPriority,"
                            + "abp.code as activityBudgetProgramCode,"
                            + "abp.name as activityBudgetProgramName,"
                            + "poiu.code as poiUnityCode,"
                            + "poiu.name as poiUnityName,"
                            + "sta.code as strategicActivityCode,"
                            + "sta.name as strategicActivityName,"
                            + "spg.code as specificGoalCode,"
                            + "spg.name as specificGoalName,"
                            + "stg.code as strategicGoalCode,"
                            + "stg.name as strategicGoalName,"
                            + "sa.code as strategicAxisCode,"
                            + "sa.name as strategicAxisName "
                        + ") "
                        + "FROM "
                        + "POIActivity poia "
                        + "join poia.activityBudgetProgram abp "
                        + "left join poia.poiUnity poiu "
                        + "join poia.specificActivity sta "
                        + "join sta.specificGoal spg "
                        + "join spg.strategicGoal stg "
                        + "join stg.strategicAxis sa "
                        + "right join poia.poi poi "
                        + "right join poi.dependency d "
                        + "WHERE "
                            + "d.operationYear = :year AND "
                            + "(poia.active = true OR poia.active is null) AND "
                            + "d.active = true")
                .setParameter("year", operationYear, IntegerType.INSTANCE)
                .list();
    
    }
    
//    @Override
//    public List<Map<String, Object>> getListFullData(Integer operationYear) {

//    
//    }
}
