/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.exception;

/**
 * The type Import exception.
 *
 * @author User
 */
public class ImportException extends Exception{
    private String title;

    /**
     * Instantiates a new Import exception.
     *
     * @param title   the title
     * @param message the message
     */
    public ImportException(String title, String message) {
        super(message);
        this.title = title;
    }
}
