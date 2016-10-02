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
package rsrc_edit.codec;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Codec {

    protected String name = "None";
    private static List<Codec> codecList = new ArrayList<>();
    
    public static final String BYTE_SPACER = "Byte Spacer";
    public static final String XOR = "XOR";
   
    //========================================================================
    /*
    */
    public static List<Codec> getCodecs() {
        
        //Is the list empty
        if( codecList.isEmpty() ){
            codecList.add( new Codec() );
            codecList.add( new ByteSpacer() );
            codecList.add( new Xor() );
        }
        
        return codecList;
        
    }
    
    //========================================================================
    /*
    
    */
    public static Codec getCodec(String codecStr ) {
        Codec retCodec = null;
        
        for( Codec aCodec: codecList ){
            if( aCodec.name.equals(codecStr) ){
                retCodec = aCodec;
                break;
            }
        }
        return retCodec;
    }
    
    //========================================================================
    /**
     * 
     * @param codecName 
     */
    public Codec( String codecName ){
        name = codecName;
    }

    //========================================================================
    /**
     * 
     */
    public Codec() {
    
    }
    
    //=========================================================================
    /**
    * 
     * @param passedBytes
    * @return 
    */
    public byte[] encode( byte[] passedBytes ){
        return passedBytes;
    }
    
    //=========================================================================
    /**
    * 
     * @param passedBytes
    * @return 
    */
    public byte[] decode( byte[] passedBytes ){
        return passedBytes;
    }
    
    //========================================================================
    /*
    */
    @Override
    public String toString(){
        return name;
    }
}
