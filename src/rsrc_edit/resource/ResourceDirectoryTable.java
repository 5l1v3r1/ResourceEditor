/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
