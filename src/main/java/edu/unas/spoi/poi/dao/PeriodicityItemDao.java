/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao;

import dn.core3.hibernate.generic.GenericDao;
import edu.unas.spoi.poi.dao.interfac.IPeriodicityItemDao;
import edu.unas.spoi.poi.model.Periodicity;
import edu.unas.spoi.poi.model.PeriodicityItem;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * The type Periodicity item dao.
 *
 * @author Darkus Nightmare
 */
@Repository("periodicityItemDao")
public class PeriodicityItemDao extends GenericDao<PeriodicityItem, Integer> implements IPeriodicityItemDao{

    @Override
    public List<PeriodicityItem> getListBy(Periodicity periodicity) {
          return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM PeriodicityItem pi "
                        + "WHERE "
                            + "pi.periodicity = :periodicity "
                        + "ORDER BY pi.ordinal")
                .setParameter("periodicity", periodicity)
                .list();    
    }
    
}
