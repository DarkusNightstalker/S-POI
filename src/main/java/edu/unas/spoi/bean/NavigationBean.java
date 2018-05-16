/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * The type Navigation bean.
 *
 * @author CORE i7
 */
@ManagedBean
@SessionScoped
public class NavigationBean implements java.io.Serializable{
    private String content = "/pages/home.xhtml";
    private String javascriptMenu = "Menu.change('#form-nav-menu\\\\:home',false)";

    /**
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets javascript menu.
     *
     * @return the javascriptMenu
     */
    public String getJavascriptMenu() {
        return javascriptMenu;
    }

    /**
     * Sets javascript menu.
     *
     * @param javascriptMenu the javascriptMenu to set
     */
    public void setJavascriptMenu(String javascriptMenu) {
        this.javascriptMenu = javascriptMenu;
    }
    
}
