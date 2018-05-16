/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.oei.dao.interfac.IBudgetProgramDao;
import edu.unas.spoi.oei.model.BudgetProgram;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

/**
 * The type Budget program dao.
 *
 * @author CORE i7
 */
@Repository("budgetProgramDao")
public class BudgetProgramDao extends GenericDao<BudgetProgram, Integer> implements IBudgetProgramDao {

    @Override
    public boolean existCode(String code, Integer id) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT "
                            + "1 "
                        + "FROM BudgetProgram bp "
                        + "WHERE "
                            + "bp.code LIKE :code AND "
                            + "bp.id <> :id")
                .setParameter("code", code,StringType.INSTANCE)
                .setParameter("id", id == null ? -1 : id,IntegerType.INSTANCE)
                .uniqueResult() != null;
    }
}
