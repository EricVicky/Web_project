package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class BACKUPConfig<T extends COMConfig> extends COMConfig implements Serializable{
	/**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L; 
	private T config;
	private BackupLocation backupLocation;
	
	public BackupLocation getBackupLocation() {
		return backupLocation;
	}
	public void setBackupLocation(BackupLocation backupLocation) {
		this.backupLocation = backupLocation;
	}

	public class BackupLocation implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public BackupLocation() {
			
		}
		private String local_backup_dir;
		private String local_backup_file;
		private String remote_server_dir="";  //
		private String remote_server_ip="";

		public String getLocal_backup_dir() {
			return local_backup_dir;
		}
		public void setLocal_backup_dir(String local_backup_dir) {
			this.local_backup_dir = local_backup_dir;
		}
		public String getLocal_backup_file() {
			return local_backup_file;
		}
		public void setLocal_backup_file(String local_backup_file) {
			this.local_backup_file = local_backup_file;
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
	
	public T getConfig() {
		return config;
	}
	public void setConfig(T config) {
		this.config = config;
	}

	@Override
	@JsonIgnore 
	public Inventory getInventory() {
		return config.getInventory();
	}

	@Override
	@JsonIgnore 
	public String getVars() {
		Yaml yaml = new Yaml();
		return config.getVars()+YamlFormatterUtil.formatbackup(yaml.dump(this.backupLocation));
	}

	@Override
	public Environment getEnvironment() {
		return config.getEnvironment();
	}

	@Override
	public COMType getCOMType() {
		return config.getCOMType();
	}

	@Override
	public String getStackName() {
		return config.getStackName();
	}

}
