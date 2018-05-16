/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.report.util;

import edu.unas.spoi.report.Field;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

/**
 * The type Field util.
 *
 * @author Jhoan Brayam
 */
public class FieldUtil {

    /**
     * Prepare fields object [ ].
     *
     * @param fields the fields
     * @return the object [ ]
     */
    public static Object[] prepareFields(List<Field> fields) {
        Variant variant = new Variant();

        Map<Integer, Order> orders = new TreeMap();
        Map<Integer, Field> groupOrders = new TreeMap();
        Map<Integer, Field> projectionOrder = new TreeMap();

        for (Field field : fields) {
            Set<String> localAlias = field.getAliasList().keySet();

            if (field.isVisible() || !field.getCriterionList().isEmpty()) {
                for (String alias : localAlias) {
                    if (variant.getAliasList().get(alias) == null) {
                        variant.getAliasList().add(alias, field.getAliasList().get(alias).getAlias(), field.getAliasList().get(alias).getJoinType());
                    }
                }
            }

            if (field.isVisible()) {
                if (field.getGroupNumber() == null) {
                    groupOrders.put(field.getGroupNumber(), field);
                } else {
                    orders.put(field.getOrderNumber(), field.getOrder());
                }
                projectionOrder.put(field.getViewOrder(), field);
            }
            
            variant.criterionList.addAll(field.getCriterionList());
        }

        for (Map.Entry<Integer, Field> entry : groupOrders.entrySet()) {
            variant.orderList.add(entry.getValue().getOrder());
        }

        for (Map.Entry<Integer, Order> entry : orders.entrySet()) {
            variant.orderList.add(entry.getValue());
        }

        for (Map.Entry<Integer, Field> entry : projectionOrder.entrySet()) {
            variant.projectionList.add(entry.getValue().getFieldProjection());
        }

        return new Object[]{groupOrders, projectionOrder, Arrays.asList(variant.criterionList, variant.aliasList, variant.orderList, variant.projectionList)};
    }

    private static class Variant {

        private CriterionList criterionList;
        private AliasList aliasList;
        private OrderList orderList;
        private ProjectionList projectionList;

        /**
         * Instantiates a new Variant.
         */
        public Variant() {
            criterionList = new CriterionList();
            aliasList = new AliasList();
            orderList = new OrderList();
            projectionList = Projections.projectionList();
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
         * Gets order list.
         *
         * @return the orderList
         */
        public OrderList getOrderList() {
            return orderList;
        }

        /**
         * Sets order list.
         *
         * @param orderList the orderList to set
         */
        public void setOrderList(OrderList orderList) {
            this.orderList = orderList;
        }

        /**
         * Gets projection list.
         *
         * @return the projectionList
         */
        public ProjectionList getProjectionList() {
            return projectionList;
        }

        /**
         * Sets projection list.
         *
         * @param projectionList the projectionList to set
         */
        public void setProjectionList(ProjectionList projectionList) {
            this.projectionList = projectionList;
        }

    }
}
