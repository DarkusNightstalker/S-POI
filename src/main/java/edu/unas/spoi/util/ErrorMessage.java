/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.util;

/**
 * The type Error message.
 *
 * @author CORE i7
 */
public class ErrorMessage implements java.io.Serializable{
    private String code;
    private String content;
    private Object[] options;

    /**
     * Instantiates a new Error message.
     *
     * @param code    the code
     * @param content the content
     */
    public ErrorMessage(String code, String content) {
        this.code = code;
        this.content = content;
    }

    /**
     * Instantiates a new Error message.
     *
     * @param code    the code
     * @param content the content
     * @param options the options
     */
    public ErrorMessage(String code, String content, Object... options) {
        this.code = code;
        this.content = content;
        this.options = options;
    }


    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

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
     * Get options object [ ].
     *
     * @return the options
     */
    public Object[] getOptions() {
        return options;
    }

    /**
     * Sets options.
     *
     * @param options the options to set
     */
    public void setOptions(Object[] options) {
        this.options = options;
    }
}
