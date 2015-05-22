package com.alu.omc.oam.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtils 
{
	private static final int BYTE_LEN = 4096;
	private static final String ZIP_SUFFIX = ".zip";
	
	private static boolean isChildFolder(File src, File dest) 
	{
	    if (src != null && dest!=null &&  src.isDirectory() && dest.isDirectory()) {
	        File[] files = src.listFiles();
	        for (File file : files) {
	        if (dest.getName().equals(file.getName())||isChildFolder(file, dest))
	            return true;                
	        }
	    }
	    return false;
	    }
	
	public static boolean zip(String srcPath, String destZipPath)
	{
		if(srcPath == null || destZipPath == null)
			return false;
		
		File srcFile = new File(srcPath);
		File destFile = new File(destZipPath);
		
		if(!srcFile.exists())
			return false;
		if(srcFile.exists() && srcFile.isFile())
			return false;
		if(destFile.isDirectory())
			return false;
		if(destFile.exists())
			destFile.delete();
		if(isChildFolder(new File(srcPath), new File(destZipPath)))
			return false;
		
		ZipOutputStream zipOutStream = null;
		BufferedOutputStream bufferedOutStream = null;
		try 
		{
			zipOutStream = new ZipOutputStream(new FileOutputStream(destFile));
			bufferedOutStream = new BufferedOutputStream(zipOutStream);
			
			boolean isSuccess = zip(zipOutStream, srcFile, srcFile.getName(), bufferedOutStream);
			
			bufferedOutStream.close();
			zipOutStream.close();
			
			return isSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				zipOutStream.close();
				bufferedOutStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static boolean zip(ZipOutputStream zipOutStream, File srcFile, String srcFileName,  
            BufferedOutputStream bufferedOutStream)
    {
		
		if(srcFile.isDirectory())
		{
			File[] fileList = srcFile.listFiles();
			if(fileList.length == 0)
			{
				try {
					zipOutStream.putNextEntry(new ZipEntry(srcFileName+"/"));
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			for(int i = 0; i < fileList.length; ++i)
			{
				zip(zipOutStream, fileList[i], srcFileName + File.separator + fileList[i].getName(), bufferedOutStream);
			}
			return true;
		}
		else if(srcFile.isFile())
		{
			BufferedInputStream bufferedInStream = null;
			try {
				zipOutStream.putNextEntry(new ZipEntry(srcFileName));
				bufferedInStream = new BufferedInputStream(new FileInputStream(srcFile));  
				byte[] byteBuffer = new byte[BYTE_LEN];
				int byteCnt = 0;
				while ((byteCnt = bufferedInStream.read(byteBuffer, 0, BYTE_LEN)) != -1) 
				{  
					bufferedOutStream.write(byteBuffer, 0, byteCnt);
				}  
				bufferedOutStream.flush();
				zipOutStream.closeEntry();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					bufferedInStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else
			return false;
    }
    
    public static boolean unzip(String zipFilePath, String destRootPath) 
    {  
    	if(zipFilePath == null || destRootPath == null)
    		return false;
    	if(!zipFilePath.endsWith(ZIP_SUFFIX))
    		return false;
    	File srcZipFile = new File(zipFilePath);
    	File destRootFile = new File(destRootPath);
    	if(!srcZipFile.exists())
    		return false;
    	if(!srcZipFile.isFile())
    		return false;
    	if(!destRootFile.exists())
    		destRootFile.mkdirs();
    	if(!destRootFile.isDirectory())
    		return false;
    	
    	try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			Enumeration<? extends ZipEntry> emu = zipFile.entries();
			while(emu.hasMoreElements())
			{
			    ZipEntry entry = (ZipEntry)emu.nextElement();
			    if (entry.isDirectory())
			    {
			        new File(destRootPath + File.separator + entry.getName()).mkdirs();
			        continue;
			    }
			    BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
			    File file = new File(destRootPath + File.separator + entry.getName());
			    File parent = file.getParentFile();
			    if(parent != null && (!parent.exists())){
			        parent.mkdirs();
			    }
			    FileOutputStream fos = new FileOutputStream(file);
			    BufferedOutputStream bos = new BufferedOutputStream(fos,BYTE_LEN);           
			    
			    int count;
			    byte data[] = new byte[BYTE_LEN];
			    while ((count = bis.read(data, 0, BYTE_LEN)) != -1)
			    {
			        bos.write(data, 0, count);
			    }
			    bos.close();
			    bis.close();
			}
			zipFile.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
    } 

}
