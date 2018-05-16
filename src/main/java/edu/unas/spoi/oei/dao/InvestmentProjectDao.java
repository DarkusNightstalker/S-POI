/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.dao;

import edu.unas.spoi.oei.dao.interfac.IInvestmentProjectDao;
import edu.unas.spoi.oei.model.InvestmentProject;
import gkfire.hibernate.generic.GenericDao;
import org.springframework.stereotype.Repository;

/**
 * The type Investment project dao.
 *
 * @author Jhoan Brayam
 */
@Repository("investmentProjectDao")
public class InvestmentProjectDao  extends GenericDao<InvestmentProject, Long> implements IInvestmentProjectDao {
    
}
