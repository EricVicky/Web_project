angular.module('os', [ 'ui.router', 
                       'ui.bootstrap', 
                       'rcWizard',
                       'rcForm',  
                       'websocket', 
                       'ghiscoding.validation', 
                       'mgo-angular-wizard',
                       'ngResource']).controller('osctr', function($scope, $q, $timeout, $log, OSService,
		$state, websocketService, validationService, WizardHandler) {
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
            
            $scope.deploy = function (){
            	OSService.deploy(
            			$scope.installConfig,
            			function(data){
            				$state.go("dashboard.monitor");
            			}, 
            			function(response){
            				$log.info(response);
            			});
            };
            $scope.$watch('installConfig.com_provider_network.network',function(){
            	if($scope.installConfig.com_provider_network!=null){
            		OSService.getSubnets($scope.installConfig.com_provider_network.network)
               	 	.then(function(data){
               	 		$scope.subNetworkStore = data;
               	 	});
            	}
            });
            OSService.getFlavorStore().then(function(data) {
            	$scope.flavorStore = data;
			});
            OSService.getComTypeStore().then(function(data) {
            	$scope.comTypeStore = data;
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
