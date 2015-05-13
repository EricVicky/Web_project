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
				$scope.loadimglist($scope.installConfig.active_host_ip, $scope.installConfig.vm_img_dir);
			}

			$scope.support_ars = [ 'True', 'False' ];

            $scope.changeComType = function(){
				$scope.installConfig.vm_config = null;
			}
            $scope.genExport = function(){
            	$scope.export=!$scope.export;
            }
			$scope.doDeploy = function (){
				$scope.installConfig.vm_config.oam.netmask = $scope.installConfig.netmask;
				$scope.installConfig.vm_config.oam.gateway = $scope.installConfig.gateway;
				if($scope.installConfig.comType=='FCAPS' || $scope.installConfig.comType=='OAM' || $scope.installConfig.comType=='CM'){
					$scope.installConfig.vm_config.db.netmask = $scope.installConfig.netmask;
					$scope.installConfig.vm_config.db.gateway = $scope.installConfig.gateway;
				}
				if($scope.installConfig.comType=='FCAPS' || $scope.installConfig.comType=='CM'){
					$scope.installConfig.vm_config.cm.netmask = $scope.installConfig.netmask;
					$scope.installConfig.vm_config.cm.gateway = $scope.installConfig.gateway;
				}
				$scope.installConfig.netmask = null;
				$scope.installConfig.gateway = null;
            	KVMService.deploy(
                 		$scope.installConfig,
            			function(data){
            				monitorService.monitorKVMInstall($scope.installConfig.active_host_ip);
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
            						monitorService.monitorKVMInstall($scope.installConfig.active_host_ip);
            					}else if(status.lastAction  =="UPGRADE"){
            						monitorService.monitorKVMUpgrade($scope.installConfig.active_host_ip);
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
            			});
            KVMService.getTimezoneStore().then( function(data) {
            				$scope.timezoneStore = data;
            			});
            KVMService.hostips().then(function(data) {
            				$scope.hostIPs = data;
            			});
} );



