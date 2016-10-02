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
package rsrc_edit.resource;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class ResourceDirectoryTable {
    
    public static long Characteristics;
    public static long TimeStamp;  
    public short   MajorVersion;  
    public short   MinorVersion; 
    public int   NumberOfNameEntries;              
    public int   NumberOfIDEntries;
    
    List<ResourceDirectoryEntry> DirectoryEntries = new ArrayList<>();
    
    public ResourceDirectoryTable( ByteBuffer passedBB, long delta, long write_delta ) {   
        
        Characteristics = passedBB.getInt() & 0xffffffff;
        TimeStamp = passedBB.getInt() & 0xffffffff;
        
        MajorVersion  = passedBB.getShort();
        MinorVersion  = passedBB.getShort();
        NumberOfNameEntries = passedBB.getShort();
        NumberOfIDEntries = passedBB.getShort();
        
        //Parse each named entry
        for( int i = 0; i < NumberOfNameEntries + NumberOfIDEntries; i++ ){
            ResourceDirectoryEntry anEntry = new ResourceDirectoryEntry(passedBB);
            DirectoryEntries.add(anEntry);
        }
               
        //Loop through each entry
        for( ResourceDirectoryEntry anEntry : DirectoryEntries ){
            passedBB.rewind();
            anEntry.populateLeafNodes(passedBB, delta, write_delta);
        }
        
    }

    //=========================================================================
    /**
     * 
     * @return 
     */
    public List<ResourceDataEntry> getResources() {
        List<ResourceDataEntry> resourceList = new ArrayList<>();
        for( ResourceDirectoryEntry anEntry : DirectoryEntries ){
            resourceList.addAll( anEntry.getResources() );
        }        
        return resourceList;
    }
    
}
