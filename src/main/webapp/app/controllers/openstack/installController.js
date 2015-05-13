angular.module('os', [ 'ui.router', 
                       'ui.bootstrap', 
                       'rcWizard',
                       'rcForm',  
                       'websocket', 
                       'ghiscoding.validation', 
                       'mgo-angular-wizard',
                       'ngResource']).controller('osctr', function($scope, $q, $timeout, $log, OSService,
		$state, websocketService, validationService, WizardHandler) {
            $scope.heat_version = [ 'juno' , 'icehouse','havana'];
            $scope.config_drive = [ 'True', 'False' ];
            $scope.oam_cm_images = [ 'Redhat+orac_client' ,'Redhat+orac_server' ];
            $scope.db_images = [ 'Redhat+orac_client', 'Redhat+orac_server'];
            $scope.private_network = [ 'True', 'False'];
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
} );
