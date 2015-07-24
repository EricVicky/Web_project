angular.module('os').controller('ovmosctr', function($scope,  $log, OSService, monitorService, timezoneService, $state){
     
	$scope.genExport = function (){
    	$scope.export = !$scope.export;
    };
    
    $scope.heat_version = [ {"name":"juno", "version": "2014-10-16" }];
    $scope.installConfig ={};
    
    $scope.deploy = function (){
    	OSService.deployOVM($scope.installConfig).then( function(){
    		monitorService.monitor("Openstack", "INSTALL", $scope.installConfig.comType,  $scope.installConfig.stack_name);
			$state.go("dashboard.monitor");
		});
    };
    
    OSService.getComTypeStore().then(function(data){
		$scope.temp_comTypeStore = data.OVMType;
		var comTypeStore=[];
		for(var index in $scope.temp_comTypeStore){
			if($scope.temp_comTypeStore[index].Name=='ATC'||$scope.temp_comTypeStore[index].Name=='HPSIM'){
				comTypeStore.push($scope.temp_comTypeStore[index]);
			}
		}
		$scope.comTypeStore = comTypeStore;
	 	$scope.installConfig.comType = OSService.VNFType;
	});
 
    timezoneService.timezonelist().then( function(data) {
		$scope.timezoneStore = data;
	});   
   
    OSService.getNetworkStore().then(function(data) {
    	$scope.networkStore = data;
	});
    $scope.$watch('installConfig.com_provider_network.network',function(){
    	if($scope.installConfig.com_provider_network){
    		OSService.getV4Subnets($scope.installConfig.com_provider_network.network)
       	 	.then(function(data){
       	 		$scope.v4subnets = data;
       	 	});
    		OSService.getV6Subnets($scope.installConfig.com_provider_network.network)
       	 	.then(function(data){
       	 		$scope.v6subnets = data;
       	 	});
    	}
    });
    OSService.getFlavorStore().then(function(data) {
    	$scope.flavorStore = data;
	});
    OSService.getComputeAvailZoneStore().then(function(data) {
    	$scope.computeAvailZoneStore = data;
	});
    OSService.getcinderzones().then(function(data) {
    	$scope.blockAvailZoneStore = data;
	});
    OSService.getImages().then(function(data){
    	$scope.ovm_images = data;
    });
    OSService.getKeys().then(function(data){
    	$scope.keys= data;
    });
})
.controller('ovmosqosacctr', function($scope,  $log, OSService, monitorService, timezoneService, $state){
	
	OSService.getComTypeStore().then(function(data){
		$scope.temp_comTypeStore = data.OVMType;
		var comTypeStore=[];
		for(var index in $scope.temp_comTypeStore){
			if($scope.temp_comTypeStore[index].Name=='QOSAC'){
				comTypeStore.push($scope.temp_comTypeStore[index]);
			}
		}
		$scope.comTypeStore = comTypeStore;
	 	$scope.installConfig.comType = 'QOSAC';
	});
 
    timezoneService.timezonelist().then( function(data) {
		$scope.timezoneStore = data;
	});  
    
    OSService.getFlavorStore().then(function(data) {
    	$scope.flavorStore = data;
	});
    
    OSService.getNetworkStore().then(function(data) {
    	$scope.networkStore = data;
	});
    $scope.$watch('installConfig.com_provider_network.network',function(){
    	if($scope.installConfig.com_provider_network){
    		OSService.getV4Subnets($scope.installConfig.com_provider_network.network)
       	 	.then(function(data){
       	 		$scope.v4subnets = data;
       	 	});
    		OSService.getV6Subnets($scope.installConfig.com_provider_network.network)
       	 	.then(function(data){
       	 		$scope.v6subnets = data;
       	 	});
    	}
    });
    
    OSService.getComputeAvailZoneStore().then(function(data) {
    	$scope.computeAvailZoneStore = data;
	});
    OSService.getcinderzones().then(function(data) {
    	$scope.blockAvailZoneStore = data;
	});
    
    OSService.getImages().then(function(data){
    	$scope.ovm_images = data;
    });
    
    OSService.getKeys().then(function(data){
    	$scope.keys= data;
    });
    
    $scope.genExport = function (){
    	$scope.export = !$scope.export;
    };
    
    $scope.heat_version = [ {"name":"juno", "version": "2014-10-16" }];
    $scope.installConfig ={};
    
    $scope.installConfig.app_install_options = {
			BACKUP_SERVER_DISK_SPACE:'2000',
			CALL_TRACE_DISK_SPACE:'1000',
			CODE_SERVER_DISK_SPACE:'2000',
			OMCCN_SUPPORT_WEBSSO_SANE:'false',
			NTP_SERVER:'COM_LOCAL_CLOCK',
			SEC_UNIX_ENABLE:'YES',
			OMCCN_SUPPORT_COM_GR:'false',
			OMCCN_SUPPORT_SP_FM:'YES',
			OMCCN_SUPPORT_SP_PM:'YES',
			OMCCN_SUPPORT_SP_HVP:'NO',
			BACKUP_SERVER_IS_LOCAL:'YES',
			SOFTWARE_SERVER_IS_LOCAL:'YES',
			OMCCN_SUPPORT_3GPP:'true',
			OMCCN_SUPPORT_SNMP_N_ITF:'true',
			OMCCN_SUPPORT_GSST:'false',
			OMCCN_SUPPORT_NETRA:'false',
			INSTALL_ETHEREAL:'YES'
	};
    
    $scope.deploy = function (){
    	OSService.deployOVM($scope.installConfig).then( function(){
    		monitorService.monitor("Openstack", "INSTALL", $scope.installConfig.comType,  $scope.installConfig.stack_name);
			$state.go("dashboard.monitor");
		});
    };

});