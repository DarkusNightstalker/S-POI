/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.service;

import dn.core3.hibernate.generic.GenericService;
import dn.core3.hibernate.generic.interfac.IGenericDao;
import edu.unas.spoi.poi.dao.interfac.IPeriodicityDao;
import edu.unas.spoi.poi.model.Periodicity;
import edu.unas.spoi.poi.service.interfac.IPeriodicityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Periodicity service.
 *
 * @author Darkus Nightmare
 */
@Service("periodicityService")
public class PeriodicityService extends GenericService<Periodicity, Short> implements IPeriodicityService{
    
    @Autowired
    @Qualifier("periodicityDao")
    private IPeriodicityDao periodicityDao;


    @Override
    protected IGenericDao<Periodicity, Short> getBasicDao() {
        return periodicityDao;
    }

    @Override
    public Periodicity getBy(Integer operationYear) {
         return periodicityDao.getById(operationYear);
    }
}
