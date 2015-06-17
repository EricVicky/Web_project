package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class KVMCOMConfig extends COMConfig implements NetworkConfig, Serializable{
	  
	
	/**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L; 
    private COMType            comType;
	private Map<String, Object> vm_config;
	private Map<String, String> app_install_options;

	private String timezone;
	private String active_host_ip;

	private String deployment_prefix;
	private String oam_cm_image;
	private String db_image;
	private String vm_img_dir;
	
	public Map<String, String> getApp_install_options() {
		return app_install_options;
	}
	
	public void setApp_install_options(Map<String, String> app_install_options) {
		this.app_install_options = app_install_options;
	}
	public String getActive_host_ip() {
		return active_host_ip;
	}
	
	public void setActive_host_ip(String active_host_ip) {
		this.active_host_ip = active_host_ip;
	}
	
	public Host getHost(){
		return new Host(this.active_host_ip);
	}
	
	@Override
    public Environment getEnvironment()
    {
        return Environment.KVM;
    }

    @Override
    public COMType getCOMType()
    {
        return comType;
    }
    
	public String getDeployment_prefix() {
		return deployment_prefix;
	}

	public void setDeployment_prefix(String deployment_prefix) {
		this.deployment_prefix = deployment_prefix;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public COMType getComType()
    {
        return comType;
    }

    public void setComType(COMType comType)
    {
        this.comType = comType;
    }
    
    public Map<String, Object> getVm_config()
    {
        return vm_config;
    }

    public void setVm_config(Map<String, Object> vm_config)
    {
        this.vm_config = vm_config;
    }
    
	@Override
	@JsonIgnore 
	public Inventory getInventory() {
	    Inventory inv = new Inventory();
	    Group hostg = new Group("host");
	    hostg.add(new Host(this.active_host_ip));
	    inv.addGroup(hostg);
	    @SuppressWarnings("unchecked")
        Iterator<String> it = vm_config.keySet().iterator(); 
	    Group allVM = new Group(Inventory.ALL_VMS);
	    inv.addGroup(allVM);
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)vm_config.get(name);
	        String ipAddress = vmcfg.get("ip_address");
	        Group g = new Group(name);
	        allVM.add(g);
	        g.add(new Host(ipAddress));
	        inv.addGroup(g);
	    }
		return inv;
	}

	@Override
	@JsonIgnore 
	public String getVars() {
        Iterator<String> it = vm_config.keySet().iterator(); 
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)vm_config.get(name);
	        VNFHostName.add(vmcfg, this.getComType(), name, this.deployment_prefix);
	        InstallOptions.add(vmcfg, comType, name);
	        vmcfg.put("imgname", this.getVMImageName(name));
	    }
		Yaml yaml = new Yaml();
        return YamlFormatterUtil.format(yaml.dump(this));	
	}
	
	protected String getHostName(){
	   return null; 
	}
	

    public String getOam_cm_image()
    {
        return oam_cm_image;
    }

    public void setOam_cm_image(String oam_cm_image)
    {
        this.oam_cm_image = oam_cm_image;
    }

    public String getDb_image()
    {
        return db_image;
    }

    public void setDb_image(String db_image)
    {
        this.db_image = db_image;
    }
    
    public String getVm_img_dir() {
		return vm_img_dir;
	}

	public void setVm_img_dir(String vm_img_dir) {
		this.vm_img_dir = vm_img_dir;
	}

	private String getVMImageName(String vmname){
       if(vmname.equals(VMType.cm.toString()) || vmname.equals(VMType.oam.toString())){
           return this.oam_cm_image;
       }else{
           return this.db_image;
       }
    }

    @Override
    @JsonIgnore 
    public String getStackName()
    {
       return this.deployment_prefix; 
    }

    @Override
    public Map<String, List<NIC>> allInterface()
    {
        Map<String, List<NIC>> vmnics = new HashMap<String, List<NIC>>();
        Iterator<String> it = vm_config.keySet().iterator(); 
	    while(it.hasNext()){
	        String vm = it.next();
	        NIC nic = new NIC();
	        Map<String, Object> config = (Map<String, Object>)vm_config.get(vm);
	        IFCfg cfg = new IFCfg();
	        cfg.setIpaddress((String)config.get("ip_address"));
	        nic.setIpv4(cfg);
	        IFCfg v6cfg = new IFCfg();
	        v6cfg.setIpaddress((String)config.get("v6_ip_addr"));
	        nic.addIpv6(v6cfg);
	        List<NIC> nics = new ArrayList<NIC>();
	        nics.add(nic);
	        vmnics.put(vm, nics);
	    }
        return vmnics;
    }


    
    

}
