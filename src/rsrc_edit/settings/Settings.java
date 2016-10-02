/*

Copyright (C) 2013-2016, Securifera, Inc 

All rights reserved. 

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
	this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
	this list of conditions and the following disclaimer in the documentation 
	and/or other materials provided with the distribution.

    * Neither the name of Securifera, Inc nor the names of its contributors may be 
	used to endorse or promote products derived from this software without specific
	prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER 
OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

================================================================================

The copyright on this package is held by Securifera, Inc

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
