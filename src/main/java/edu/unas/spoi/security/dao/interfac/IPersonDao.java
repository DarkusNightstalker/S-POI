/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao.interfac;

import edu.unas.spoi.security.model.Person;
import gkfire.hibernate.generic.interfac.IGenericDao;
import java.io.Serializable;

/**
 * The interface Person dao.
 *
 * @author Jhoan Brayam
 */
public interface IPersonDao extends IGenericDao<Person, Integer>{

    /**
     * Gets by.
     *
     * @param dni the dni
     * @return the by
     */
    public Person getBy(String dni);
}
