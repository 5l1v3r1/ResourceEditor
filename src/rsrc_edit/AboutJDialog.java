package rsrc_edit;

import java.awt.Image;
import javax.swing.JFrame;



/**
 *
 *  
 */
public class AboutJDialog extends javax.swing.JDialog {

    
    private static final String aboutTitle = "About Resource Editor";
    public static String CURRENT_VERSION = "1.0.0.0";
   
    //===========================================================================
    /**
     * Creates new form that is displayed in the center of the screen by default.
     * This can be changed by using one of the {@link #setLocation} methods on the created object.
     * @param parent
     */
    public AboutJDialog( JFrame parent ) {
        super(parent, true);
        initComponents();

        initializeCustomData();

        setLocationRelativeTo( null );
    }



    /**
     * Initializes any custom data to be used on the window.
     */
    private void initializeCustomData() {
        
        setTitle( aboutTitle );
        setVersionInfo( CURRENT_VERSION );        
        Image appIcon = Utilities.loadImageFromJar( MainJFrame.RESOURCE_IMG_STR );        
        setAppIconImage(appIcon);

    }
    
    
    
    /**
     * Sets the application version to display on this window.
     *
     * @param version   the version to be displayed
     */
    public void setVersionInfo( String version ) {
        versionValueLabel.setText( version );
    }

    /**
     * Sets the icon to be used on the window (in the top left of the dialog 
     * and also displayed next to the text on the dialog).
     *
     * @param iconImage   the image to be used on the dialog
     */
    public void setAppIconImage( Image iconImage ) {
        setIconImage( iconImage );
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        logoImageLabel = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        developerValueLabel = new javax.swing.JLabel();
        versionValueLabel = new javax.swing.JLabel();
        versionLabel = new javax.swing.JLabel();
        developerLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setModal(true);
        setName("aboutDialog"); // NOI18N
        setResizable(false);

        logoImageLabel.setForeground(new java.awt.Color(0, 0, 255));
        logoImageLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        developerValueLabel.setText("<html>Ryan Wincey</html>");

        versionValueLabel.setText("1.0.0.0");

        versionLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        versionLabel.setText("Version: ");

        developerLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        developerLabel.setText("Developer:");

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(developerLabel)
                    .addComponent(versionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(versionValueLabel)
                    .addComponent(developerValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(versionLabel)
                    .addComponent(versionValueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(developerLabel)
                    .addComponent(developerValueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logoImageLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(logoImageLabel)
                .addGap(18, 18, 18)
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel developerLabel;
    private javax.swing.JLabel developerValueLabel;
    private javax.swing.JLabel logoImageLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JLabel versionValueLabel;
    // End of variables declaration//GEN-END:variables

}
