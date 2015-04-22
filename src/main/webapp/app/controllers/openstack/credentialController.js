angular.module('os').controller('credentialsController', function($scope, $q, $timeout, $log, OSService,
		$state,validationService) {
		$scope.crendential = {
				osUsername : "jeff",
				osPassword : "newsys"
				}
		$scope.submit = function(){
			OSService.updateOSCred($scope.crendential);
		}
} );
