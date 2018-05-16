/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.ppto.dao.interfac.IClassifierDao;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.util.ErrorMessage;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.type.ShortType;
import org.springframework.stereotype.Repository;

/**
 * The type Classifier dao.
 *
 * @author CORE i7
 */
@Repository("classifierDao")
public class ClassifierDao extends GenericDao<Classifier, Long> implements IClassifierDao {

    @Override
    public ErrorMessage verifyCode(String code, Integer year, Long exception) {
        if (code == null || "".equalsIgnoreCase(code)) {
            return new ErrorMessage("CG-0001", "El codigo no puede ser nulo o vacio");
        }
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT 1 FROM Classifier c WHERE c.path LIKE :path  AND c.id != :exception");
        query.setLong("exception", exception);
        query.setString("path", code);
        if (query.uniqueResult() != null) {
            return new ErrorMessage("CG-0002", "El codigo '" + code + "' ya se encuentra registrado");
        }
        query = getSessionFactory().getCurrentSession().createQuery("SELECT 1 FROM ClassifierType ct WHERE ct.codeDigit = :digits");
        query.setShort("digits", (short) code.length());
        if (query.uniqueResult() == null) {
            return new ErrorMessage("CG-0003", "El codigo '" + code + "' no tiene un formato adecuado");
        }
        query = getSessionFactory().getCurrentSession().createQuery("SELECT ct.parent.codeDigit FROM ClassifierType ct WHERE ct.codeDigit = :digits");
        query.setShort("digits", (short) code.length());
        Short parentDigits = (Short) query.uniqueResult();
        if (parentDigits == null) {
            return null;
        } else {
            query = getSessionFactory().getCurrentSession().createQuery("SELECT 1 FROM Classifier c WHERE c.path LIKE :path");
            query.setString("path", code.substring(0, parentDigits));
            if (query.uniqueResult() == null) {
                return new ErrorMessage("CG-0004", "No se encuentra el clasificador de codigo '" + code.substring(0, parentDigits) + "' generico para el clasificador '" + code + "'", code.substring(0, parentDigits));
            } else {
                return null;
            }
        }
    }

    @Override
    public Classifier getByPath(String path) {
        Query query = getSessionFactory().getCurrentSession().createQuery("FROM Classifier c WHERE c.path LIKE :path");
        query.setString("path", path);
        return (Classifier) query.uniqueResult();
    }

    @Override
    public Classifier getParent(String path) {
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT ct.parent.codeDigit FROM ClassifierType ct WHERE ct.codeDigit = :digits");
        query.setShort("digits", (short) path.length());
        Short parentDigit = (Short) query.uniqueResult();
        try {
            path = path.substring(0, parentDigit);
            return getByPath(path);
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Object[] getParentInfo(String path) {
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT ct.parent.codeDigit FROM ClassifierType ct WHERE ct.codeDigit = :digits");
        query.setShort("digits", (short) path.length());
        Short parentDigit = (Short) query.uniqueResult();
        try {
            path = path.substring(0, parentDigit);
            query = getSessionFactory().getCurrentSession().createQuery("SELECT c.id,c.path,c.classifierType.name,c.name FROM Classifier c WHERE c.path LIKE :path");
            query.setString("path", path);
            return (Object[]) query.uniqueResult();
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public Classifier getGeneric(String path) {
        if(path.length() <= 2){
            return null;
        }
        path = path.substring(0, 2);
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT c FROM Classifier c WHERE c.path = :path");
        query.setString("path", path);
        return (Classifier) query.uniqueResult();
    }

    @Override
    public List<Classifier> getGenericClassifiers() {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM Classifier c "
                        + "WHERE "
                            + "c.active = true AND "
                            + "c.classifierType.codeDigit = :digits "
                        + "ORDER BY c.path ")
                .setParameter("digits", (short)2,ShortType.INSTANCE)
                .list();
    }
}
