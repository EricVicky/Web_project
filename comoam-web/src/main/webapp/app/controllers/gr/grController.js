angular.module('gr', [ 'ui.router',
                        'ui.bootstrap', 
                        'dialogs',
                        'rcWizard',
                        'rcForm', 
                        'ghiscoding.validation',
                        'ngResource'])
     .controller('grInstallController', function($scope, $q, $timeout, $log, 
            $state, GRService,monitorService) {
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
    		GRService.install($scope.gr_config).then( function(){
    			monitorService.monitorKVMGR_Pri_Install($scope.gr_config.pri.active_host_ip);
             	$state.go("dashboard.monitor");
    		});
    	 }
    	 $scope.UnInstallGR = function(){
    		 GRService.uninstall($scope.gr_config).then( function(){
     			monitorService.monitorKVMGR_Pri_UnInstall($scope.gr_config.comConfig.active_host_ip);
              	$state.go("dashboard.monitor");
     		});
    	 }
} );



