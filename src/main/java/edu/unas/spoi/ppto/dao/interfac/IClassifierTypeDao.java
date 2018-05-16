/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao.interfac;

import gkfire.hibernate.generic.interfac.IGenericDao;
import gkfire.hibernate.generic.interfac.IGenericService;
import edu.unas.spoi.ppto.model.ClassifierType;
import java.util.List;

/**
 * The interface Classifier type dao.
 *
 * @author CORE i7
 */
public interface IClassifierTypeDao extends IGenericDao<ClassifierType, Integer> {

    /**
     * Gets for classifiers.
     *
     * @param year the year
     * @return the for classifiers
     */
    public List<Object[]> getForClassifiers(Integer year);

    /**
     * Gets digits.
     *
     * @param id the id
     * @return the digits
     */
    public Integer getDigits(Integer id);

    /**
     * Gets by digits.
     *
     * @param codeDigit the code digit
     * @return the by digits
     */
    public ClassifierType getByDigits(Integer codeDigit);
}
