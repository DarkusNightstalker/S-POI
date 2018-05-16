/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.unas.spoi.util;

/**
 * The type Password generator.
 *
 * @author Danny
 */
public class PasswordGenerator {

    /**
     * The constant NUMEROS.
     */
    public static String NUMEROS = "0123456789";

    /**
     * The constant MAYUSCULAS.
     */
    public static String MAYUSCULAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * The constant MINUSCULAS.
     */
    public static String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * The constant ESPECIALES.
     */
    public static String ESPECIALES = "ñÑ";

    /**
     * Gets pin number.
     *
     * @return the pin number
     */
//
    public static String getPinNumber() {
        return getPassword(NUMEROS, 4);
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public static String getPassword() {
        return getPassword(8);
    }

    /**
     * Gets password.
     *
     * @param length the length
     * @return the password
     */
    public static String getPassword(int length) {
        return getPassword(NUMEROS + MAYUSCULAS + MINUSCULAS, length);
    }

    /**
     * Gets password.
     *
     * @param key    the key
     * @param length the length
     * @return the password
     */
    public static String getPassword(String key, int length) {
        String pswd = "";
        for (int i = 0; i < length; i++) {
            pswd += (key.charAt((int) (Math.random() * key.length())));
        }
        return pswd;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println(getPassword(NUMEROS,6));
    }
}