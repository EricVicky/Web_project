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
				$scope.deploy();
				alert('Completed!');
			}
            $scope.ahostIP="IPV4/IPV6";
            $scope.shostIP="IPV4/IPV6";
            $scope.com_types = [ 'FCAPS', 'QOSAC', 'CM' ,'OAM' ];
            $scope.gr_options = ['True' , 'False' ];
            (function (comType, vm){
            	OSService.getFlavorStore(
            			function(data) {
            				$log.info(data);
            				$scope.flavorStore = data;
            			}, 
            			function(response){
            				$log.error(response);
            			});
            })();
            $scope.timezones = [ 'Asia/Shanghai' , 'American/New York' , 'London'];
            $scope.oam_cm_images = [ 'Redhat+orac_client' ,'Redhat+orac_server' ];
            $scope.db_images = [ 'Redhat+orac_client', 'Redhat+orac_server'];
            $scope.installConfig = {
            };
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
} );
