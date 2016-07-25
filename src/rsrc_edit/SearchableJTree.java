
/*
 * SearchableJTree.java
 *
 */

package rsrc_edit;

import java.util.Enumeration;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 *  
 */
public class SearchableJTree extends JTree {
    
    
    //****************************************************************************
    /**
    * Returns the {@link TreePath} to the passed {@code objectToFind} within the
    * passed {@link JTree}.
    *
    * @param theTree       the {@link JTree} to be searched for {@code objectToFind}
    * @param objectToFind  the object whose path is to be found in {@code theTree}
    *
    * @return  the {@link TreePath} to the passed object ({@code null} if it is not found})
    */
    private TreePath findObjectPathInTree( Object objectToFind ) {
        
        TreePath objectPath = null;
        if( objectToFind != null ) {

            DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)getModel().getRoot();
            Enumeration<DefaultMutableTreeNode> e = rootNode.depthFirstEnumeration();
            DefaultMutableTreeNode currentNode;
            while( e.hasMoreElements() ) {
                currentNode = e.nextElement();
                if( objectToFind == currentNode.getUserObject() ) {
                    objectPath = new TreePath( currentNode.getPath() );
                    break;
                }
            }
        }

        return objectPath;
    }
    
    //===========================================================================
    /**
    * Selects the tree node containing the {@code objectToSelect}.  If the {@code objectToSelect}
    * is {@code null}, or it is not found in any tree node, the root node will be selected.
    *
    * @param objectToSelect  the object to be selected in the tree
    */
    public void setSelectedObject( Object objectToSelect ) {
        TreePath objectPath = findObjectPathInTree( objectToSelect );
        setSelectionPath( objectPath );      
    }

}
