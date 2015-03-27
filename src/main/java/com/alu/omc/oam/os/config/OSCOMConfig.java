package com.alu.omc.oam.os.config;

import java.io.Serializable;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.alu.omc.oam.COMConfig;
import com.alu.omc.oam.COMType;
import com.alu.omc.oam.Environment;
import com.alu.omc.oam.Inventory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OSCOMConfig extends COMConfig implements  Serializable
{

    /**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L;
    private boolean            config_drive           = false;
    private String             deployment_prifix;
    private boolean            enable_private_network = true;
    private BlockAvailZone     block_storage_avail_zone;
    private ComputeAvailZone   compute_avail_zone;
    private COMProvidernetwork com_provider_network;
    private Map vm_config;
    private COMType            comType;
    private String timezone;
    private boolean com_private_network;
    private String template_version;
    private String stack_name;
    private String deployment_prefix;
    


	public boolean getCom_private_network() {
		return com_private_network;
	}

	public void setCom_private_network(boolean com_private_network) {
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

        public String getZoneB()
        {
            return zoneB;
        }

        public void setZoneB(String zoneB)
        {
            this.zoneB = zoneB;
        }

        private String zoneB;

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

        public String getZoneB()
        {
            return zoneB;
        }

        public BlockAvailZone()
        {
            // TODO Auto-generated constructor stub
        }

        public void setZoneB(String zoneB)
        {
            this.zoneB = zoneB;
        }

        private String zoneA;
        private String zoneB;
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

    public class VMConfig implements Serializable
    {
      
        /**
          * @Fields serialVersionUID :
          */
        private static final long serialVersionUID = 1L;
         String                    provider_ip_address;
         String                    private_ip_address;
         String                    flavor;
        String                    image;
        int                       com_data_vol_size;
        
        @JsonProperty 
        public String getProvider_ip_address()
        {
            return provider_ip_address;
        }

        public void setProvider_ip_address(String provider_ip_address)
        {
            this.provider_ip_address = provider_ip_address;
        }
        @JsonProperty  
        public String getPrivate_ip_address()
        {
            return private_ip_address;
        }

        public void setPrivate_ip_address(String private_ip_address)
        {
            this.private_ip_address = private_ip_address;
        }
        @JsonProperty 
        public String getFlavor()
        {
            return flavor;
        }

        public void setFlavor(String flavor)
        {
            this.flavor = flavor;
        }
         @JsonProperty 
        public String getImage()
        {
            return image;
        }

        public void setImage(String image)
        {
            this.image = image;
        }
         @JsonProperty 
        public int getCom_data_vol_size()
        {
            return com_data_vol_size;
        }

        public void setCom_data_vol_size(int com_data_vol_size)
        {
            this.com_data_vol_size = com_data_vol_size;
        }
        @JsonCreator
        public VMConfig()
        {
        }

    }

    public boolean getConfig_drive()
    {
        return config_drive;
    }

    public void setConfig_drive(boolean config_drive)
    {
        this.config_drive = config_drive;
    }

    public String getDeployment_prifix()
    {
        return deployment_prifix;
    }

    public void setDeployment_prifix(String deployment_prifix)
    {
        this.deployment_prifix = deployment_prifix;
    }

    public boolean getEnable_private_network()
    {
        return enable_private_network;
    }

    public void setEnable_private_network(boolean enable_private_network)
    {
        this.enable_private_network = enable_private_network;
    }

    public BlockAvailZone getBlock_storage_avail_zone()
    {
        return block_storage_avail_zone;
    }

    public void setBlock_storage_avail_zone(
            BlockAvailZone block_storage_avail_zone)
    {
        this.block_storage_avail_zone = block_storage_avail_zone;
    }

    public ComputeAvailZone getCompute_avail_zone()
    {
        return compute_avail_zone;
    }

    public void setCompute_avail_zone(ComputeAvailZone compute_avail_zone)
    {
        this.compute_avail_zone = compute_avail_zone;
    }

    public COMProvidernetwork getCom_provider_network()
    {
        return com_provider_network;
    }

    public void setCom_provider_network(COMProvidernetwork com_provider_network)
    {
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

    public Map getVm_config()
    {
        return vm_config;
    }

    public void setVm_config(Map vm_config)
    {
        this.vm_config = vm_config;
    }

    @Override
    public Inventory getInventory()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getVars()
    {
    	Yaml yaml = new Yaml();
        return yaml.dump(this);
    }


}
