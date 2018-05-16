/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.report;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Table.
 *
 * @author Jhoan Brayam
 */
public class Table implements java.io.Serializable{
    private String name;
    private String icon;
    private String description;
    
    private boolean selected;
    
    private List<Field> fields;
    
    private List<Table> dependencies;

    /**
     * Instantiates a new Table.
     */
    public Table() {
        fields = new ArrayList();
        dependencies = new ArrayList();
    }

    /**
     * Instantiates a new Table.
     *
     * @param name        the name
     * @param icon        the icon
     * @param description the description
     */
    public Table(String name, String icon, String description) {
        this();
        this.name = name;
        this.icon = icon;
        this.description = description;
    }


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets icon.
     *
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets icon.
     *
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets fields.
     *
     * @return the fields
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * Sets fields.
     *
     * @param fields the fields to set
     */
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    /**
     * Gets dependencies.
     *
     * @return the dependencies
     */
    public List<Table> getDependencies() {
        return dependencies;
    }

    /**
     * Sets dependencies.
     *
     * @param dependencies the dependencies to set
     */
    public void setDependencies(List<Table> dependencies) {
        this.dependencies = dependencies;
    }

    /**
     * Is selected boolean.
     *
     * @return the selected
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets selected.
     *
     * @param selected the selected to set
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    
    
}
