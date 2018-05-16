/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.util;

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * The type Aes.
 *
 * @author CIUNAS
 */
public class AES {

    /**
     * Decrypt query link email string.
     *
     * @param q the q
     * @return the string
     * @throws Exception the exception
     */
    public static String decryptQueryLinkEmail(String q) throws Exception{
        return AES.decrypt(q.getBytes(), "Bker1989email___");
    }

    /**
     * Encrypt query link email string.
     *
     * @param link the link
     * @return the string
     * @throws Exception the exception
     */
    public static String encryptQueryLinkEmail(String link) throws Exception{
        return new String(encrypt(link, "Bker1989email___"));
    }

    /**
     * Encrypt byte [ ].
     *
     * @param text      the text
     * @param keyString the key string
     * @return the byte [ ]
     * @throws Exception the exception
     */
    public static byte[] encrypt(String text, String keyString) throws Exception {
        Key key = generateKey(keyString);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(text.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encVal);
        return encryptedValue.getBytes();
    }

    /**
     * Decrypt string.
     *
     * @param encryptedData the encrypted data
     * @param keyString     the key string
     * @return the string
     * @throws Exception the exception
     */
    public static String decrypt(byte[] encryptedData, String keyString) throws Exception {
        if(encryptedData == null) return null;
        Key key = generateKey(keyString);
        Cipher c = Cipher.getInstance("AES");
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(new String(encryptedData));
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
       
        return decryptedValue;
    }

    private static Key generateKey(String keyValue) throws Exception {
        Key key = new SecretKeySpec(keyValue.getBytes(), "AES");
        return key;
    }

}
