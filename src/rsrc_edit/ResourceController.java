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
package rsrc_edit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import static rsrc_edit.MainJFrame.IMAGE_DIRECTORY_ENTRY_RESOURCE;
import rsrc_edit.codec.Codec;
import rsrc_edit.pe.DosHeader;
import rsrc_edit.pe.ImageDataDirectory;
import rsrc_edit.pe.ImageSectionHeader;
import rsrc_edit.pe.NtHeader;
import rsrc_edit.resource.ResourceDataEntry;
import rsrc_edit.resource.ResourceDirectoryTable;

/**
 *
 * @author user
 */
public class ResourceController extends Controller implements Iconable, Ancestor {

    private ResourceDataEntry theRDE;
    protected Icon theIcon;
    private final List<ResourceController> childControllers = new ArrayList<>();
    private JPanel thePanel = null;
    private final MainJFrame parentFrame;

    public ResourceController( MainJFrame passedFrame, ResourceDataEntry passedRDE ) {
        super();
        parentFrame = passedFrame;
        theRDE = passedRDE;
        if( theRDE.toString().equals("String Table")){
            
            //Wrap data in byte buffer
            int marker = 0;
            byte[] stringData = theRDE.data;
            
            ByteBuffer aBB = ByteBuffer.wrap(stringData);
            aBB.order(ByteOrder.LITTLE_ENDIAN);
            
            //Get size of string
            while( aBB.hasRemaining() ){
                
                int length = aBB.getShort();
                marker += 2;
                if( length == 0x0)
                    continue;
                
                
                int data_off = marker;
                int str_size = length * 2;
                
                if( str_size > 0 ){
                    
                    byte[] aStrArr = new byte[str_size];
                    aBB.get(aStrArr);
                    marker += str_size;
                    
                    //Get the id
                    aBB.position( marker - 8);
                    byte[] idStr = new byte[4];
                    for( int i =0; i < 4; i++ ){
                        idStr[i] = aBB.get();
                        aBB.get();
                    }
                    
                    ByteBuffer idBB = ByteBuffer.wrap(idStr);
                    String id = String.format("0x%08x", idBB.getInt() );

                    int actual_str_size = str_size - 10;
                    ResourceDataEntry aRDE = new ResourceDataEntry(passedRDE.DataVirtualAddr + data_off, 
                            theRDE.Embedded_Write_Delta, actual_str_size, id,aStrArr );
                    
                    aRDE.setType("String");
                    //Add the resource
                    ResourceController aRC = new ResourceController( parentFrame, aRDE );
                    childControllers.add(aRC);
                        
                   
                }
            }
            
        } else if(theRDE.getType().equals(ResourceDataEntry.NAMED)){
            
            //Check if the internal binary is a PE and parse it
            if( theRDE.data[0] == (byte)'M' && theRDE.data[1] == (byte)'Z' ){
                
                //Wrapped buffer
                ByteBuffer buf = ByteBuffer.wrap(theRDE.data);
                buf.order(ByteOrder.LITTLE_ENDIAN); // or ByteOrder.BIG_ENDIAN
                
                try {
                    //Read the DOS header
                    DosHeader theDosHeader = new DosHeader(buf);
                    buf.position((int) theDosHeader.e_lfanew);
                                         
                    //Get the NT header
                    NtHeader theNtHeader = new NtHeader(buf, theDosHeader.e_lfanew);

                    //Get Sections
                    HashMap<String, ImageSectionHeader> sectionHeaderMap = new HashMap<>();
                    for( int i =0; i < theNtHeader.FileHeader.NumberOfSections; i++ ){
                        ImageSectionHeader aSectionHeader = new ImageSectionHeader(buf);
                        sectionHeaderMap.put( aSectionHeader.Name, aSectionHeader);                                        
                    }

                    ImageDataDirectory relocDataDir = theNtHeader.OptionalHeader.DataDirectory[IMAGE_DIRECTORY_ENTRY_RESOURCE];
                    long resourceVitualAddr = relocDataDir.VirtualAddress;
                    
                    //Get the resource section
                    ImageSectionHeader resourceSectionHeader = sectionHeaderMap.get(".rsrc");
                   // long size = resourceSectionHeader.SizeOfRawData;
                    if( resourceSectionHeader != null ){
                        long rawResourceAddr = resourceSectionHeader.PointerToRawData;

                        //Get the resource data
                        buf.position((int) rawResourceAddr);
                        ByteBuffer buf2 = buf.slice();
                        buf2.order(ByteOrder.LITTLE_ENDIAN); // or ByteOrder.BIG_ENDIAN

                        //Parse resource table
                        ResourceDirectoryTable mainTable = new ResourceDirectoryTable(buf2, (rawResourceAddr - resourceVitualAddr), theRDE.DataVirtualAddr );
                        List<ResourceDataEntry> resourceList = mainTable.getResources();

                         //Go get data
                        for( ResourceDataEntry aRDS : resourceList ) {

                            if( aRDS.data == null ){                       

                                buf.position((int) aRDS.DataVirtualAddr);

                                aRDS.data = new byte[(int)aRDS.Size];
                                buf.get(aRDS.data);

                            }

                            //Add the resource
                            ResourceController aRC = new ResourceController( parentFrame, aRDS );
                            childControllers.add(aRC);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ResourceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }
    }
   
    @Override
    public Icon getIcon() {
        return theIcon;
    }

    @Override
    public ResourceDataEntry getObject() {
        return theRDE;
    }

    @Override
    public void setObject(Object passedObj) {
        theRDE = (ResourceDataEntry)passedObj;
    }

    @Override
    public JPanel getRootPanel() {
        
        if( thePanel == null ){
            if( theRDE.getType().equals("String")){
                thePanel = new StringResourceJPanel( this );
            } else if( theRDE.getType().equals(ResourceDataEntry.NAMED) ){
                thePanel = new BinaryResourceJPanel( this );                
            } else {
                thePanel = new JPanel();
            }
        }
        return thePanel;
    }

    @Override
    public void updateComponents() {
    
    }
    
    @Override
    public String toString() {
        return theRDE.toString();
    }

    @Override
    public List<ResourceController> getChildren() {
        return new ArrayList<>(childControllers);
    }

    @Override
    public void addChild(ResourceController libraryItem) {
        childControllers.add(libraryItem);
    }

    @Override
    public void insertChild(ResourceController libraryItem, int index) {
        childControllers.add(index, libraryItem);
    }

    @Override
    public boolean removeChild(ResourceController libraryItem) {
        return childControllers.remove(libraryItem);
    }

    @Override
    public boolean hasChild(ResourceController libraryItem) {
        return childControllers.size() > 0;
    }

    //=======================================================================
    /**
     * 
     */
    public MainJFrame getParentFrame() {
        return parentFrame;
    }

    //=======================================================================
    /**
     * 
     * @param passedString
     */
    public void saveString( String passedString ) {
        
        File binFile = getParentFrame().getLoadedFile();
        if( binFile != null ){
            
            //Don't count the string size and id fields
            ResourceDataEntry aRDE = getObject();
            int availSpace = (int) (aRDE.Size);
            
            FileOutputStream aFOS = null;
            FileChannel currentBinaryFileChannel = null;
                          

            //Get selected encoding
            MainJFrame aFrame = getParentFrame();
            Codec curCodec = aFrame.getSelectedCodec();
            byte[] value = curCodec.encode(passedString.getBytes());
            passedString = new String(value);               

            if( passedString.length() > 0 ){
                byte[] strBytes;
                try {
                    strBytes = passedString.getBytes("UTF-16LE");
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(StringResourceJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }
                
                if( strBytes.length > aRDE.Size ){
                    JOptionPane.showMessageDialog( getParentFrame(), "Length of the provided string is too long.","Error", JOptionPane.ERROR_MESSAGE );
                } else {

                    //Pad the buffer up to the available size
                    strBytes = Arrays.copyOf(strBytes, availSpace);
                    //Copy to object data
                    System.arraycopy(strBytes, 0, aRDE.data, 0, availSpace);
                    try { 
                        //Open file
                        aFOS = new FileOutputStream(binFile, true);
                        currentBinaryFileChannel = aFOS.getChannel();

                        //Write to the file
                        ByteBuffer aBB = ByteBuffer.wrap(strBytes);
                        currentBinaryFileChannel.write(aBB, aRDE.DataVirtualAddr + aRDE.Embedded_Write_Delta);


                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(StringResourceJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(StringResourceJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            if(currentBinaryFileChannel != null )
                                currentBinaryFileChannel.close();
                        } catch (IOException ex) {
                        }
                    }
                }
            }   
            
        }
    }
    
}
