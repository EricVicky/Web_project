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
		private String backup_data_file;
		private String backup_server_ip;
		private String backup_server_directory;
		
		public String getBackup_server_ip() {
			return backup_server_ip;
		}
		public void setBackup_server_ip(String backup_server_ip) {
			this.backup_server_ip = backup_server_ip;
		}
		public String getBackup_server_directory() {
			return backup_server_directory;
		}
		public void setBackup_server_directory(String backup_server_directory) {
			this.backup_server_directory = backup_server_directory;
		}
		public String getBackup_data_file() {
			return backup_data_file;
		}
		public void setBackup_data_file(String backup_data_file) {
			this.backup_data_file = backup_data_file;
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
