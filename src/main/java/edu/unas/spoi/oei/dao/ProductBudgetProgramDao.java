/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.IProductBudgetProgramDao;
import edu.unas.spoi.oei.model.ProductBudgetProgram;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Product budget program dao.
 *
 * @author CORE i7
 */
@Repository("productBudgetProgramDao")
public class ProductBudgetProgramDao extends GenericDao<ProductBudgetProgram, Long> implements IProductBudgetProgramDao {

    @Override
    public boolean existCode(String code, Long id) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                        + "1 "
                        + "FROM ProductBudgetProgram pbp "
                        + "WHERE pbp.code LIKE :code AND pbp.id <> :id")
                .setParameter("code", code, StringType.INSTANCE)
                .setParameter("id", id == null ? -1 : id, LongType.INSTANCE)
                .uniqueResult() != null;
    }
}
