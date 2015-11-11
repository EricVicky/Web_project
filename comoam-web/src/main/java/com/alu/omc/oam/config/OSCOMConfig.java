package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.Json2Object;
import com.alu.omc.oam.util.JsonYamlConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class OSCOMConfig extends COMConfig implements NetworkConfig, Serializable, EncryPassword
{

    /**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L;
    private boolean            config_drive           = true;
    private String             deployment_prefix;
    private boolean            enable_private_network = true;
    private BlockAvailZoneConfig     block_storage_avail_zone;
    private ComputeAvailZoneConfig   compute_avail_zone;
    private COMProvidernetworkConfig com_provider_network;
    private Map vm_config;
    private Map<String, String> app_install_options;
    private COMType            comType;
    private String timezone;
    private COMPrivatenetworkConfig com_private_network = new COMPrivatenetworkConfig("192.168.10.0/22", "192.168.10.1", "255.255.252.0", "22");
    private String template_version;
	private String stack_name;
    private String oam_cm_image;
	private String db_image;
	private String key_name;
	private String environment;
	private String comtype;
	private String stackName;
	private boolean juno_base = false;
	private static Logger log = LoggerFactory.getLogger(OSCOMConfig.class);	
	

    public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Map<String, String> getApp_install_options() {
		return app_install_options;
	}

	public void setApp_install_options(Map<String, String> app_install_options) {
		this.app_install_options = app_install_options;
	}

	public boolean getJuno_base()
    {
        return this.isJuno();
    }

    public void setJuno_base(boolean juno_base)
    {
        this.juno_base = juno_base;
    }

    public String getKey_name()
    {
        return key_name;
    }

    public void setKey_name(String key_name)
    {
        this.key_name = key_name;
    }

    public String getOam_cm_image() {
		return oam_cm_image;
	}

	public void setOam_cm_image(String oam_cm_image) {
		this.oam_cm_image = oam_cm_image;
	}

	public String getDb_image() {
		return db_image;
	}

	public void setDb_image(String db_image) {
		this.db_image = db_image;
	}
	
	public String getVMImageName(String vmname){
	       if(vmname.equals(VMType.cm.toString()) || vmname.equals(VMType.oam.toString())){
	           return this.oam_cm_image;
	       }else{
	           return this.db_image;
	       }
	    }
	

	public COMPrivatenetworkConfig getCom_private_network() {
		return com_private_network;
	}

	public void setCom_private_network(COMPrivatenetworkConfig com_private_network) {
		this.com_private_network = com_private_network;
	}

	public String getTemplate_version() {
		return template_version;
	}

	public void setTemplate_version(String template_version) {
		this.template_version = template_version;
	}

	public String getStack_name() {
		return stack_name;
	}

	public void setStack_name(String stack_name) {
		this.stack_name = stack_name;
	}

	public String getDeployment_prefix() {
		return deployment_prefix;
	}

	public void setDeployment_prefix(String deployment_prefix) {
		this.deployment_prefix = deployment_prefix;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	@Override
    public Environment getEnvironment()
    {
        // TODO Auto-generated method stub
        return Environment.OPENSTACK;
    }

    @Override
    public COMType getCOMType()
    {
        // TODO Auto-generated method stub
        return comType;
    }

   
    public boolean getConfig_drive()
    {
        return config_drive;
    }

    public void setConfig_drive(boolean config_drive)
    {
        this.config_drive = config_drive;
    }


    public boolean getEnable_private_network()
    {
        return enable_private_network;
    }

    public void setEnable_private_network(boolean enable_private_network)
    {
        this.enable_private_network = enable_private_network;
    }



    public BlockAvailZoneConfig getBlock_storage_avail_zone() {
		return block_storage_avail_zone;
	}

	public void setBlock_storage_avail_zone(
			BlockAvailZoneConfig block_storage_avail_zone) {
		this.block_storage_avail_zone = block_storage_avail_zone;
	}


    public ComputeAvailZoneConfig getCompute_avail_zone() {
		return compute_avail_zone;
	}

	public void setCompute_avail_zone(ComputeAvailZoneConfig compute_avail_zone) {
		this.compute_avail_zone = compute_avail_zone;
	}

	public COMProvidernetworkConfig getCom_provider_network() {
		return com_provider_network;
	}

	public void setCom_provider_network(
			COMProvidernetworkConfig com_provider_network) {
		this.com_provider_network = com_provider_network;
	}

	public COMType getComType()
    {
        return comType;
    }

    public void setComType(COMType comType)
    {
        this.comType = comType;
    }

    public String toString()
    {
        return this.comType + "," + this.getEnvironment();
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
    public Inventory getInventory()
    {
    	Inventory inv = new Inventory();
	    Group hostg = new Group("host");
	    hostg.add(new Host("localhost"));
	    inv.addGroup(hostg);
	    @SuppressWarnings("unchecked")
        Iterator<String> it = vm_config.keySet().iterator(); 
	    Group allVM = new Group(Inventory.ALL_VMS);
	    inv.addGroup(allVM);
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)vm_config.get(name);
	        String ipAddress = vmcfg.get("provider_ip_address");
	        Group g = new Group(name);
	        allVM.add(g);
	        g.add(new Host(ipAddress));
	        inv.addGroup(g);
	    }
		return inv;
    }

    @Override
    @JsonIgnore
    public String getVars()
    {
    	Iterator<String> it = vm_config.keySet().iterator(); 
	    while(it.hasNext()){
	        String name = it.next();
	        @SuppressWarnings("unchecked")
            Map<String, String> vmcfg = (Map<String, String>)vm_config.get(name);
	        //VNFHostName.add(vmcfg, this.getComType(), name, this.deployment_prefix);
	        vmcfg.put("image", this.getVMImageName(name));
	        if(this.getCom_private_network().ippool.size() >0){
	        	vmcfg.put("private_ip_address", this.getCom_private_network().popIp());
	        }else{
	        	log.error("the private network pool is empty");
	        }
	    }
	   String json = Json2Object.object2Json(this);
       return JsonYamlConverter.convertJson2Yaml(json);
    }
    

    @Override
    public String getStackName()
    {
        return this.getStack_name();
    }

    @Override
    public Map<String, List<NIC>> allInterface()
    {
        Map<String, List<NIC>> vmnics = new HashMap<String, List<NIC>>();
        Iterator<String> it = vm_config.keySet().iterator(); 
	    while(it.hasNext()){
	        String vm = it.next();
	        Map<String, Object> config = (Map<String, Object>)vm_config.get(vm);
	        List<NIC> nics = new ArrayList<NIC>();
	        NIC eth0 = new NIC();
	        IFCfg eth0cfg = new IFCfg();
	        eth0cfg.setIpaddress((String)config.get("provider_ip_address"));
	        eth0.setIp_v4(eth0cfg);
	        IFCfg v6cfg = new IFCfg();
		    v6cfg.setIpaddress((String)config.get("v6_ip_addr"));
	        eth0.setIp_v6(v6cfg);        nics.add(eth0);
	        NIC eth1 = new NIC();
	        IFCfg eth1cfg = new IFCfg();
	        eth1cfg.setIpaddress((String)config.get("private_ip_address"));
	        v6cfg.setIpaddress((String)config.get("v6_ip_addr"));
	        eth0.setIp_v6(v6cfg);
	        eth1.setIp_v4(eth1cfg);
	        nics.add(eth1);
	        vmnics.put(vm, nics);
	    }
        return vmnics;
    }
    
   private boolean  isJuno(){
       return this.getTemplate_version().indexOf("2014-10-16") != -1;
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
