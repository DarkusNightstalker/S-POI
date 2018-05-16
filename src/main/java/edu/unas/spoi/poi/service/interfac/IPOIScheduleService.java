/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service.interfac;

import dn.core3.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.poi.model.POISchedule;
import java.util.List;
import java.util.Map;

/**
 * The interface Ipoi schedule service.
 *
 * @author Jhoan Brayam
 */
public interface IPOIScheduleService extends IGenericService<POISchedule, Long> {

    /**
     * Gets by.
     *
     * @param periodicityItemId the periodicity item id
     * @param poiActivityId     the poi activity id
     * @return the by
     */
    public POISchedule getBy(Integer periodicityItemId, Long poiActivityId);

    /**
     * Gets quantity.
     *
     * @param periodicityItemId the periodicity item id
     * @param poiActivityId     the poi activity id
     * @return the quantity
     */
    public Integer getQuantity( Integer periodicityItemId,Long poiActivityId);

    /**
     * Gets list poi shedules basic data.
     *
     * @param aLong the a long
     * @return the list poi shedules basic data
     */
    public List<Map<String, Object>> getListPoiShedulesBasicData(Long aLong);
}
