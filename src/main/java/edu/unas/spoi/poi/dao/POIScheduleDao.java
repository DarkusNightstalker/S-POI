/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao;

import dn.core3.hibernate.generic.GenericDao;
import edu.unas.spoi.poi.dao.interfac.IPOIScheduleDao;
import edu.unas.spoi.poi.model.POISchedule;
import java.util.List;
import java.util.Map;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

/**
 * The type Poi schedule dao.
 *
 * @author Jhoan Brayam
 */
@Repository("poiScheduleDao")
public class POIScheduleDao  extends GenericDao<POISchedule, Long> implements IPOIScheduleDao{

    @Override
    public POISchedule getBy(Integer periodicityItemId, Long poiActivityId) {
        return (POISchedule) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM POISchedule ps "
                        + "WHERE "
                            + "ps.poiActivity.id = :poiActivityId AND "
                            + "ps.periodicityItem.id = :periodicityItemId")
                .setParameter("poiActivityId", poiActivityId, LongType.INSTANCE)
                .setParameter("periodicityItemId", periodicityItemId,IntegerType.INSTANCE)
                .uniqueResult();
    }

    @Override
    public Integer getQuantity(Integer periodicityItemId, Long poiActivityId) {
        return (Integer) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT ps.quantity "
                        + "FROM POISchedule ps "
                        + "WHERE "
                            + "ps.poiActivity.id = :poiActivityId AND "
                            + "ps.periodicityItem.id = :periodicityItemId")
                .setParameter("poiActivityId", poiActivityId, LongType.INSTANCE)
                .setParameter("periodicityItemId", periodicityItemId,IntegerType.INSTANCE)
                .uniqueResult();
    }

    @Override
    public List<Map<String, Object>> getListPoiShedulesBasicData(Long poiActivityId) {
           return  getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "ps.periodicityItem.id as periodicityItemId,"
                            + "ps.quantity as quantity"
                        + ") "
                        + "FROM POISchedule ps "
                        + "WHERE "
                            + "ps.poiActivity.id = :poiActivityId ")
                .setParameter("poiActivityId", poiActivityId, LongType.INSTANCE)
                .list();
    }
    
}
