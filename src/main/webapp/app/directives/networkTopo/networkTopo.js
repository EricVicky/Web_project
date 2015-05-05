'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('comoamApp').directive('networkTopo',function($log,KVMService){
	
	return{
		restrict:'EA',
		controller: function($log, $scope,$position, KVMService) {
			  KVMService.getComInstance().then( function(data) {
					$scope.comInstance = data;
					return data;
		       }).then(function(allInstances){
		    	   $scope.installConfig = JSON3.parse(allInstances[0].comConfig);
		       })
		  },
		link:function($scope,ele,attrs,ctrl, $log){
            $scope.$watch('installConfig', function(installConfig) {
            	if(angular.isDefined(installConfig)){
            		console.log(installConfig);
            		
            		paper.install(window);
        	        paper = new PaperScope();
        	        paper.setup(ele[0]);
            		NetworkTopology.prototype = {
            				constructor : NetworkTopology,
            				drawNetworktopology : function(){
            					
            					//drawNetworks
            					var networkTopologyX = 80;					//distance between network and topology on direction X
            					var networkTopologyY = 20;					//distance between network and topology on direction Y
            					var networkWidth = 800;
            					var networkHeight = 20;
            					var networkCornerWidth = 2;
            					var networkCornerHeight = 2;
            					var networkInterval = 20;					//interval between networks
            					for(var numberofNetwork=0;numberofNetwork<this.Networks.length;numberofNetwork++){
            						var networkPoint = new Point(this.networkTopologyStartX + networkTopologyX,
            													 this.networkTopologyStartY + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork);
            						var networkSize = new Size(networkWidth, networkHeight);
            						var networkRectangle = new Rectangle(networkPoint, networkSize);
            						var networkCornerSize = new Size(networkCornerWidth, networkCornerHeight);
            						var networkPath = new Path.Rectangle(networkRectangle, networkCornerSize);
            						networkPath.strokeColor= this.Networks[numberofNetwork].networkandlineColor[numberofNetwork];
            						networkPath.fillColor=this.Networks[numberofNetwork].networkandlineColor[numberofNetwork];
            						
            						var netWorkNamePoint = new Point(this.networkTopologyStartX + networkTopologyX,
            														 this.networkTopologyStartY + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork - 3);
            						var netWorkNameText = new PointText(netWorkNamePoint);
            						netWorkNameText.content = "gateway:" + this.Networks[numberofNetwork].networkIP;
            						netWorkNameText.fillColor = this.Networks[numberofNetwork].networkandlineColor[numberofNetwork];
            						netWorkNameText.fontSize = '15px';
            					}
            					
            					//draw COMStack
            					
            					//draw COM outline
            					var COMStackTopologyX = 80;				//distance between COMStack and topology on direction X
            					var COMStackNetworkInterval = 100;			//Interval between COMStack and the last network
            					var COMStackWidth = 800;
            					var COMStackHeight = 400;
            					var COMStackCornerWidth = 10;
            					var COMStackCornerHeight = 10;
            					var COMStackPoint = new Point(this.networkTopologyStartX + COMStackTopologyX,
            												  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork);
            					var COMStackSize = new Size(COMStackWidth,COMStackHeight);
            					var COMStackRectangle = new Rectangle(COMStackPoint,COMStackSize);
            					var COMStackCornerSize = new Size(COMStackCornerWidth,COMStackCornerHeight);
            					var COMStackPath = new Path.Rectangle(COMStackRectangle,COMStackCornerSize);
            					COMStackPath.strokeColor='black';
            					COMStackPath.fillColor = '#E0FFFF';
            					//COM name
            					var COMStackNamePoint = new Point(this.networkTopologyStartX + COMStackTopologyX,
            													  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + 30);
								var COMStackNameText = new PointText(COMStackNamePoint);
								COMStackNameText.content = this.COMStacks[0].COMStackType;
								COMStackNameText.fillColor = 'red';
								COMStackNameText.fontSize = '30px';
            					//draw host
            					var hostCOMStackX = 50;						//distance between host and COMStack on direction X, it's value should equal VNFCCOMX
            					var hostCOMStackY = 250;					//distance between host and COMStack on direction Y
            					var hostCOMWidth = 700;
            					var hostCOMHeight = 80;
            					var hostCornerWidth = 20;
            					var hostCornerHeight = 20;
            					var hostPoint = new Point(this.networkTopologyStartX + COMStackTopologyX + hostCOMStackX,
            											  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + hostCOMStackY);
            					var hostSize = new Size(hostCOMWidth, hostCOMHeight);
            					var hostRectangle = new Rectangle(hostPoint, hostSize);
            					var hostCornerSize = new Size(hostCornerWidth, hostCornerHeight);
            					var hostPath = new Path.Rectangle(hostRectangle, hostCornerSize);
            					
            					var hostNamePoint = new Point(this.networkTopologyStartX + COMStackTopologyX + hostCOMStackX + hostCornerWidth,
            												  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + hostCOMStackY + hostCornerHeight);
            					var hostNameText = new PointText(hostNamePoint);
            					hostNameText.content = this.COMStacks[0].hostName + ':' +this.COMStacks[0].hostIP;
            					hostNameText.fillColor = 'red';
            					hostNameText.fontSize = '30px';
            					//hostNameText.strokeWidth = '40';
            					//hostNameText.fontFamily = 'Times New Roman';
            					//hostNameText.leading = '100';
            					
            					hostPath.strokeColor= 'black';
            					hostPath.fillColor='#5CACEE';
            					
            					//draw VNFCs
            					var VNFCCOMStackX = 50;						//distance between first VNFC and COMStack on direction X 
            					var VNFCCOMStackY = 150;					//distance between first VNFC and COMStack on direction Y
            					var VNFCInterval = 50;						//interval between VNFCs
            					var VNFCWidth = 200;		
            					var VNFCHeight = 80;
            					var VNFCCornerWidth = 10;
            					var VNFCCornerHeight = 10;
            					var portWidth = 20;
            					var portHeight = 10;
            					var portCornerWidth = 3;
            					var portCornerHeight = 3;
            					var portInterval = 50;						//interval between ports
            					var raster = [];
            					for(var numberofCOMStack=0;numberofCOMStack<this.COMStacks.length;numberofCOMStack++){
            						for(var numberofVNFC=0;numberofVNFC<this.COMStacks[numberofCOMStack].VNFCs.length;numberofVNFC++){
            							//draw VNFC[numberofVNFC]
            							var VNFCPoint = new Point(this.networkTopologyStartX + COMStackTopologyX + VNFCCOMStackX + (VNFCWidth + VNFCInterval) * numberofVNFC,
            													  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + VNFCCOMStackY);
            							var VNFCSize = new Size(VNFCWidth, VNFCHeight);
            							var VNFCRectangle = new Rectangle(VNFCPoint, VNFCSize);
            							var VNFCCornerSize = new Size(VNFCCornerWidth, VNFCCornerHeight);
            							var VNFCPath = new Path.Rectangle(VNFCRectangle, VNFCCornerSize);
            							VNFCPath.strokeColor= 'black';
            							VNFCPath.fillColor='#D1EEEE';
            							
            							var VNFCNamePoint = new Point(this.networkTopologyStartX + COMStackTopologyX + VNFCCOMStackX + (VNFCWidth + VNFCInterval) * numberofVNFC + VNFCCornerWidth + 50,
            														  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + VNFCCOMStackY + 30);
            							var VNFCNameText = new PointText(VNFCNamePoint);
            							VNFCNameText.content = this.COMStacks[numberofCOMStack].VNFCs[numberofVNFC].VNFCType;
            							VNFCNameText.fillColor = 'black';
            							VNFCNameText.fontSize = '30px';
            							
            							//VNFC Raster
            							raster[numberofVNFC] = new Raster('VNFC');
            							raster[numberofVNFC].position = new Point(this.networkTopologyStartX + COMStackTopologyX + VNFCCOMStackX + (VNFCWidth + VNFCInterval) * numberofVNFC + VNFCCornerWidth + 20,
												  								  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + VNFCCOMStackY + 25);
            							raster[numberofVNFC].scale(0.1);
            							
            							for(var numberofPort=0;numberofPort<this.COMStacks[numberofCOMStack].VNFCs[numberofVNFC].ports.length;numberofPort++){
            								//draw port : move down to use the color of the line
            								
            								//draw line between port and network
            								var lineEndY = '';				//the Y coordinate of the line between port and network
            								var lineColor = '';				//the color of the line between port and network
            								for(var numberofNetwork2=0;numberofNetwork2<this.Networks.length;numberofNetwork2++){
            									if(this.COMStacks[numberofCOMStack].VNFCs[numberofVNFC].ports[numberofPort].gateway == this.Networks[numberofNetwork2].networkIP){
            										lineColor = this.Networks[numberofNetwork2].networkandlineColor[numberofNetwork2];
            										lineEndY = this.networkTopologyStartY + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork2 + networkHeight;
            									}
            								}
            								//draw port
            								var portPoint = new Point(this.networkTopologyStartX + COMStackTopologyX + VNFCCOMStackX + VNFCCornerWidth + (portWidth + portInterval) * numberofPort + (VNFCWidth + VNFCInterval) * numberofVNFC,
            														  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + VNFCCOMStackY -portHeight);
            								var portSize = new Size(portWidth, portHeight);
            								var portRectangle = new Rectangle(portPoint, portSize);
            								var portCornerSize = new Size(portCornerWidth, portCornerHeight);
            								var portPath = new Path.Rectangle(portRectangle, portCornerSize);
            								portPath.strokeColor=lineColor;
            								portPath.fillColor=lineColor;
            								//draw port ip_address
            								var PortNamePoint = new Point(this.networkTopologyStartX + COMStackTopologyX + VNFCCOMStackX + VNFCCornerWidth + (portWidth + portInterval) * numberofPort + (VNFCWidth + VNFCInterval) * numberofVNFC,
            															  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + VNFCCOMStackY -portHeight);
											var PortNameText = new PointText(PortNamePoint);
											PortNameText.content = this.COMStacks[numberofCOMStack].VNFCs[numberofVNFC].ports[numberofPort].IP;
											PortNameText.fillColor = 'black';
											PortNameText.fontSize = '10px';
											PortNameText.rotate(270);
            								//draw line
            								var line = new Path.Line([this.networkTopologyStartX + COMStackTopologyX + VNFCCOMStackX + VNFCCornerWidth + (portWidth + portInterval) * numberofPort + (VNFCWidth + VNFCInterval) * numberofVNFC + portWidth/2,
            														  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + VNFCCOMStackY -portHeight],
            														 [this.networkTopologyStartX + COMStackTopologyX + VNFCCOMStackX + VNFCCornerWidth + (portWidth + portInterval) * numberofPort + (VNFCWidth + VNFCInterval) * numberofVNFC + portWidth/2,
            														  lineEndY]);
            								line.strokeColor = lineColor;
            								line.strokeWidth = "2";
            							}
            						}
            					}
            				}
            			}
            		
            		var comStack1 = new COMStack(installConfig.comType,installConfig.active_host_ip.name,installConfig.active_host_ip.ip_address);
            		
            		//get network, VNFC, port
            		var i = 0;
            		var vnfcInCOMLogic = [];					//the VNFC in this installConfig
            		var portsLogic = [];						//standby
            		var networksLogic = {};
            		for(var vnfc in installConfig.vm_config){
            			var vnfcsLogic = installConfig.vm_config[vnfc];			//reference the VNFC object in this vm_config, e.g object cm
            			
            			//get the gateway of the vnfcsLogic
            			var gatewayLogic = vnfcsLogic.gateway;
            			//push the gate way into the networkLogic,if this gateway exists, then ignore
            			if(!networksLogic[gatewayLogic]){
            				var network = new Network(gatewayLogic);
            				networksLogic[gatewayLogic] = network;
            			}
            			
            			//create VNFC object 
            			vnfcInCOMLogic[i] = new VNFC(vnfc);
            			comStack1.addVNFC(vnfcInCOMLogic[i]);
            			
            			//create port object	/////////////////////////how to represent more than one port in a VNFC
            			var port = new Port(vnfcsLogic.ip_address,vnfcsLogic.gateway);
            			vnfcInCOMLogic[i].addPort(port);
            			
            			i++;
            		}
            		console.log(comStack1);
            		
            		var networkTopology = new NetworkTopology(5,5);
            		networkTopology.addCOMStack(comStack1);
            		//add network into this topology
            		(function(){
            			for(var i in networksLogic){
                			networkTopology.addNetwork(networksLogic[i]);
                		}
            		})();
            		console.log(networkTopology);
        			networkTopology.drawNetworktopology();
            	};
            });
		}
	}
});

function Network(networkIP){
	this.networkIP = networkIP;
	this.networkandlineColor = ["blue","green","pink","yellow","purple","red","black"];					//the color of network and the line between network and port
}

//class Port
function Port(IP,gateway){
	this.IP = IP;
	this.gateway = gateway;
}

//class VNFC
function VNFC(VNFCType){
	this.VNFCType = VNFCType;
	this.ports = [];		//ports of this VNFC
	this.addPort = function(port){
		this.ports.push(port);
	}
}

//class COMStack
function COMStack(COMStackType,hostName,hostIP){
	this.COMStackType = COMStackType;
	this.hostName = hostName;
	this.hostIP = hostIP;
	this.VNFCs = [];		//VNFCs of this COMStack
	this.addVNFC = function(vnfc){
		this.VNFCs.push(vnfc);
	}
}

//class NetworkTopology
function NetworkTopology(networkTopologyStartX,networkTopologyStartY){
	this.networkTopologyStartX = networkTopologyStartX;
	this.networkTopologyStartY = networkTopologyStartY;
	this.COMStacks = [];							//COMStacks of this topology
	this.Networks = [];								//Networks of this topology
	this.addCOMStack = function(comStack){
		this.COMStacks.push(comStack);
	}
	this.addNetwork = function(network){
		this.Networks.push(network);
	}
}

