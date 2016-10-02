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

import java.nio.ByteBuffer;

/**
 *
 * @author user
 */
public class ImageSectionHeader {
    
    private static final int IMAGE_SIZEOF_SECTION_HEADER = 40;
    private static final int IMAGE_SIZEOF_SHORT_NAME = 8;
    
    public String Name;
    public long  PhysicalAddress_VirtualSize;
    //public long  VirtualSize;
    public long  VirtualAddress;
    public long  SizeOfRawData;
    public long  PointerToRawData;
    public long  PointerToRelocations;
    public long  PointerToLinenumbers;
    public int NumberOfRelocations;
    public int NumberOfLinenumbers;
    public long Characteristics;

    public ImageSectionHeader( ByteBuffer passedBB ) {  
        
        byte[] tmp = new byte[IMAGE_SIZEOF_SHORT_NAME];
        passedBB.get(tmp);
        Name = new String(tmp).trim();
                
        PhysicalAddress_VirtualSize = passedBB.getInt() & 0xffffffff;
        //VirtualSize = passedBB.getInt() & 0xffffffff;
        VirtualAddress = passedBB.getInt() & 0xffffffff;         
        SizeOfRawData = passedBB.getInt() & 0xffffffff;
        PointerToRawData = passedBB.getInt() & 0xffffffff;
        PointerToRelocations = passedBB.getInt() & 0xffffffff; 
        PointerToLinenumbers = passedBB.getInt() & 0xffffffff; 
        
        NumberOfRelocations = passedBB.getShort() & 0xffff;
        NumberOfRelocations = passedBB.getShort() & 0xffff;
        
        Characteristics = passedBB.getInt() & 0xffffffff; 
    }
    
}
