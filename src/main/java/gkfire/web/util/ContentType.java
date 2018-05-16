/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gkfire.web.util;

/**
 * The enum Content type.
 *
 * @author Danny
 */
public enum ContentType {
    /**
     * Doc content type.
     */
    DOC("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    /**
     * Docx content type.
     */
    DOCX("application/wsword"),
    /**
     * Jpg content type.
     */
    JPG("image/jpeg"),
    /**
     * Png content type.
     */
    PNG("image/png"),
    /**
     * Bmp content type.
     */
    BMP("image/bmp"),
    /**
     * Pdf content type.
     */
    PDF("application/pdf");
    
    private final String description;

    private ContentType(String description) {
        this.description = description;
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
     * Get by description content type.
     *
     * @param description the description
     * @return the content type
     */
    public static ContentType getByDescription(String description){
        for(ContentType c : values()){
            if(c.description.contentEquals(description)){
                return c;
            }
        }
        return null;
    }
}
