angular.module('monitor', [ 'ui.router',
                        'ui.bootstrap', 
                        'websocket', 
                        'mgo-angular-wizard',
                        'ngResource'])
     .controller('monitorController', function($scope, $q, $timeout, $log, 
            $state, websocketService,  WizardHandler, monitorService) {
			$scope.ansibleSteps = monitorService.getSteps();
			$scope.channel = monitorService.getChannel();
} );



