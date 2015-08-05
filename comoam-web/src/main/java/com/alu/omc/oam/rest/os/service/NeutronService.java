package com.alu.omc.oam.rest.os.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.network.IP;
import org.openstack4j.model.network.IPVersionType;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.openstack.networking.domain.NeutronSubnet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.rest.os.domain.neutron.YaoNetwork;
import com.alu.omc.oam.rest.os.domain.neutron.YaoPort;
import com.alu.omc.oam.rest.os.domain.neutron.YaoSubnet;
import com.alu.omc.oam.rest.os.service.access.YaoOsClientService;

@Service
public class NeutronService 
{
	@Resource
	private YaoOsClientService yaoOsClientService;
	private static Logger log = LoggerFactory.getLogger(NeutronService.class);
	public class IdNamePair
	{
		private String id;
		private String name;
		
		public IdNamePair(String id, String name)
		{
			this.id = id;
			this.name = name;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	public List<YaoNetwork> getNetworkList(OSClient client)
	{
		
		List<? extends Network> netWorkList = client.networking().network().list();
		Map<String, Subnet> subnetMap= buildSubnetMap(client);
		
		
		List<YaoNetwork> yaoNetworkList = new ArrayList<YaoNetwork>();
		
		for(Network network: netWorkList)
		{
			yaoNetworkList.add(assembleYaoNetwork(network, subnetMap));
			
		}
		
		return yaoNetworkList;
	}
	
	@SuppressWarnings("unchecked")
	public List<IdNamePair> getSubetNamesByNetworkId(OSClient client, String networkId)
	{
		List<NeutronSubnet> subnetList = (List<NeutronSubnet>)client.networking().subnet().list();
		List<IdNamePair> subnetNames = new ArrayList<IdNamePair>();
		for(NeutronSubnet subnet : subnetList)
		{
			if(subnet.getNetworkId().equalsIgnoreCase(networkId))
			{
				subnetNames.add(new IdNamePair(subnet.getId(), subnet.getName()));
			}
		}
		return subnetNames;
	}
	
		@SuppressWarnings("unchecked")
	public List<IdNamePair> getSubetNamesByNetworkId(OSClient client, String networkId, boolean ipv4)
	{
		List<NeutronSubnet> subnetList = (List<NeutronSubnet>)client.networking().subnet().list();
		List<IdNamePair> subnetNames = new ArrayList<IdNamePair>();
		for(NeutronSubnet subnet : subnetList)
		{
			if((subnet.getIpVersion() == IPVersionType.V4) ^ ipv4){
			    continue;
			}
		    if(subnet.getNetworkId().equalsIgnoreCase(networkId))
			{
				subnetNames.add(new IdNamePair(subnet.getId(), subnet.getName()));
			}
		}
		return subnetNames;
	}
	
	@SuppressWarnings("unchecked")
	public List<IdNamePair> getSubetNames(OSClient client)
	{
		List<NeutronSubnet> subnetList = (List<NeutronSubnet>)client.networking().subnet().list();
		List<IdNamePair> subnetNames = new ArrayList<IdNamePair>();
		for(NeutronSubnet subnet : subnetList)
		{		
			subnetNames.add(new IdNamePair(subnet.getId(), subnet.getName()));	
		}
		return subnetNames;
	}
	@SuppressWarnings("unchecked")
	public NeutronSubnet getSubetById( String subnetId)
	{
		OSClient os = yaoOsClientService.getOsClient();
		List<NeutronSubnet> subnetList = (List<NeutronSubnet>)os.networking().subnet().list();
		for(NeutronSubnet subnet : subnetList)
		{		
			if(subnet.getId().equalsIgnoreCase(subnetId))
			{
				return subnet;
			}
		}
		return null;
	}
	
	public List<IdNamePair> getNetworkNames(OSClient client)
	{
		List<YaoNetwork> yaoNetworkList = getNetworkList(client);
		List<IdNamePair> networkNames = new ArrayList<IdNamePair>();
		for(YaoNetwork network : yaoNetworkList)
		{
			networkNames.add(new IdNamePair(network.getId(), network.getName()));
		}
		return networkNames;
	}
	
	private Map<String, Subnet> buildSubnetMap(OSClient client) {
	    List<? extends Subnet> subnetList = client.networking().subnet().list();
	    log.info("subnetList.size=" + String.valueOf(subnetList.size()));
	    Map<String, Subnet> subnetMap = new HashMap<String, Subnet>();
	    for (Subnet subnet : subnetList) {
	        subnetMap.put(subnet.getId(), subnet);
	        log.info("add subnet:" + subnet.getId());
	    }
	    return subnetMap;
	}
	
	
	
	private YaoNetwork assembleYaoNetwork(Network network, Map<String, Subnet> subnetMap)
	{
		YaoNetwork yaoNetwork = new YaoNetwork();
		yaoNetwork.setId(network.getId());
		yaoNetwork.setName(network.getName());
		yaoNetwork.setShared(network.isShared());
		yaoNetwork.setStatus(network.getStatus().toString());
		yaoNetwork.setAdminStatusUp(network.isAdminStateUp());
		List<YaoSubnet> yaoSubnetList = new ArrayList<YaoSubnet>();
		
		
		
		for(String subnetID: network.getSubnets())
		{
			
			log.info("try get subnet:" + subnetID);
		    if(subnetMap.get(subnetID)!=null){			    
			    yaoSubnetList.add(assembleYaoSubnet(subnetMap.get(subnetID)));
			}else{
			    log.warn("unable to get subnet" + subnetID);
			}
		}
						
		yaoNetwork.setNeutronSubnets(yaoSubnetList);
				
		return yaoNetwork;
	}
	
	private YaoSubnet assembleYaoSubnet(Subnet subnet)
	{
		YaoSubnet yaoSubnet = new YaoSubnet();
		yaoSubnet.setId(subnet.getId());
		yaoSubnet.setName(subnet.getName());
		yaoSubnet.setCidr(subnet.getCidr());
		yaoSubnet.setEnableDhcp(subnet.isDHCPEnabled());
		yaoSubnet.setGatewayIp(subnet.getGateway());
		yaoSubnet.setIpVersion(subnet.getIpVersion().toString());
		yaoSubnet.setNetworkId(subnet.getNetworkId());
		return yaoSubnet;
	}


	
	public List<YaoPort> getPortList(OSClient client, String networkid)
	{
		List<? extends Port> ports = client.networking().port().list();
		List<YaoPort> yaoPortList = new ArrayList<YaoPort>();
		
		for(Port port : ports)
		{
			if(port.getNetworkId().equals(networkid))
			
				yaoPortList.add(assembleYaoPort(port));	
			
		}
		
		return yaoPortList;
	}
	
	
	private YaoPort assembleYaoPort(Port port)
    {
		List<IP> ipList = new ArrayList<IP>();
		Set<? extends IP> ips = port.getFixedIps();
		
		for (Iterator<? extends IP> iterator = ips.iterator(); iterator.hasNext();) 
		{
		    IP ip = (IP) iterator.next();
			ipList.add(ip);
	    }
		
	   YaoPort yaoPort = new YaoPort();
	   yaoPort.setName(port.getName());
	   yaoPort.setFixedIps(ipList);
	   yaoPort.setDevOwner(port.getDeviceOwner());
	   yaoPort.setNetworkId(port.getNetworkId());
	   yaoPort.setState(port.getState());
	   yaoPort.setDeviceId(port.getDeviceId());
	   yaoPort.setMacAdress(port.getMacAddress());
	   yaoPort.setSecurityGroup(port.getSecurityGroups());
	   yaoPort.setPortId(port.getId());
	   yaoPort.setTennantId(port.getTenantId());
	   yaoPort.setAdminStatusUp(port.isAdminStateUp());
	   return yaoPort;
	    
    }
	
}   

