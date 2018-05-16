/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.dao;

import edu.unas.spoi.ppto.dao.interfac.IBudgetCeilingDao;
import edu.unas.spoi.ppto.model.BudgetCeiling;
import edu.unas.spoi.ppto.model.Classifier;
import gkfire.hibernate.generic.GenericDao;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

/**
 * The type Budget ceiling dao.
 *
 * @author Jhoan Brayam
 */
@Repository("budgetCeilingDao")
public class BudgetCeilingDao extends GenericDao<BudgetCeiling, Long> implements IBudgetCeilingDao {

    @Override
    public List<BudgetCeiling> getBy(Integer dependencyId, Integer year) {
        if (dependencyId == null) {
            return Collections.EMPTY_LIST;
        }
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "FROM BudgetCeiling bc "
                        + "WHERE "
                            + "bc.dependency.id = :dependency AND "
                            + "bc.year = :year "
                        + "ORDER BY bc.id")
                .setInteger("dependency", dependencyId)
                .setInteger("year", year)
                .list();
    }

    @Override
    public List getListForCopyPreviousYear(Integer dependencyId, int operationYear) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "bc.fundingSource.id as fundingSource,"
                            + "bc.classifier.id as classifier,"
                            + "bc.quantity as quantity"
                        + ") "
                        + "FROM BudgetCeiling bc "
                        + "WHERE "
                            + "bc.dependency.id = :dependency AND "
                            + "bc.year = :year "
                        + "ORDER BY bc.id")
                .setInteger("dependency", dependencyId)
                .setInteger("year", operationYear)
                .list();
    }

    @Override
    public List getListNotEmptyFundingSourceBasicData(Integer dependencyId, Integer operationYear) {
                   
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT DISTINCT new map("
                            + "bc.fundingSource.id as id,"
                            + "bc.fundingSource.code as code,"
                            + "bc.fundingSource.abbr as abbr,"
                            + "bc.fundingSource.name as name"
                        + ") "
                        + "FROM BudgetCeiling bc "
                        + "WHERE "
                            + "bc.dependency.id = :dependencyId AND "
                            + "bc.year = :year AND "
                            + "bc.fundingSource.active = true AND "
                            + "( "
                                + "SELECT "
                                    + "SUM(bc_.quantity) "
                                + "FROM BudgetCeiling bc_ "
                                + "WHERE "
                                    + "bc_.fundingSource = bc.fundingSource AND "
                                    + "bc_.dependency = bc.dependency AND "
                                    + "bc_.year = bc.year"
                            + ") > 0 "
                        + "ORDER BY bc.fundingSource.code")
                .setInteger("dependencyId", dependencyId)
                .setInteger("year", operationYear)
                .list();    
    }

    @Override
    public List<Map<String, Object>> getListBasicData(Integer dependencyId, Integer year) {
           if (dependencyId == null) {
            return Collections.EMPTY_LIST;
        }
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT new map("
                            + "bc.id as id,"
                            + "bc.fundingSource.id as fundingSourceId,"
                            + "bc.classifier.id as classifierId,"
                            + "bc.quantity as quantity "
                        + ") "
                        + "FROM BudgetCeiling bc "
                        + "WHERE "
                            + "bc.dependency.id = :dependency AND "
                            + "bc.year = :year "
                        + "ORDER BY bc.id")
                .setInteger("dependency", dependencyId)
                .setInteger("year", year)
                .list();
    }

    @Override
    public List<Classifier> getGenericClassifiers(Integer dependencyId, boolean withEmpty) {
     return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT DISTINCT "
                            + "bc.classifier "
                        + "FROM BudgetCeiling bc "
                        + "WHERE "
                            + "bc.dependency.id = :dependency AND "
                            +(withEmpty ? "" : "bc.quantity > 0 AND ")
                            + "bc.year = (SELECT bc_.dependency.operationYear FROM BudgetCeiling bc_ WHERE bc_.id = bc.id) "
                        )
                .setInteger("dependency", dependencyId)
                .list();}
}
