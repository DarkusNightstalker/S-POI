/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean.util;

import dn.core3.hibernate.generic.interfac.IGenericService;
import gkfire.auditory.Auditory;
import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import gkfire.model.interfac.EntityActivate;
import gkfire.web.bean.ILoadable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type A managed bean.
 *
 * @param <T> the type parameter
 * @param <S> the type parameter
 * @author Benji
 */
public abstract class AManagedBean<T, S extends IGenericService> implements java.io.Serializable {

    /**
     * The Selected.
     */
    protected T selected;
    /**
     * The Create again.
     */
    protected boolean createAgain;
    /**
     * The Keep managed.
     */
    protected boolean keepManaged = false;

    /**
     * Gets selected.
     *
     * @return the selected
     */
    public T getSelected() {
        return selected;
    }

    /**
     * Sets selected.
     *
     * @param selected the selected
     */
    public void setSelected(T selected) {
        this.selected = selected;
        if (selected != null) {
            fillFields();
        } else {
            clearFields();
        }
    }

    /**
     * Gets id selected.
     *
     * @return the id selected
     */
    public Number getIdSelected() {
        return null;
    }

    /**
     * Sets id selected.
     *
     * @param id the id
     */
    public void setIdSelected(Number id) {
        setSelected((T) getMainService().getById(id));
        createAgain = false;
    }

    /**
     * Create.
     */
    public void create() {
        Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        createAgain = true;
        try {
            setSelected((T) persistentClass.getConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            Logger.getLogger(AManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Delete.
     *
     * @param id the id
     */
    public void delete(java.io.Serializable id) {
        T object = (T) getMainService().getById(id);
        if(object instanceof  AuditoryEntity){
            Auditory.make((AuditoryEntity)object, getSessionBean().getCurrentUser());
        }
        getMainService().delete(object);
    }

    /**
     * Recovery.
     *
     * @param id the id
     */
    public void recovery(java.io.Serializable id) {
        T object = (T) getMainService().getById(id);
        if(object instanceof EntityActivate){
            ((EntityActivate)object).setActive(true);
        }else{
            throw new IllegalArgumentException("El objeto no es desactivable");
        }
        getMainService().update(object);
    }

    /**
     * Save boolean.
     *
     * @return the boolean
     */
    public boolean save() {
        fillSelected();
        getMainService().saveOrUpdate(selected);
        return true;
    }

    /**
     * Do save.
     *
     * @param page     the page
     * @param loadable the loadable
     */
    public void doSave(String page, ILoadable loadable) {
        if (save()) {
            if (!createAgain) {
                getNavigationBean().setContent(page);
                loadable.onLoad(true);
                getSessionBean().setLoadable(loadable);
            } else {
                create();
            }
        }
    }

    /**
     * Refresh.
     */
    public void refresh() {
        fillFields();
    }

    /**
     * Gets navigation bean.
     *
     * @return the navigation bean
     */
    public abstract NavigationBean getNavigationBean();

    /**
     * Sets navigation bean.
     *
     * @param navigationBean the navigation bean
     */
    public abstract void setNavigationBean(NavigationBean navigationBean);

    /**
     * Gets session bean.
     *
     * @return the session bean
     */
    public abstract SessionBean getSessionBean();

    /**
     * Sets session bean.
     *
     * @param sessionBean the session bean
     */
    public abstract void setSessionBean(SessionBean sessionBean);

    /**
     * Gets main service.
     *
     * @return the main service
     */
    public abstract S getMainService();

    /**
     * Sets main service.
     *
     * @param mainService the main service
     */
    public abstract void setMainService(S mainService);

    /**
     * Fill fields.
     */
    protected abstract void fillFields();

    /**
     * Clear fields.
     */
    protected abstract void clearFields();

    /**
     * Fill selected.
     */
    protected abstract void fillSelected();

    /**
     * Is create again boolean.
     *
     * @return the createAgain
     */
    public boolean isCreateAgain() {
        return createAgain;
    }

    /**
     * Sets create again.
     *
     * @param createAgain the createAgain to set
     */
    public void setCreateAgain(boolean createAgain) {
        this.createAgain = createAgain;
    }

    /**
     * Is keep managed boolean.
     *
     * @return the keepManaged
     */
    public boolean isKeepManaged() {
        return keepManaged;
    }

    /**
     * Sets keep managed.
     *
     * @param keepManaged the keepManaged to set
     */
    public void setKeepManaged(boolean keepManaged) {
        this.keepManaged = keepManaged;
    }

}
