package com.alu.omc.oam.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Pattern;

public class ReadAnsibleLog {
	
	private Pattern rootPass = Pattern.compile("^.*root_password.*$");
	private Pattern axadminPass = Pattern.compile("^.*axadmin_password.*$");
	public String ExAnsibleLog(String dir) throws Exception{
		Reader reader = new FileReader(dir);
		BufferedReader br = new BufferedReader(reader);
		StringBuffer sb = new StringBuffer();
		try{
			String data = null;
			while((data = br.readLine())!=null){
				if(rootPass.matcher(data).find()||axadminPass.matcher(data).find()){
					String tmp = data.substring(data.indexOf(":")+1,data.length());
					data = data.replace(tmp," ******");
				}
				sb.append(data+"\r\n");
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				reader.close();
				br.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
