/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao;

import edu.unas.spoi.security.dao.interfac.IInvolvedDao;
import edu.unas.spoi.security.model.Involved;
import edu.unas.spoi.security.model.Person;
import gkfire.hibernate.generic.GenericDao;
import java.util.List;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

/**
 * The type Involved dao.
 *
 * @author Jhoan Brayam
 */
@Repository("involvedDao")
public class InvolvedDao extends GenericDao<Involved, Integer> implements IInvolvedDao {

    @Override
    public Involved getBy(Person person, Integer dependency) {
        return (Involved) getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "i "
                        + "FROM Involved i "
                        + "WHERE "
                            + "i.person = :person AND "
                            + "i.dependency.id = :id")
                .setParameter("person", person)
                .setParameter("id", dependency,IntegerType.INSTANCE)
                .uniqueResult();
    }

    @Override
    public List<Integer> getDependenciesAvailable(Person person) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "DISTINCT i.dependency.id "
                        + "FROM Involved i "
                            + "WHERE i.person = :person")
                .setEntity("person", person)
                .list();
    }

}
