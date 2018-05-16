/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao;

import dn.core3.hibernate.generic.GenericDao;
import edu.unas.spoi.poi.dao.interfac.IPeriodicityDao;
import edu.unas.spoi.poi.model.Periodicity;
import org.springframework.stereotype.Repository;

/**
 * The type Periodicity dao.
 *
 * @author Darkus Nightmare
 */
@Repository("periodicityDao")
public class PeriodicityDao extends GenericDao<Periodicity, Short> implements IPeriodicityDao{

    @Override
    public Periodicity getById(Integer operationYear) {
        return (Periodicity) getSessionFactory()
                .getCurrentSession()
                .createQuery("SELECT DISTINCT poi.periodicity FROM POI poi WHERE poi.year = :year")
                .setParameter("year", operationYear)
                .uniqueResult();
    }
    
}