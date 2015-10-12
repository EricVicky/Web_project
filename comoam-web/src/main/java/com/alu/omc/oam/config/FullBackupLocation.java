package com.alu.omc.oam.config;

import java.io.Serializable;

public class FullBackupLocation implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FullBackupLocation() {
		
	}
	private String full_backup_dir;
	private String remote_server_dir  = "";  
	private String remote_server_ip = "";


	public String getFull_backup_dir() {
		return full_backup_dir;
	}
	public void setFull_backup_dir(String full_backup_dir) {
		this.full_backup_dir = full_backup_dir;
	}
	public String getRemote_server_dir() {
		return remote_server_dir;
	}
	public void setRemote_server_dir(String remote_server_dir) {
		this.remote_server_dir = remote_server_dir;
	}
	public String getRemote_server_ip() {
		return remote_server_ip;
	}
	public void setRemote_server_ip(String remote_server_ip) {
		this.remote_server_ip = remote_server_ip;
	}
}
