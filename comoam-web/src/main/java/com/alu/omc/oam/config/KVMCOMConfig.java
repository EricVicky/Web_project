package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.Json2Object;
import com.alu.omc.oam.util.JsonYamlConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class KVMCOMConfig extends COMConfig implements NetworkConfig, Serializable, EncryPassword{
	  
	
	/**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L; 
    private COMType            comType;
	public Map<String, VMConfig> vm_config;

	private String timezone;
	private String active_host_ip;

	private String deployment_prefix;
	private String oam_cm_image;
	public Map<String, String> getApp_install_options()
    {
        return app_install_options;
    }

    public void setApp_install_options(Map<String, String> app_install_options)
    {
        this.app_install_options = app_install_options;
    }

    private String db_image;
	private String vm_img_dir;
	private Map<String, String> app_install_options;
	
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


    public COMType getComType()
    {
        return comType;
    }

    public void setComType(COMType comType)
    {
        this.comType = comType;
    }
    
    public Map<String, VMConfig> getVm_config()
    {
        return vm_config;
    }

    public void setVm_config(Map<String, VMConfig> vm_config)
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
            VMConfig vmcfg = vm_config.get(name);
	        Group g = new Group(name);
	        allVM.add(g);
	        g.add(new Host(vmcfg.getNic().get(0).getIp_v4().getIpaddress()));
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
            VMConfig vmcfg = vm_config.get(name);
	       // VNFHostName.add(vmcfg, this.getComType(), name, this.deployment_prefix);
	        vmcfg.setImgname(this.getVMImageName(name));
	    }
	   String json = Json2Object.object2Json(this);
       return JsonYamlConverter.convertJson2Yaml(json);
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

	public String getVMImageName(String vmname){
       if(vmname.equals(VMType.cm.toString()) || vmname.equals(VMType.oam.toString())){
           return this.oam_cm_image;
       }else{
           return this.db_image;
       }
    }

    @Override
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
	        VMConfig  config = vm_config.get(vm);
	        vmnics.put(vm, config.getNic());
	    }
        return vmnics;
    }

    private String root_password;
    private String axadmin_password;
	public String getRoot_password() {
		return root_password;
	}

	public void setRoot_password(String root_password) {
		this.root_password = root_password;
	}

	public String getAxadmin_password() {
		return axadmin_password;
	}

	public void setAxadmin_password(String axadmin_password) {
		this.axadmin_password = axadmin_password;
	}
    
    

}
