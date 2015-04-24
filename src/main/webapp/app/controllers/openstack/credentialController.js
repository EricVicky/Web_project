angular.module('os').controller('credentialsController', function($scope, $q, $timeout, $log, OSService,
		$state,validationService) {
		$scope.crendential = {
				osUsername : "",
				osPassword : "",
				osTenant   : "",
				authURL    : "",
				osDomainName :""
		};
		$scope.submit = function(){
			OSService.updateOSCred($scope.crendential).then(function (data){
				$log.info(data);
			});
		};
		OSService.getUpdateOSCred().then(function(data) {
			$scope.crendential = data;
		});
} );
