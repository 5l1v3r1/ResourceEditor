/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.JPanel;
import rsrc_edit.resource.ResourceDataEntry;

/**
 *
 * @author user
 */
public class ResourceController extends Controller implements Iconable, Ancestor {

    private ResourceDataEntry theRDE;
    protected Icon theIcon;
    private final List<ResourceController> childControllers = new ArrayList<>();

    public ResourceController(ResourceDataEntry passedRDE, boolean passedBool) {
        super(passedBool);
        theRDE = passedRDE;
        if( theRDE.toString().equals("String Table")){
            
            //Wrap data in byte buffer
            int marker = 26;
            byte[] stringData = theRDE.data;
            ByteBuffer aBB = ByteBuffer.wrap(stringData);
            aBB.order(ByteOrder.LITTLE_ENDIAN);
            aBB.position(marker);
            
            //Get size of string
            while( aBB.hasRemaining() ){
                
                int str_size = aBB.getShort() * 2;
                marker += 2;
                
                if( str_size > 0 ){
                    
                    byte[] aStrArr = new byte[str_size];
                    aBB.get(aStrArr);
                    marker += str_size;
                    
                    //Get the id
                    aBB.position( marker - 8);
                    byte[] idStr = new byte[8];
                    aBB.get(idStr);
                    try {
                        String uniStr = new String(idStr, "UTF-16LE");
                        byte[] id_bytes = uniStr.getBytes();
                        ByteBuffer idBB = ByteBuffer.wrap(id_bytes);
                        long id = idBB.getInt();
                        
                        ResourceDataEntry aRDE = new ResourceDataEntry(passedRDE.DataVirtualAddr + marker, str_size - 8, id,aStrArr);
                        aRDE.setType("String");
                        //Add the resource
                        ResourceController aRC = new ResourceController(aRDE, passedBool);
                        childControllers.add(aRC);
                        
                    } catch (UnsupportedEncodingException ex) {
                    }
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
        return null;
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
    
}
