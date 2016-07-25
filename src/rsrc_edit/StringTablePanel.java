/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit;

import java.awt.Dimension;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JTextField;
import rsrc_edit.resource.ResourceDataEntry;

/**
 *
 * @author user
 */
public class StringTablePanel extends JPanel {

    //=======================================================================
    /**
     * 
     * @param aRDS 
     */
    public StringTablePanel(ResourceDataEntry aRDS) {
        populatePanel(aRDS);    
    }

    //======================================================================
    /**
     * 
     * @param aRDS 
     */
    private void populatePanel(ResourceDataEntry aRDS) {
        
        try {
            //Wrap data in byte buffer
            byte[] stringData = aRDS.data;
            ByteBuffer aBB = ByteBuffer.wrap(stringData);
            aBB.order(ByteOrder.LITTLE_ENDIAN);
            aBB.position(26);
            
            //Get size of string
            int str_size = aBB.getShort();
            byte[] aStrArr = new byte[str_size];
            aBB.get(aStrArr);
            
            //Decode from unicode
            String uniStr = new String(aStrArr, "UTF-16LE");
            
            JTextField aTextField = new JTextField();
            aTextField.setPreferredSize( new Dimension(200, 20));
            aTextField.setText(uniStr);
            add(aTextField);
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(StringTablePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
