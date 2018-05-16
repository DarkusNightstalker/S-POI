/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.report;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;

/**
 * The type Field.
 *
 * @author Jhoan Brayam
 */
public class Field implements java.io.Serializable{

    private boolean visible;
    private String fieldLabel;
    private Projection fieldProjection;
    private CriterionList criterionList;
    private AliasList aliasList;
    private Integer groupNumber;
    private Order order;
    private Integer orderNumber;
    private Integer viewOrder;

    /**
     * Instantiates a new Field.
     */
    public Field() {
        visible =  false;
        aliasList = new AliasList();
        criterionList = new CriterionList();
        orderNumber = null;
        viewOrder =  null;
        order  = null;
        groupNumber = null;
    }

    /**
     * Instantiates a new Field.
     *
     * @param fieldLabel      the field label
     * @param fieldProjection the field projection
     */
    public Field(String fieldLabel,Projection fieldProjection){
        this();
        this.fieldLabel = fieldLabel;
        this.fieldProjection = fieldProjection;        
    }

    /**
     * Gets field label.
     *
     * @return the fieldLabel
     */
    public String getFieldLabel() {
        return fieldLabel;
    }

    /**
     * Sets field label.
     *
     * @param fieldLabel the fieldLabel to set
     */
    public void setFieldLabel(String fieldLabel) {
        this.fieldLabel = fieldLabel;
    }

    /**
     * Gets field projection.
     *
     * @return the fieldProjection
     */
    public Projection getFieldProjection() {
        return fieldProjection;
    }

    /**
     * Sets field projection.
     *
     * @param fieldProjection the fieldProjection to set
     */
    public void setFieldProjection(Projection fieldProjection) {
        this.fieldProjection = fieldProjection;
    }

    /**
     * Gets criterion list.
     *
     * @return the criterionList
     */
    public CriterionList getCriterionList() {
        return criterionList;
    }

    /**
     * Sets criterion list.
     *
     * @param criterionList the criterionList to set
     */
    public void setCriterionList(CriterionList criterionList) {
        this.criterionList = criterionList;
    }

    /**
     * Gets alias list.
     *
     * @return the aliasList
     */
    public AliasList getAliasList() {
        return aliasList;
    }

    /**
     * Sets alias list.
     *
     * @param aliasList the aliasList to set
     */
    public void setAliasList(AliasList aliasList) {
        this.aliasList = aliasList;
    }

    /**
     * Is visible boolean.
     *
     * @return the visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Sets visible.
     *
     * @param visible the visible to set
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Gets group number.
     *
     * @return the groupNumber
     */
    public Integer getGroupNumber() {
        return groupNumber;
    }

    /**
     * Sets group number.
     *
     * @param groupNumber the groupNumber to set
     */
    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }

    /**
     * Gets order.
     *
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * Sets order.
     *
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * Gets order number.
     *
     * @return the orderNumber
     */
    public Integer getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets order number.
     *
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Gets view order.
     *
     * @return the viewOrder
     */
    public Integer getViewOrder() {
        return viewOrder;
    }

    /**
     * Sets view order.
     *
     * @param viewOrder the viewOrder to set
     */
    public void setViewOrder(Integer viewOrder) {
        this.viewOrder = viewOrder;
    }
}
