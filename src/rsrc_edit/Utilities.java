/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsrc_edit;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import javax.imageio.ImageIO;

/**
 *
 * @author user
 */
public class Utilities {
    
    public static final String IMAGE_PATH_IN_JAR= "rsrc_edit/images";
    private static final URL ourUrl= Utilities.class.getProtectionDomain().getCodeSource().getLocation();
    private static File classPath;   
     
    static {
        try {
            URI ourUri= ourUrl.toURI();
            classPath = new File( ourUri );
        } catch( URISyntaxException ex ) {
            ex = null;
        }
    }
    
       //=====================================================================
    /**
    * Reads the bytes from an element in the jar or build path.
    * 
    * @param passedRelativePathInJar
    * @param passedJarElementName
    * 
    * @return an array of bytes representing the jar element
    */
    private static byte [] getBytesForJarElement( File jarPath, String passedRelativePathInJar, String passedJarElementName ) {

        byte [] theBytesForJarEntry= null;    
        if ( jarPath != null ) {
            try {
                if ( jarPath.isDirectory() ) {

                    // Then this execution is running within the IDE's '.../build/classes/...'
                    // and there isnt actualy a JAR file !!
                    String tmpFullFilenameWithPath = jarPath.getAbsolutePath() + File.separator + passedRelativePathInJar;
                    File testFile = new File( tmpFullFilenameWithPath, passedJarElementName );

                    if( testFile.canRead() ) {
                        
                        FileInputStream tmpFileInputStream= new FileInputStream( testFile );
                        int fileLen= (int)testFile.length();
                        theBytesForJarEntry= new byte[ fileLen ];

                        int totalQtyReadAlready= 0;
                        int qtyStillNeeded= fileLen;
                        int qtyJustRead;

                        try{
                            while ( totalQtyReadAlready < fileLen ) {
                                qtyJustRead= tmpFileInputStream.read( theBytesForJarEntry, fileLen-qtyStillNeeded, qtyStillNeeded);

                                if ( qtyJustRead < 0 ) { // eg -1 stream closed
                                    break;
                                }

                                totalQtyReadAlready+= qtyJustRead;
                                qtyStillNeeded-= qtyJustRead;
                            }
                            // if totalQtyread != btyeSizeOfEntry then an error has occurred

                        } finally {
                            tmpFileInputStream.close();
                        }
                    }

                } else {

                    JarFile tmpJarFile = null;
                    try {
                        
                        tmpJarFile= new JarFile( jarPath );

                        // These exist too, but not needed: JarEntry tmpJarEntry= tmpJarFile.getJarEntry( ImagePathWithinJar +passedImageName);
                        String tmpIntraJarPathAndFilename= passedRelativePathInJar + "/" +passedJarElementName;

                        ZipEntry tmpZipEntry= tmpJarFile.getEntry( tmpIntraJarPathAndFilename );
                        if ( tmpZipEntry != null ) {
                            
                            int btyeSizeOfEntry= (int)tmpZipEntry.getSize();
                            theBytesForJarEntry= new byte[btyeSizeOfEntry];
                            
                            try (InputStream tmpInputStream = tmpJarFile.getInputStream( tmpZipEntry )) {

                                int totalQtyReadAlready= 0;
                                int qtyStillNeeded= btyeSizeOfEntry;

                                int qtyJustRead;
                                while ( totalQtyReadAlready < btyeSizeOfEntry ) {
                                    qtyJustRead= tmpInputStream.read( theBytesForJarEntry, btyeSizeOfEntry-qtyStillNeeded, qtyStillNeeded);

                                    if ( qtyJustRead < 0 ) { // eg -1 stream closed
                                        break;
                                    }

                                    totalQtyReadAlready+= qtyJustRead;
                                    qtyStillNeeded-= qtyJustRead;
                                }
                            // if totalQtyread != btyeSizeOfEntry then an error has occurred
                            }
                        }

                    } finally {

                        //Close the jar
                        if(tmpJarFile != null){

                            try{
                                tmpJarFile.close();
                            } catch (IOException ex) {
                                ex = null;
                            }
                        }
                    }
                }
            
            } catch (IOException ex) {
                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return theBytesForJarEntry;
    }

           // ===============================================
    /**
     * Convenience method for loadImageFromJar
     * @param passedImageName
     * @return 
    */
    public static BufferedImage loadImageFromJar ( String passedImageName ) {

       return loadImageFromJar ( passedImageName, classPath, IMAGE_PATH_IN_JAR);
    }
  
    // ===============================================
    /**
    * Returns the image in the jar, or jar supplement, that matches the {@code passedImageName).
    *
    * @param passedImageName   the name of the image to be loaded
     * @param jarPath
     * @param imagePath
    *
    * @return  the {@link BufferedImage} with the given filename,
    *          or {@code null} if unable to locate a matching image
    */
    public static BufferedImage loadImageFromJar ( String passedImageName, File jarPath, String imagePath ) {

        BufferedImage rtnImage = null;
        byte [] theBytes = getBytesForJarElement( jarPath, imagePath, passedImageName );

        if ( theBytes != null ) {
            rtnImage = createImageFromBytes( theBytes );    //get an "Image"
        }

        return rtnImage;
    }
    
     /**
    * Creates a {@link BufferedImage} from the passed array of {@code byte}s.
    * The images produced from this method tend to be better quality than the images
    * produced by {@link #toBufferedImage(Image)}.  However, if you cannot obtain the
    * image bytes, that provides an alternate method to use for creating a {@link BufferedImage}
    * from an {@link Image}.
    *
    * @param passedBytesForImage   the {@code byte} array containing image data
    *
    * @return  the {@link BufferedImage} created from the passed {@code byte} array
    *          of image data, {@code null} if unable to create an image from the passed bytes
    */
    private static BufferedImage createImageFromBytes( byte [] passedBytesForImage ) {
        BufferedImage rtnImage = null;

        if ( passedBytesForImage != null ) {
           
            try {
                rtnImage = ImageIO.read( new ByteArrayInputStream( passedBytesForImage ) );
            } catch (IOException ex) {
                Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }

        return rtnImage;
    }
    
}
