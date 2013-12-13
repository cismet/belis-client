/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.belis.util;

/**
 *
 * @author jruiz
 */
public class JnlpSystemPropertyHelper {

    public static String getProperty(String propertyName) {
        return getProperty(propertyName, null);
    }   
    
    public static String getProperty(String propertyName, final String defaultValue) {
        if (propertyName == null) {
            return null;
        }
        
        final String normalPropertyValue = System.getProperty(propertyName);
        
        if (normalPropertyValue == null) {
            propertyName = "jnlp." + propertyName;
        }
        
        return System.getProperty(propertyName, defaultValue);
    }
 
    public static void main(String[] args) {
        System.setProperty("testProp", "ohne jnlp");
        System.setProperty("jnlp.testProp", "mit jnlp");
        System.out.println(getProperty("testProp"));
    }
}
