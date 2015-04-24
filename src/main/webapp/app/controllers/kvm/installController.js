angular.module('kvm', [ 'ui.router',
                        'ui.bootstrap', 
                        'dialogs',
                        'rcWizard',
                        'rcForm', 
                        'ghiscoding.validation',
                        'monitor',
                        'ngResource']).controller('kvmctr', function($scope,  $log, KVMService,
           $state,  $dialogs, monitorService) {
			$scope.submitComtype = function(){
				$scope.loadimglist($scope.installConfig.active_host_ip.ip_address, $scope.installConfig.vm_img_dir);
			}
			$scope.imagelist= [];
			$scope.support_ars = [ 'True', 'False' ];
            $scope.installConfig ={
            		vm_img_dir:"/var/images",
            		vm_config: {
            		  oam:{
            		    ip_address: "10.223.0.50",
            		  },
            		  db:{
            		    ip_address: "10.223.0.54",
            		  },
            		  cm:{
            		    ip_address: "135.251.236.105",
            		  }
            		}
            };
            
			$scope.doDeploy = function (){
				$scope.installConfig.vm_config.oam.netmask = $scope.netmask;
				$scope.installConfig.vm_config.oam.gateway = $scope.gateway;
				if($scope.installConfig.comType='FCAPS' || $scope.installConfig.comType=='OAM' || $scope.installConfig.comType=='CM'){
					$scope.installConfig.vm_config.db.netmask = $scope.netmask;
					$scope.installConfig.vm_config.db.gateway = $scope.gateway;
				}
				if($scope.installConfig.comType=='FCAPS' || $scope.installConfig.comType=='CM'){
					$scope.installConfig.vm_config.cm.netmask = $scope.netmask;
					$scope.installConfig.vm_config.cm.gateway = $scope.gateway;
				}
				$scope.netmask = null;
				$scope.gateway = null;
            	KVMService.deploy(
                 		$scope.installConfig,
            			function(data){
            				monitorService.monitorKVMInstall($scope.installConfig.active_host_ip.ip_address);
                 			$state.go("dashboard.monitor");
            			}, 
            			function(response){
            					$log.info(response);
            			});
            };
            $scope.loadimglist = function(host, dir){
            	KVMService.imagelist( { "host":host, "dir":dir}).then(
            			function(data) {
            				$log.info(data);
            				$scope.imagelist = data;
            				$scope.installConfig.oam_cm_image = $scope.imagelist[0];
            				$scope.installConfig.db_image = $scope.imagelist[1];
            			}); 
            };
            $scope.deploy = function(){
            	KVMService.isLockedHost($scope.installConfig.active_host_ip).then(function(response){
            		//if the host is locked, then ask
            		if(response.succeed == true){
            			locked = true;
            			if(window.confirm("The installation proceed on selected Host, go to monitor?")){
            				KVMService.lockedHostStatus($scope.installConfig.active_host_ip).then(function(status){
            					if(status.lastAction == 'INSTALL'){
            						monitorService.monitorKVMInstall($scope.installConfig.active_host_ip.ip_address);
            					}else if(status.lastAction  =="UPGRADE"){
            						monitorService.monitorKVMUpgrade($scope.installConfig.active_host_ip.ip_address);
            					}
            					$state.go('dashboard.monitor');
            				})
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
            				$scope.comTypeStore = data;
            				$scope.installConfig.comType = $scope.comTypeStore[0].Name;
            			});
            KVMService.getTimezoneStore().then( function(data) {
            				$scope.timezoneStore = data;
            				$scope.installConfig.timezone = $scope.timezoneStore[0].Time;
            			});
            KVMService.hostips().then(function(data) {
            				$log.info(data);
            				$scope.hostIPs = data;
            				$scope.installConfig.active_host_ip = $scope.hostIPs[0];
            			});
} );



