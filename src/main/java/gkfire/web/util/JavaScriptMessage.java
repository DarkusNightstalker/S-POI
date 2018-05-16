/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.util;

/**
 * The type Java script message.
 *
 * @author CORE i7
 */
public abstract class JavaScriptMessage implements java.io.Serializable{

    /**
     * To java script string.
     *
     * @return the string
     */
    public abstract String toJavaScript();

    /**
     * Execute.
     */
    public void execute() {
        BeanUtil.exceuteJS(toJavaScript());
    }
}
