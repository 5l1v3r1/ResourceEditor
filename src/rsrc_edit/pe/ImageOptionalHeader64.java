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
public class ImageOptionalHeader64 extends ImageOptionalHeader{
    
    public static final int IMAGE_NT_OPTIONAL_HDR64_MAGIC = 0x20b;
    
//    public int Magic;
//    public int  MajorLinkerVersion;
//    public int  MinorLinkerVersion;
//    public long   SizeOfCode;
//    public long   SizeOfInitializedData;
//    public long   SizeOfUninitializedData;
//    public long   AddressOfEntryPoint;
//    public long   BaseOfCode;
    public long   ImageBase;
    public long   SectionAlignment;
    public long    FileAlignment;
    public int        MajorOperatingSystemVersion;
    public int        MinorOperatingSystemVersion;
    public int        MajorImageVersion;
    public int        MinorImageVersion;
    public int        MajorSubsystemVersion;
    public int        MinorSubsystemVersion;
    public long       Win32VersionValue;
    public long       SizeOfImage;
    public long       SizeOfHeaders;
    public long       CheckSum;
    public int        Subsystem;
    public int        DllCharacteristics;
    public long   SizeOfStackReserve;
    public long   SizeOfStackCommit;
    public long   SizeOfHeapReserve;
    public long   SizeOfHeapCommit;
    public long        LoaderFlags;
    public long        NumberOfRvaAndSizes;
   
    public ImageOptionalHeader64( ByteBuffer passedBB ) {
        
        super(passedBB);
        
        ImageBase = passedBB.getLong();
        
        SectionAlignment = passedBB.getInt() & 0xffffffff;
        FileAlignment = passedBB.getInt() & 0xffffffff;
        
        MajorOperatingSystemVersion = passedBB.getShort() & 0xffff;
        MinorOperatingSystemVersion = passedBB.getShort() & 0xffff;
        MajorImageVersion = passedBB.getShort() & 0xffff;
        MinorImageVersion = passedBB.getShort() & 0xffff;
        MajorSubsystemVersion = passedBB.getShort() & 0xffff;
        MinorSubsystemVersion = passedBB.getShort() & 0xffff;
        
        Win32VersionValue = passedBB.getInt() & 0xffffffff;
        SizeOfImage = passedBB.getInt() & 0xffffffff;
        SizeOfHeaders = passedBB.getInt() & 0xffffffff;
        CheckSum = passedBB.getInt() & 0xffffffff;
        
        Subsystem= passedBB.getShort() & 0xffff;
        DllCharacteristics= passedBB.getShort() & 0xffff;
        
        SizeOfStackReserve = passedBB.getLong();
        SizeOfStackCommit = passedBB.getLong();
        SizeOfHeapReserve = passedBB.getLong();
        SizeOfHeapCommit = passedBB.getLong();
        
        LoaderFlags = passedBB.getInt() & 0xffffffff;
        NumberOfRvaAndSizes = passedBB.getInt() & 0xffffffff;
        
        populateImageDataDirectoryArray(passedBB);
        
    }
    
    //=========================================================================
    /**
     * 
     * @return 
     */
    @Override
    public int size() {
        int ret = super.size();
        return ret + 78;
    }
    
}
