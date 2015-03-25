var app = angular.module('osinstall', [ 'ui.router', 'ui.bootstrap', 'rcWizard',
		'rcForm', 'rest' ]);

app.controller('osctr', function($scope, $q, $timeout, $log, OSService) {
			$scope.user = {};
			$scope.saveState = function() {
				var deferred = $q.defer();
				$timeout(function() { 
					deferred.resolve();
				}, 1000);
				return deferred.promise;
			};
			$scope.completeWizard = function() {
				alert("Completed!");
			}
            $scope.support_gr = [ 'Yes', 'No' ];
            $scope.heat_version = [ 'juno' , 'icehouse','havana'];
            $scope.config_drive = [ 'Yes', 'No' ];
            $scope.oamcm_image = [ 'Redhat+orac_client' ,'Redhat+orac_server' ];
            $scope.db_image = [ 'Redhat+orac_client', 'Redhat+orac_server'];
            $scope.private_network = [ 'True', 'False'];
			//$('#myModal').modal('show');
			(function (){
            	OSService.getFlavorStore(
            			function(data) {
            				$log.info(data);
            				$scope.flavorStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getSecurityGroupStore(
            			function(data) {
            				$log.info(data);
            				$scope.securityGroup = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getComTypeStore(
            			function(data) {
            				$log.info(data);
            				$scope.comTypeStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getTimezoneStore(
            			function(data) {
            				$log.info(data);
            				$scope.timezoneStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getNetworkStore(
            			function(data) {
            				$log.info(data);
            				$scope.networkStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getSubNetworkStore(
            			function(data) {
            				$log.info(data);
            				$scope.subNetworkStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	OSService.getComputeAvailZoneStore(
            			function(data) {
            				$log.info(data);
            				$scope.computeAvailZoneStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
} );
