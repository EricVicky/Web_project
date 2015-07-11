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
            };
            $scope.installConfig.app_install_options = {
					BACKUP_SERVER_DISK_SPACE:'2000',
					CALL_TRACE_DISK_SPACE:'1000',
					CODE_SERVER_DISK_SPACE:'2000',
					OMCCN_SUPPORT_WEBSSO_SANE:'false',
					NTP_SERVER:'135.251.111.73',
					SEC_UNIX_ENABLE:'NO',
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
					INSTALL_ETHEREAL:'NO'
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
            $scope.avaliable_flavors = ["Low End", "Medium", "High End"];
        	
            $scope.Backup_Server_Addr = function(){
            	var vm_config = $scope.installConfig.vm_config;
            	for(var vm in vm_config){
            		for(var indexofNic=0;indexofNic<vm_config[vm].nic.length;indexofNic++){
            			if( !vm_config[vm].nic[indexofNic] || !vm_config[vm].nic[indexofNic].bridge){
            				delete vm_config[vm].nic[indexofNic];
            				vm_config[vm].nic.length--;
            			}
            		}
            	}
            	if($scope.installConfig.vm_config.oam.nic[0]!=null&&$scope.installConfig.vm_config.oam.nic[0].ip_v4!=null){
            		$scope.installConfig.app_install_options.SOFTWARE_SERVER_ADDRESS = $scope.installConfig.vm_config.oam.nic[0].ip_v4.ipaddress;
                    $scope.installConfig.app_install_options.BACKUP_SERVER_ADDRESS = $scope.installConfig.vm_config.oam.nic[0].ip_v4.ipaddress;
                    $scope.oamRowspan = $scope.installConfig.vm_config.oam.nic.length * 2 + 2;
                	$scope.dbRowspan = $scope.installConfig.vm_config.db.nic.length * 2 + 2;
                	if($scope.installConfig.comType != "OAM"){
                		$scope.cmRowspan = $scope.installConfig.vm_config.cm.nic.length * 2 + 2;                		
                	}
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
            		monitorService.monitorKVMInstall($scope.installConfig.active_host_ip);
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
            				$log.info(data);
            				$scope.imagelist = data;
            			});   
            };
            $scope.deploy = function(){
            	var vms_flavor = $scope.flavorStore[$scope.installConfig.comType];
            	for(var name in vms_flavor){
            		var vm_flavor = vms_flavor[name];
            		for(var index in vm_flavor){
            			if(vm_flavor[index].label.indexOf($scope.flavor) != -1){
            				$scope.installConfig.vm_config[name].flavor = vm_flavor[index];
            			}
            		}
            	}
            	KVMService.isLockedHost($scope.installConfig.active_host_ip).then(function(response){
            		if(response.succeed == true){
            			locked = true;
            			if(window.confirm("The installation proceed on selected Host, go to monitor?")){
            				KVMService.lockedHostStatus($scope.installConfig.active_host_ip).then(function(status){
            					if(status.lastAction == 'INSTALL'){
            						monitorService.monitorKVMInstall($scope.installConfig.active_host_ip);
            					}else if(status.lastAction  =="UPGRADE"){
            						monitorService.monitorKVMUpgrade($scope.installConfig.active_host_ip);
            					}
            					$state.go('dashboard.monitor');
            				});
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
            			 	$scope.installConfig.comType = KVMService.VNFType;
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


