/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.bean;

import gkfire.auditory.AuditoryEntity;
import edu.unas.spoi.bean.SessionBean;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.hibernate.generic.interfac.IGenericService;
import gkfire.web.util.BeanUtil;
import java.util.Arrays;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

/**
 * The type A view bean.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 * @param <S>  the type parameter
 * @author Jhoan Brayam
 */
public abstract class AViewBean<T extends Object, ID extends Number, S extends IGenericService> implements java.io.Serializable, ILoadable {

    /**
     * The Permission disabled.
     */
    protected String permissionDisabled;
    /**
     * The Row number.
     */
    protected Long rowNumber;
    /**
     * The Row count.
     */
    protected Long rowCount;
    /**
     * The Exist next.
     */
    protected boolean existNext;
    /**
     * The Exist previous.
     */
    protected boolean existPrevious;
    /**
     * The Current id.
     */
    protected ID currentId;
    /**
     * The User created.
     */
    protected String userCreated;
    /**
     * The User edited.
     */
    protected String userEdited;
    /**
     * The Direction.
     */
    protected Boolean direction;
    /**
     * The Selected.
     */
    protected T selected;
    /**
     * The Order list.
     */
    protected OrderList orderList;

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        direction = null;
        update(getSessionBean().authorize(permissionDisabled));
    }

    /**
     * Begin.
     *
     * @param id the id
     */
    public void begin(ID id) {
        currentId = id;
        direction = null;
        update(getSessionBean().authorize(permissionDisabled));
    }

    /**
     * Update.
     *
     * @param widthDisabled the width disabled
     */
    protected void update(boolean widthDisabled) {

        rowNumber = getMainService().rowNumber(currentId, widthDisabled).longValue();
        if (!widthDisabled) {
            rowCount = getMainService().countRestrictions(Arrays.asList((Criterion) Restrictions.eq("active", true))).longValue();
        } else {
            rowCount = getMainService().countRestrictions(Arrays.asList()).longValue();
        }
        existNext = getMainService().nextId(currentId, "id", widthDisabled) != null;
        existPrevious = getMainService().previousId(currentId, "id", widthDisabled) != null;
        selected = (T) getMainService().getById(currentId);
        if (selected instanceof AuditoryEntity) {
            AliasList aliasList = new AliasList();
            aliasList.add("createUser", "cu");
            aliasList.add("editUser", "eu", JoinType.LEFT_OUTER_JOIN);
            ProjectionList projectionList = Projections.projectionList()
                    .add(Projections.property("cu.username"))
                    .add(Projections.property("eu.username"));
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("id", currentId));
            Object[] users = (Object[]) getMainService().addRestrictionsVariant(projectionList, criterionList, aliasList).get(0);
            userCreated = (String) users[0];
            userEdited = (String) users[1];
        }
    }

    /**
     * Next.
     */
    public void next() {
        boolean widthDisable = getSessionBean().authorize(permissionDisabled);
        currentId = (ID) getMainService().nextId(currentId, "id", widthDisable);
        direction = true;
        update(widthDisable);
    }

    /**
     * Previous.
     */
    public void previous() {
        boolean widthDisable = getSessionBean().authorize(permissionDisabled);
        currentId = (ID) getMainService().previousId(currentId, "id", widthDisable);
        direction = false;
        update(widthDisable);
    }

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
     * Gets row number.
     *
     * @return the rowNumber
     */
    public Long getRowNumber() {
        return rowNumber;
    }

    /**
     * Sets row number.
     *
     * @param rowNumber the rowNumber to set
     */
    public void setRowNumber(Long rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * Gets row count.
     *
     * @return the rowCount
     */
    public Long getRowCount() {
        return rowCount;
    }

    /**
     * Sets row count.
     *
     * @param rowCount the rowCount to set
     */
    public void setRowCount(Long rowCount) {
        this.rowCount = rowCount;
    }

    /**
     * Is exist next boolean.
     *
     * @return the existNext
     */
    public boolean isExistNext() {
        return existNext;
    }

    /**
     * Sets exist next.
     *
     * @param existNext the existNext to set
     */
    public void setExistNext(boolean existNext) {
        this.existNext = existNext;
    }

    /**
     * Is exist previous boolean.
     *
     * @return the existPrevious
     */
    public boolean isExistPrevious() {
        return existPrevious;
    }

    /**
     * Sets exist previous.
     *
     * @param existPrevious the existPrevious to set
     */
    public void setExistPrevious(boolean existPrevious) {
        this.existPrevious = existPrevious;
    }

    /**
     * Gets current id.
     *
     * @return the currentId
     */
    public ID getCurrentId() {
        return currentId;
    }

    /**
     * Sets current id.
     *
     * @param currentId the currentId to set
     */
    public void setCurrentId(ID currentId) {
        this.currentId = currentId;
    }

    /**
     * Gets user created.
     *
     * @return the userCreated
     */
    public String getUserCreated() {
        return userCreated;
    }

    /**
     * Sets user created.
     *
     * @param userCreated the userCreated to set
     */
    public void setUserCreated(String userCreated) {
        this.userCreated = userCreated;
    }

    /**
     * Gets user edited.
     *
     * @return the userEdited
     */
    public String getUserEdited() {
        return userEdited;
    }

    /**
     * Sets user edited.
     *
     * @param userEdited the userEdited to set
     */
    public void setUserEdited(String userEdited) {
        this.userEdited = userEdited;
    }

    /**
     * Gets direction.
     *
     * @return the direction
     */
    public Boolean getDirection() {
        return direction;
    }

    /**
     * Sets direction.
     *
     * @param direction the direction to set
     */
    public void setDirection(Boolean direction) {
        this.direction = direction;
    }

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
     * @param selected the selected to set
     */
    public void setSelected(T selected) {
        this.selected = selected;
    }
}
