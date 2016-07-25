
/*
 * IconNode.java
 *
 */

package rsrc_edit;

import javax.swing.Icon;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 *  
 */
public class IconNode extends DefaultMutableTreeNode {

    //===========================================================================
    /**
     *  Constructor
     * 
     * @param passedObject
     * @param passedBool 
     */
    public IconNode(Object passedObject, boolean passedBool ) {
        super(passedObject, passedBool );    
    }

    //===========================================================================
    /**
     *  Return the icon
     * 
     * @return 
     */
    public Icon getIcon() {

        Icon theIcon = null;
        Object theObject = getUserObject();
        
        if( theObject instanceof Iconable){
            Iconable theController = (Iconable)theObject;
            return theController.getIcon();
        }

        return theIcon;
    }

}
