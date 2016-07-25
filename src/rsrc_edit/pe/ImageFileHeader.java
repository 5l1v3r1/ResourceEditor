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
