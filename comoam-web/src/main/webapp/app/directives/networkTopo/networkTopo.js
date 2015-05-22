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
		    	   $scope.instances = [];
		    	   if(allInstances!=null){
		    		   (function(){
		    			   for(var i=0;i<allInstances.length;i++){
		    				   $scope.instances[i] = JSON3.parse(allInstances[i].comConfig);
		    			   }
		    		   })();
		    	   }
		       })
		  },
		link:function($scope,ele,attrs,ctrl, $log){
            $scope.$watch('instances', function(instances) {
            	if(angular.isDefined(instances)){
            		console.log(instances);
            		
            		paper.install(window);
        	        //paper = new PaperScope();
        	        paper.setup(ele[0]);
            		NetworkTopology.prototype = {
            				constructor : NetworkTopology,
            				drawNetworktopology : function(){
            					//draw comStacks
            					var comStackTopoX = 300;				//distance between comStack and this topology on direction X
            					var comStackTopoY = 50;					//distance between comStack and this topology on direction Y
            					var comStackWidth = 200;					
            					var comStackHeight = 350;
            					var comStackCornerWidth = 5;
            					var comStackCornerHeight = 5;
            					var comStackInterval = 30;
            					var comStackPath = [];
            					for(var comStacksNum = 0;comStacksNum < this.COMStacks.length;comStacksNum++){
            						//draw COM outline
            						var comStackPoint = new Point(this.networkTopologyStartX + comStackTopoX,
            								this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum);
            						var comStackSize = new Size(comStackWidth,comStackHeight);
            						var comStackRectangle = new Rectangle(comStackPoint,comStackSize);
                					var comStackCornerSize = new Size(comStackCornerWidth,comStackCornerHeight);
                					comStackPath[comStacksNum] = new Path.Rectangle(comStackRectangle,comStackCornerSize);
                					comStackPath[comStacksNum].strokeColor='black';
                					comStackPath[comStacksNum].fillColor = '#E0FFFF';
                					
                					//onMouseEnter event
//                					var tool = new Tool();
//                					tool.onMouseUp = function(event){
//                						comStackPath[].fillColor = "black";
//                					}
                					
                					if(this.COMStacks[comStacksNum].environment == "KVM"){
                						//draw host
                    					var hostCOMStackX = 30;						//distance between host and COMStack on direction X, it's value should equal vnfcCOMX
                    					var hostCOMStackY = 25;					//distance between host and COMStack on direction Y
                    					var hostCOMWidth = 50;
                    					var hostCOMHeight = comStackHeight - hostCOMStackY * 2;
                    					var hostCornerWidth = 10;
                    					var hostCornerHeight = 10;
                    					var hostPoint = new Point(this.networkTopologyStartX + comStackTopoX + hostCOMStackX,
                    							this.networkTopologyStartY + comStackTopoY + hostCOMStackY + (comStackHeight + comStackInterval) * comStacksNum);
    						            var hostSize = new Size(hostCOMWidth, hostCOMHeight);
    									var hostRectangle = new Rectangle(hostPoint, hostSize);
    									var hostCornerSize = new Size(hostCornerWidth, hostCornerHeight);
    									var hostPath = new Path.Rectangle(hostRectangle, hostCornerSize);
    									hostPath.strokeColor= 'black';
    	            					hostPath.fillColor='#C6E2FF';
    	            					
    	            					//draw vnfcs
    	            					var vnfcCounts = 0;
    	            					var j = 0;
    	            					for(var name in this.COMStacks[comStacksNum].vm_config){
    	            						vnfcCounts++;
    	            					}
    	            					console.log(vnfcCounts);
    	            					var vnfcCOMStackX = 100;						//distance between first VNFC and COMStack on direction X 
    	            					var vnfcCOMStackY = 30;						//distance between first VNFC and COMStack on direction Y
    	            					var vnfcInterval = 10;						//interval between VNFCs
    	            					var vnfcHeight = (comStackHeight - vnfcCOMStackY * 2) / vnfcCounts - vnfcInterval;
    	            					var vnfcWidth = vnfcHeight / 2;		
    	            					var vnfcCornerWidth = 10;
    	            					var vnfcCornerHeight = 10;
    	            					for(var vnfcNum in this.COMStacks[comStacksNum].vm_config){
    	            						this.COMStacks[comStacksNum].vm_config[vnfcNum];
    	            						var vnfcPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX,
    	            								this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j);
    	            						var vnfcSize = new Size(vnfcWidth, vnfcHeight);
                							var vnfcRectangle = new Rectangle(vnfcPoint, vnfcSize);
                							var vnfcCornerSize = new Size(vnfcCornerWidth, vnfcCornerHeight);
                							var vnfcPath = new Path.Rectangle(vnfcRectangle, vnfcCornerSize);
                							vnfcPath.strokeColor= 'blue';
                							vnfcPath.fillColor='#D1EEEE';
                							
                							//draw port
                							//port's position is connected to vnfc
                							var portWidth = 10;
                        					var portHeight = 20;
                        					var portCornerWidth = 3;
                        					var portCornerHeight = 3;
                        					var portInterval = 50;						//interval between ports
                        					var vnfcPortY = (vnfcHeight - portHeight) / 2;							//distance between the first port's position and the vnfc
                        					var portPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth,
                        							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY);
											var portSize = new Size(portWidth, portHeight);
											var portRectangle = new Rectangle(portPoint, portSize);
											var portCornerSize = new Size(portCornerWidth, portCornerHeight);
											var portPath = new Path.Rectangle(portRectangle, portCornerSize);
											portPath.strokeColor = "black";
											portPath.fillColor = "black";
											
											j++;
    	            					}
                					}
            					}
            					
            					//draw networks
            					var comStackNetworkInterval = 150;		//interval between comStack and network
            					var networkTopoY = 50;					//network parallel to comStack or higher? to be confirm...
            															//networkTopoY depends on the number of networks
            					var networkWidth = 30;
            					var networkHeight = (comStackHeight + comStackInterval) * comStacksNum - comStackInterval;
            					var networkCornerWidth = 10;
            					var networkCornerHeight = 10;
            					var networkInterval = 20;					//interval between networks
            					for(var networkNum = 0;networkNum < this.Networks.length;networkNum++){
            						var networkPoint = new Point(this.networkTopologyStartX + comStackTopoX + comStackWidth + comStackNetworkInterval + (networkWidth + networkInterval) * networkNum,
            								this.networkTopologyStartX + networkTopoY);
            						var networkSize = new Size(networkWidth, networkHeight);
            						var networkRectangle = new Rectangle(networkPoint, networkSize);
            						var networkCornerSize = new Size(networkCornerWidth, networkCornerHeight);
            						var networkPath = new Path.Rectangle(networkRectangle, networkCornerSize);
            						networkPath.strokeColor = 'blue';
            						networkPath.fillColor = 'blue';
            						
            					}
            				}
            			}
//            		var comStacks = [];
//            		var vnfcs = [];
//            		var networks = {};
//            		for(var instanceNumber=0;instanceNumber<instances.length;instanceNumber++){
//            			comStacks[instanceNumber] = new COMStack(instances[instanceNumber].comType,
//									            					instances[instanceNumber].deployment_prefix,
//									            					instances[instanceNumber].active_host_ip);
//            			for(var vnfcNumber in instances[instanceNumber].vm_config){
//            				vnfcs[vnfcNumber] = new VNFC(vnfcNumber, 
//						            						instances[instanceNumber].vm_config[vnfcNumber].flavor.memory, 
//						            						instances[instanceNumber].vm_config[vnfcNumber].flavor.vCpu);
//            				var port = new Port(instances[instanceNumber].vm_config[vnfcNumber].ip_address, instances[instanceNumber].vm_config[vnfcNumber].gateway);
//            				vnfcs[vnfcNumber].addPort(port);
//            				comStacks[instanceNumber].addVNFC(vnfcs[vnfcNumber]);
//            				if(!instances[instanceNumber].vm_config[vnfcNumber].gateway){
//            					var network = new Network(instances[instanceNumber].vm_config[vnfcNumber].gateway);
//            					networks[instances[instanceNumber].vm_config[vnfcNumber].gateway] = network;
//            				}
//            			}
//            		}
            		var networks = {};
            		(function(){
            			for(var comstack in instances){
            				for(var vnfc in instances[comstack].vm_config){
            					if(!networks[instances[comstack].vm_config[vnfc].gateway]){
            						//var network = new Network(instances[comstack].vm_config[vnfc].gateway);
            						networks[instances[comstack].vm_config[vnfc].gateway] = instances[comstack].vm_config[vnfc].gateway;
            					}
            				}
            			}
            		})();
            		var networkTopology = new NetworkTopology(5,5);
            		networkTopology.addCOMStack(instances);
            		//add network into this topology
            		(function(){
            			for(var i in networks){
                			networkTopology.addNetwork(networks[i]);
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
function VNFC(VNFCType,memory,vCpu){
	this.VNFCType = VNFCType;
	this.memory = memory;
	this.vCpu = vCpu;
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
	this.addCOMStack = function(comStacks){
		this.COMStacks = comStacks;
	}
	this.addNetwork = function(network){
		this.Networks.push(network);
	}
}

