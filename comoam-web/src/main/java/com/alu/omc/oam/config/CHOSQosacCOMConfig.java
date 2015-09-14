package com.alu.omc.oam.config;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.ansible.Group;
import com.alu.omc.oam.ansible.Inventory;
import com.alu.omc.oam.config.HpsimOSCOMConfig.BlockAvailZone;
import com.alu.omc.oam.config.HpsimOSCOMConfig.COMProvidernetwork;
import com.alu.omc.oam.config.HpsimOSCOMConfig.ComputeAvailZone;
import com.alu.omc.oam.kvm.model.Host;
import com.alu.omc.oam.util.YamlFormatterUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CHOSQosacCOMConfig extends COMConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2478186978192603088L;
	private Map vm_config;
	private Map old_vm_config;
    private boolean            config_drive           = true;
    private boolean juno_base = false;
	private Map<String, String> app_install_options;
	private COMType            comType;
	private String             deployment_prefix;
	private String ovm_image;
	private String timezone;
	private COMProvidernetwork com_provider_network;
	private String template_version;
	private String stack_name;
	private BlockAvailZone     block_storage_avail_zone;
    private ComputeAvailZone   compute_avail_zone;
    private String key_name;
    private static Logger log = LoggerFactory.getLogger(CHOSQosacCOMConfig.class);

	@Override
	public COMType getCOMType() {
		return COMType.QOSAC;
	}
	
	public CHOSQosacCOMConfig(){
		
	}
	
	@Override
	public Environment getEnvironment() {
		return Environment.OPENSTACK;
	}

	@Override
	public String getStackName() {
		return this.getStack_name(); 
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
	        //vmcfg.put("image", this.getVMImageName(name));
	    }
	    Iterator<String> old_it = old_vm_config.keySet().iterator(); 
    	Yaml yaml = new Yaml();
    	return YamlFormatterUtil.format(yaml.dump(this));
    }
    
    public Map<String, String> getApp_install_options() {
		return app_install_options;
	}

	public void setApp_install_options(Map<String, String> app_install_options) {
		this.app_install_options = app_install_options;
	}

	public Map getVm_config() {
		return vm_config;
	}

	public void setVm_config(Map vm_config) {
		this.vm_config = vm_config;
	}
	
	public Map<String, Object> getOld_vm_config() {
		return old_vm_config;
	}

	public void setOld_vm_config(Map<String, Object> old_vm_config) {
		this.old_vm_config = old_vm_config;
	}

	public COMType getComType() {
		return comType;
	}

	public void setComType(COMType comType) {
		this.comType = comType;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public COMProvidernetwork getCom_provider_network() {
		return com_provider_network;
	}

	public void setCom_provider_network(COMProvidernetwork com_provider_network) {
		this.com_provider_network = com_provider_network;
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

	public BlockAvailZone getBlock_storage_avail_zone() {
		return block_storage_avail_zone;
	}

	public void setBlock_storage_avail_zone(BlockAvailZone block_storage_avail_zone) {
		this.block_storage_avail_zone = block_storage_avail_zone;
	}

	public ComputeAvailZone getCompute_avail_zone() {
		return compute_avail_zone;
	}

	public void setCompute_avail_zone(ComputeAvailZone compute_avail_zone) {
		this.compute_avail_zone = compute_avail_zone;
	}

	public String getKey_name() {
		return key_name;
	}

	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}

	public String getDeployment_prefix() {
		return deployment_prefix;
	}

	public void setDeployment_prefix(String deployment_prefix) {
		this.deployment_prefix = deployment_prefix;
	}

	public String getOvm_image() {
		return ovm_image;
	}

	public void setOvm_image(String ovm_image) {
		this.ovm_image = ovm_image;
	}
    
	private String getVMImageName(String vmname){
        return this.ovm_image;
    }
	
	public class ComputeAvailZone implements Serializable
    {
        /**
         * @Fields serialVersionUID :
         */
        private static final long serialVersionUID = 1L;
        private String            zoneA;

        public String getZoneA()
        {
            return zoneA;
        }

        public void setZoneA(String zoneA)
        {
            this.zoneA = zoneA;
        }



        public ComputeAvailZone()
        {
        }
    }

    public class BlockAvailZone implements Serializable
    {
        /**
         * @Fields serialVersionUID :
         */
        private static final long serialVersionUID = 1L;

        public String getZoneA()
        {
            return zoneA;
        }

        public void setZoneA(String zoneA)
        {
            this.zoneA = zoneA;
        }


        public BlockAvailZone()
        {
            // TODO Auto-generated constructor stub
        }


        private String zoneA;
    }

	
    
    public class COMProvidernetwork implements Serializable
    {
        /**
         * @Fields serialVersionUID :
         */
        private static final long serialVersionUID = -8224828541397406250L;
        String                    network;
        String                    subnet;
        String                    netmask;
        String                    gateway;
        String                    dns1;
        String                    v6_subnet = "";
        String                    v6_gateway = "";
        String                    v6_prefix = "";

        public String getV6_subnet()
        {
            return v6_subnet;
        }

        public void setV6_subnet(String v6_subnet)
        {
            this.v6_subnet = v6_subnet;
        }

        public String getV6_gateway()
        {
            return v6_gateway;
        }

        public void setV6_gateway(String v6_gateway)
        {
            this.v6_gateway = v6_gateway;
        }

        public String getV6_prefix()
        {
            return v6_prefix;
        }

        public void setV6_prefix(String v6_prefix)
        {
            this.v6_prefix = v6_prefix;
        }

        public String getDns1()
        {
            return dns1;
        }

        public void setDns1(String dns1)
        {
            this.dns1 = dns1;
        }

        public String getGateway()
        {
            return gateway;
        }

        public void setGateway(String gateway)
        {
            this.gateway = gateway;
        }

        public String getNetwork()
        {
            return network;
        }

        public void setNetwork(String network)
        {
            this.network = network;
        }

        public String getSubnet()
        {
            return subnet;
        }

        public void setSubnet(String subnet)
        {
            this.subnet = subnet;
        }

        public String getNetmask()
        {
            return netmask;
        }

        public void setNetmask(String netmask)
        {
            this.netmask = netmask;
        }
    }



	public boolean isConfig_drive() {
		return config_drive;
	}

	public void setConfig_drive(boolean config_drive) {
		this.config_drive = config_drive;
	}
    
	public boolean getJuno_base()
    {
        return this.isJuno();
    }

    public void setJuno_base(boolean juno_base)
    {
        this.juno_base = juno_base;
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
