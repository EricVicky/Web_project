angular.module('kvm', [ 'ui.router',
                        'ui.bootstrap', 
                        'rcWizard',
                        'rcForm', 
                        'ghiscoding.validation',
                        'monitor',
                        'dashboard',
                        'ngResource']).controller('kvmctr', function($scope,  $log, KVMService,
           $state,  $dialogs, monitorService, $modal) {
			$scope.submitComtype = function(){
				$scope.loadimglist($scope.installConfig.active_host_ip, $scope.installConfig.vm_img_dir);
			};
			$scope.installConfig ={
					vm_img_dir : "/var/images"
					};
            $scope.changeComType = function(){
				$scope.installConfig.vm_config = null;
			};
            $scope.genExport = function(){
            	$scope.export=!$scope.export;
            };
            $scope.installConfig.app_install_options = {
					BACKUP_SERVER_DISK_SPACE:'20000',
					CALL_TRACE_DISK_SPACE:'1000',
					CODE_SERVER_DISK_SPACE:'20000',
					OMCCN_SUPPORT_WEBSSO_SANE:'false',
					NTP_SERVER:'135.251.111.73',
					SEC_UNIX_ENABLE:'No',
					OMCCN_SUPPORT_COM_GR:'false',
					OMCCN_SUPPORT_SP_FM:'Yes',
					OMCCN_SUPPORT_SP_PM:'Yes',
					OMCCN_SUPPORT_SP_HVP:'No'
			};
            
			$scope.doDeploy = function (){
				$scope.installConfig.vm_config.oam.netmask = $scope.installConfig.netmask;
				$scope.installConfig.vm_config.oam.gateway = $scope.installConfig.gateway;
				$scope.installConfig.vm_config.oam.v6_gateway = $scope.installConfig.v6_gateway;
				$scope.installConfig.vm_config.oam.v6_prefix= $scope.installConfig.v6_prefix;
				if($scope.installConfig.comType=='FCAPS' || $scope.installConfig.comType=='OAM' || $scope.installConfig.comType=='CM'){
					$scope.installConfig.vm_config.db.netmask = $scope.installConfig.netmask;
					$scope.installConfig.vm_config.db.gateway = $scope.installConfig.gateway;
					$scope.installConfig.vm_config.db.v6_gateway = $scope.installConfig.v6_gateway;
					$scope.installConfig.vm_config.db.v6_prefix= $scope.installConfig.v6_prefix;
				
				}
				if($scope.installConfig.comType=='FCAPS' || $scope.installConfig.comType=='CM'){
					$scope.installConfig.vm_config.cm.netmask = $scope.installConfig.netmask;
					$scope.installConfig.vm_config.cm.gateway = $scope.installConfig.gateway;
					$scope.installConfig.vm_config.cm.v6_gateway = $scope.installConfig.v6_gateway;
					$scope.installConfig.vm_config.cm.v6_prefix= $scope.installConfig.v6_prefix;
				}
				$scope.installConfig.netmask = null;
				$scope.installConfig.gateway = null;
            	KVMService.deploy($scope.installConfig).then( function(){
            		monitorService.monitorKVMInstall($scope.installConfig.active_host_ip);
         			$state.go("dashboard.monitor");
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
});


