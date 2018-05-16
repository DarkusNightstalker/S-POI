/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao.interfac;

import edu.unas.spoi.security.model.Involved;
import edu.unas.spoi.security.model.Person;
import gkfire.hibernate.generic.interfac.IGenericDao;
import java.io.Serializable;
import java.util.List;

/**
 * The interface Involved dao.
 *
 * @author Jhoan Brayam
 */
public interface IInvolvedDao extends IGenericDao<Involved, Integer>{
    /**
     * Gets by.
     *
     * @param person     the person
     * @param dependency the dependency
     * @return the by
     */
    public Involved getBy(Person person,Integer dependency);

    /**
     * Gets dependencies available.
     *
     * @param person the person
     * @return the dependencies available
     */
    public List<Integer> getDependenciesAvailable(Person person);
}
