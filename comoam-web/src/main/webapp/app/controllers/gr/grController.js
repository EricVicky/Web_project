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
    		    var Stack = new Array();
    		    for(var i=0; i < comstacks.length; i++){
    		    	instances.push(JSON3.parse(comstacks[i].comConfig));
    		    	Stack.push(comstacks[i]);
    		    }
    		    $scope.COMStack = Stack;
    		    $scope.instances = instances;
    		 }
    	 }); 
    	 $scope.$watch('gr_config.pri',function(){
    		 if($scope.gr_config!=null){
    			 GRService.checkInstalled({"name":$scope.gr_config.pri.deployment_prefix}).then(function(data){
        			 if(data.succeed){
        				 $scope.installedPriGR = "Installed";
        			 }else{
        				 $scope.installedPriGR = "Not Installed";
        			 }
            	 });
    		 }
    		 
    	 });
    	 $scope.$watch('gr_config.sec',function(){
    		 if($scope.gr_config!=null){
    			 GRService.checkInstalled({"name":$scope.gr_config.sec.deployment_prefix}).then(function(data){
        			 if(data.succeed){
        				 $scope.installedSecGR = "Installed";
        			 }else{
        				 $scope.installedSecGR = "Not Installed";
        			 }
            	 });
    		 }
    		 
    	 });
    	 $scope.installGR = function() {
    		 if($scope.COMStack.length > 0){
    			 for(var i=0; i < $scope.COMStack.length; i++){
     		    	if($scope.COMStack[i].name == $scope.gr_config.pri.deployment_prefix){
     		    		if($scope.COMStack[i].status == "GRINSTALLED"){
     		    			$scope.gr_config.gr_install_active = false;
     		    			GRService.install($scope.gr_config).then( function(){
     		    				monitorService.monitorKVMGR_Sec_Install($scope.gr_config.sec.active_host_ip);
     		    				$state.go("dashboard.monitor");
     		    			});
     		    		}else{
     		    			$scope.gr_config.gr_install_active = true;
     		    			GRService.install($scope.gr_config).then( function(){
     		    				monitorService.monitorKVMGR_Pri_Install($scope.gr_config.pri.active_host_ip);
     		    				$state.go("dashboard.monitor");
     		    			});
     		    		}
     		    	}
     		    }
    		 }
    	 }
    	 
}).controller('grUnInstallController', function($scope, $q, $timeout, $log, 
             $state, GRService,monitorService) {
	GRService.getComInstance().then(function( comstacks ){
		 if(comstacks.length > 0){
		    var instances = new Array();
		    var Stack = new Array();
		    for(var i=0; i < comstacks.length; i++){
		    	if(comstacks[i].status == "GRINSTALLED"){
		    		instances.push(JSON3.parse(comstacks[i].comConfig));
		    	}
		    }
		    $scope.instances = instances;
		 }
	 });
	$scope.UnInstallGR = function(){
		 GRService.uninstall($scope.gr_config).then( function(){
			monitorService.monitorKVMGR_UnInstall($scope.gr_config.comConfig.active_host_ip);
         	$state.go("dashboard.monitor");
		});
	 }
});



