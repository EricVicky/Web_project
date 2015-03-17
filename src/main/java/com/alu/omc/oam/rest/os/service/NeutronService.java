package com.alu.omc.oam.rest.os.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openstack4j.api.OSClient;
import org.openstack4j.model.network.IP;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.openstack.networking.domain.NeutronSubnet;
import org.springframework.stereotype.Service;

import com.alu.omc.oam.rest.os.domain.neutron.YaoNetwork;
import com.alu.omc.oam.rest.os.domain.neutron.YaoPort;
import com.alu.omc.oam.rest.os.domain.neutron.YaoSubnet;

@Service
public class NeutronService 
{
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
	    Map<String, Subnet> subnetMap = new HashMap<String, Subnet>();
	    for (Subnet subnet : subnetList) {
	        subnetMap.put(subnet.getId(), subnet);
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
			
			yaoSubnetList.add(assembleYaoSubnet(subnetMap.get(subnetID)));
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

