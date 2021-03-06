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

import java.awt.Cursor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import rsrc_edit.codec.Codec;
import rsrc_edit.resource.ResourceDataEntry;

/**
 *
 * @author user
 */
public class BinaryResourceJPanel extends javax.swing.JPanel {

    private final ResourceController theResourceController;
    private JFileChooser theFileChooser = null;
    
    /**
     * Creates new form BinaryResourceJPanel
     * @param passedController
     */
    public BinaryResourceJPanel( ResourceController passedController ) {
        theResourceController = passedController;
        initComponents();
        initializeComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        getDataButton = new javax.swing.JButton();
        setDataButton = new javax.swing.JButton();
        dataScrollPane = new javax.swing.JScrollPane();
        dataTextArea = new javax.swing.JTextArea();

        getDataButton.setText("View Data");
        getDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getDataButtonActionPerformed(evt);
            }
        });

        setDataButton.setText("Set Data");
        setDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setDataButtonActionPerformed(evt);
            }
        });

        dataTextArea.setEditable(false);
        dataTextArea.setColumns(20);
        dataTextArea.setFont(new java.awt.Font("Courier New", 0, 13)); // NOI18N
        dataTextArea.setLineWrap(true);
        dataTextArea.setRows(5);
        dataScrollPane.setViewportView(dataTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataScrollPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(68, 158, Short.MAX_VALUE)
                        .addComponent(getDataButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(setDataButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getDataButton)
                    .addComponent(setDataButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void getDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getDataButtonActionPerformed
      
        setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
        dataTextArea.setText("");
        
        ResourceDataEntry aRDE = theResourceController.getObject();
        String retStr = Utilities.byteArrayToHex( aRDE.data );
        dataTextArea.append(retStr);
        repaint();
        
        setCursor( null );
    }//GEN-LAST:event_getDataButtonActionPerformed

    private void setDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setDataButtonActionPerformed
    
        setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
        File userSelectedFile = null;
        int returnVal = theFileChooser.showDialog( this, "Select File" ); //Show the dialog
        switch( returnVal ) {

            case JFileChooser.CANCEL_OPTION: //If the user canceled the selecting...
            case JFileChooser.ERROR_OPTION: //If the dialog was dismissed or an error occurred...
                break; //Do nothing

            case JFileChooser.APPROVE_OPTION: //If the user approved the selection...
                userSelectedFile = theFileChooser.getSelectedFile(); //Get the files the user selected
                break;
            default:
                break;

        }

        //Open file and read it
        if(userSelectedFile != null && userSelectedFile.exists()){
            
            ResourceDataEntry aRDE = theResourceController.getObject();
            int file_size  = (int)userSelectedFile.length();
            if( file_size > aRDE.Size ){
                setCursor( null );
                JOptionPane.showMessageDialog( this, "Length of the provided string is too long.","Error", JOptionPane.ERROR_MESSAGE );
                return;    
            }
            
            //Get file length
            FileChannel currentBinaryFileChannel = null;
            try {    
                
                //Open file
                FileInputStream aFIS = new FileInputStream(userSelectedFile);
                currentBinaryFileChannel = aFIS.getChannel();
                
                ByteBuffer aBB = ByteBuffer.allocate(file_size);
                currentBinaryFileChannel.read(aBB);
                aBB.flip();
                
                //Create new array
                byte[] file_bytes = new byte[file_size];
                aBB.get(file_bytes);
                 
                //Set the data
                //Pad the buffer up to the available size
                File binFile = theResourceController.getParentFrame().getLoadedFile();
                if( binFile != null ){
                    
                    FileOutputStream aFOS = null;
                    FileChannel outputBinaryFileChannel = null;

                    //Get selected encoding
                    MainJFrame aFrame = theResourceController.getParentFrame();
                    Codec curCodec = aFrame.getSelectedCodec();
                    byte[] encodedBytes = curCodec.encode( file_bytes );  
                    
                    if( encodedBytes.length <= aRDE.Size ){
                    
                        //Pad out the bytes
                        byte[] writeBytes = Arrays.copyOf(encodedBytes, (int)aRDE.Size);
                        aRDE.data = writeBytes;
                        try { 

                            //Open file
                            aFOS = new FileOutputStream(binFile, true);
                            outputBinaryFileChannel = aFOS.getChannel();

                            //Write to the file
                            ByteBuffer outBuf = ByteBuffer.wrap(writeBytes);
                            outputBinaryFileChannel.write(outBuf, aRDE.DataVirtualAddr + aRDE.Embedded_Write_Delta);


                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(StringResourceJPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(StringResourceJPanel.class.getName()).log(Level.SEVERE, null, ex);
                        } finally {
                            
                            try {
                                if(outputBinaryFileChannel != null )
                                    outputBinaryFileChannel.close();
                            } catch (IOException ex) {
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog( this, "Length of the provided string is too long.","Error", JOptionPane.ERROR_MESSAGE );
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(BinaryResourceJPanel.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if( currentBinaryFileChannel != null )
                        currentBinaryFileChannel.close();
                } catch (IOException ex) {
                }
            }
            
        }
        
        setCursor( null );
    }//GEN-LAST:event_setDataButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane dataScrollPane;
    private javax.swing.JTextArea dataTextArea;
    private javax.swing.JButton getDataButton;
    private javax.swing.JButton setDataButton;
    // End of variables declaration//GEN-END:variables

    //========================================================================
    /**
     * 
     * @param passedCodec 
     */
    public void encodingChanged( Codec passedCodec ) {    
           
        ResourceDataEntry aRDE =  theResourceController.getObject();
        if( aRDE.data[0] != 0x0 || aRDE.data[1] != 0x0){
            byte[] bytes = Arrays.copyOfRange(aRDE.data, 0, aRDE.data.length);
            byte[] decoded = passedCodec.decode(bytes);
            String retStr = Utilities.byteArrayToHex( decoded );
            dataTextArea.setText( retStr );
        } 
    }   

    //========================================================================
    /**
     * 
     */
    private void initializeComponents() {
        theFileChooser = new JFileChooser();
        theFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        theFileChooser.setMultiSelectionEnabled( false ); 
    }

}
