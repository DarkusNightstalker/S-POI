/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.ppto.dao.interfac.IFundingSourceDao;
import edu.unas.spoi.ppto.model.FundingSource;
import java.util.List;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Funding source dao.
 *
 * @author CORE i7
 */
@Repository("fundingSourceDao")
public class FundingSourceDao extends GenericDao<FundingSource, Integer> implements IFundingSourceDao {
    
    @Override
    public boolean existCode(String code,Integer id){
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "1 "
                        + "FROM FundingSource fs "
                        + "WHERE "
                            + "fs.code LIKE :code AND "
                            + "fs.id <> :id")
                .setParameter("code", code, StringType.INSTANCE)
                .setParameter("id", id == null ? -1 : id, IntegerType.INSTANCE)
                .uniqueResult() != null;
    }

    @Override
    public List getListBasicData(List<Integer> idNotInclude) {
           return idNotInclude.isEmpty() ? getListBasicData() : getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "fs.id as id,"
                            + "fs.code as code,"
                            + "fs.abbr as abbr,"
                            + "fs.name as name"
                        + ") "
                        + "FROM FundingSource fs "
                        + "WHERE "
                            + "fs.active = true AND "
                            + "fs.id NOT IN (:ids) "
                        + "ORDER BY fs.code")
                .setParameterList("ids", idNotInclude, IntegerType.INSTANCE)
                .list();
    }

    @Override
    public List getListBasicData() {
           return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "fs.id as id,"
                            + "fs.code as code,"
                            + "fs.abbr as abbr,"
                            + "fs.name as name"
                        + ") "
                        + "FROM FundingSource fs "
                        + "WHERE "
                            + "fs.active = true "
                        + "ORDER BY fs.code")
                .list();
    }

    @Override
    public List<FundingSource> getFundingSources() {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM FundingSource fs "
                        + "WHERE fs.active = true "
                        + "ORDER BY fs.code")
                .list();
    }
}
