/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.report;

import org.hibernate.criterion.Projections;

/**
 * The type Custom report.
 *
 * @author Jhoan Brayam
 */
public class CustomReport {
    
    
    private Table classifierTable;
    private Table necessaryItemTable;
    private Table bneItemTable;

    /**
     * Init.
     */
    public void init(){
        Field cCode = new Field("Código",Projections.property("code"));
        Field cName = new Field("Nombre",Projections.property("name"));
        
        classifierTable = new Table("Clasificadores","fa fa-tags","Campos de los clasificadores");
       
        classifierTable.getFields().add(cCode);
        classifierTable.getFields().add(cName);
        
        necessaryItemTable = new Table("Clasificadores","fa fa-tags","Campos de los registros de item");
    }
}
