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
    	
    	/*check if it is locked: prevent multiple operation on same comstack
         * it may cause log chaos on monitor page
        */
        KVMService.comstackStatus($scope.installConfig.deployment_prefix).then(function(status){
            		var ACTION_IN_PROGRESS = 2;
            		if(status.state == ACTION_IN_PROGRESS){
            			if(window.confirm("some operation  proceed on selected VNF instance, go to monitor?")){
            				monitorService.monitor("KVM_OVM", status.lastaction, $scope.installConfig.deployment_prefix);
            				$state.go('dashboard.monitor');
            			}
            		}else{
            			$scope.doDeploy();
            		}
        });
    };
    
    $scope.doDeploy = function (){
    	KVMService.deployOVM($scope.installConfig).then( function(){
 			if($scope.installConfig.environment == "QOSAC"){
        		monitorService.monitorKVMQOSACInstall($scope.installConfig.deployment_prefix);
             	$state.go("dashboard.monitor");
        	}else{
        		monitorService.monitorKVMOVMInstall($scope.installConfig.deployment_prefix);
     			$state.go("dashboard.monitor");
        	}
		});
    };
    
    $scope.genExport = function (){
    	$scope.export = !$scope.export;
    }
    
});