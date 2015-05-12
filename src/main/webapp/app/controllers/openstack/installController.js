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
//			$scope.saveState = function() {
//				var deferred = $q.defer();
//				$timeout(function() { 
//					deferred.resolve();
//				}, 1000);
//				return deferred.promise;
//			};
            $scope.deploy = function (){
            	OSService.deploy(
            			$scope.installConfig,
            			function(data){
            				$log.info(data);
            			}, 
            			function(response){
            				$log.info(response);
            			});
            };
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
            OSService.getSubNetworkStore().then(function(data) {
            	$scope.subNetworkStore = data;
			});
            OSService.getComputeAvailZoneStore().then(function(data) {
            	$scope.computeAvailZoneStore = data;
			});
} );
