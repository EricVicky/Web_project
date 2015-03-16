var app = angular.module('comoam', [ 'ui.router', 'ui.bootstrap', 'rcWizard',
		'rcForm', ]);
app.controller('kvmctr', function($scope, $q, $timeout) {
			$scope.user = {};
			$scope.user = {};
			$scope.saveState = function() {
				var deferred = $q.defer();
				$timeout(function() {
					deferred.resolve();
				}, 5000);
				return deferred.promise;
			};
			$scope.completeWizard = function() {
				alert('Completed!');
			}
			//$('#myModal').modal('show');
		} );
