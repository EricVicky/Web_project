angular.module('kvm', [ 'ui.router',
                        'ui.bootstrap', 
                        'rcWizard',
                        'rcForm', 
                        'ghiscoding.validation',
                        'monitor',
                        'dashboard',
                        'sysconst']).controller('kvmctr', function($scope,  $log, KVMService,
           $state,  $dialogs, monitorService, timezoneService, $modal) {
			$scope.submitComtype = function(){
				$scope.loadimglist($scope.installConfig.active_host_ip, $scope.installConfig.vm_img_dir);
			};
			$scope.installConfig ={
					vm_img_dir : "/var/images"
					};
            $scope.genExport = function(){
            	$scope.export=!$scope.export;
            	var vms_flavor = $scope.flavorStore[$scope.installConfig.comType];
            	for(var name in vms_flavor){
            		var vm_flavor = vms_flavor[name];
            		for(var index in vm_flavor){
            			if(vm_flavor[index].label.indexOf($scope.flavor) != -1){
            				$scope.installConfig.vm_config[name].flavor = vm_flavor[index];
            			}
            		}
            	}
            };
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
            
            $scope.installConfig.vm_config = {
            		"oam": { "nic": []},
            		"cm" : { "nic": []},
            		"db" : { "nic": []}
            };
            
            $scope.nics = [ "eth0", "eth1", "eth2"];
            $scope.ntoptions  = [ {"label":"Simple", "mode": 1}, 
    	                             {"label":"Traffic Separation", "mode": 2 },
    	                             { "label":"Traffic Separation & Redundency", "mode": 3}];
            $scope.networktraffic = 1;
            $scope.avaliable_flavors = ["Enterprise", "Low End", "Medium", "High End"];
            $scope.flavor = $scope.avaliable_flavors[2];
            $scope.initNic = function(){
            	if($scope.installConfig.vm_config['oam'].flavor){
            		if($scope.installConfig.vm_config['oam'].flavor.label == 'Medium(6*24*150)'){
                		$scope.flavor = $scope.avaliable_flavors[2];
                	}else if($scope.installConfig.vm_config['oam'].flavor.label == 'Low End(4*16*80)'){
                		$scope.flavor = $scope.avaliable_flavors[1];
                	}else if($scope.installConfig.vm_config['oam'].flavor.label == 'High End(8*32*300)'){
                		$scope.flavor = $scope.avaliable_flavors[3];
                	}else{
                		$scope.flavor = $scope.avaliable_flavors[0];
                	}
            	}
            };
            
            	
            $scope.Backup_Server_Addr = function(){
            	var vm_config = $scope.installConfig.vm_config;
            	for(var vm in vm_config){
            		vm_config[vm].nic.length = $scope.networktraffic;
            	}
            	if($scope.installConfig.vm_config.oam.nic[0]!=null&&$scope.installConfig.vm_config.oam.nic[0].ip_v4!=null){
            		$scope.installConfig.app_install_options.SOFTWARE_SERVER_ADDRESS = $scope.installConfig.vm_config.oam.nic[0].ip_v4.ipaddress;
                    $scope.installConfig.app_install_options.BACKUP_SERVER_ADDRESS = $scope.installConfig.vm_config.oam.nic[0].ip_v4.ipaddress;
            	}
            };
            $scope.nicConfig = function(index, vm){
            	var modalInstance = $modal.open({
            	      animation: true,
            	      backdrop:'static',
            	      templateUrl: 'views/kvm/nicConfig.html',
            	      controller: 'nicctr',
            	      resolve: {
         		         config: function () {
         		        	 if($scope.installConfig.vm_config!=null){
         		        		return angular.copy($scope.installConfig.vm_config[vm].nic[index]);
         		        	 }
         		         },
            			 vm: function() {
            				 return vm;
            			 }
         		      },
            	});
            	modalInstance.result.then(function (item) {
            		$scope.installConfig.vm_config[vm].nic[index] = item;
            	}, function () {
    		    });
            };
			$scope.doDeploy = function (){
				$log.info($scope.installConfig);
				$scope.clean_dirty();
            	KVMService.deploy($scope.installConfig).then( function(){
            		monitorService.monitor("KVM", "INSTALL", $scope.installConfig.comType,  $scope.installConfig.deployment_prefix);
         			$state.go("dashboard.monitor");
        		});
            };
            $scope.clean_dirty = function(){
            	var vm_config = $scope.installConfig.vm_config;
				if($scope.installConfig.comType=='OAM'){
            		delete $scope.installConfig.vm_config['cm'];
            	}
				for(var vm in vm_config){
					if(vm_config[vm].nic.length == 0){
						delete vm_config[vm];
					}
				}
            };
            
            $scope.loadimglist = function(host, dir){
            	KVMService.imagelist( { "host":host, "dir":dir}).then(
            			function(data) {
            				$scope.imagelist = data;
            			});   
            };
            $scope.deploy = function(){
            	/*check if it is locked: prevent multiple operation on same comstack
            	 * it may cause log chaos on monitor page
            	 */
            	KVMService.comstackStatus($scope.installConfig.deployment_prefix).then(function(status){
            		var ACTION_IN_PROGRESS = 2;
            		if(status.state == ACTION_IN_PROGRESS){
            			if(window.confirm("some operation  proceed on selected VNF instance, go to monitor?")){
            				monitorService.monitor("KVM", "INSTALL", $scope.installConfig.comType,  $scope.installConfig.deployment_prefix);
            				$state.go('dashboard.monitor');
            			}
            		}else{
            			$scope.doDeploy();
            		}
            	});
            };

            KVMService.getFlavorStore().then( function(data) {
            				$scope.flavorStore = data.Flavors;
            			});
            KVMService.getComTypeStore().then(function(data){
            				$scope.comTypeStore = data.COMType;
            			 	$scope.installConfig.comType = KVMService.VNFType==''?$scope.comTypeStore[0].Name:KVMService.VNFType;
            			});
            timezoneService.timezonelist().then( function(data) {
            				$scope.timezoneStore = data;
            			});
            KVMService.hostips().then(function(data) {
            				$scope.hostIPs = data;
            			});

	  $scope.animationsEnabled = true;
	  $scope.NFVTypes = ["FCAPS", "CM", "OAM"];
	  $scope.open = function (size) {

		  var modalInstance = $modal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'views/common/NFVChooseModal.html',
		      controller: 'NFVChooseController',
		      size: size,
		      backdrop: true,
		      resolve: {
		         NFVTypes: function () {
		          return $scope.NFVTypes;
		        }
		      }
		    });
	
		    modalInstance.result.then(function (selectedItem) {
		      $scope.NFV = selectedItem;
		    }, function () {
		      $log.info('Modal dismissed at: ' + new Date());
		    });
	  };
	 //$scope.open('sm');	

})
.controller('NFVChooseController', function($scope, $modalInstance, NFVTypes ){
	$scope.NFVTypes = NFVTypes;
	$scope.NFV = $scope.NFVTypes[0];
	$scope.ok = function(){
		$modalInstance.close($scope.NFV);
	};
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
    };
})
.controller('nicctr', function($scope, $modalInstance,config,vm){
    $scope.ok = function(){
    	$scope.alert=true;
    	if($scope.nic.ip_v4!=null){
    		if(!$scope.nic.ip_v4.ipaddress||!$scope.nic.ip_v4.prefix||!$scope.nic.bridge){
    			return;
        	}else{
        		$scope.alert=false;
        		$modalInstance.close($scope.nic);
        	}
    	}
	};
	$scope.nic = config;
	$scope.oneAtATime = true;
	$scope.vm = vm;
	if($scope.nic){
		if($scope.nic.ip_v6){
			$scope.open = !status.open;
		}else{
			$scope.open = status.open; 
		}
	}
	
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
    };
});


