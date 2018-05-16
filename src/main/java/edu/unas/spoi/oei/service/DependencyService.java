/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.oei.service;

import dn.core3.hibernate.generic.GenericService;
import dn.core3.hibernate.generic.interfac.IGenericDao;
import dn.core3.model.util.Auditory;
import edu.unas.spoi.oei.dao.interfac.IDependencyDao;
import edu.unas.spoi.oei.model.Dependency;
import edu.unas.spoi.oei.model.DependencyHasABP;
import edu.unas.spoi.oei.service.interfac.IDependencyService;
import edu.unas.spoi.ppto.dao.interfac.IBudgetCeilingDao;
import edu.unas.spoi.ppto.model.BudgetCeiling;
import edu.unas.spoi.ppto.model.Classifier;
import edu.unas.spoi.ppto.model.FundingSource;
import edu.unas.spoi.security.dao.interfac.IInvolvedDao;
import edu.unas.spoi.security.model.Person;
import edu.unas.spoi.security.model.User;
import gkfire.hibernate.OrderFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Dependency service.
 *
 * @author CORE i7
 */
@Service("dependencyService")
public class DependencyService extends GenericService<Dependency, Integer> implements IDependencyService {

    @Autowired
    @Qualifier("dependencyDao")
    private IDependencyDao dependencyDao;
    @Autowired
    @Qualifier("budgetCeilingDao")
    private IBudgetCeilingDao budgetCeilingDao;
    @Autowired
    @Qualifier("involvedDao")
    private IInvolvedDao involvedDao;

    @Override
    protected IGenericDao<Dependency, Integer> getBasicDao() {
        return dependencyDao;
    }

    @Override
    public Dependency getParent(Integer dependencyId) {
        return dependencyDao.getParent(dependencyId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void saveOrUpdate(DependencyHasABP dependencyHasSpecificGoal) {
        dependencyDao.saveOrUpdate(dependencyHasSpecificGoal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void delete(Dependency object) {
        dependencyDao.deleteParent(object.getId());
        dependencyDao.delete(object);
    }

    @Override
    public List<DependencyHasABP> getSpecificGoalInYear(Integer dependencyId, Integer year) {
        return dependencyDao.getABPInYear(dependencyId, year);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void delete(DependencyHasABP dependencyHasSpecificGoal) {
        dependencyDao.delete(dependencyHasSpecificGoal);
    }

    @Override
    public List getListLazyForMainView(int page, int rows, String path, String name, Boolean operational, Integer operationYear, Boolean active, OrderFactory orderObject) {
        return dependencyDao.getListLazyForMainView(page, rows, path, name, operational, operationYear, active, orderObject);
    }

    @Override
    public Long countListForMainView(String path, String name, Boolean operational, Integer operationYear, Boolean active) {
        return dependencyDao.countListForMainView(path, name, operational, operationYear, active);
    }

    @Override
    public List getListForUserAssigment(Integer personId, Integer currentDependencyId,Integer operationYear) {
        List<Integer> dependenciesId = personId == null ? Collections.EMPTY_LIST : involvedDao.getDependenciesAvailable(new Person(personId));
        return dependencyDao.getListForUserAssigment(dependenciesId, currentDependencyId, operationYear);
    }

    @Override
    public List getListForCopyPreviousYear(Integer operationYear) {
        return dependencyDao.getListForCopyPreviousYear(operationYear);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void copyDataToYear(List<Map<String, Object>> data, Integer operationYear, User currentUser) {
        List<BudgetCeiling> budgetCeilings = new ArrayList<>();
        List<Dependency> dependencies = new ArrayList<>();
        for (Map<String, Object> item : data) {
            Dependency dependency = new Dependency();
            dependency.setPath((String) item.get("path"));
            dependency.setName((String) item.get("name"));
            dependency.setOperationYear(operationYear);
            dependency.setCode(dependency.getPath());
            dependency.setOperational((Boolean) item.get("operational"));
            try{
            if (item.get("id") != null && dependency.getOperational()) {
                dependency.setPreviousYearDependency(new Dependency((Integer) item.get("id")));
                dependency.setActivityBudgetPrograms(dependencyDao.getActivityBudgetProgramsIds((Integer) item.get("id")));
                List<Map<String, Object>> budgetMapList = budgetCeilingDao.getListForCopyPreviousYear((Integer) item.get("id"), operationYear - 1);
                for (Map<String, Object> budgetMap : budgetMapList) {
                    BudgetCeiling budgetCeiling = new BudgetCeiling();
                    budgetCeiling.setDependency(dependency);
                    budgetCeiling.setFundingSource(new FundingSource((Integer) budgetMap.get("fundingSource")));
                    budgetCeiling.setClassifier(new Classifier((Long) budgetMap.get("classifier")));
                    budgetCeiling.setQuantity((BigDecimal) budgetMap.get("quantity"));
                    budgetCeiling.setYear(operationYear);
                    budgetCeilings.add(budgetCeiling);
                }
            }
            }catch(Exception e){
                e.printStackTrace();
            }
            Auditory.make(dependency, currentUser);
            dependencies.add(dependency);
        }

        dependencies.sort(new Comparator<Dependency>() {
            @Override
            public int compare(Dependency o1, Dependency o2) {
                return o1.getPath().compareTo(o2.getPath());
            }
        });
        for (int i = 0; i < dependencies.size(); i++) {
            Dependency dependency = dependencies.get(i);
            Dependency parent = null;
            for (int j = i - 1; j >= 0; j--) {
                if (dependencies.get(i).getPath().startsWith(dependencies.get(j).getPath())) {
                    parent = dependencies.get(j);
                    break;
                }
            }
            if (parent != null) {
                dependency.setCode(dependency.getPath().replace(parent.getPath(), ""));
                dependency.setParent(parent);
            }
            dependencyDao.save(dependency);
        }
        for (BudgetCeiling budgetCeiling : budgetCeilings) {
            budgetCeilingDao.save(budgetCeiling);
        }
    }

    @Override
    public List getListOperationalBasicData(Integer operationYear) {
        return dependencyDao.getListOperationalBasicData(operationYear);
    }

    @Override
    public boolean validatePath(String path, Integer operationYear, Integer currentId) {
        return dependencyDao.validatePath(path, operationYear, currentId);
    }

    @Override
    public Dependency getBy(String path, Integer operationYear) {
        return dependencyDao.getBy(path, operationYear);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void saveOrUpdate(Dependency selected, Map<Integer, List<Map<String, Object>>> budgetCeilings) {
        dependencyDao.saveOrUpdate(selected);
        if (selected.getOperational()) {
            for (Map.Entry<Integer, List<Map<String, Object>>> entry : budgetCeilings.entrySet()) {
                for (Map<String, Object> item : entry.getValue()) {
                    BudgetCeiling budgetCeiling = new BudgetCeiling((Long) item.get("id"));
                    budgetCeiling.setQuantity((BigDecimal) (item.get("quantity") == null ? BigDecimal.ZERO : item.get("quantity")));
                    budgetCeiling.setClassifier(new Classifier((Long) item.get("classifierId")));
                    budgetCeiling.setDependency(selected);
                    budgetCeiling.setFundingSource(new FundingSource((Integer) item.get("fundingSourceId")));
                    budgetCeiling.setYear(selected.getOperationYear());
                    budgetCeilingDao.saveOrUpdate(budgetCeiling);
                }

            }
        }
    }

    @Override
    public boolean getAllowCopy(Integer operationYear) {
        return dependencyDao.getAllowCopy(operationYear);
    }

    @Override
    public List getChildrenDependenciesBasicData(String path, Integer operationYear) {
        return dependencyDao.getChildrenDependenciesBasicData(path,operationYear);    
    }


}
