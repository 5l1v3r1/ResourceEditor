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
package rsrc_edit.pe;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author user
 */
public class NtHeader {
    
    public static final int IMAGE_NT_SIGNATURE = 0x00004550;  /* PE00 */
    
    public int Signature;
    public ImageFileHeader FileHeader;
    public ImageOptionalHeader OptionalHeader;
    public int fileHeaderOffset;
    
    public NtHeader( ByteBuffer passedBB, int passed_file_offset ) throws IOException{
        
        fileHeaderOffset = passed_file_offset;
        
        //Read all values from header
        Signature = passedBB.getInt();
        
        //Not a valid file header
        if( Signature != IMAGE_NT_SIGNATURE )
            throw new IOException("File does not have a valid NT header.");
        
        //Read all values from header
        FileHeader = new ImageFileHeader(passedBB);
        
        //Read optional header
        switch (FileHeader.Machine) {            
            case ImageFileHeader.IMAGE_FILE_MACHINE_I386:
                OptionalHeader = new ImageOptionalHeader32(passedBB);
                break;
            case ImageFileHeader.IMAGE_FILE_MACHINE_AMD64:
                OptionalHeader = new ImageOptionalHeader64(passedBB);
                break;
            default:
                throw new IOException("Unsupported image file header.");
        }
    }
    
    public boolean isX86(){
        return OptionalHeader instanceof ImageOptionalHeader32;
    }
    
    public boolean isX64(){
        return OptionalHeader instanceof ImageOptionalHeader64;
    }

    //=========================================================================
    /**
     * 
     * @return 
     */
    public long size() {
        return 4 + ImageFileHeader.IMAGE_FILE_HEADER_SIZE + OptionalHeader.size();
    }

    //=========================================================================
    /**
     * 
     * @return 
     */
    public int getFileOffset() {
        return fileHeaderOffset;
    }
    
}
