package com.alu.omc.oam.os.config;

import java.io.Serializable;
import java.util.Map;

import com.alu.omc.oam.COMType;
import com.alu.omc.oam.Environment;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OSCOMConfig implements InstallConfig, Serializable
{

    /**
     * @Fields serialVersionUID :
     */
    private static final long  serialVersionUID       = -3535916139459672300L;
    private boolean            config_drive           = false;
    private boolean            support_com_gr         = true;
    private String             deployment_prifix;
    private boolean            enable_private_network = true;
    private BlockAvailZone     block_storage_avail_zone;
    private ComputeAvailZone   compute_avail_zone;
    private COMProvidernetwork com_provider_network;
    private Map vm_config;
    private COMType            comType;

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

    public boolean getSupport_com_gr()
    {
        return support_com_gr;
    }

    public void setSupport_com_gr(boolean support_com_gr)
    {
        this.support_com_gr = support_com_gr;
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

    public Map<String, VMConfig> getVm_config()
    {
        return vm_config;
    }

    public void setVm_config(Map<String, VMConfig> vm_config)
    {
        this.vm_config = vm_config;
    }


}
