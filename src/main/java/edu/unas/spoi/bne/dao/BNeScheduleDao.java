/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.dao;

import edu.unas.spoi.bne.dao.interfac.IBNeScheduleDao;
import edu.unas.spoi.bne.model.BNeSchedule;
import edu.unas.spoi.bne.model.enumerated.Month;
import edu.unas.spoi.poi.model.POISchedule;
import gkfire.hibernate.generic.GenericDao;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.type.EntityType;
import org.hibernate.type.EnumType;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

/**
 * The type B ne schedule dao.
 *
 * @author Jhoan Brayam
 */
@Repository("bneScheduleDao")
public class BNeScheduleDao extends GenericDao<BNeSchedule, Long> implements IBNeScheduleDao{

    @Override
    public BNeSchedule getBy(Month month, Long id) {
        return (BNeSchedule) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM BNeSchedule be "
                        + "WHERE "
                            + "be.bneItem.id = :id AND "
                            + "be.month = :month")
                .setParameter("id", id,LongType.INSTANCE)
                .setParameter("month", month)
                .uniqueResult();
        
    }

    @Override
    public List<Map<String, Object>> getListBneSchedulesBasicData(Long bneItemId) {
        return  getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "be.id as id,"
                            + "be.month as month,"
                            + "be.quantity as quantity"
                        + ") "
                        + "FROM BNeSchedule be "
                        + "WHERE "
                            + "be.bneItem.id = :id")
                .setParameter("id", bneItemId,LongType.INSTANCE)
                .list();
        
    }
    
}
