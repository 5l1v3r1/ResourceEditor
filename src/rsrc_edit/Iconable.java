
package rsrc_edit;

import javax.swing.Icon;
import javax.swing.plaf.metal.MetalIconFactory;

/**
 *
 *  
 */
public interface Iconable {
    
    //Tree Icon Constants
    public static final int ICON_HEIGHT = MetalIconFactory.getTreeFolderIcon().getIconHeight();
    public static final int ICON_WIDTH = MetalIconFactory.getTreeFolderIcon().getIconWidth();
    
    // ========================================================================
    /**
     *  Returns the icon for object.
     * @return 
     */
    public Icon getIcon();

}
