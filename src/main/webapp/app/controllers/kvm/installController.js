angular.module('kvm', [ 'ui.router', 'ui.bootstrap', 'rcWizard',
		'rcForm',  'websocket', 'ghiscoding.validation', 'mgo-angular-wizard','ngResource']).controller('kvmctr', function($scope, $q, $timeout, $log, KVMService,
		$state, websocketService, validationService, WizardHandler) {
			var logviewer = $('#logviewer');
			var task = $('#task');
			$scope.editing = true;
			$scope.detaillog = false;
			$scope.user = {};
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
            $scope.nextstep = null;
            $scope.logtail = function(data){
        		$scope.socket = websocketService.connect("/oam", function(socket) {
        			socket.stomp.subscribe('/log/tail', $scope.showlog);
        		});
            }
            
            $scope.showDetailLog= function(){
            	$scope.detaillog= !$scope.detaillog;
            }
            
            $scope.showlog= function(data){
            //	$log.info(data);
            	var log =  JSON3.parse(data.body);
            	if( $scope.nextstep != log.step){
            		$scope.nextstep = log.step;
            		$scope.$apply(function(){
        				WizardHandler.wizard().next();
            		})
            	}
            	if(log.task!=null && log.task!=""){
            		task.text(log.task);
            	}
            	logviewer.append(log.logMsg + "\n");
            	logviewer.scrollTop(logviewer[0].scrollHeight - logviewer.height());
            }
			$scope.deploy = function (){
				if($scope.installConfig.comType=='FCAPS' || $scope.installConfig.comType=='OAM' || $scope.installConfig.comType=='CM' || $scope.installConfig.comType=='QoSAC'){
					$scope.installConfig.vm_config.oam.netmask = $scope.netmask;
					$scope.installConfig.vm_config.oam.gateway = $scope.gateway;
				}
				if($scope.installConfig.comType=='FCAPS' || $scope.installConfig.comType=='OAM' || $scope.installConfig.comType=='CM'){
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
                 			$scope.logtail(data); 
                 			$scope.editing = false;
                 			//$state.go("dashboard.installpregress");
            			}, 
            			function(response){
            					$log.info(response);
            			});
            };
            $scope.loadimglist = function(host, dir){
            	KVMService.imagelist(
            			{ "host":host, "dir":dir},
            			function(data) {
            				$log.info(data);
            				$scope.imagelist = data;
            				$scope.installConfig.oam_cm_image = $scope.imagelist[0];
            				$scope.installConfig.db_image = $scope.imagelist[1];
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            };
			(function (){
            	KVMService.getFlavorStore(
            			function(data) {
            				$scope.flavorStore = data.Flavors;
            			}, 
            			function(response){
            				$log.error(response);
            			});
            })();
            (function (){
            	KVMService.getComTypeStore(
            			function(data) {
            				$scope.comTypeStore = data;
            				$scope.installConfig.comType = $scope.comTypeStore[0].Name;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	KVMService.getTimezoneStore(
            			function(data) {
            				$scope.timezoneStore = data;
            				$scope.installConfig.timezone = $scope.timezoneStore[0].Time;
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
            (function (){
            	KVMService.hostips(
            			function(data) {
            				$log.info(data);
            				$scope.hostIPs = data;
            				$scope.installConfig.active_host_ip = $scope.hostIPs[0];
            			}, 
            			function(response){
            				$log.error(response);
            			}
            	);
            })();
} );



