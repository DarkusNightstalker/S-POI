/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service;

import dn.core3.hibernate.generic.GenericService;
import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.poi.dao.interfac.IPOIScheduleDao;
import edu.unas.spoi.poi.model.POISchedule;
import edu.unas.spoi.poi.service.interfac.IPOIScheduleService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Poi schedule service.
 *
 * @author Jhoan Brayam
 */
@Service("poiScheduleService")
public class POIScheduleService extends GenericService<POISchedule, Long> implements IPOIScheduleService {

    @Autowired
    @Qualifier("poiScheduleDao")
    private IPOIScheduleDao poiScheduleDao;

    @Override
    protected IGenericDao<POISchedule, Long> getBasicDao() {
        return poiScheduleDao;
    }
    @Override
    public POISchedule getBy(Integer periodicityItemId, Long poiActivityId) {
        return poiScheduleDao.getBy(periodicityItemId, poiActivityId);
    }

    @Override
    public Integer getQuantity(Integer periodicityItemId, Long poiActivityId) {
        return poiScheduleDao.getQuantity(periodicityItemId, poiActivityId);
    }


    @Override
    public List<Map<String, Object>> getListPoiShedulesBasicData(Long poiActivityId) {
        return poiScheduleDao.getListPoiShedulesBasicData( poiActivityId);
    }

}
