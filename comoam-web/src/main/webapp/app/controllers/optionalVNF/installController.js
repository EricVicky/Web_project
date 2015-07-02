angular.module('kvm').controller('ovmctr', function($scope,  $log, KVMService, monitorService, timezoneService, $state){
                        	
    $scope.installConfig ={
    		"vm_config":{
    			"ovm":{
    				"ipv6_address":"",
    				"ipv6_netmask":"",
    				"ipv6_gateway":""
    			}
    		}
    };    
    
    $scope.submitComtype = function(){
		$scope.loadimglist($scope.installConfig.active_host_ip, $scope.installConfig.vm_img_dir);
	};
	
	 $scope.loadimglist = function(host, dir){
     	KVMService.imagelist( { "host":host, "dir":dir}).then(
     			function(data) {
     				$log.info(data);
     				$scope.imagelist = data;
     			});   
     };

    
    KVMService.getFlavorStore().then( function(data) {
		$scope.flavorStore = data.Flavors;
	});
    
    KVMService.hostips().then(function(data) {
		$scope.hostIPs = data;
	});

    timezoneService.timezonelist().then( function(data) {
		$scope.timezoneStore = data;
	});
    
    KVMService.getComTypeStore().then(function(data){
		$scope.comTypeStore = data.OVMType;
	 	$scope.installConfig.comType = KVMService.VNFType;
	});
    
    $scope.deploy = function(){
    	
    	KVMService.isLockedHost($scope.installConfig.active_host_ip).then(function(response){
    		if(response.succeed == true){
    			locked = true;
    			if(window.confirm("The installation proceed on selected Host, go to monitor?")){
    				KVMService.lockedHostStatus($scope.installConfig.active_host_ip).then(function(status){
    					if(status.lastAction == 'INSTALL'){
    						monitorService.monitorKVMOVMInstall($scope.installConfig.active_host_ip);
    					}
    					$state.go('dashboard.monitor');
    				});
    			}
    		}else{
    			$scope.doDeploy();
    		}
    	});
    };
    
    $scope.doDeploy = function (){
    	KVMService.deployOVM($scope.installConfig).then( function(){
    		monitorService.monitorKVMOVMInstall($scope.installConfig.active_host_ip);
 			$state.go("dashboard.monitor");
		});
    };
    
    $scope.genExport = function (){
    	$scope.export = !$scope.export;
    }
    
});