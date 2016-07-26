/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author user
 */
public class Constants {
    
    public static final Map<String, String> ID_STRING_MAP = new HashMap<>();
    static{
        populateIdStringMap();
    }
        
    private static void populateIdStringMap(){
        ID_STRING_MAP.put("0x2fc6b91a", "Watchdog Host File Path");
        ID_STRING_MAP.put("0x1117294c", "Payload Host File Path");
    }      
    
}
