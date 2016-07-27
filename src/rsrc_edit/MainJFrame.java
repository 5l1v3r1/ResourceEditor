/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import rsrc_edit.pe.DosHeader;
import rsrc_edit.pe.NtHeader;
import rsrc_edit.pe.ImageSectionHeader;
import rsrc_edit.resource.ResourceDataEntry;
import rsrc_edit.resource.ResourceDirectoryTable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import rsrc_edit.pe.ImageDataDirectory;
import rsrc_edit.settings.Settings;
import rsrc_edit.settings.SettingsJDialog;
import rsrc_edit.settings.SettingsJDialogListener;

/**
 *
 * @author user
 */
public class MainJFrame extends javax.swing.JFrame implements TreeSelectionListener, SettingsJDialogListener {

    private JFileChooser theFileChooser = null;
    private final ResourceJTree mainJTree = new ResourceJTree();
    
    public static final int IMAGE_DIRECTORY_ENTRY_RESOURCE = 2;
    public static Map<String, String> theResourceMap = new HashMap<>();
   
    public static final String RESOURCE_IMG_STR = "rsrc.png";
    private File userSelectedFile;
    
    //Savable settings
    private Settings theSettings = null;
     
    
      
    /**
     * Creates new form ConfigJFrame
     */
    public MainJFrame() {
        initComponents();
        initializeComponents();
        
        //Set the icon
        setAppIconImage(RESOURCE_IMG_STR);
        
        //Center
        setLocationRelativeTo(null);
    }
    
     //===============================================================
    /**
     *  Initialize the panel components
     */
    private void initializeComponents() {
        
        theSettings = instantiateSettings();
        
        theFileChooser = new JFileChooser();
        theFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        theFileChooser.setMultiSelectionEnabled( false ); 
        
        ResourceJTreeModel theModel = new ResourceJTreeModel( new DefaultMutableTreeNode( new String("Root"), true ) );
        mainJTree.setRootVisible(false);
        mainJTree.setModel( theModel );
        mainJTree.setShowsRootHandles(true);
        mainJTree.setRowHeight(20);
                 
        //Set the resource map
        theResourceMap.put("9", "Accelerator Table");
        theResourceMap.put("21", "Animated Cursor");
        theResourceMap.put("22", "Animated Icon");
        theResourceMap.put("2", "Bitmap Resource");
        theResourceMap.put("1", "Cursor Resource");
        theResourceMap.put("5", "Dialog Box");
        theResourceMap.put("17", "RC File Name");
        theResourceMap.put("8", "Font Resource");
        theResourceMap.put("7", "Font Dir Resource");
        theResourceMap.put("23", "HTML Resource");
        theResourceMap.put("3", "Icon Resource");
        theResourceMap.put("24", "SxS Manifest");
        theResourceMap.put("4", "Menu Resource");
        theResourceMap.put("11", "Message-table Entry");
        theResourceMap.put("19", "Plug&Play Resource");
        theResourceMap.put("10", "Raw Data");
        theResourceMap.put("6", "String Table");
        theResourceMap.put("16", "Version Resource");
        theResourceMap.put("20", "VXD Resource");
               
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")                  
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        typeLabel = new javax.swing.JLabel();
        sizeLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        dataScrollPane = new javax.swing.JScrollPane();
        jTreeScrollPane = new javax.swing.JScrollPane();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        loadMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        settingsMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();        
        
        //Set preferred height
        jTreeScrollPane.setPreferredSize(new Dimension(100,322));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Resources");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Details"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Size:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Type:");

        typeLabel.setBackground(new java.awt.Color(255, 255, 255));
        typeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        typeLabel.setOpaque(true);

        sizeLabel.setBackground(new java.awt.Color(255, 255, 255));
        sizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sizeLabel.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sizeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Data");

        jTreeScrollPane.setViewportView(mainJTree);

        fileMenu.setText("File");

        loadMenuItem.setText("Load Binary");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadMenuItem);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainMenuBar.add(fileMenu);

        editMenu.setText("Edit");
        
        settingsMenuItem.setText("Settings");
        settingsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsMenuItemActionPerformed(evt);
            }            
        });
        editMenu.add(settingsMenuItem);
        
        mainMenuBar.add(editMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                    .addComponent(jTreeScrollPane))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dataScrollPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dataScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTreeScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }                  

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                           
        System.exit(0);
    }   
    
    //================================================================
    /**
     * 
     * @param evt 
     */
    private void settingsMenuItemActionPerformed(ActionEvent evt) {
        SettingsJDialog settingsDialog = new SettingsJDialog( this, true);
        settingsDialog.setVisible(true); // This blocks...(evt);
    }

    private void loadMenuItemActionPerformed(java.awt.event.ActionEvent evt) {                                           
        loadBinary();
    }                                         
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
        
        UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName());
      
        final MainJFrame theFrame = new MainJFrame();
        theFrame.setDefaultLookAndFeelDecorated(true);

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                theFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify             
    private javax.swing.JScrollPane dataScrollPane;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem settingsMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu editMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jTreeScrollPane;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JLabel sizeLabel;
    private javax.swing.JLabel typeLabel;
    // End of variables declaration         

       
     //======================================================================
    /**
    *   Populates the tree model
    */
    private boolean populateTreeModel( List<ResourceDataEntry> resourceList ) {

        setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR) );
        try {

            mainJTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
            mainJTree.setScrollsOnExpand( true );

            // Only add a TreeListener if one has not already been added
            if( mainJTree.getTreeSelectionListeners().length == 0) {
                mainJTree.addTreeSelectionListener( this );
            }
             //Clear the model
            ResourceJTreeModel theModel = new ResourceJTreeModel(new DefaultMutableTreeNode( new String("Root"), true ));
            mainJTree.setModel(theModel);
            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) theModel.getRoot();
            
            for( ResourceDataEntry aRDE : resourceList ){
                
                //Get an entry
                ResourceController aController = new ResourceController( this, aRDE );
                int index = parentNode.getChildCount();
                mainJTree.addObjectToTree( aController, parentNode, index );
                
            }

            mainJTree.setRootVisible( false );
            mainJTree.getModel().reload();
            mainJTree.setSelectionRow(0);

            //Pack the componenents
            pack();

        } finally {
            setCursor( null );
        }

        return true;

    }
    
    //=========================================================================
    /**
     * 
     * @return 
     */
    public File getLoadedFile(){
        return userSelectedFile;
    }
    
    //Load binary from disk
    private void loadBinary() {
        
        int returnVal = theFileChooser.showDialog( this, "Select File(s)" ); //Show the dialog
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
        if(userSelectedFile != null){
            
            FileInputStream aFIS = null;
            FileChannel currentBinaryFileChannel = null;
            try {
                
                //Open file
                aFIS = new FileInputStream(userSelectedFile);
                currentBinaryFileChannel = aFIS.getChannel();
                                
                //Allocate space for dos header
                ByteBuffer buf = ByteBuffer.allocate(DosHeader.DOS_HEADER_SIZE);
                buf.order(ByteOrder.LITTLE_ENDIAN); // or ByteOrder.BIG_ENDIAN
                currentBinaryFileChannel.read(buf);
                buf.flip();
                
                //Read the DOS header
                DosHeader theDosHeader = new DosHeader(buf);
                currentBinaryFileChannel.position(theDosHeader.e_lfanew);
              
                //Read the NT header
                buf = ByteBuffer.allocate(0x2000);
                buf.order(ByteOrder.LITTLE_ENDIAN); // or ByteOrder.BIG_ENDIAN
                currentBinaryFileChannel.read(buf);
                buf.flip();
                
                //Get the NT header
                NtHeader theNtHeader = new NtHeader(buf);
                
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
                long size = resourceSectionHeader.SizeOfRawData;
                long rawResourceAddr = resourceSectionHeader.PointerToRawData;
                 
                //Get the resource data
                buf = ByteBuffer.allocate((int) size);
                buf.order(ByteOrder.LITTLE_ENDIAN); // or ByteOrder.BIG_ENDIAN
                currentBinaryFileChannel.read(buf, rawResourceAddr);
                buf.flip();
                         
                //Parse resource table
                ResourceDirectoryTable mainTable = new ResourceDirectoryTable(buf, rawResourceAddr - resourceVitualAddr, 0 );
                List<ResourceDataEntry> resourceList = mainTable.getResources();
                
                 //Go get data
                for( ResourceDataEntry aRDS : resourceList ) {
                    
                    if( aRDS.data == null ){
                        try {

                            ByteBuffer aBB = ByteBuffer.allocate((int) aRDS.Size);
                            currentBinaryFileChannel.position(aRDS.DataVirtualAddr);
                            currentBinaryFileChannel.read(aBB);
                            aBB.flip();

                            aRDS.data = new byte[(int)aRDS.Size];
                            aBB.get(aRDS.data);

                        } catch (IOException ex) {
                            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                //Add to the tree map
                populateTreeModel(resourceList);
                
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if( currentBinaryFileChannel != null )
                    try { 
                        currentBinaryFileChannel.close(); 
                    } catch (IOException ex) { }
            }
        
        }
    }

    //==========================================================================
    /**
     * 
     * @param evt 
     */
    @Override
    public void valueChanged(TreeSelectionEvent evt) {
        
          //Returns the last path element of the selection.
        //Note: This method is useful only when the selection model allows only a single selection.
        DefaultMutableTreeNode theSelectedNode = (DefaultMutableTreeNode)mainJTree.getLastSelectedPathComponent();

        if ( theSelectedNode != null ) {

            TreePath[] theSelNodes = mainJTree.getSelectionPaths();

            if(theSelNodes != null && theSelNodes.length == 1){
                
                setCursor( Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
                
                ResourceController selectedObject = (ResourceController)theSelectedNode.getUserObject();
                ResourceDataEntry aRDS = selectedObject.getObject();
                sizeLabel.setText( String.valueOf( aRDS.Size) );                
                typeLabel.setText( aRDS.getType());
                
                //Set the panel
                JPanel objPanel = selectedObject.getRootPanel();
                if( objPanel != null )
                    dataScrollPane.setViewportView(objPanel);

                setCursor( null );
            }
        }
    
    }
    
    //======================================================================
    /**
    * Sets the icon to be used for the application (on the top left of the windows
    * and on the taskbar).
    *
    * @param imageName the name of the image to be used as the application icon
    */
    private void setAppIconImage( String imageName ) {
        Image appIcon = Utilities.loadImageFromJar( imageName );
        if( appIcon != null ) {
            setIconImage( appIcon );
        }
    }    

    //======================================================================
    /**
     * 
     * @return 
     */
    @Override
    public MainJFrame getParentJFrame() {
        return this;
    }

    //======================================================================
    /**
     * 
     * @return 
     */
    public Settings getSettings() {
        return theSettings;
    }
    
    //======================================================================
    /**
     * 
     * @return 
     */
    private Settings instantiateSettings() {
         
        Settings localSettings = null;
        InputStream file = null;
        try{
            //use buffering
            File settingsFile = new File(Settings.SETTINGS_FILENAME);
            if( settingsFile.exists() ){
                file = new FileInputStream(settingsFile);
                InputStream buffer = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream (buffer);
                try{
                    //deserialize the List
                    localSettings = (Settings)input.readObject();

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
                } finally{
                    input.close();
                }
            }
      
        } catch (IOException ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                
                if( file != null )
                    file.close();
                
            } catch (IOException ex) {
                Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Create a new one
        if( localSettings == null )
            localSettings = new Settings();        
    
        return localSettings;
    }
    
     //======================================================================
    /**
     * 
     */
    public void saveSettings() {
         
        OutputStream filestream = null;
        try{
            //use buffering
            //serialize the List
            //note the use of abstract base class references           
            //use buffering
            filestream = new FileOutputStream(Settings.SETTINGS_FILENAME);
            OutputStream buffer = new BufferedOutputStream(filestream);
            ObjectOutput output = new ObjectOutputStream(buffer);
            output.writeObject(theSettings);
            output.flush();
            output.close();
                  
        } catch (IOException ex) {
            Logger.getLogger(MainJFrame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if( filestream != null ){
                try {
                    filestream.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}