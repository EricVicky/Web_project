package com.alu.omc.oam.os.config;

import java.io.Serializable;

import com.alu.omc.oam.COMType;
import com.alu.omc.oam.Environment;

public class OSCOMConfig implements InstallConfig, Serializable
{

    /**
      * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
      */
    private static final long serialVersionUID = -3535916139459672300L;
    private boolean config_drive = false;
    private boolean support_com_gr = true;
    private String deployment_prifix;
    private boolean enable_private_network = true;
    private BlockAvailZone block_storage_avail_zone;
    private ComputeAvailZone compute_avail_zone;
    private COMProvidernetwork com_provider_network;
    
    private COMType comType;
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
    
    class ComputeAvailZone implements Serializable{
        /**
          * @Fields serialVersionUID :
          */
        private static final long serialVersionUID = 1L;
        private String zoneA;
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
    }
    
    class BlockAvailZone implements Serializable{
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
        public void setZoneB(String zoneB)
        {
            this.zoneB = zoneB;
        }
        private String zoneA;
        private String zoneB;
    }
    
    class COMProvidernetwork implements Serializable{
        /**
          * @Fields serialVersionUID : 
          */
        private static final long serialVersionUID = -8224828541397406250L;
        String network;
        String subnet;
        String netmask;
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

    public boolean isConfig_drive()
    {
        return config_drive;
    }

    public void setConfig_drive(boolean config_drive)
    {
        this.config_drive = config_drive;
    }

    public boolean isSupport_com_gr()
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

    public boolean isEnable_private_network()
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

    public void setBlock_storage_avail_zone(BlockAvailZone block_storage_avail_zone)
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

}
