/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean;

import edu.unas.spoi.bne.model.enumerated.Month;
import edu.unas.spoi.bne.service.interfac.IBNeItemService;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.ppto.model.BudgetCeiling;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.ppto.service.interfac.IBudgetCeilingService;
import edu.unas.spoi.ppto.service.interfac.IClassifierService;
import edu.unas.spoi.ppto.service.interfac.IFundingSourceService;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.web.bean.ILoadable;
import gkfire.web.util.BeanUtil;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import lombok.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * The type Home bean.
 *
 * @author Jhoan Brayam
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class HomeBean implements ILoadable, Serializable {

    @ManagedProperty(value = "#{classifierService}")
    private IClassifierService classifierService;
    @ManagedProperty(value = "#{fundingSourceService}")
    private IFundingSourceService fundingSourceService;
    @ManagedProperty(value = "#{budgetCeilingService}")
    private IBudgetCeilingService budgetCeilingService;
    @ManagedProperty(value = "#{bneItemService}")
    private IBNeItemService bneItemService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;

    private List<FundingSource> fundingSources;
    private List<Classifier> classifiers;
    private Classifier selectedClassifier;

    private Ceiling ceiling;
    private Expected expected;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        ceiling = new Ceiling();
        expected = new Expected();
    }

    @Override
    public void onLoad(boolean allowAjax) {
        if (BeanUtil.isAjaxRequest() && !allowAjax) {
            return;
        }
        if (!sessionBean.isSuperAdmin()) {
            fundingSources = fundingSourceService.getFundingSources();
            classifiers = budgetCeilingService.getGenericClassifiers(sessionBean.getCurrentDependency().getId(), false);
            selectedClassifier = classifiers.get(0);
            ceiling.refresh();
            expected.refresh();
        }
    }

    /**
     * Build month chart data string.
     *
     * @param classifier the classifier
     * @return the string
     */
    public String buildMonthChartData(Classifier classifier) {
        StringJoiner sjTotal = new StringJoiner(",");
        for (FundingSource fundingSource : fundingSources) {
            List<Map<String, Object>> boxNeedMonthData = bneItemService.getScheduledAmount(sessionBean.getCurrentDependency().getId(), sessionBean.getOperationYear(), fundingSource.getId(), classifier.getId());
            boxNeedMonthData.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return ((Month) o1.get("month")).compareTo((Month) o2.get("month"));
                }
            });
            StringBuilder sb = new StringBuilder();
            sb.append("{data: [");
            StringJoiner sj = new StringJoiner(",");
            for (Map<String, Object> map : boxNeedMonthData) {
                sj.add("[" + (((Month) map.get("month")).ordinal() + 1) + "," + map.get("quantity") + "]");
            }
            sb.append(sj.toString());
            sb.append("],label:'" + fundingSource.getAbbr() + "',lines : {show : true,lineWidth : 1,fill : true,fillColor : {colors : [{opacity : 0.1}, {opacity : 0.13}]}},points : {show : true}}");
            sjTotal.add(sb.toString());
        }
        System.out.println("[" + sjTotal.toString() + "]");
        return "[" + sjTotal.toString() + "]";
    }

    /**
     * The type Ceiling.
     */
    public class Ceiling implements java.io.Serializable {

        private Map<Long, List<BudgetCeiling>> budgetCeilings;
        private Map<Long, BigDecimal> budgetCeilingsTotal;

        /**
         * Refresh.
         */
        public void refresh() {

            Dependency current = sessionBean.getCurrentDependency();
            budgetCeilings = new HashMap();
            budgetCeilingsTotal = new HashMap();
            for (Classifier classifier : classifiers) {
                BigDecimal total = BigDecimal.ZERO;
                CriterionList criterionList = new CriterionList();
                criterionList.add(Restrictions.eq("dependency", current));
                criterionList.add(Restrictions.eq("classifier", classifier));
                criterionList.add(Restrictions.eq("year", sessionBean.getOperationYear()));
                List<BudgetCeiling> data = budgetCeilingService.addRestrictions(criterionList);
                for (BudgetCeiling item : data) {
                    total = total.add(item.getQuantity());
                }
                budgetCeilingsTotal.put(classifier.getId(), total);
                budgetCeilings.put(classifier.getId(), data);
            }

        }

        /**
         * Gets by.
         *
         * @param fundingSource the funding source
         * @param classifier    the classifier
         * @return the by
         */
        public BudgetCeiling getBy(FundingSource fundingSource, Classifier classifier) {

            for (BudgetCeiling item : budgetCeilings.get(classifier.getId())) {
                if (fundingSource.getId() == item.getFundingSource().getId().longValue()) {
                    return item;
                }
            }
            return null;
        }

        /**
         * Gets budget ceilings.
         *
         * @return the budgetCeilings
         */
        public Map<Long, List<BudgetCeiling>> getBudgetCeilings() {
            return budgetCeilings;
        }

        /**
         * Sets budget ceilings.
         *
         * @param budgetCeilings the budgetCeilings to set
         */
        public void setBudgetCeilings(Map<Long, List<BudgetCeiling>> budgetCeilings) {
            this.budgetCeilings = budgetCeilings;
        }

        /**
         * Gets budget ceilings total.
         *
         * @return the budgetCeilingsTotal
         */
        public Map<Long, BigDecimal> getBudgetCeilingsTotal() {
            return budgetCeilingsTotal;
        }

        /**
         * Sets budget ceilings total.
         *
         * @param budgetCeilingsTotal the budgetCeilingsTotal to set
         */
        public void setBudgetCeilingsTotal(Map<Long, BigDecimal> budgetCeilingsTotal) {
            this.budgetCeilingsTotal = budgetCeilingsTotal;
        }
    }

    /**
     * The type Expected.
     */
    @Data
    public class Expected implements Serializable {

        private Map<Long, List<Map<String, Object>>> budgetExpected;

        /**
         * Refresh.
         */
        public void refresh() {
            budgetExpected = new HashMap();
            for (Classifier classifier : classifiers) {
                List<Map<String, Object>> result = bneItemService.getFundingSourceAmount(sessionBean.getCurrentDependency().getId(), sessionBean.getOperationYear(), classifier.getId());
                budgetExpected.put(classifier.getId(), result);
            }
        }

        /**
         * Gets by.
         *
         * @param fs         the fs
         * @param classifier the classifier
         * @return the by
         */
        public Map<String,Object> getBy(FundingSource fs, Classifier classifier) {
            for (Map<String,Object> item : budgetExpected.get(classifier.getId())) {
                if (Objects.equals(fs.getId().intValue(), item.get("fundingSourceId"))) {
                    return item;
                }
            }
            return null;
        }
    }
}
