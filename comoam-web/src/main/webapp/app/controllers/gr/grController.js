angular.module('gr', [ 'ui.router',
                        'ui.bootstrap', 
                        'dialogs',
                        'rcWizard',
                        'rcForm', 
                        'ghiscoding.validation',
                        'ngResource'])
     .controller('grInstallController', function($scope, $q, $timeout, $log, 
            $state, GRService,monitorService) {
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
    	 $scope.invalideIPv6 = false;
    	 $scope.all_gr_traffic_types = [ {"label":"Simple", "value": "SIMPlE", "index": 0}, 
    	                                 {"label":"Traffic Separation", "value":"SEPARATION", "index" :1},
    	                                 { "label":"Traffic Redundency", "value": "REDUDENCY", "index" :2}];
    	 $scope.grTrafficTypeMap = {
    			 "SIMPlE":"1",
    			 "SEPARATION":"2",
    			 "REDUDENCY":"3"
    	 }
    	 $scope.getIPNum = function(gr_traffic){
    		 for(var indexofTypes=0;indexofTypes<$scope.all_gr_traffic_types.length;indexofTypes++){
    			 if($scope.all_gr_traffic_types[indexofTypes].value == gr_traffic){
    				 return $scope.all_gr_traffic_types[indexofTypes].index;
    			 }
    		 }
    	 }
    	 $scope.primarycfg = function(){
    		 GRService.checkInstalled({"name":$scope.gr_config.pri.deployment_prefix}).then(function(data){
    			 $scope.installedPriGR = (data.succeed == true?"Installed":"Not Installed");
        	 });
    		 $scope.priOAMRowspan = $scope.gr_config.pri.vm_config.oam.nic.length * 2;
 	    	 $scope.priDBRowspan = $scope.gr_config.pri.vm_config.db.nic.length * 2;
 	    	 if($scope.gr_config.pri.comType != "OAM"){
 	    		 $scope.priCMRowspan = $scope.gr_config.pri.vm_config.cm.nic.length * 2; 	    		 
 	    	 }
 	      	 
    	 };
    	 $scope.secondarycfg = function(){
    		 $scope.secOAMRowspan = $scope.gr_config.sec.vm_config.oam.nic.length * 2;
 	    	 $scope.secDBRowspan = $scope.gr_config.sec.vm_config.db.nic.length * 2;
 	    	 if($scope.gr_config.sec.comType != "OAM"){
 	    		 $scope.secCMRowspan = $scope.gr_config.sec.vm_config.cm.nic.length * 2; 	    		 
 	    	 }
 	      	 
    	 };
    	 
    	 $scope.gr_IP = {"pri":{}, "sec": {}};
    	 $scope.gr_ip_setup = function(role){
    		 var vm_config = $scope.gr_config[role].vm_config;
    		 var IPNum =  $scope.getIPNum($scope.gr_config.gr_traffic);
    		 for(var vm in vm_config){
    			 //single vm comfig
    			 var cfg = vm_config[vm];
    			 for(var i =0; i <= IPNum; i++){
    				 if(i==0){
    					 $scope.gr_IP[role][vm] = [];
    				 }
    				 if($scope.gr_config.gr_ip_type == 'ipv4'){
    					 $scope.gr_IP[role][vm].push(cfg.nic[i].ip_v4.ipaddress);
    				 }else{
    					 if(cfg.nic[i].ip_v6){
    						 $scope.gr_IP[role][vm].push(cfg.nic[i].ip_v6.ipaddress);
    					 }
    				 }
    			 }
    		 }
    		 
    	 };
    	 $scope.gr_ip_changed = function(){
    		 if($scope.gr_config.gr_traffic && $scope.gr_config.gr_ip_type){
    			 $scope.gr_ip_setup('pri');
    			 $scope.gr_ip_setup('sec');
    		 }
    		 $log.info($scope.gr_IP);
    		 
    		 if($scope.gr_config.gr_ip_type == 'ipv6'){
    			 for(var role in $scope.gr_IP){
    				 for(var vm in $scope.gr_IP[role]){
        				 if($scope.gr_IP[role][vm].length != $scope.grTrafficTypeMap[$scope.gr_config.gr_traffic]){
        					 $scope.invalideIP = true;
        					 return;
        				 }
        			 }
    			 }
    		 }else{
    			 $scope.invalideIP = false;
    		 }
    	 };
    	 $scope.getMinTraffic = function(){
    		 var priNic = $scope.gr_config.pri.vm_config.oam.nic.length;
    		 var secNic = $scope.gr_config.sec.vm_config.oam.nic.length;
    		 var minNic = priNic<secNic?priNic:secNic;
    		 $scope.gr_traffic_types = [minNic];
    		 for(var i=0;i<minNic;i++){
    			 $scope.gr_traffic_types[i] = $scope.all_gr_traffic_types[i];
    		 }
    	 }
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
             $state, GRService,monitorService, $modal) {
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
	 };
	$scope.translateSelectedGR = function(){
			$scope.priOAMRowspan = $scope.gr_config.comConfig.vm_config.oam.nic.length * 2;
			$scope.priDBRowspan = $scope.gr_config.comConfig.vm_config.db.nic.length * 2;
			$scope.priCMRowspan = $scope.gr_config.comConfig.vm_config.cm.nic.length * 2;
	}
	$scope.changeForce = function(){
		if($scope.gr_config.forced){
		      var modalInstance = $modal.open({
		      animation: $scope.animationsEnabled,
		      templateUrl: 'views/gr/forceConfirm.html',
		      controller: 'forceController',
		      size: "sm",
		      backdrop: true
		    });	
		    modalInstance.result.then(function () {
		       $log.info("force confirmed!"); 
		    }, function () {
		    	$scope.gr_config.forced = false;
		    });	
		}
	}
})
.controller('forceController', function($scope, $modalInstance){
	$scope.ok = function(){
		$modalInstance.close();
	};
	$scope.cancel = function () {
		$modalInstance.dismiss('cancel');
    };
});
;




