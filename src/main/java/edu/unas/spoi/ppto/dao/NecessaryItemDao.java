/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.ppto.dao.interfac.INecessaryItemDao;
import edu.unas.spoi.ppto.model.NecessaryItem;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Necessary item dao.
 *
 * @author CORE i7
 */
@Repository("necessaryItemDao")
public class NecessaryItemDao extends GenericDao<NecessaryItem, Integer> implements INecessaryItemDao {

    @Override
    public String getNextCode(Long classifier) {
        String code = (String)getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "COALESCE(MAX(ni.correlative),'0') "
                        + "FROM NecessaryItem ni "
                        + "WHERE ni.classifier.id = :classifier")
                .setParameter("classifier", classifier,LongType.INSTANCE)
                .uniqueResult();
        code = String.valueOf(Integer.parseInt(code) + 1);
        code = StringUtils.leftPad(code, 3, "0");
        return code;
    }

    @Override
    public boolean existCode(String code) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "1 "
                        + "FROM NecessaryItem ni "
                            + "WHERE ni.code LIKE :code")
                .setParameter("code", code, StringType.INSTANCE)
                .uniqueResult() != null;
    }

}
