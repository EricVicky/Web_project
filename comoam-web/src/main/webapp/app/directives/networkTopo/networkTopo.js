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
            					var commoncolor = ["rgb(255,151,35)","rgb(52,191,52)","rgb(225,46,47)","rgb(37,142,215)","rgb(148,103,189)","rgb(227,119,194)","rgb(224,225,40)"];					//the color of network and the line between network and port
            					
            					//draw comStacks
            					var comStackTopoX = 200;				//distance between comStack and this topology on direction X
            					var comStackTopoY = 50;					//distance between comStack and this topology on direction Y
            					var comStackWidth = 130;					
            					var comStackHeight = 180;				//this parameter is needed when draw networks, so init it before draw networks
            					var comStackCornerWidth = 5;
            					var comStackCornerHeight = 5;
            					var comStackInterval = 30;				//this parameter is needed when draw networks
            					var comStackPath = [];
            					
            					var comStackNameY = 2;					//distance between comStack and it's name on direction Y
            					
            					var comStackMenuX = 20;					//distance between comStack and it's menu on direction X
            					var comStackMenuY = 10;					//distance between comStack and it's menu on direction Y
            					var canvasTop = $("#canvas").position().top;
        						var canvasLeft = $("#canvas").position().left;
            					var comColor = '#337ab7';
            					var hostColor = 'rgb(55,91,205)';
            					var vnfcColor = 'rgb(42,176,224)';
            					for(var comStacksNum = 0;comStacksNum < this.COMStacks.length;comStacksNum++){
            						//draw COM outline
            						var comStackPoint = new Point(this.networkTopologyStartX + comStackTopoX,
            								this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum);
            						var comStackSize = new Size(comStackWidth,comStackHeight);
            						var comStackRectangle = new Rectangle(comStackPoint,comStackSize);
                					var comStackCornerSize = new Size(comStackCornerWidth,comStackCornerHeight);
                					comStackPath[comStacksNum] = new Path.Rectangle(comStackRectangle,comStackCornerSize);
                					comStackPath[comStacksNum].strokeColor='#8B8B83';
                					comStackPath[comStacksNum].fillColor = comColor;
                					
                					comStackPath[comStacksNum].attach('mousemove' , function(){
                						
                						var indexOfcomStack = comStackPath.indexOf(this);
                						$("#menu").css({"top":canvasTop + comStackTopoY + (comStackHeight + comStackInterval) * indexOfcomStack + comStackMenuY, left:comStackTopoX + comStackWidth + canvasLeft - comStackMenuX, "position": "absolute"});
                						$("#menu").show();
                						$scope.selectedIns = instances[comStackPath.indexOf(this)];
                						
                						comStackPath[comStackPath.indexOf(this)].opacity = 0.6;
									});
                					comStackPath[comStacksNum].attach('mouseleave' , function(){
                						comStackPath[comStackPath.indexOf(this)].opacity = 1;
									});
                					//alert(indexOfcomStack);
                					
                					//draw comStack Name
                					var comStackNamePoint = new Point(this.networkTopologyStartX + comStackTopoX,
                							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum - comStackNameY);
									var comStackNameText = new PointText(comStackNamePoint);
									comStackNameText.content = this.COMStacks[comStacksNum].deployment_prefix;
									comStackNameText.fillColor = 'blue';
									comStackNameText.fontSize = '15px';
									comStackNameText.fontFamily = 'Arial Rounded MT Bold';
                					
                					if(this.COMStacks[comStacksNum].environment == "KVM"){
                						//draw host
                    					var hostCOMStackX = 20;						//distance between host and COMStack on direction X, it's value should equal vnfcCOMX
                    					var hostCOMStackY = 15;					//distance between host and COMStack on direction Y
                    					var hostWidth = 35;
                    					var hostHeight = comStackHeight - hostCOMStackY * 2;
                    					var hostCornerWidth = 5;
                    					var hostCornerHeight = 5;
                    					
                    					var hostPoint = new Point(this.networkTopologyStartX + comStackTopoX + hostCOMStackX,
                    							this.networkTopologyStartY + comStackTopoY + hostCOMStackY + (comStackHeight + comStackInterval) * comStacksNum);
    						            var hostSize = new Size(hostWidth, hostHeight);
    									var hostRectangle = new Rectangle(hostPoint, hostSize);
    									var hostCornerSize = new Size(hostCornerWidth, hostCornerHeight);
    									var hostPath = new Path.Rectangle(hostRectangle, hostCornerSize);
    									hostPath.strokeColor= 'white';
    	            					hostPath.fillColor=hostColor;
    									
    	            					//draw host name
    	            					var hostNamePoint = new Point(this.networkTopologyStartX + comStackTopoX + hostCOMStackX,
    	            							this.networkTopologyStartY + comStackTopoY + hostCOMStackY + (comStackHeight + comStackInterval) * comStacksNum + hostHeight / 2 + 4);
    									var hostNameText = new PointText(hostNamePoint);
    									hostNameText.content = this.COMStacks[comStacksNum].host.ip_address;
    									hostNameText.fillColor = 'black';
    									hostNameText.fontSize = '15px'; 
    									hostNameText.justification = 'center';
    									hostNameText.rotate(90);
    									hostNameText.position.x += 17;
    	            					
    	            					//draw vnfcs
    	            					var vnfcCounts = 0;
    	            					var j = 0;
    	            					for(var name in this.COMStacks[comStacksNum].vm_config){
    	            						vnfcCounts++;
    	            					}
    	            					var vnfcCOMStackX = 60;						//distance between VNFC and COMStack on direction X 
    	            					var vnfcCOMStackY = 20;						//distance between first VNFC and COMStack on direction Y
    	            					var vnfcInterval = 10;						//interval between VNFCs
    	            					var vnfcHeight = 40;
    	            					var vnfcWidth = vnfcHeight / 2;		
    	            					var vnfcCornerWidth = 5;
    	            					var vnfcCornerHeight = 5;
    	            					var vnfcNamevnfcX = 3;						//distance between vnfc and it's name on direction X
    	            					for(var vnfcNum in this.COMStacks[comStacksNum].vm_config){
    	            						this.COMStacks[comStacksNum].vm_config[vnfcNum];
    	            						var vnfcPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX,
    	            								this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j);
    	            						var vnfcSize = new Size(vnfcWidth, vnfcHeight);
                							var vnfcRectangle = new Rectangle(vnfcPoint, vnfcSize);
                							var vnfcCornerSize = new Size(vnfcCornerWidth, vnfcCornerHeight);
                							var vnfcPath = new Path.Rectangle(vnfcRectangle, vnfcCornerSize);
                							vnfcPath.strokeColor= 'white';
                							vnfcPath.fillColor=vnfcColor;
                							
                							//draw vnfc name
                							var vnfcNamePoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX,
                									this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcHeight / 2 + 5);
        									var vnfcNameText = new PointText(vnfcNamePoint);
        									vnfcNameText.content = vnfcNum.toUpperCase();
        									vnfcNameText.fillColor = 'black';
        									vnfcNameText.fontSize = '15px'; 
        									vnfcNameText.fontFamily = 'Calibri';
        									vnfcNameText.justification = 'center';
        									vnfcNameText.rotate(90);
        									vnfcNameText.position.x += 10;
        									
        									//get line end X coordinate
											var lineEndX = '';
											var linePortColor = '';
											var networkWidth = 18;
											var networkInterval = 40;					//interval between networks
											var networkTopoX = 550;					//distance between topology and network on direction X
											for(var numberofNetwork=0;numberofNetwork<this.Networks.length;numberofNetwork++){
            									if(getNetworkAddress(this.COMStacks[comStacksNum].vm_config[vnfcNum].ip_address , this.COMStacks[comStacksNum].vm_config[vnfcNum].netmask) == this.Networks[numberofNetwork]){
            										lineEndX = this.networkTopologyStartX + networkTopoX + (networkWidth + networkInterval) * numberofNetwork;
            										linePortColor = commoncolor[numberofNetwork];
            									}
            								}
                							
                							//draw vnfc port
                							//port's position is connected to vnfc
        									var portWidth = 5;
                        					var portHeight = 10;
                        					var portCornerWidth = 2;
                        					var portCornerHeight = 2;
                        					var portInterval = 50;						//interval between ports
                        					var vnfcPortY = (vnfcHeight - portHeight) / 2;							//distance between the first port's position and the vnfc
                        					var vnfcportPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth,
                        							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY);
											var vnfcportSize = new Size(portWidth, portHeight);
											var vnfcportRectangle = new Rectangle(vnfcportPoint, vnfcportSize);
											var vnfcportCornerSize = new Size(portCornerWidth, portCornerHeight);
											var vnfcportPath = new Path.Rectangle(vnfcportRectangle, vnfcportCornerSize);
											vnfcportPath.fillColor = linePortColor;
											
											//draw vnfc port ip address
											var vnfcportIpPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + portWidth,
													this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY);
        									var vnfcportIpText = new PointText(vnfcportIpPoint);
        									vnfcportIpText.content = this.COMStacks[comStacksNum].vm_config[vnfcNum].ip_address;
        									vnfcportIpText.fillColor = linePortColor;
        									vnfcportIpText.fontSize = '15px'; 
        									
											//draw line between vnfc port and networks
			            					var line = new Path.Line([this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + portWidth,
			            					                          this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY + portHeight / 2],
																	 [lineEndX,
																	  this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY + portHeight / 2]);
											line.strokeColor = linePortColor;
											line.strokeWidth = "3";
											
											j++;
    	            					}
                					}
            					}
            					//draw networks
            					//networks is not conneted to comStack, it's connected to the whole topology directly
            					var comStackHeight = 180;				//
            					var comStackInterval = 30;				//
            					var networkTopoX = 550;					//distance between topology and network on direction X
            					var networkTopoY = 50;					//network parallel to comStack or higher? to be confirm...
            															//networkTopoY depends on the number of networks
            					var networkWidth = 18;
            					var networkHeight = (comStackHeight + comStackInterval) * this.COMStacks.length - comStackInterval;
            					var networkCornerWidth = 10;
            					var networkCornerHeight = 10;
            					var networkInterval = 40;					//interval between networks
            					var networkPath = [];
            					
            					for(var networkNum = 0;networkNum < this.Networks.length;networkNum++){
            						var networkPoint = new Point(this.networkTopologyStartX + networkTopoX + (networkWidth + networkInterval) * networkNum,
            								this.networkTopologyStartX + networkTopoY);
            						var networkSize = new Size(networkWidth, networkHeight);
            						var networkRectangle = new Rectangle(networkPoint, networkSize);
            						var networkCornerSize = new Size(networkCornerWidth, networkCornerHeight);
            						networkPath[networkNum] = new Path.Rectangle(networkRectangle, networkCornerSize);
            						var networkColor = commoncolor[networkNum];
            						networkPath[networkNum].fillColor = networkColor;
            						networkPath[networkNum].blendMode = 'source-over';
            						
            						networkPath[networkNum].attach('mousemove' , function(){
            							networkPath[networkPath.indexOf(this)].opacity = 0.5;
									});
            						networkPath[networkNum].attach('mouseleave' , function(){
            							networkPath[networkPath.indexOf(this)].opacity = 1;
									});
            						
            						//draw network name
            						var networkNamePoint = new Point(this.networkTopologyStartX + networkTopoX + (networkWidth + networkInterval) * networkNum,
            								this.networkTopologyStartX + networkTopoY + networkHeight / 2);
									var networkNameText = new PointText(networkNamePoint);
									networkNameText.content = this.Networks[networkNum];
									networkNameText.fillColor = 'white';
									networkNameText.fontSize = '15px';
									networkNameText.justification = 'center';
									networkNameText.rotate(90);
									networkNameText.position.x += 8;
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
//            				if(!networks[instances[comstack].active_host_ip]){
//            					networks[instances[comstack].active_host_ip] = instances[comstack].active_host_ip;
//            				}
            				for(var vnfc in instances[comstack].vm_config){
            					var networkAddress = getNetworkAddress(instances[comstack].vm_config[vnfc].ip_address,instances[comstack].vm_config[vnfc].netmask);
            					if(!networks[networkAddress]){
            						//var network = new Network(instances[comstack].vm_config[vnfc].gateway);
            						networks[networkAddress] = networkAddress;
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

function Network(networkAddress){
	this.networkAddress = networkAddress;
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

function getNetworkAddress (ipAddress , netmask) {
	var ipAddressArr = [];
	var netmaskArr = [];
	var networkAddress = [];
	ipAddressArr = ipAddress.split(".");
	netmaskArr = netmask.split(".");
	for(var i=0;i<ipAddressArr.length;i++){
		networkAddress.push(parseInt(ipAddressArr[i]) & parseInt(netmaskArr[i]));
	}
	return networkAddress.join(".");
}

function getRandomColor(){ 
	return "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6); 
} 