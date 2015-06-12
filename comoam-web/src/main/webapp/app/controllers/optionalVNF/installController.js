angular.module('kvm').controller('ovmctr', function($scope,  $log, KVMService, monitorService, $state){
                        	
    $scope.installConfig ={};                    	
    
    KVMService.getFlavorStore().then( function(data) {
		$scope.flavorStore = data.Flavors;
	});
    
    KVMService.hostips().then(function(data) {
		$scope.hostIPs = data;
	});
    
    $scope.deploy = function(){
    	if(KVMService.VNFType=='HPsim'){
    		$scope.installConfig.vm_config.imgname='hpsim_new.c.qcow2';
    	}else if(KVMService.VNFType=='ATC'){
    		$scope.installConfig.vm_config.imgname='atc.c.qcow2';
    	}
    	
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
    
});