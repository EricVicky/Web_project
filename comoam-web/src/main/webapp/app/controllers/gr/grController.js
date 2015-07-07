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
    		    $scope.COMStack = comstacks;
    		    $scope.instances = instances;
    		 }
    	 }); 
    	 $scope.primarycfg = function(){
    		 GRService.checkInstalled({"name":$scope.gr_config.pri.deployment_prefix}).then(function(data){
    			 $scope.installedPriGR = (data.succeed == true?"Installed":"Not Installed");
        	 });
    		 $scope.priOAMRowspan = $scope.gr_config.pri.vm_config.oam.nic.length * 2;
 	    	 $scope.priDBRowspan = $scope.gr_config.pri.vm_config.db.nic.length * 2;
 	    	 $scope.priCMRowspan = $scope.gr_config.pri.vm_config.cm.nic.length * 2;
    	 };
    	 $scope.secondarycfg = function(){
    		 GRService.checkInstalled({"name":$scope.gr_config.sec.deployment_prefix}).then(function(data){
    			 $scope.installedSecGR = (data.succeed == true?"Installed":"Not Installed");
        	 });
    		 $scope.secOAMRowspan = $scope.gr_config.sec.vm_config.oam.nic.length * 2;
 	    	 $scope.secDBRowspan = $scope.gr_config.sec.vm_config.db.nic.length * 2;
 	    	 $scope.secCMRowspan = $scope.gr_config.sec.vm_config.cm.nic.length * 2;
    	 };
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
	$scope.translateSelectedGR = function(){
		$scope.priOAMRowspan = $scope.gr_config.comConfig.vm_config.oam.nic.length * 2;
		$scope.priDBRowspan = $scope.gr_config.comConfig.vm_config.db.nic.length * 2;
		$scope.priCMRowspan = $scope.gr_config.comConfig.vm_config.cm.nic.length * 2;
	}
});



