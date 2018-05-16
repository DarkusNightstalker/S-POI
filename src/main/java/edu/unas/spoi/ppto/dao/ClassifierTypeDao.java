/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.ppto.dao.interfac.IClassifierTypeDao;
import edu.unas.spoi.ppto.model.ClassifierType;
import java.util.List;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

/**
 * The type Classifier type dao.
 *
 * @author CORE i7
 */
@Repository("classifierTypeDao")
public class ClassifierTypeDao extends GenericDao<ClassifierType, Integer> implements IClassifierTypeDao {
    
    @Override
    public List<Object[]> getForClassifiers(Integer year){
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "ct.name,"
                            + "ct.codeDigit,"
                            + "ct.partDigit "
                        + "FROM ClassifierType ct "
                        + "ORDER BY ct.codeDigit")
                .list();
    }
    @Override
    public Integer getDigits(Integer id){
        return (Integer) getSessionFactory()
                .getCurrentSession()
                .createQuery("SELECT ct.codeDigit FROM ClassifierType ct WHERE ct.id = :id")
                .setParameter("id", id,IntegerType.INSTANCE)
                .uniqueResult();
    }
    @Override
    public ClassifierType getByDigits(Integer codeDigit){
        return (ClassifierType) getSessionFactory()
                .getCurrentSession()
                .createQuery("FROM ClassifierType ct WHERE ct.codeDigit = :codeDigit")
                .setParameter("codeDigit",codeDigit,IntegerType.INSTANCE)
                .uniqueResult();
    }
}
