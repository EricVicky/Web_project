angular.module('gr', [ 'ui.router',
                        'ui.bootstrap', 
                        'dialogs',
                        'rcWizard',
                        'rcForm', 
                        'ghiscoding.validation',
                        'ngResource'])
     .controller('grInstallController', function($scope, $q, $timeout, $log, 
            $state, GRService) {
    	 $scope.gr_traffic_types = [ {"label":"Simple", "value": "SIMPlE"}, 
    	                             {"label":"Traffic Separation", "value":"SEPARATION" },
    	                             { "label":"Traffic Redundency", "value": "REDUDENCY"}];
    	 $scope.ip_types = ["ipv4", "ipv6"];
    	 GRService.getComInstance().then(function( comstacks ){
    		 if(comstacks.length > 0){
    		    var instances = new Array();
    		    for(var i=0; i < comstacks.length; i++){
    		    	instances.push(JSON3.parse(comstacks[i].comConfig));
    		    }
    		    $scope.instances = instances;
    		 }
    	 }); 
    	 $scope.installGR = function() {
    		GRService.install(
    				$scope.gr_config, 
    				function(data){ 
    					$log.info(data)
    				}, 
    				function(data){ 
    						$log.info(data)
    			    } 
    		); 
    	 }
} );



