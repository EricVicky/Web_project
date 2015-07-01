angular.module('os', [ 'ui.router', 
                       'ui.bootstrap', 
                       'rcWizard',
                       'rcForm',  
                       'websocket', 
                       'ghiscoding.validation', 
                       'mgo-angular-wizard',
                       'ngResource']).controller('osctr', function($scope, $q, $timeout, $log, OSService,
		$state, websocketService, validationService, WizardHandler,monitorService) {
            OSService.getUpdateOSCred().then(function(data) {
                $scope.crendential = data;
                if($scope.crendential.osUsername == "" && $scope.crendential.osPassword == ""){
                	alert("config first");
                	$state.go("dashboard.oscredential");
                }
            });
            $scope.heat_version = [ {"name":"juno", "version": "2014-10-16" },{"name": "icehouse", "version": "2013-05-23"}];
            $scope.config_drive = [ 'True', 'False' ];
            $scope.private_network = [ 'True', 'False'];
            $scope.blockAvailZoneStore = ['Nova', 'nova'];
            $scope.installConfig ={};
            
            $scope.changeComType = function(){
				$scope.installConfig.vm_config = null;
			}
            $scope.genExport = function(){
            	$scope.export=!$scope.export;
            }
            $scope.installConfig.app_install_options = {
					BACKUP_SERVER_DISK_SPACE:'20000',
					CALL_TRACE_DISK_SPACE:'1000',
					CODE_SERVER_DISK_SPACE:'20000',
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
			};
            $scope.Backup_Server_Addr = function(){
            	$scope.installConfig.app_install_options.SOFTWARE_SERVER_ADDRESS = $scope.installConfig.vm_config.oam.provider_ip_address;
                $scope.installConfig.app_install_options.BACKUP_SERVER_ADDRESS = $scope.installConfig.vm_config.oam.provider_ip_address;
            }
            $scope.deploy = function (){
            	OSService.deploy($scope.installConfig).then( function(){
            		monitorService.monitorOSInstall($scope.installConfig.stack_name);
    				$state.go("dashboard.monitor");
        		});
            };
            $scope.$watch('installConfig.com_provider_network.network',function(){
            	if($scope.installConfig.com_provider_network!=null){
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
            OSService.getComTypeStore().then(function(data) {
            	$scope.comTypeStore = data.COMType;
			 	$scope.installConfig.comType = OSService.VNFType;
			});
            OSService.getTimezoneStore().then(function(data) {
            	$scope.timezoneStore = data;
			});
            OSService.getNetworkStore().then(function(data) {
            	$scope.networkStore = data;
			});
            OSService.getComputeAvailZoneStore().then(function(data) {
            	$scope.computeAvailZoneStore = data;
			});
            OSService.getImages().then(function(data){
            	$scope.oam_cm_images = data;
            	$scope.db_images = data;
            });
            OSService.getKeys().then(function(data){
            	$scope.keys= data;
            });
            
} );
