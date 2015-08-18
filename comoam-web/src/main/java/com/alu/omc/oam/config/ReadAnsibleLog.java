package com.alu.omc.oam.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class ReadAnsibleLog {
	
	public String AnsibleLog(String dir) throws Exception{
		Reader reader = new FileReader(dir);
		BufferedReader br = new BufferedReader(reader);
		StringBuffer sb = new StringBuffer();
		try{
			String data = null;
			while((data = br.readLine())!=null){
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
