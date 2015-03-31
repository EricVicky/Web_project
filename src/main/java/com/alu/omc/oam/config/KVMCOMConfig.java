package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Host;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.util.YamlFormatterUtil;

public class KVMCOMConfig extends COMConfig implements Serializable{
	  
	
	/**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L; 
    private COMType            comType;
	private Map vm_config;
	private boolean support_gr;
	private String timezone;
	private String host_ip;
	private String deployment_prefix;
	private String oam_cm_image;
	private String db_image;
	
	
	@Override
    public Environment getEnvironment()
    {
        // TODO Auto-generated method stub
        return Environment.KVM;
    }

    @Override
    public COMType getCOMType()
    {
        // TODO Auto-generated method stub
        return comType;
    }
	public String getDeployment_prefix() {
		return deployment_prefix;
	}

	public void setDeployment_prefix(String deployment_prefix) {
		this.deployment_prefix = deployment_prefix;
	}

	public String getHost_ip() {
		return host_ip;
	}

	public void setHost_ip(String host_ip) {
		this.host_ip = host_ip;
	}
	
    public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public boolean getSupport_gr() {
		return support_gr;
	}

	public void setSupport_gr(boolean support_gr) {
		this.support_gr = support_gr;
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
    
    public Map getVm_config()
    {
        return vm_config;
    }

    public void setVm_config(Map vm_config)
    {
        this.vm_config = vm_config;
    }

	@Override
	public Inventory getInventory() {
	    Inventory inv = new Inventory();
	    Group hostg = new Group("host");
	    hostg.add(new Host(this.host_ip));
	    inv.addGroup(hostg);
	    @SuppressWarnings("unchecked")
        Iterator<String> it = vm_config.keySet().iterator(); 
	    Group allVM = new Group("allvm:children");
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
	public String getVars() {
        Iterator<String> it = vm_config.keySet().iterator(); 
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)vm_config.get(name);
	        vmcfg.put("hostname", this.getDeployment_prefix().concat("-").concat(name).concat("-1"));
	        String istoption = InstallOptions.get(this.getComType(), name);
	        vmcfg.put("install_options", istoption );
	        vmcfg.put("imgname", this.getVMImageName(name));
	    }
		Yaml yaml = new Yaml();
        return YamlFormatterUtil.format(yaml.dump(this));	
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
    
    private String getVMImageName(String vmname){
       if(vmname.equals(VMType.cm.toString()) || vmname.equals(VMType.oam.toString())){
           return this.oam_cm_image;
       }else{
           return this.db_image;
       }
    }
    
}