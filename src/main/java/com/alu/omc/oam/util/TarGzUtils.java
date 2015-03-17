package com.alu.omc.oam.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;

public class TarGzUtils 
{
	private static final int BYTE_LEN = 4096;
	private static final String TAR_GZ_SUFFIX = ".tar.gz";
	
    public static boolean unTargzFile(String compressedFileName, String destDir) 
    {
    	if(compressedFileName == null || destDir == null)
    		return false;
    	if(!compressedFileName.endsWith(TAR_GZ_SUFFIX))
    		return false;
    	File srcZipFile = new File(compressedFileName);
    	File destRootFile = new File(destDir);
    	if(!srcZipFile.exists())
    		return false;
    	if(!srcZipFile.isFile())
    		return false;
    	if(!destRootFile.exists())
    		destRootFile.mkdirs();
    	if(!destRootFile.isDirectory())
    		return false;
    	
	    return unzipOarFile(compressedFileName, destDir);
 
    }
 
    private static boolean unzipOarFile(String compressedFileName, String outputDirectory) {
       FileInputStream fis = null;
       ArchiveInputStream in = null;
       BufferedInputStream bufferedInputStream = null;
       try {
           fis = new FileInputStream(compressedFileName);
           GZIPInputStream is = new GZIPInputStream(new BufferedInputStream(
                  fis));
           in = new ArchiveStreamFactory().createArchiveInputStream("tar", is);
           bufferedInputStream = new BufferedInputStream(in);
           TarArchiveEntry entry = (TarArchiveEntry) in.getNextEntry();
           while (entry != null) 
           {
              String name = entry.getName();
              String[] names = name.split("/");
              String fileName = outputDirectory;
              for(int i = 0;i<names.length;i++){
                  String str = names[i];
                  fileName = fileName + File.separator + str;
              }
              if (name.endsWith("/")) {
                  mkFolder(fileName);
              } else {
                  File file = mkFile(fileName);
                  BufferedOutputStream bufferedOutputStream 
                  	= new BufferedOutputStream(new FileOutputStream(file));
	              byte[] byteBuffer = new byte[BYTE_LEN];
	              int byteCnt = 0;
	              while ((byteCnt = bufferedInputStream.read(byteBuffer, 0, BYTE_LEN)) != -1) 
	              {  
	            	  bufferedOutputStream.write(byteBuffer, 0, byteCnt);
	              }
                  bufferedOutputStream.flush();
                  bufferedOutputStream.close();
              }
              entry = (TarArchiveEntry) in.getNextEntry();
           }
           return true;
       } catch (FileNotFoundException e) {
           e.printStackTrace();
           return false;
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       } catch (ArchiveException e) {
           e.printStackTrace();
           return false;
       } finally {
           try {
              if (bufferedInputStream != null) {
                  bufferedInputStream.close();
              }
           } catch (IOException e) {
              e.printStackTrace();
           }
       }
    }
 
    private static void mkFolder(String fileName) {
       File f = new File(fileName);
       if (!f.exists()) {
           f.mkdir();
       }
    }
 
    private static File mkFile(String fileName) {
       File f = new File(fileName);
       try {
           f.createNewFile();
       } catch (IOException e) {
           e.printStackTrace();
       }
       return f;
    }
}
