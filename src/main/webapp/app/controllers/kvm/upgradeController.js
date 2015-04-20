angular.module('kvm').controller('upgradectr', function($scope, $q, $timeout, $log, KVMService, websocketService, validationService) {
	var logviewer = $('#logviewer');
	var task = $('#task');
	var tasks = $('#tasks');
    var taskgroup = new Array();
	$scope.editing = true;
	$scope.detaillog = false;
	$scope.buttonlog = true;
	$scope.loadingshow = true;
	$scope.user = {};
	$scope.saveState = function() {
		var deferred = $q.defer();
		$timeout(function() { 
			deferred.resolve();
		}, 1);
		return deferred.promise;
	};
	$scope.completeWizard = function() {
		$scope.upgrade();
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
    $scope.showButton= function(){
    	$scope.buttonlog= !$scope.buttonlog;
    }
    
    $scope.showlog= function(data){
    	$log.info(data);
    	if(data.body == "end"){
    		$scope.$apply(function(){
    			$scope.loadingshow = false;
    		});
    		return;
    	}
    	var log =  JSON3.parse(data.body);
    	if( $scope.nextstep != log.step){
    		$scope.nextstep = log.step;
    		$scope.$apply(function(){
				WizardHandler.wizard().next();
    		})
    	}
    	if(log.task!=null && log.task!=""){
    		tasks.append("<i class=\"fa fa-check\" style=\"color:green\"></i>" + "&nbsp;&nbsp;" + log.task + "<br>");
    		taskgroup.push(log.task);
    		var taskhtml = "";
    		var startIndex = taskgroup.length >10?taskgroup.length-10:0;
    		for(var n=startIndex; n<taskgroup.length; n++){
            		taskhtml = taskhtml + "<i class=\"fa fa-check\" style=\"color:green\"></i>" + "&nbsp;&nbsp;" + taskgroup[n] + "<br>";
            }
    		task.html(taskhtml);
    	}
    	logviewer.append(log.logMsg + "<br>");
    	logviewer.scrollTop(logviewer[0].scrollHeight - logviewer.height());
    }
	$scope.loadimglist = function(host, dir) {
		KVMService.imagelist({
			"host" : host,
			"dir" : dir
		}, function(data) {
			$log.info(data);
			$scope.imagelist = data;
			$scope.$parent.oam_cm_image = $scope.imagelist[0];
			$scope.$parent.db_image = $scope.imagelist[1];
		}, function(response) {
			$log.error(response);
		});

	};
    $scope.reloadimglist = function(){
    	if($scope.com_instance != null){
        	$scope.installConfig = JSON3.parse($scope.com_instance.comConfig);
    	}
        $scope.vm_img_dir = $scope.installConfig.vm_img_dir;
    	$scope.loadimglist($scope.installConfig.active_host_ip.ip_address, $scope.vm_img_dir);
    }
	$scope.upgrade = function (){
		var installConfig = JSON3.parse($scope.com_instance.comConfig);
		installConfig.oam_cm_image = $scope.oam_cm_image;
		installConfig.db_image = $scope.db_image;
		installConfig.com_iso = $scope.com_iso;
		installConfig.vm_img_dir = $scope.vm_img_dir;
		KVMService.upgrade(
         		$scope.installConfig,
    			function(data){
         			$scope.logtail(data);
         			$scope.editing = false;
         			//$state.go("dashboard.upgradepregress");
    			}, 
    			function(response){
    					$log.info(response);
    			});
    };
    (function (){
    	KVMService.getComInstance(
    			function(data) {
    				$log.info(data);
    				$scope.comInstance = data;
    			}, 
    			function(response){
    				$log.error(response);
    			}
    	);
    })();

} );


