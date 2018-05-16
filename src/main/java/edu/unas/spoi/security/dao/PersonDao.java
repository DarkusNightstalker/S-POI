/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao;

import edu.unas.spoi.security.dao.interfac.IPersonDao;
import edu.unas.spoi.security.model.Person;
import gkfire.hibernate.generic.GenericDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * The type Person dao.
 *
 * @author Jhoan Brayam
 */
@Repository("personDao")
public class PersonDao extends GenericDao<Person, Integer> implements IPersonDao{

    @Override
    public Person getBy(String dni) {
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT p FROM Person p WHERE p.dni LIKE :dni");
        query.setString("dni", dni);
        return (Person)query.uniqueResult();
    }
    
}
