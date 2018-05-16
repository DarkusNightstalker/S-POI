/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.unas.spoi.util;

import gkfire.web.util.JavaScriptMessage;
import lombok.Data;

/**
 * The type Smart message.
 *
 * @author CORE i7
 */
@Data
public class SmartMessage extends JavaScriptMessage {

    /**
     * Save message.
     *
     * @param content the content
     */
    public static void saveMessage(String content) {
        new SmartMessage("Cambios guardados!!", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-save shake animated").execute();
    }

    /**
     * Success message.
     *
     * @param content the content
     */
    public static void successMessage(String content) {
        new SmartMessage("Operación exitosa", content, SmartMessage.SmartColor.SUCCESS, 3000L, "fa fa-check shake animated").execute();
    }

    /**
     * Error message.
     *
     * @param content the content
     */
    public static void errorMessage(String content) {
        new SmartMessage("ERROR!", content, SmartMessage.SmartColor.DANGER, 3000L, "fa fa-save shake animated").execute();

    }
    private String title;
    private String content;
    private SmartColor color;
    private Long timeout;
    private String icon;

    /**
     * Instantiates a new Smart message.
     *
     * @param title   the title
     * @param content the content
     * @param color   the color
     * @param timeout the timeout
     * @param icon    the icon
     */
    public SmartMessage(String title, String content, SmartColor color, Long timeout, String icon) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.timeout = timeout;
        this.icon = icon;
    }

    @Override
    public String toJavaScript() {
        return "$.smallBox({"
                + "     title: \"" + title + "\","
                + "     content: \"" + content + "\","
                + "     color: \"" + color.getRgb() + "\","
                + (timeout == null ? "" : "     timeout: 5000,")
                + "     icon: \"" + icon + "\""
                + "});";
    }

    /**
     * The enum Smart color.
     */
    public enum SmartColor {

        /**
         * Danger smart color.
         */
        DANGER("#C46A69"), /**
         * Success smart color.
         */
        SUCCESS("#739E73");
        private final String rgb;

        private SmartColor(String rgb) {
            this.rgb = rgb;
        }

        /**
         * Gets rgb.
         *
         * @return the rgb
         */
        public String getRgb() {
            return rgb;
        }
    }
}
