/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.dao;

import edu.unas.spoi.bne.dao.interfac.IBNeItemDao;
import edu.unas.spoi.bne.model.BNeItem;
import gkfire.hibernate.generic.GenericDao;
import java.util.List;
import java.util.Map;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

/**
 * The type B ne item dao.
 *
 * @author Jhoan Brayam
 */
@Repository("bneItemDao")
public class BNeItemDao extends GenericDao<BNeItem, Long> implements IBNeItemDao {

    @Override
    public List<Map<String, Object>> getListFullData(Integer operationYear) {
        return getSessionFactory()
                .getCurrentSession()
                //<editor-fold defaultstate="collapsed" desc="Creacion de HQL">
                .createQuery(""
                        + "SELECT new map("
                            + "d.path as dependencyPath,"
                            + "d.name as dependencyName,"
                            + "d.operationYear as year,"
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
                            + "sa.name as strategicAxisName, "
                            + "fs.code as fundingSourceCode,"
                            + "fs.name as fundingSourceName, "
                            + "c.path as classifierCode,"
                            + "c.name as classifierName, "
                            + "bnei.id as bneItemId,"
                            + "bnei.productCode as necessaryItemCode, "
                            + "bnei.productName as necessaryItemName, "
                            + "bnei.unitPrice as necessaryItemUnitPrice "
                        + ") "
                        + "FROM "
                        + "BNeItem bnei "
                            + "join bnei.classifier c "
                            + "join bnei.fundingSource fs "
                            + "right join bnei.poiActivity poia "
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
                            + "(bnei.active = true OR bnei.active is null) AND "
                            + "d.active = true")
                //</editor-fold>
                .setParameter("year", operationYear, IntegerType.INSTANCE)
                .list();
    }

    @Override
    public List<Map<String, Object>> getScheduledAmount(Integer dependencyId, Integer operationYear, Integer fundingSourceId, Long classifierId) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "bs.month as month,"
                            + "SUM(bs.quantity*bi.unitPrice) as quantity"
                        + ") "
                        + "FROM BNeItem bi join bi.bneSchedules bs "
                        + "WHERE "
                            + "bi.fundingSource.id = :fundingSourceId AND "
                            + "bi.poiActivity.poi.dependency.id = :dependencyId AND "
                            + "bi.classifier.path LIKE (SELECT c.path FROM Classifier c WHERE c.id = :classifierId)||'%' AND "
                            + "bi.poiActivity.poi.year = :operationYear AND "
                            + "bi.active = true AND "
                            + "bi.poiActivity.active  = true "
                        + "GROUP BY bs.month "
                        + "ORDER BY bs.month")
                .setParameter("dependencyId", dependencyId,IntegerType.INSTANCE)
                .setParameter("operationYear", operationYear,IntegerType.INSTANCE)
                .setParameter("fundingSourceId", fundingSourceId,IntegerType.INSTANCE)
                .setParameter("classifierId", classifierId,LongType.INSTANCE)
                .list();
    }

    @Override
    public List<Map<String, Object>> getScheduledAmount(Integer dependencyId, Integer operationYear, Long classifierId) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "bi.fundingSource.id as fundingSourceId,"
                            + "SUM(bs.quantity*bi.unitPrice) as quantity"
                        + ") "
                        + "FROM BNeItem bi join bi.bneSchedules bs "
                        + "WHERE "
                            + "bi.poiActivity.poi.dependency.id = :dependencyId AND "
                            + "bi.classifier.path LIKE (SELECT c.path FROM Classifier c WHERE c.id = :classifierId)||'%' AND "
                            + "bi.poiActivity.poi.year = :operationYear AND "
                            + "bi.active = true AND "
                            + "bi.poiActivity.active  = true "
                        + "GROUP BY bi.fundingSource.id "
                        + "ORDER BY bi.fundingSource.id")
                .setParameter("dependencyId", dependencyId,IntegerType.INSTANCE)
                .setParameter("operationYear", operationYear,IntegerType.INSTANCE)
                .setParameter("classifierId", classifierId,LongType.INSTANCE)
                .list();
    }

}
