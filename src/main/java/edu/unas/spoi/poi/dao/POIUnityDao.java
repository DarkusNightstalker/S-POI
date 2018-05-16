/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.poi.dao;

import edu.unas.spoi.poi.dao.interfac.IPOIUnityDao;
import edu.unas.spoi.poi.model.POIUnity;
import gkfire.hibernate.generic.GenericDao;
import org.hibernate.Query;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Poi unity dao.
 *
 * @author Jhoan Brayam
 */
@Repository("poiUnityDao")
public class POIUnityDao extends GenericDao<POIUnity, Integer> implements IPOIUnityDao {

    @Override
    public boolean existCode(String code, Integer id) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "1 "
                        + "FROM POIUnity pu "
                        + "WHERE "
                            + "pu.code LIKE :code AND "
                            + "pu.id <> :id")
                .setParameter("code", code, StringType.INSTANCE)
                .setParameter("id", id == null ? -1 : id, IntegerType.INSTANCE)
                .uniqueResult() != null;
    }

}
