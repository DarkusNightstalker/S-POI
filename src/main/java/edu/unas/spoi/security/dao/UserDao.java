/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.dao;

import gkfire.hibernate.generic.GenericDao;
import edu.unas.spoi.security.dao.interfac.IUserDao;
import edu.unas.spoi.security.model.User;
import edu.unas.spoi.util.ErrorMessage;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

/**
 * The type User dao.
 *
 * @author CORE i7
 */
@Repository("userDao")
public class UserDao extends GenericDao<User, Integer> implements IUserDao {

    public User login(String username, String password) {
        Query query = getSessionFactory().getCurrentSession().createQuery("SELECT u FROM User u WHERE u.username LIKE :username AND u.password LIKE :password");
        query.setString("username", username);
        query.setString("password", password);
        return (User) query.uniqueResult();
    }

    @Override
    public Integer getNextId() {
        return ((BigInteger) getSessionFactory().getCurrentSession().createSQLQuery("SELECT (last_value+1) FROM user_id_seq").uniqueResult()).intValue();
    }

    @Override
    public List<String> getPermissionsCode(Integer userId) {
        return getSessionFactory()
                .getCurrentSession()
                .createQuery(""
                        + "SELECT DISTINCT "
                            + "p.code "
                        + "FROM User u "
                            + "join u.rols r "
                            + "join r.permissions p "
                        + "WHERE "
                            + "r.active = true AND "
                            + "p.active = true AND "
                            + "u.active = true AND "
                            + "u.id = :id ")
                .setParameter("id", userId,IntegerType.INSTANCE)
                .list();
    }
}
