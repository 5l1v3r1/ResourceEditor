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
public class ImageOptionalHeader32 extends ImageOptionalHeader{
    
    public static final int IMAGE_NT_OPTIONAL_HDR32_MAGIC  = 0x10b;
    
    public int   BaseOfData;

    //
    // NT additional fields.
    //
    public long   ImageBase;
    public long   SectionAlignment;
    public long   FileAlignment;
    public int    MajorOperatingSystemVersion;
    public int    MinorOperatingSystemVersion;
    public int    MajorImageVersion;
    public int    MinorImageVersion;
    public int    MajorSubsystemVersion;
    public int    MinorSubsystemVersion;
    public long   Win32VersionValue;
    public long   SizeOfImage;
    public long   SizeOfHeaders;
    public long   CheckSum;
    public int    Subsystem;
    public int    DllCharacteristics;
    public long   SizeOfStackReserve;
    public long   SizeOfStackCommit;
    public long   SizeOfHeapReserve;
    public long   SizeOfHeapCommit;
    public long   LoaderFlags;
    public long   NumberOfRvaAndSizes;
    
    public ImageOptionalHeader32( ByteBuffer passedBB ) {
        
        super(passedBB);

        BaseOfData = passedBB.getInt() & 0xffffffff;        
        
        //Read all values from header
        ImageBase = passedBB.getInt() & 0xffffffff;
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
        
        SizeOfStackReserve = passedBB.getInt() & 0xffffffff;
        SizeOfStackCommit = passedBB.getInt() & 0xffffffff;
        SizeOfHeapReserve = passedBB.getInt() & 0xffffffff;
        SizeOfHeapCommit = passedBB.getInt() & 0xffffffff;
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
        return ret + 68;
    }
    
}
