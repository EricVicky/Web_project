angular.module('os', [ 'ui.router', 
                       'ui.bootstrap', 
                       'rcWizard',
                       'rcForm',  
                       'websocket', 
                       'ghiscoding.validation', 
                       'mgo-angular-wizard',
                       'ngFileUpload',
                       'ngResource']).controller('osctr', function($scope, $q, $timeout, $log, OSService,
		$state, websocketService, validationService, WizardHandler,monitorService,timezoneService) {
            OSService.getUpdateOSCred().then(function(data) {
                $scope.crendential = data;
                if($scope.crendential.osUsername == "" && $scope.crendential.osPassword == ""){
                	alert("config first");
                	$state.go("dashboard.oscredential");
                }
            });
            $scope.heat_version = [ {"name":"juno", "version": "2014-10-16" }];
            $scope.config_drive = [ 'True', 'False' ];
            $scope.installConfig ={};
            
            $scope.changeComType = function(){
				$scope.installConfig.vm_config = null;
			}
            $scope.genExport = function(){
            	$scope.export=!$scope.export;
            }
            $scope.$watchGroup(['installConfig.root_password', 'installConfig.re_root_password','installConfig.axadmin_password','installConfig.re_axadmin_password'], function() {
            	if($scope.installConfig.root_password!=$scope.installConfig.re_root_password||$scope.installConfig.axadmin_password!=$scope.installConfig.re_axadmin_password){
            		$scope.disMatch = true;
            	}else{
            		$scope.disMatch = false;
            	}
            });
            
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
            		$scope.installConfig.com_provider_network.v6_subnet = '';
            		$scope.installConfig.com_provider_network.subnet = '';
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
            timezoneService.timezonelist().then( function(data) {
        		$scope.timezoneStore = data;
        	});
            OSService.getNetworkStore().then(function(data) {
            	$scope.networkStore = data;
			});
            OSService.getComputeAvailZoneStore().then(function(data) {
            	$scope.computeAvailZoneStore = data;
			});
            OSService.getcinderzones().then(function(data) {
            	$scope.blockAvailZoneStore = data;
			});
            OSService.getImages().then(function(data){
            	$scope.oam_cm_images = data;
            	$scope.db_images = data;
            });
            OSService.getKeys().then(function(data){
            	$scope.keys= data;
            });
            
} );
