/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service;

import dn.core3.hibernate.OrderFactory;
import dn.core3.hibernate.generic.GenericService;
import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.poi.dao.interfac.IPOIActivityDao;
import edu.unas.spoi.poi.dao.interfac.IPOIScheduleDao;
import edu.unas.spoi.poi.model.POIActivity;
import edu.unas.spoi.poi.model.PeriodicityItem;
import edu.unas.spoi.poi.service.interfac.IPOIActivityService;
import edu.unas.spoi.security.model.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Poi activity service.
 *
 * @author Jhoan Brayam
 */
@Service("poiActivityService")
public class POIActivityService extends GenericService<POIActivity, Long> implements IPOIActivityService {

    @Autowired
    @Qualifier("poiActivityDao")
    private IPOIActivityDao poiActivityDao;
    @Autowired
    @Qualifier("poiScheduleDao")
    private IPOIScheduleDao poiScheduleDao;

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(POIActivity object) {
        object.setActive(false);
        update(object);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void delete(Long id, User user) {
        if (!poiActivityDao.isActive(id)) {
            throw new IllegalArgumentException("La actividad ya fue eliminada");
        }
        poiActivityDao.setActive(id, false, user);
    }

    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void recovery(Long id, User user) {
        if (poiActivityDao.isActive(id)) {
            throw new IllegalArgumentException("La actividad ya fue recuperada");
        }
        poiActivityDao.setActive(id, true, user);
    }

    @Override
    public String getNextCode(Long poiId) {
        return poiActivityDao.getNextCode(poiId);
    }

    @Override
    public List getListLazyForMainView(int page, int recordsPerPage, Long poiId, List<PeriodicityItem> periodicityItems, Integer strategicAxisId, Long activityBudgetProgramId, Integer strategicActivityId, Integer poiUnityId, Boolean active, OrderFactory orderFactory) {
        List<Map<String, Object>> result = poiActivityDao.getListLazyForMainView(page, recordsPerPage, poiId, strategicAxisId, activityBudgetProgramId, strategicActivityId, poiUnityId, active, orderFactory);

        for (Map<String, Object> item : result) {
            Map<Integer, Integer> map = new HashMap();
            Integer total = 0;
            for (PeriodicityItem periodicityItem : periodicityItems) {
                Integer current;
                try {
                    current = poiScheduleDao.getQuantity(periodicityItem.getId(), (Long) item.get("id"));
                } catch (IndexOutOfBoundsException e) {
                    current = 0;
                }
                total += current;
                map.put(periodicityItem.getId(), current);
            }
            item.put("schedule", map);
            item.put("total", total);
        }
        return result;
    }

    @Override
    public Long countListForMainView(Long poiId, Integer strategicAxisId, Long activityBudgetProgramId, Integer strategicActivityId, Integer poiUnityId, Boolean active) {
        return poiActivityDao.countListForMainView(poiId, strategicAxisId, activityBudgetProgramId, strategicActivityId, poiUnityId, active);
    }

    @Override
    protected IGenericDao<POIActivity, Long> getBasicDao() {
        return poiActivityDao;
    }

}
