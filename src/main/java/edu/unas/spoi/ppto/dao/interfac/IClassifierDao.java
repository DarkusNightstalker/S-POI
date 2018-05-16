/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao.interfac;

import gkfire.hibernate.generic.interfac.IGenericDao;
import gkfire.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.util.ErrorMessage;
import java.util.List;

/**
 * The interface Classifier dao.
 *
 * @author CORE i7
 */
public interface IClassifierDao extends IGenericDao<Classifier, Long> {

    /**
     * Verify code error message.
     *
     * @param code      the code
     * @param year      the year
     * @param exception the exception
     * @return the error message
     */
    public ErrorMessage verifyCode(String code, Integer year, Long exception);

    /**
     * Gets by path.
     *
     * @param path the path
     * @return the by path
     */
    public Classifier getByPath(String path);

    /**
     * Gets parent.
     *
     * @param path the path
     * @return the parent
     */
    public Classifier getParent(String path);

    /**
     * Get parent info object [ ].
     *
     * @param path the path
     * @return the object [ ]
     */
    public Object[] getParentInfo(String path);

    /**
     * Gets generic.
     *
     * @param path the path
     * @return the generic
     */
    public Classifier getGeneric(String path);

    /**
     * Gets generic classifiers.
     *
     * @return the generic classifiers
     */
    public List<Classifier> getGenericClassifiers();
}
