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
    
    public static String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();
    }
    
     //===============================================================
    /**
     * 
     * @param value
     * @return 
     */
    public static short byteArrayToShort(byte[] value){

        short tempInt = 0;
        for(int i = 0, j = value.length; i < value.length; i++, j-- ){
            tempInt += (value[i] & 0xff) << (8 * (j - 1));
        }
        return tempInt;

    }

    //===============================================================
    /**
     * 
     * @param unicodebytes
     * @return 
     */
    public static byte[] unicodeToAscii(byte[] unicodebytes) {
        byte[] retBytes = null;
        if( unicodebytes.length % 2 == 0 ){
            //Get every other byte
            retBytes = new byte[unicodebytes.length / 2];            
            for(int i = 0; i < retBytes.length; i++ ){
                retBytes[i] = unicodebytes[i * 2];
            }
        }
        return retBytes;
    }
}
