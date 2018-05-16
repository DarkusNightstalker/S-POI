/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.security.rol;

import edu.unas.spoi.security.model.Rol;
import edu.unas.spoi.security.service.interfac.IRolService;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.persistence.MapsId;
import lombok.Data;

/**
 * The type Rol info bean.
 *
 * @author Darkus Nightmare
 */
@Data
@ManagedBean
@SessionScoped
public class RolInfoBean implements java.io.Serializable {

    @ManagedProperty(value = "#{rolService}")
    private IRolService rolService;

    private Rol rol;
    private List permissionCategories;
    private Map<Integer, List> permissions;

    /**
     * Refresh.
     */
    public void refresh() {
        rol = null;
        permissionCategories = Collections.EMPTY_LIST;
        permissions = Collections.EMPTY_MAP;
    }

    /**
     * Load.
     *
     * @param id the id
     */
    public void load(final Integer id) {
        rol = rolService.getById(id);
        permissionCategories = rolService.getPermissionCategoriesBasicData(id);
        permissions = new HashMap();
        permissionCategories.forEach(new Consumer<Map<String, Object>>() {
            @Override
            public void accept(Map<String, Object> item) {
                permissions.put((Integer) item.get("id"), rolService.getPermissionsBasicData((Integer) item.get("id"), id));
            }
        });
    }
}
