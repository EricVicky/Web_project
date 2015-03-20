var app = angular.module('osinstall', [ 'ui.router', 'ui.bootstrap', 'rcWizard',
		'rcForm', 'rest' ]);

angular.module("osinstall").directive({
	  'rcStep1': function () {
		    return {
		      restrict: 'A',
		      require: ['^rcWizard', '?form', '?rcSubmit'],
		      link: function (scope, element, attributes, controllers) {
		        
		        var wizardController = controllers[0];
		        
		        // find all the optional controllers for the step
		        var formController = controllers.length > 1 ? controllers[1] : null;
		        var submitController = controllers.length > 2 ? controllers[2] : null;
		        
		        // add the step to the wizard controller
		        var step = wizardController.addStep({ 
		          'element': element, 
		          'attributes': attributes, 
		          'formController': formController,
		          'submitController': submitController });
		      }
		    };
		  }
});

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
				alert('Completed!');
			}
            $scope.ahostIP="IPV4/IPV6";
            $scope.shostIP="IPV4/IPV6";
            $scope.com_types = [ 'FCAPS', 'QOSAC', 'CM' ,'OAM' ];
            $scope.gr_options = ['Yes' , 'No' ];
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
			//$('#myModal').modal('show');
} );
