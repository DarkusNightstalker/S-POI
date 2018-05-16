/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.rol;

import edu.unas.spoi.bean.NavigationBean;
import edu.unas.spoi.bean.SessionBean;
import edu.unas.spoi.security.model.Permission;
import edu.unas.spoi.security.model.PermissionCategory;
import edu.unas.spoi.security.model.Rol;
import edu.unas.spoi.security.service.interfac.IPermissionCategoryService;
import edu.unas.spoi.security.service.interfac.IPermissionService;
import edu.unas.spoi.security.service.interfac.IRolService;
import edu.unas.spoi.util.SmartMessage;
import gkfire.auditory.Auditory;
import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.OrderList;
import gkfire.web.bean.AManagedBean;
import gkfire.web.bean.ILoadable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import lombok.Data;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * The type Managed rol bean.
 *
 * @author Jhoan Brayam
 */
@ManagedBean
@SessionScoped
@Data
public class ManagedRolBean extends AManagedBean<Rol, IRolService> implements ILoadable {

    @ManagedProperty(value = "#{permissionCategoryService}")
    private IPermissionCategoryService permissionCategoryService;
    @ManagedProperty(value = "#{permissionService}")
    private IPermissionService permissionService;
    @ManagedProperty(value = "#{rolService}")
    private IRolService mainService;
    @ManagedProperty(value = "#{sessionBean}")
    private SessionBean sessionBean;
    @ManagedProperty(value = "#{navigationBean}")
    private NavigationBean navigationBean;

    private PermissionSearcher permissionSearcher;

    private String code;
    private String name;
    private List<Permission> permissions;

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
        permissionSearcher = new PermissionSearcher();
    }

    @Override
    public boolean save() {
        if (!mainService.validateCode(code, selected.getId())) {
            SmartMessage.errorMessage("El codigo '" + code + "' ya pertenece a otro rol");
            return false;
        } else {
            String content = getSelected().getId() != null ? "Se ha actualizado un rol" : "Se ha creado un rol";
            super.save(); //To change body of generated methods, choose Tools | Templates.
            SmartMessage.saveMessage(content);
            return true;
        }
    }

    @Override
    public void onLoad(boolean allowAjax) {
    }

    @Override
    protected void fillFields() {
        if (selected.getId() != null) {
            AliasList aliasList = new AliasList();
            aliasList.add("rols", "r");
            CriterionList criterionList = new CriterionList();
            criterionList.add(Restrictions.eq("r.id", selected.getId()));
            permissions = permissionService.addRestrictionsVariant(Arrays.asList(aliasList, criterionList));
        } else {
            permissions = new ArrayList();
        }
        permissionSearcher.update();
        name = selected.getName();
        code = selected.getCode();
        createAgain = selected.getId() == null;
    }

    @Override
    protected void clearFields() {
    }

    @Override
    protected void fillSelected() {
        selected.setCode(code.trim());
        selected.setName(name.trim());
        List<Permission> permissions = new ArrayList(permissionSearcher.selecteds.keySet());
        selected.setPermissions(new ArrayList());
        for (Permission item : permissions) {
            if (permissionSearcher.selecteds.get(item)) {
                selected.getPermissions().add(item);
            }
        }
        Auditory.make(selected, sessionBean.getCurrentUser());
    }

    /**
     * The type Permission searcher.
     */
    @Data
    public class PermissionSearcher implements java.io.Serializable {

        private List<PermissionCategory> categories;
        private Map<Integer, List<Permission>> permissions;
        private Map<Permission, Boolean> selecteds;
        private List<Permission> permissionNoCategory;

        /**
         * Update.
         */
        public void update() {
            OrderList orderList = new OrderList();
            orderList.add(Order.asc("id"));
            this.categories = getPermissionCategoryService().addRestrictionsVariant(Arrays.asList(orderList));
            this.permissions = new HashMap();
            this.selecteds = new HashMap();
            for (PermissionCategory category : this.categories) {
                List<Permission> permissions = getPermissionService().getBy(category);
                for (Permission item1 : permissions) {
                    selecteds.put(item1, ManagedRolBean.this.getPermissions().contains(item1));
                }
                this.permissions.put(category.getId(), permissions);
            }
            permissionNoCategory = getPermissionService().getBy(null);
            for (Permission item1 : permissionNoCategory) {
                selecteds.put(item1, ManagedRolBean.this.getPermissions().contains(item1));
            }
        }
    }
}
