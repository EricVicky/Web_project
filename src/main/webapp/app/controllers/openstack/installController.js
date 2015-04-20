angular.module('openstack', [ 'ui.router', 'ui.bootstrap', 'rcWizard', 'rcForm' ]).controller('osctr', function($scope, $q, $timeout, $log, OSService) {
			$scope.user = {};
			$scope.saveState = function() {
				var deferred = $q.defer();
				$timeout(function() { 
					deferred.resolve();
				}, 1000);
				return deferred.promise;
			};
			$scope.completeWizard = function() {
				$scope.deploy();
				alert('Completed!');
			}
            $scope.heat_version = [ 'juno' , 'icehouse','havana'];
            $scope.config_drive = [ 'True', 'False' ];
            $scope.oamcm_images = [ 'Redhat+orac_client' ,'Redhat+orac_server' ];
            $scope.db_images = [ 'Redhat+orac_client', 'Redhat+orac_server'];
            $scope.private_network = [ 'True', 'False'];
            $scope.installConfig ={};
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
