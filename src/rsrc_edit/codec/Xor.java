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

/**
 *
 * @author user
 */
public class Xor extends Codec{
    
    public byte[] key = null;

    //===================================================================
    /**
     * 
     */
    public Xor() {
        super(Codec.XOR);
    }  

    //===================================================================
    /**
     * 
     * @return 
     */
    public byte[] getKey() {
        return key;
    }

    //===================================================================
    /**
     * 
     * @param passedHexStr  
     */
    public void setKey(String passedHexStr ) {
        
        String s = passedHexStr.replace("0x", "");
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                 + Character.digit(s.charAt(i+1), 16));
        }        
        
        key = data;
    }

    
    //===================================================================
    /**
     * 
     * @param passedBytes
     * @return 
     */
    @Override
    public byte[] encode(byte[] passedBytes ) {
        
        int key_len = key.length;
      
	//XOR the data
	for( int i = 0; i < passedBytes.length; i++ )
            passedBytes[i] = (byte)(passedBytes[i] ^ key[i % key_len]);	
        
        return passedBytes;
    }

    @Override
    public byte[] decode(byte[] passedBytes) {        
        return encode(passedBytes);
    }
    
}
