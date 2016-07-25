/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit.pe;

import java.nio.ByteBuffer;

/**
 *
 * @author user
 */
public class ImageOptionalHeader {
    
    public static final int IMAGE_NUMBEROF_DIRECTORY_ENTRIES  =  16;
    
    public int   Magic;
    public int   MajorLinkerVersion;
    public int   MinorLinkerVersion;
    public long  SizeOfCode;
    public long   SizeOfInitializedData;
    public long   SizeOfUninitializedData;
    public long   AddressOfEntryPoint;
    public long   BaseOfCode;    
    public ImageDataDirectory[] DataDirectory = new ImageDataDirectory[IMAGE_NUMBEROF_DIRECTORY_ENTRIES];

    public ImageOptionalHeader( ByteBuffer passedBB ) {
        
        Magic = passedBB.getShort() & 0xffff;
        
        MajorLinkerVersion = passedBB.get() & 0xff;
        MinorLinkerVersion = passedBB.get() & 0xff;
        
        SizeOfCode = passedBB.getInt() & 0xffffffff;
        SizeOfInitializedData = passedBB.getInt() & 0xffffffff;
        SizeOfUninitializedData = passedBB.getInt() & 0xffffffff;
        AddressOfEntryPoint = passedBB.getInt() & 0xffffffff;
        BaseOfCode = passedBB.getInt() & 0xffffffff;
        
    }
    
    final protected void populateImageDataDirectoryArray( ByteBuffer passedBB){
        
        //Populate them
        for( int i = 0; i < IMAGE_NUMBEROF_DIRECTORY_ENTRIES; i++){
            DataDirectory[i] = new ImageDataDirectory(passedBB);
        }
        
    }
    
    //=========================================================================
    /**
     * 
     * @return 
     */
    public int size() {
        return 28 + (IMAGE_NUMBEROF_DIRECTORY_ENTRIES * 8 /* Size of ImageDataDir*/);
    }
    
}
