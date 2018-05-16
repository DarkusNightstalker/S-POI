/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bne.service.interfac;

import edu.unas.spoi.bne.model.BNeSchedule;
import edu.unas.spoi.bne.model.enumerated.Month;
import gkfire.hibernate.generic.interfac.IGenericService;
import java.util.List;
import java.util.Map;

/**
 * The interface Ib ne schedule service.
 *
 * @author Jhoan Brayam
 */
public interface IBNeScheduleService extends IGenericService<BNeSchedule, Long>{

    /**
     * Gets by.
     *
     * @param month the month
     * @param id    the id
     * @return the by
     */
    public BNeSchedule getBy(Month month, Long id);

    /**
     * Gets list bne schedules basic data.
     *
     * @param bneItemId the bne item id
     * @return the list bne schedules basic data
     */
    public List<Map<String, Object>> getListBneSchedulesBasicData(Long bneItemId);

    
}
