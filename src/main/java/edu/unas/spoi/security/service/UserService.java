/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.service;

import edu.unas.spoi.security.dao.interfac.IUserDao;
import edu.unas.spoi.security.model.User;
import edu.unas.spoi.security.service.interfac.IUserService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import java.util.List;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type User service.
 *
 * @author CORE i7
 */
@Service("userService")
public class UserService implements IUserService {

    @Autowired
    @Qualifier("userDao")
    private IUserDao userDao;

    @Override
    public Integer save(User object) {
        return userDao.save(object);
    }

    @Override
    public void update(User object) {
        userDao.update(object);
    }

    @Override
    public void saveOrUpdate(User object) {
        userDao.saveOrUpdate(object);
    }

    @Override
    public void delete(User object) {
        userDao.delete(object);
    }

    @Override
    public List list() {
        return userDao.list();
    }

    @Override
    public List listHQL(String hql) {
        return userDao.listHQL(hql);
    }

    @Override
    public User getById(Integer id) {
        return userDao.getById(id);
    }

    @Override
    public List listOrderByColumns(String[] nameColumns, boolean asc) {
        return userDao.listOrderByColumns(nameColumns, asc);
    }

    @Override
    public Integer count() {
        return userDao.count();
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion) {
        return userDao.addRestrictions(listCriterion);
    }

    @Override
    public int updateHQL(String hql) throws Exception {
        return userDao.updateHQL(hql);
    }

    @Override
    public List addRestrictions(List<Criterion> listCriterion, int page, int rows) {
        return userDao.addRestrictions(listCriterion, page, rows);
    }

    @Override
    public Number countRestrictions(List<Criterion> listCriterion) {
        return userDao.countRestrictions(listCriterion);
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
        return userDao.addRestrictionsVariant(variant);
    }

    @Override
    public User login(String username, String password) {
        return userDao.login(username, password);
    }

    @Override
    public Integer getNextId() {
        return userDao.getNextId();
    }

    @Override
    public Integer nextId(Integer id, String idName, boolean withDisabled) {
        return userDao.nextId(id, idName, withDisabled);
    }

    @Override
    public Integer previousId(Integer id, String idName, boolean withDisabled) {
        return userDao.previousId(id, idName, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(Object... variant) {
        return userDao.addRestrictionsVariant(variant);
    }

    @Override
    public Number rowNumber(Integer id, boolean withDisabled) {
        return userDao.rowNumber(id, withDisabled);
    }

    @Override
    public List addRestrictionsVariant(int rows, int page, Object... variant) {
        return userDao.addRestrictionsVariant(rows, page, variant);
    }

    @Override
    public Number countRestrictions(CriterionList criterionList, AliasList aliasList) {
        return userDao.countRestrictions(criterionList, aliasList);
    }

    @Override
    public List listHQL(String hql, Object... parameters) {
        return userDao.listHQL(hql, parameters);
    }

    @Override
    public Object getByHQL(String hql) {
        return userDao.getByHQL(hql);
    }

    @Override
    public Object getByHQL(String hql, Object... parameters) {
        return userDao.getByHQL(hql, parameters);
    }

    @Override
    public List<String> getPermissionsCode(Integer userId) {
        return userDao.getPermissionsCode(userId);
    }

}
