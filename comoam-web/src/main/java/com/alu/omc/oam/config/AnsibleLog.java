package com.alu.omc.oam.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alu.omc.oam.ansible.Ansibleworkspace;

public class AnsibleLog extends ReadAnsibleLog implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String LOG_FILE = "log.txt";
	private final String ALL_FILE = "group_vars/all";
	private final String HOST_FILE = "inventory/hosts";
	private String log;
	private String all;
	private String host;
	private String dir;
	
	public AnsibleLog(String dir){
		this.dir = dir;
		try {
			this.log = ReadLog();
			this.all = ReadAll();
			this.host = ReadHosts();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String ReadLog() throws Exception{
		return ExAnsibleLog(this.dir+LOG_FILE);
	}
	
	public String ReadAll() throws Exception{
		return ExAnsibleLog(this.dir+ALL_FILE);
	}
	
	public String ReadHosts() throws Exception{
		return ExAnsibleLog(this.dir+HOST_FILE);
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
