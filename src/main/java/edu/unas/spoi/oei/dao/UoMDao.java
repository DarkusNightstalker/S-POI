/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.IUoMDao;
import edu.unas.spoi.oei.model.UoM;
import org.hibernate.Query;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Uo m dao.
 *
 * @author CORE i7
 */
@Repository("uomDao")
public class UoMDao extends GenericDao<UoM, Integer> implements IUoMDao {

    @Override
    public boolean existCode(String code, Integer id) {
        return getSessionFactory()
                .getCurrentSession().
                createQuery(""
                        + "SELECT "
                            + "1 "
                        + "FROM UoM uom "
                        + "WHERE "
                            + "uom.code LIKE :code AND "
                            + "uom.id <> :id")
                .setParameter("code", code,StringType.INSTANCE)
                .setParameter("id", id == null ? -1 : id,IntegerType.INSTANCE)
                .uniqueResult() != null;
    }

}
