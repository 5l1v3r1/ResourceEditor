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
