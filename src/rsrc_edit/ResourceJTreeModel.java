
/*
 *  MainGuiTreeModel.java
 *
 */

package rsrc_edit;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 *
 *  
 */
public class ResourceJTreeModel extends DefaultTreeModel {
    
    //=============================================================
    /**
     *  Constructor
     * 
     * @param root 
    */
    public ResourceJTreeModel(TreeNode root ) {
        super(root);
    }
    
       

    //=============================================================
    /**
     *  Gets the local host root node.
     * 
     * @return 
     */
    public DefaultMutableTreeNode getLocalRoot() {
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)getRoot();
        return (DefaultMutableTreeNode)rootNode.getFirstChild();
    }
    
}
