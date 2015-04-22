angular.module('kvm', [ 'ui.router',
                        'ui.bootstrap', 
                        'rcWizard',
                        'rcForm', 
                        'websocket', 
                        'ghiscoding.validation',
                        'mgo-angular-wizard',
                        'ngResource']).controller('kvmctr', function($scope, $q, $timeout, $log, KVMService,
           $state, websocketService, validationService, WizardHandler) {
			$scope.ansibleSteps = ["Start", "Generate Config Driver", "Start VM instance", "Prepare Install Options",  "Finished"];
			$scope.editing = true;
			$scope.submitComtype = function(){
				$scope.loadimglist($scope.installConfig.active_host_ip.ip_address, $scope.installConfig.vm_img_dir);
			}
			$scope.imagelist= [];
			$scope.completeWizard = function() {
				$scope.deploy();
			};
			$scope.support_ars = [ 'True', 'False' ];
            $scope.installConfig ={
            		vm_img_dir:"/var/images",
            		vm_config: {
            		  oam:{
            		    ip_address: "10.223.0.50",
            		  },
            		  db:{
            		    ip_address: "10.223.0.54",
            		  },
            		  cm:{
            		    ip_address: "135.251.236.105",
            		  }
            		}
            };
            
			$scope.deploy = function (){
				$scope.installConfig.vm_config.oam.netmask = $scope.netmask;
				$scope.installConfig.vm_config.oam.gateway = $scope.gateway;
				if($scope.installConfig.comType='FCAPS' || $scope.installConfig.comType=='OAM' || $scope.installConfig.comType=='CM'){
					$scope.installConfig.vm_config.db.netmask = $scope.netmask;
					$scope.installConfig.vm_config.db.gateway = $scope.gateway;
				}
				if($scope.installConfig.comType=='FCAPS' || $scope.installConfig.comType=='CM'){
					$scope.installConfig.vm_config.cm.netmask = $scope.netmask;
					$scope.installConfig.vm_config.cm.gateway = $scope.gateway;
				}
				$scope.netmask = null;
				$scope.gateway = null;
            	KVMService.deploy(
                 		$scope.installConfig,
            			function(data){
                 			$scope.editing = false;
            			}, 
            			function(response){
            					$log.info(response);
            			});
            };
            $scope.loadimglist = function(host, dir){
            	KVMService.imagelist( { "host":host, "dir":dir}).then(
            			function(data) {
            				$log.info(data);
            				$scope.imagelist = data;
            				$scope.installConfig.oam_cm_image = $scope.imagelist[0];
            				$scope.installConfig.db_image = $scope.imagelist[1];
            			}); 
            };
            KVMService.getFlavorStore().then( function(data) {
            				$scope.flavorStore = data.Flavors;
            			});
            KVMService.getComTypeStore().then(function(data){
            				$scope.comTypeStore = data;
            				$scope.installConfig.comType = $scope.comTypeStore[0].Name;
            			});
            KVMService.getTimezoneStore().then( function(data) {
            				$scope.timezoneStore = data;
            				$scope.installConfig.timezone = $scope.timezoneStore[0].Time;
            			});
            KVMService.hostips().then(function(data) {
            				$log.info(data);
            				$scope.hostIPs = data;
            				$scope.installConfig.active_host_ip = $scope.hostIPs[0];
            			});
} );



