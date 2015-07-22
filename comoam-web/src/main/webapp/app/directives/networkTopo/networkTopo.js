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
            					$scope.comstackNum = this.COMStacks.length;
            					var commoncolor = ["rgb(255,151,35)","rgb(52,191,52)","rgb(225,46,47)","rgb(227,119,194)","rgb(37,142,215)","rgb(148,103,189)","rgb(224,225,40)","pink","grey"];					//the color of network and the line between network and port
            					
            					//draw comStacks
            					var comStackTopoX = 200;				//distance between comStack and this topology on direction X
            					var comStackTopoY = 50;					//distance between comStack and this topology on direction Y
            					var comStackWidth = 130;					
            					var comStackHeight = 180;				//this parameter is needed when draw networks, so init it before draw networks
            					var comStackCornerWidth = 5;
            					var comStackCornerHeight = 5;
            					var comStackInterval = 30;				//this parameter is needed when draw networks
            					var selectedPathNum = '';				//save the selected path's number temporary when the mouse entered
            					var comStackPath = [];
            					var hostPath = [];
            					var hostNameText = [];
            					
            					var comStackNameY = 2;					//distance between comStack and it's name on direction Y
            					
            					var comStackMenuX = 20;					//distance between comStack and it's menu on direction X
            					var comStackMenuY = 10;					//distance between comStack and it's menu on direction Y
            					var canvasTop = $("#canvas").position().top;
        						var canvasLeft = $("#canvas").position().left;
        						var indexOfcomStack = '';
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
                					
                					comStackPath[comStacksNum].attach('mouseleave' , function(){
                						comStackPath[comStackPath.indexOf(this)].opacity = 1;
									});
                					$scope.selectedInsNum;
                					
                					//draw comStack Name
                					var comStackNamePoint = new Point(this.networkTopologyStartX + comStackTopoX,
                							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum - comStackNameY);
									var comStackNameText = new PointText(comStackNamePoint);
									if(this.COMStacks[comStacksNum].environment == "KVM"){
										comStackNameText.content = this.COMStacks[comStacksNum].comType + "::" + this.COMStacks[comStacksNum].deployment_prefix;
									}else{
										comStackNameText.content = this.COMStacks[comStacksNum].comType + "::" + this.COMStacks[comStacksNum].stackName;
									}
									comStackNameText.fillColor = 'black';
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
    									hostPath[comStacksNum] = new Path.Rectangle(hostRectangle, hostCornerSize);
    									hostPath[comStacksNum].strokeColor= 'white';
    	            					hostPath[comStacksNum].fillColor=hostColor;
    	            					
    	            					//draw host name
    	            					var hostNamePoint = new Point(this.networkTopologyStartX + comStackTopoX + hostCOMStackX,
    	            							this.networkTopologyStartY + comStackTopoY + hostCOMStackY + (comStackHeight + comStackInterval) * comStacksNum + hostHeight / 2 + 4);
    									hostNameText[comStacksNum] = new PointText(hostNamePoint);
    									hostNameText[comStacksNum].content = this.COMStacks[comStacksNum].host.ip_address;
    									hostNameText[comStacksNum].fillColor = 'black';
    									hostNameText[comStacksNum].fontSize = '15px'; 
    									hostNameText[comStacksNum].justification = 'center';
    									hostNameText[comStacksNum].rotate(90);
    									hostNameText[comStacksNum].position.x += 17;
    	            					
    									
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
												if(instances[comStacksNum].vm_config[vnfcNum].ip_address){
													if(getNetworkAddress4HPSIM(this.COMStacks[comStacksNum].vm_config[vnfcNum].ip_address , this.COMStacks[comStacksNum].vm_config[vnfcNum].netmask) == this.Networks[numberofNetwork]){
	            										lineEndX = this.networkTopologyStartX + networkTopoX + (networkWidth + networkInterval) * numberofNetwork;
	            										linePortColor = commoncolor[numberofNetwork];
	            									}
												}else{
													for(var indexofNic = 0;indexofNic<instances[comStacksNum].vm_config[vnfcNum].nic.length;indexofNic++){
		                        						if(getNetworkAddress(this.COMStacks[comStacksNum].vm_config[vnfcNum].nic[indexofNic].ip_v4.ipaddress , this.COMStacks[comStacksNum].vm_config[vnfcNum].nic[indexofNic].ip_v4.prefix) == this.Networks[numberofNetwork]){
		            										lineEndX = this.networkTopologyStartX + networkTopoX + (networkWidth + networkInterval) * numberofNetwork;
		            										linePortColor = commoncolor[numberofNetwork];
		            									}
		                    						}
												}
            								}
                							
											var indexofNIC = 0;
											if(!instances[comStacksNum].vm_config[vnfcNum].ip_address){
												for(var nicNum in this.COMStacks[comStacksNum].vm_config[vnfcNum].nic){
													//draw vnfc port
		                							//port's position is connected to vnfc
		        									var portWidth = 5;
		                        					var portHeight = 10;
		                        					var portCornerWidth = 2;
		                        					var portCornerHeight = 2;
		                        					var portInterval = 5;						//interval between ports
		                        					var vnfcPortY = 1;							//distance between the first port's position and the vnfc
		                        					var vnfcportPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth,
		                        							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + (portHeight + portInterval) * indexofNIC + vnfcPortY);
													var vnfcportSize = new Size(portWidth, portHeight);
													var vnfcportRectangle = new Rectangle(vnfcportPoint, vnfcportSize);
													var vnfcportCornerSize = new Size(portCornerWidth, portCornerHeight);
													var vnfcportPath = new Path.Rectangle(vnfcportRectangle, vnfcportCornerSize);
													vnfcportPath.fillColor = linePortColor;
													
													//draw vnfc port ip address
													var vnfcportIpPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + 50,
		                        							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + (portHeight + portInterval) * indexofNIC + 3);
		        									var vnfcportIpText = new PointText(vnfcportIpPoint);
		        									vnfcportIpText.content = this.COMStacks[comStacksNum].vm_config[vnfcNum].nic[nicNum].ip_v4.ipaddress + "/" + this.COMStacks[comStacksNum].vm_config[vnfcNum].nic[nicNum].ip_v4.prefix;
		        									vnfcportIpText.fillColor = linePortColor;
		        									vnfcportIpText.fontSize = '15px';
		        									
		        									//draw line between vnfc port and networks
					            					var line = new Path.Line([this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + portWidth,
					            					                          this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + (portHeight + portInterval) * indexofNIC + vnfcPortY + portHeight / 2],
																			 [lineEndX,
																			  this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + (portHeight + portInterval) * indexofNIC + vnfcPortY + portHeight / 2]);
													line.strokeColor = linePortColor;
													line.strokeWidth = "3";
													
													indexofNIC++;
												}
											}else{
												//draw vnfc port
	                							//port's position is connected to vnfc
	        									var portWidth = 5;
	                        					var portHeight = 10;
	                        					var portCornerWidth = 2;
	                        					var portCornerHeight = 2;
	                        					var portInterval = 5;						//interval between ports
	                        					var vnfcPortY = 1;							//distance between the first port's position and the vnfc
	                        					var vnfcportPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth,
	                        							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY);
												var vnfcportSize = new Size(portWidth, portHeight);
												var vnfcportRectangle = new Rectangle(vnfcportPoint, vnfcportSize);
												var vnfcportCornerSize = new Size(portCornerWidth, portCornerHeight);
												var vnfcportPath = new Path.Rectangle(vnfcportRectangle, vnfcportCornerSize);
												vnfcportPath.fillColor = linePortColor;
												
												//draw vnfc port ip address
												var vnfcportIpPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + 50,
	                        							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + 3);
	        									var vnfcportIpText = new PointText(vnfcportIpPoint);
	        									vnfcportIpText.content = this.COMStacks[comStacksNum].vm_config[vnfcNum].ip_address + "/" + getPrefix(this.COMStacks[comStacksNum].vm_config[vnfcNum].netmask);
	        									vnfcportIpText.fillColor = linePortColor;
	        									vnfcportIpText.fontSize = '15px';
	        									
	        									//draw line between vnfc port and networks
				            					var line = new Path.Line([this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + portWidth,
				            					                          this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY + portHeight / 2],
																		 [lineEndX,
																		  this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY + portHeight / 2]);
												line.strokeColor = linePortColor;
												line.strokeWidth = "3";
											}
											j++;
    	            					}
                					}else {
    	            					//draw vnfcs
    	            					var vnfcCounts = 0;
    	            					var j = 0;
    	            					for(var name in this.COMStacks[comStacksNum].vm_config){
    	            						vnfcCounts++;
    	            					}
    	            					var vnfcCOMStackX = 40;						//distance between VNFC and COMStack on direction X 
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
											var privateLineEndX = '';
											var providerLineEndX = '';
											var linePortColor = '';
											var priLinePortColor = '';
											var proLinePortColor = '';
											var networkWidth = 18;
											var networkInterval = 40;					//interval between networks
											var networkTopoX = 550;					//distance between topology and network on direction X
											for(var numberofPriNetwork=0;numberofPriNetwork<this.Networks.length;numberofPriNetwork++){
            									if(this.COMStacks[comStacksNum].com_private_network.cidr == this.Networks[numberofPriNetwork]){
            										privateLineEndX = this.networkTopologyStartX + networkTopoX + (networkWidth + networkInterval) * numberofPriNetwork;
            										priLinePortColor = commoncolor[numberofPriNetwork];
            									}
            								}
											for(var numberofProNetwork=0;numberofProNetwork<this.Networks.length;numberofProNetwork++){
            									if(getNetworkAddress4HPSIM(this.COMStacks[comStacksNum].vm_config[vnfcNum].provider_ip_address , this.COMStacks[comStacksNum].com_provider_network.netmask) == this.Networks[numberofProNetwork] ){
            										providerLineEndX = this.networkTopologyStartX + networkTopoX + (networkWidth + networkInterval) * numberofProNetwork;
            										proLinePortColor = commoncolor[numberofProNetwork];
            									}
            								}
        									
        									//draw vnfc port
                							//port's position is connected to vnfc
        									var portWidth = 5;
                        					var portHeight = 10;
                        					var portCornerWidth = 2;
                        					var portCornerHeight = 2;
                        					var portInterval = 16;							//interval between ports
                        					var vnfcPortY = 3;							//distance between the first port's position and the vnfc
                        					//private port
                        					var vnfcPriPortPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth,
                        							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY);
											var vnfcPriPortSize = new Size(portWidth, portHeight);
											var vnfcPriPortRectangle = new Rectangle(vnfcPriPortPoint, vnfcPriPortSize);
											var vnfcPriPortCornerSize = new Size(portCornerWidth, portCornerHeight);
											var vnfcPriPortPath = new Path.Rectangle(vnfcPriPortRectangle, vnfcPriPortCornerSize);
											vnfcPriPortPath.fillColor = priLinePortColor;
											
											//draw vnfc private port ip address
											var vnfcPriPortIpPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + portWidth + 70,
													this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY);
        									var vnfcPriPortIpText = new PointText(vnfcPriPortIpPoint);
        									vnfcPriPortIpText.content = this.COMStacks[comStacksNum].vm_config[vnfcNum].private_ip_address;
        									vnfcPriPortIpText.fillColor = priLinePortColor;
        									vnfcPriPortIpText.fontSize = '15px'; 
        									
											//draw line between vnfc private port and networks
			            					var privateline = new Path.Line([this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + portWidth,
			            					                          this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY + portHeight / 2],
																	 [privateLineEndX,
																	  this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + vnfcPortY + portHeight / 2]);
			            					privateline.strokeColor = priLinePortColor;
			            					privateline.strokeWidth = "3";
											
											
											//provider port
											var vnfcProPortPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth,
                        							this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + portHeight + portInterval);
											var vnfcProPortSize = new Size(portWidth, portHeight);
											var vnfcProPortRectangle = new Rectangle(vnfcProPortPoint, vnfcProPortSize);
											var vnfcProPortCornerSize = new Size(portCornerWidth, portCornerHeight);
											var vnfcProPortPath = new Path.Rectangle(vnfcProPortRectangle, vnfcProPortCornerSize);
											vnfcProPortPath.fillColor = proLinePortColor;
											
											//draw vnfc provider port ip address
											var vnfcProPortIpPoint = new Point(this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + portWidth + 70,
													this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + portHeight + portInterval);
        									var vnfcProPortIpText = new PointText(vnfcProPortIpPoint);
        									vnfcProPortIpText.content = this.COMStacks[comStacksNum].vm_config[vnfcNum].provider_ip_address;
        									vnfcProPortIpText.fillColor = proLinePortColor;
        									vnfcProPortIpText.fontSize = '15px'; 
        									
											//draw line between vnfc provider port and networks
			            					var providerline = new Path.Line([this.networkTopologyStartX + comStackTopoX + vnfcCOMStackX + vnfcWidth + portWidth,
			            					                          this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + portHeight + portInterval + portHeight / 2],
																	 [providerLineEndX,
																	  this.networkTopologyStartY + comStackTopoY + (comStackHeight + comStackInterval) * comStacksNum + vnfcCOMStackY + (vnfcHeight + vnfcInterval) * j + portHeight + portInterval + portHeight / 2]);
			            					providerline.strokeColor = proLinePortColor;
			            					providerline.strokeWidth = "3";
        									
        									j++;									//it's an important parameter~~~~~~~
    	            					}
                					}
                					comStackPath[comStacksNum].attach('mousemove' , function(){
                						indexOfcomStack = comStackPath.indexOf(this);
                						$("#menu").css({"top":canvasTop + comStackTopoY + (comStackHeight + comStackInterval) * indexOfcomStack + comStackMenuY, left:comStackTopoX + comStackWidth + canvasLeft - comStackMenuX, "position": "absolute"});
                						$("#menu").show();
                						$scope.selectedIns = instances[comStackPath.indexOf(this)];
                						$scope.selectedInsNum = comStackPath.indexOf(this);
                						
                						comStackPath[comStackPath.indexOf(this)].opacity = 0.6;
									});
                					comStackPath[comStacksNum].attach('mouseleave' , function(){
                						comStackPath[comStackPath.indexOf(this)].opacity = 1;
									});
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
            		var networks = {};
            		(function(){
            			for(var comstack in instances){
            				for(var vnfc in instances[comstack].vm_config){
            					var ipv4NetworkAddress = '';
            					var ipv6NetworkAddress = '';
            					var providerNetworkAddress = '';
            					var networkAddress = '';
            					if(instances[comstack].environment == "KVM"){
            						if(instances[comstack].vm_config[vnfc].ip_address){// for optional vm: qosac, hpsim
            								ipv4NetworkAddress = getNetworkAddress4HPSIM(instances[comstack].vm_config[vnfc].ip_address,instances[comstack].vm_config[vnfc].netmask);
            								if(!networks[ipv4NetworkAddress]){
                    					    	networks[ipv4NetworkAddress] = ipv4NetworkAddress;
            								}
            						}else{
            							for(var indexofNic = 0;indexofNic<instances[comstack].vm_config[vnfc].nic.length;indexofNic++){
            								ipv4NetworkAddress = getNetworkAddress(instances[comstack].vm_config[vnfc].nic[indexofNic].ip_v4.ipaddress,instances[comstack].vm_config[vnfc].nic[indexofNic].ip_v4.prefix);
            								if(!networks[ipv4NetworkAddress]){
                    					    	networks[ipv4NetworkAddress] = ipv4NetworkAddress;
            								}
            							}
            						}
            					}else {
            						networkAddress = instances[comstack].com_private_network.cidr;
            						providerNetworkAddress = getNetworkAddress4HPSIM(instances[comstack].vm_config[vnfc].provider_ip_address, instances[comstack].com_provider_network.netmask);
            						if(!networks[networkAddress] || !networks[providerNetworkAddress]){
                						networks[networkAddress] = networkAddress;
                						networks[providerNetworkAddress] = providerNetworkAddress;
                					}
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
            		var canvasHeight = networkTopology.COMStacks.length * 240;
            		//$("#canvas").css({"width":"1000" , "height":canvasHeight});
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
function getNetworkAddress4HPSIM(ipAddress,netmask){
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
function getNetworkAddress (ipAddress , prefix) {
	var ipAddressArr = [];
	var netmaskArr = [];
	var networkAddress = [];
	var netmask = getNetmask(prefix);
	ipAddressArr = ipAddress.split(".");
	netmaskArr = netmask.split(".");
	for(var i=0;i<ipAddressArr.length;i++){
		networkAddress.push(parseInt(ipAddressArr[i]) & parseInt(netmaskArr[i]));
	}
	return networkAddress.join(".");
}

function getNetmask(prefix){
	var quotient = parseInt(prefix / 8);
	var remainder = prefix % 8;
	var not255 = '';
	not255 = Math.pow(2, 8-remainder);
	var netmask = '';
	switch(quotient){
	case 0:
		netmask = not255 + ".0.0.0";
		break;
	case 1:
		netmask = "255." + not255 + ".0.0";
		break;
	case 2:
		netmask = "255.255." + not255 + ".0";
		break;
	case 3:
		netmask = "255.255.255." + not255;
		break;
	}
	return netmask;
}
function getPrefix(netmask){
	var netmaskArr = [];
	netmaskArr = netmask.split(".");
	var not255 = '';
	var all255Num = 0;
	var not255BinRev = [];
	var all1Numofnot255 = 0;
	var prefix = 0;
	for(var indexofNetmaskArr=0;indexofNetmaskArr<netmaskArr.length;indexofNetmaskArr++){
		if(netmaskArr[indexofNetmaskArr] != "255"){
			not255 = netmaskArr[indexofNetmaskArr];
			all255Num = parseInt(indexofNetmaskArr);
			break;
		}
	}
	var not255Bin = parseInt(not255,10).toString(2);
	not255BinRev = not255Bin.split("").reverse();
	for(var indexofBinRev=0;indexofBinRev<not255BinRev.length;indexofBinRev++){
		if(not255BinRev[indexofBinRev] != "0"){
			all1Numofnot255 = 8 - indexofBinRev;
			break;
		}
	}
	prefix = all255Num * 8 + all1Numofnot255;
	return prefix;
	
}
function getRandomColor(){ 
	return "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6); 
} 
