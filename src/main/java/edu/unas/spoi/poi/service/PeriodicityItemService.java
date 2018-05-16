/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service;

import dn.core3.hibernate.generic.GenericService;
import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.poi.dao.interfac.IPeriodicityItemDao;
import edu.unas.spoi.poi.model.Periodicity;
import edu.unas.spoi.poi.model.PeriodicityItem;
import edu.unas.spoi.poi.service.interfac.IPeriodicityItemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Periodicity item service.
 *
 * @author Darkus Nightmare
 */
@Service("periodicityItemService")
public class PeriodicityItemService extends GenericService<PeriodicityItem, Integer> implements IPeriodicityItemService{
    
    @Autowired
    @Qualifier("periodicityItemDao")
    private IPeriodicityItemDao periodicityItemDao;


    @Override
    protected IGenericDao<PeriodicityItem, Integer> getBasicDao() {
        return periodicityItemDao;
    }

    @Override
    public List<PeriodicityItem> getListBy(Periodicity periodicity) {
        return periodicityItemDao.getListBy(periodicity);
     }

}
