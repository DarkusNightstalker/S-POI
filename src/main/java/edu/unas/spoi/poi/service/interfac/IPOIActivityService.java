/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service.interfac;

import dn.core3.hibernate.OrderFactory;
import dn.core3.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.poi.model.POIActivity;
import edu.unas.spoi.poi.model.PeriodicityItem;
import edu.unas.spoi.security.model.User;
import java.util.List;

/**
 * The interface Ipoi activity service.
 *
 * @author Jhoan Brayam
 */
public interface IPOIActivityService extends IGenericService<POIActivity, Long> {

    /**
     * Gets next code.
     *
     * @param poiId the poi id
     * @return the next code
     */
    public String getNextCode(Long poiId);

    /**
     * Delete.
     *
     * @param id   the id
     * @param user the user
     */
    public void delete(Long id,User user);

    /**
     * Recovery.
     *
     * @param id   the id
     * @param user the user
     */
    public void recovery(Long id,User user);

    /**
     * Gets list lazy for main view.
     *
     * @param page                    the page
     * @param recordsPerPage          the records per page
     * @param poiId                   the poi id
     * @param periodicityItems        the periodicity items
     * @param strategicAxisId         the strategic axis id
     * @param activityBudgetProgramId the activity budget program id
     * @param strategicActivityId     the strategic activity id
     * @param poiUnityId              the poi unity id
     * @param active                  the active
     * @param orderFactory            the order factory
     * @return the list lazy for main view
     */
    public List getListLazyForMainView(int page, int recordsPerPage,Long poiId,List<PeriodicityItem> periodicityItems, Integer strategicAxisId, Long activityBudgetProgramId, Integer strategicActivityId, Integer poiUnityId, Boolean active, OrderFactory orderFactory);

    /**
     * Count list for main view long.
     *
     * @param poiId                   the poi id
     * @param strategicAxisId         the strategic axis id
     * @param activityBudgetProgramId the activity budget program id
     * @param strategicActivityId     the strategic activity id
     * @param poiUnityId              the poi unity id
     * @param active                  the active
     * @return the long
     */
    public Long countListForMainView(Long poiId,Integer strategicAxisId, Long activityBudgetProgramId, Integer strategicActivityId, Integer poiUnityId, Boolean active);
}
