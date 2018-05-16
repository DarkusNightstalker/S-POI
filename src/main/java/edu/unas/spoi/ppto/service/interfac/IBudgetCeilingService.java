/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.service.interfac;

import edu.unas.spoi.ppto.model.BudgetCeiling;
import edu.unas.spoi.ppto.model.Classifier;
import gkfire.hibernate.generic.interfac.IGenericService;
import java.util.List;
import java.util.Map;

/**
 * The interface Budget ceiling service.
 *
 * @author Jhoan Brayam
 */
public interface IBudgetCeilingService extends IGenericService<BudgetCeiling, Long> {

    /**
     * Gets by.
     *
     * @param dependencyId the dependency id
     * @param year         the year
     * @return the by
     */
    public List<BudgetCeiling> getBy(Integer dependencyId, Integer year);

    /**
     * Gets list basic data.
     *
     * @param dependencyId the dependency id
     * @param year         the year
     * @return the list basic data
     */
    public List<Map<String, Object>> getListBasicData(Integer dependencyId, Integer year);

    /**
     * Gets list not empty funding source basic data.
     *
     * @param dependencyId  the dependency id
     * @param operationYear the operation year
     * @return the list not empty funding source basic data
     */
    public List getListNotEmptyFundingSourceBasicData(Integer dependencyId, Integer operationYear);

    /**
     * Gets generic classifiers.
     *
     * @param dependencyId the dependency id
     * @param withEmpty    the with empty
     * @return the generic classifiers
     */
    public List<Classifier> getGenericClassifiers(Integer dependencyId, boolean withEmpty);

}
