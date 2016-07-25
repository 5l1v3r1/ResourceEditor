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
