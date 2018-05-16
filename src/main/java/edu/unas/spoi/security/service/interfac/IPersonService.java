/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.service.interfac;

import edu.unas.spoi.security.model.Person;
import gkfire.hibernate.generic.interfac.IGenericService;

/**
 * The interface Person service.
 *
 * @author Jhoan Brayam
 */
public interface IPersonService  extends IGenericService<Person, Integer>{
    /**
     * Gets by.
     *
     * @param dni the dni
     * @return the by
     */
    public Person getBy(String dni);
}
