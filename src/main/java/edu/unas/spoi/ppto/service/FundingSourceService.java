/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.ppto.service;

import edu.unas.spoi.ppto.dao.interfac.IFundingSourceDao;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.service.interfac.IFundingSourceService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.io.Serializable;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Funding source service.
 *
 * @author CORE i7
 */
@Service("fundingSourceService")
public class FundingSourceService implements IFundingSourceService {

    @Autowired
    @Qualifier("fundingSourceDao")
    private IFundingSourceDao fundingSourceDao;

    @Override
    public Serializable save(FundingSource object) {
        return fundingSourceDao.save(object);
    }

    @Override
    public void update(FundingSource object) {
        fundingSourceDao.update(object);
    }

    @Override
    public void saveOrUpdate(FundingSource object) {
        fundingSourceDao.saveOrUpdate(object);
    }

    @Override
    public void delete(FundingSource object) {
        fundingSourceDao.delete(object);
    }

    @Override
    public List list() {
        return fundingSourceDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return fundingSourceDao.listHQL(hql);
    }

    @Override
    public FundingSource getById(Integer id) {
        return fundingSourceDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return fundingSourceDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return fundingSourceDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return fundingSourceDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return fundingSourceDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return fundingSourceDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return fundingSourceDao.countRestrictions(listCriterion);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, List<Projection> projections, int page, int rows) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List addRestrictionsVariant(List variant) {
        return fundingSourceDao.addRestrictionsVariant(variant);
    }

    @Override
    public boolean existCode(String code,Integer exception) {
        return fundingSourceDao.existCode(code,exception);
    }
    @Override
    public Integer nextId(Integer id, String idName,boolean withDisabled) {
        return fundingSourceDao.nextId(id,idName,withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName,boolean withDisabled) {
        return fundingSourceDao.previousId(id,idName,withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return fundingSourceDao.addRestrictionsVariant(variant);
    }
    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return fundingSourceDao.rowNumber(id,withDisabled);
    }
    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return fundingSourceDao.addRestrictionsVariant(rows,page,variant);
    }
    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return fundingSourceDao.countRestrictions(criterionList,aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return fundingSourceDao.listHQL(hql,parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return fundingSourceDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return fundingSourceDao.getByHQL(hql,parameters);
    }

    @Override
    public List getListBasicData(List<Integer> idNotInclude) {
        return fundingSourceDao.getListBasicData(idNotInclude);
    }

    @Override
    public List<FundingSource> getFundingSources() {
        return fundingSourceDao.getFundingSources();
    }
}
