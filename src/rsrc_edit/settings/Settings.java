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
import rsrc_edit.resource.ResourceString;

/**
 *
 * @author user
 */
public class Settings implements Serializable{
    
    private static final long serialVersionUID = 1L;
    public String encoding = "None";
    public final Map<String, ResourceString> ID_STRING_MAP = new HashMap<>();
    public static final String SETTINGS_FILENAME = "settings.dat";
    
    //====================================================================
    /**
     * 
     */
    public Settings(){
//        populateIdStringMap();
    }
    
    //====================================================================
    /**
     * 
     */    
    private void populateIdStringMap(){
        ID_STRING_MAP.put("0x2fc6b91a", new ResourceString("Watchdog Host File Path"));
        ID_STRING_MAP.put("0x1117294c", new ResourceString("Payload Host File Path"));
        ID_STRING_MAP.put("0x2226292c", new ResourceString("JVM Path"));
        ID_STRING_MAP.put("0x1a621151", new ResourceString("Service Name"));
        ID_STRING_MAP.put("0x14541541", new ResourceString("Service Description"));
        ID_STRING_MAP.put("0x13251438", new ResourceString("Alternate Data Stream Host File Path"));
        ID_STRING_MAP.put("0x16231721", new ResourceString("Registry Persistence Key Name"));
    } 
    
    //====================================================================
    /**
     * 
     * @param passedId
     * @return 
     */
    public String getIdDescription( String passedId ){
        
        String name = "< Undefined >";
        ResourceString retVal;
        synchronized(ID_STRING_MAP){
            retVal = ID_STRING_MAP.get(passedId);
        }    
        
        if(retVal != null )
            name = retVal.getName();
        
        return name;
    }

    //====================================================================
    /**
     * 
     * @param stringId
     * @param labelVal 
     */
    public void setStringForId(String stringId, String labelVal) {
        
        ResourceString retVal;
        synchronized(ID_STRING_MAP){
            retVal = ID_STRING_MAP.get(stringId);
        }  
        
        //Create or set
        if( retVal == null){
            retVal = new ResourceString(labelVal);
            synchronized(ID_STRING_MAP){
                ID_STRING_MAP.put( stringId, retVal );
            }
        } else {
            retVal.setName(labelVal);
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

    //====================================================================
    /**
     * 
     * @param stringId
     * @param strVal 
     */
    public void setIdValue(String stringId, String strVal) {
        ResourceString resStr;
        synchronized(ID_STRING_MAP){
            resStr = ID_STRING_MAP.get(stringId);
        }  
        //Set the value
        if( resStr != null ){
            resStr.setValue(strVal);
        }
    }
    
    //====================================================================
    /**
     * 
     * @param stringId 
     * @return  
     */
    public String getIdValue(String stringId) {
        
        String retStr = "";
        ResourceString resStr;
        synchronized(ID_STRING_MAP){
            resStr = ID_STRING_MAP.get(stringId);
        }  
        //Set the value
        if( resStr != null ){
            retStr = resStr.getValue();
        }
        return retStr;
    }

    //====================================================================
    /**
     * 
     * @param stringId
     * @param name 
     */
    public void setIdCodecName(String stringId, String name) {
        
        ResourceString resStr;
        synchronized(ID_STRING_MAP){
            resStr = ID_STRING_MAP.get(stringId);
        }  
        
        //Set the encoding
        if( resStr != null )
            resStr.setEncoding(name);
        
    }

    //====================================================================
    /**
     * 
     * @param stringId
     * @return 
     */
    public String getIdCodecName(String stringId) {
        String retStr = "";
        ResourceString resStr;
        synchronized(ID_STRING_MAP){
            resStr = ID_STRING_MAP.get(stringId);
        }
        
        //Get the encoding string
        if( resStr != null )
            retStr = resStr.getEncoding();
        
        return retStr;
        
    }
    
}
