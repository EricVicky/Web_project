paper.install(window);
window.onload = function(){
	paper.setup('canvas');
	//class Network
	function Network(networkIP){
		this.networkIP = networkIP;
		this.networkandlineColor = ["pink","yellow","green","purple","red","blue","black"];					//the color of network and the line between network and port
	}
	
	//class Port
	function Port(IP){
		this.IP = IP;
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
	NetworkTopology.prototype = {
		constructor : NetworkTopology,
		drawNetworktopology : function(){
			
			//drawNetworks
			var networkTopologyX = 200;					//distance between network and topology on direction X
			var networkTopologyY = 20;					//distance between network and topology on direction Y
			var networkWidth = 700;
			var networkHeight = 20;
			var networkCornerWidth = 10;
			var networkCornerHeight = 10;
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
										         this.networkTopologyStartY + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork);
				var netWorkNameText = new PointText(netWorkNamePoint);
				netWorkNameText.content = this.Networks[numberofNetwork].networkIP;
				netWorkNameText.fillColor = this.Networks[numberofNetwork].networkandlineColor[numberofNetwork];
				netWorkNameText.fontSize = '15px';
			}
			
			//draw COMStack
			
			//draw COM outline
			var COMStackTopologyX = 150;				//distance between COMStack and topology on direction X
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
			COMStackPath.fillColor = { hue: 90, saturation: 1, brightness: 1 };
			//draw host
			var hostCOMStackX = 50;						//distance between host and COMStack on direction X, it's value should equal VNFCCOMX
			var hostCOMStackY = 250;					//distance between host and COMStack on direction Y
			var hostCOMWidth = 700;
			var hostCOMHeight = 80;
			var hostCornerWidth = 30;
			var hostCornerHeight = 30;
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
			hostPath.fillColor='blue';
			
			//draw VNFCs
			var VNFCCOMStackX = 50;						//distance between first VNFC and COMStack on direction X 
			var VNFCCOMStackY = 150;					//distance between first VNFC and COMStack on direction Y
			var VNFCInterval = 50;						//interval between VNFCs
			var VNFCWidth = 200;		
			var VNFCHeight = 80;
			var VNFCCornerWidth = 30;
			var VNFCCornerHeight = 30;
			var portWidth = 20;
			var portHeight = 10;
			var portCornerWidth = 3;
			var portCornerHeight = 3;
			var portInterval = 50;						//interval between ports
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
					VNFCPath.fillColor='purple';
					
					var VNFCNamePoint = new Point(this.networkTopologyStartX + COMStackTopologyX + VNFCCOMStackX + (VNFCWidth + VNFCInterval) * numberofVNFC + VNFCCornerWidth,
												  this.networkTopologyStartY + COMStackNetworkInterval + networkTopologyY + (networkHeight + networkInterval) * numberofNetwork + VNFCCOMStackY + VNFCCornerHeight);
					var VNFCNameText = new PointText(VNFCNamePoint);
					VNFCNameText.content = this.COMStacks[numberofCOMStack].VNFCs[numberofVNFC].VNFCType;
					VNFCNameText.fillColor = 'black';
					VNFCNameText.fontSize = '30px';
					
					for(var numberofPort=0;numberofPort<this.COMStacks[numberofCOMStack].VNFCs[numberofVNFC].ports.length;numberofPort++){
						//draw port : move down to use the color of the line
						
						//draw line between port and network
						var lineEndY = '';				//the Y coordinate of the line between port and network
						var lineColor = '';				//the color of the line between port and network
						for(var numberofNetwork2=0;numberofNetwork2<this.Networks.length;numberofNetwork2++){
							if(this.COMStacks[numberofCOMStack].VNFCs[numberofVNFC].ports[numberofPort].IP == this.Networks[numberofNetwork2].networkIP){
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
	
	var network1 = new Network('1.1.1.1');
	var network2 = new Network('2.2.2.2');
	var network3 = new Network('3.3.3.3');
	
	var port1 = new Port('1.1.1.1');
	var port2 = new Port('2.2.2.2');
	var port3 = new Port('1.1.1.1');
	var port4 = new Port('3.3.3.3');
	
	var vnfc1 = new VNFC('CM');
	var vnfc2 = new VNFC('DB');
	var vnfc3 = new VNFC('OAM');
	//alert(vnfc1.VNFCType);
	vnfc1.addPort(port1);
	vnfc1.addPort(port2);
	vnfc2.addPort(port3);
	vnfc3.addPort(port2);
	vnfc3.addPort(port4);
	
	var comStack1 = new COMStack('FCAPS','Meng','0.0.0.0');
	//var comStack2 = new COMStack(20,20,'AAA');
	//alert(comStack1.COMStackType);
	comStack1.addVNFC(vnfc1);
	comStack1.addVNFC(vnfc2);
	comStack1.addVNFC(vnfc3);
	//alert(comStack1.VNFCs[1].VNFCType);
	
	var networkTopology = new NetworkTopology(5,5);
	networkTopology.addNetwork(network1);
	networkTopology.addNetwork(network2);
	networkTopology.addNetwork(network3);
	networkTopology.addCOMStack(comStack1);
	
	networkTopology.drawNetworktopology();
}