package com.cneport.tophare.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ToZio {
	 /**
     * Answer a byte array compressed in the Zip format from bytes.
     * 
     * @param bytes
     *            a byte array
     * @param aName
     *            a String the represents a file name
     * @return byte[] compressed bytes
     * @throws IOException
     */
    public byte[] zipBytes(byte[] bytes) throws IOException {
        ByteArrayOutputStream tempOStream = null;
        BufferedOutputStream tempBOStream = null;
        ZipOutputStream tempZStream = null;
        ZipEntry tempEntry = null;
        byte[] tempBytes = null;

        tempOStream = new ByteArrayOutputStream(bytes.length);
        tempBOStream = new BufferedOutputStream(tempOStream);
        tempZStream = new ZipOutputStream(tempBOStream);
        tempEntry = new ZipEntry(String.valueOf(bytes.length));
        tempEntry.setMethod(ZipEntry.DEFLATED);
        tempEntry.setSize((long) bytes.length);
        
        tempZStream.putNextEntry(tempEntry);
        tempZStream.write(bytes, 0, bytes.length);
        tempZStream.flush();
        tempBOStream.flush();
        tempOStream.flush();
        tempZStream.close();
        tempBytes = tempOStream.toByteArray();
        tempOStream.close();
        tempBOStream.close();
        return tempBytes;
    }


    /**
     * Answer a byte array that has been decompressed from the Zip format.
     * 
     * @param bytes a byte array of compressed bytes
     * @return byte[] uncompressed bytes
     * @throws IOException
     */
    public void unzipBytes(byte[] bytes, OutputStream os) throws IOException {
        ByteArrayInputStream tempIStream = null;
        BufferedInputStream tempBIStream = null;
        ZipInputStream tempZIStream = null;
        ZipEntry tempEntry = null;
        long tempDecompressedSize = -1;
        byte[] tempUncompressedBuf = null;

        tempIStream = new ByteArrayInputStream(bytes, 0, bytes.length);
        tempBIStream = new BufferedInputStream(tempIStream);
        tempZIStream = new ZipInputStream(tempBIStream);
        tempEntry = tempZIStream.getNextEntry();
        
        if (tempEntry != null) {
            tempDecompressedSize = tempEntry.getCompressedSize();
            if (tempDecompressedSize < 0) {
                tempDecompressedSize = Long.parseLong(tempEntry.getName());
            }

            int size = (int)tempDecompressedSize;
            tempUncompressedBuf = new byte[size];
            int num = 0, count = 0;
            while ( true ) {
                count = tempZIStream.read(tempUncompressedBuf, 0, size - num );
                num += count;
                os.write( tempUncompressedBuf, 0, count );
                os.flush();
                if ( num >= size ) break;
            }
        }
        tempZIStream.close();
    }
}
