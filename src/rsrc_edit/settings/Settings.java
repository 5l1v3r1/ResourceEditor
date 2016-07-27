/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit.settings;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author user
 */
public class Settings implements Serializable{
    
    public String encoding = "None";
    public final Map<String, String> ID_STRING_MAP = new HashMap<>();
    public static final String SETTINGS_FILENAME = "settings.dat";
    
    //====================================================================
    /**
     * 
     */
    public Settings(){
        populateIdStringMap();
    }
    
    //====================================================================
    /**
     * 
     */    
    private void populateIdStringMap(){
        ID_STRING_MAP.put("0x2fc6b91a", "Watchdog Host File Path");
        ID_STRING_MAP.put("0x1117294c", "Payload Host File Path");
        //ID_STRING_MAP.put("0x2226292c", "JVM Path");
        ID_STRING_MAP.put("0x16231721", "Alternate Data Stream Host File Path");
        ID_STRING_MAP.put("0x13251438", "Registry Persistence Key Name");
    } 
    
    //====================================================================
    /**
     * 
     * @param passedStr
     * @return 
     */
    public String getStringForId( String passedStr ){
        String retVal;
        synchronized(ID_STRING_MAP){
            retVal = ID_STRING_MAP.get(passedStr);
        }        
        return retVal;
    }

    //====================================================================
    /**
     * 
     * @param stringId
     * @param labelVal 
     */
    public void setStringForId(String stringId, String labelVal) {
        synchronized(ID_STRING_MAP){
            ID_STRING_MAP.put( stringId, labelVal );
        }
    }

    //====================================================================
    /**
     * 
     * @return 
     */
    public String getDefaultStringEncoding() {
        return encoding;
    }
    
    //====================================================================
    /**
     * 
     * @param passedEnc 
     */
    public void setDefaultStringEncoding(String passedEnc ) {
        encoding = passedEnc;
    }
    
}
