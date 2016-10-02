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
public class ImageFileHeader {
    
    public static final int IMAGE_FILE_HEADER_SIZE = 20;
    
    public static final int IMAGE_FILE_MACHINE_I386     = 0x14c; // Intel x86
    public static final int IMAGE_FILE_MACHINE_IA64     = 0x200; // Intel Itanium Processor Family (IPF)
    public static final int IMAGE_FILE_MACHINE_AMD64    = 0x8664; // x64 (AMD64 or EM64T)
    
    public int Machine;
    public short NumberOfSections;
    public int   TimeDateStamp;
    public int   PointerToSymbolTable;
    public int   NumberOfSymbols;
    public int   SizeOfOptionalHeader;
    public int   Characteristics;

    public ImageFileHeader( ByteBuffer passedBB ) {
        
        //Read all values from header
        Machine = passedBB.getShort() & 0xffff;
        NumberOfSections = passedBB.getShort();
        TimeDateStamp = passedBB.getInt();
        PointerToSymbolTable = passedBB.getInt();
        NumberOfSymbols = passedBB.getInt();
        SizeOfOptionalHeader = passedBB.getShort() & 0xffff;
        Characteristics = passedBB.getShort() & 0xffff;
    }
    
}
