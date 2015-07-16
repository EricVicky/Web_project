'use strict';
angular.module('gr').factory('GRService', function($location,  $q, $resource, $log, $http) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		getComInstance: function(success,error) {
			var comInstanceRes = $resource(restUrl + "rest/instances");
			return comInstanceRes.query().$promise;
		},
		kvminstall:function(config){
			var grInstRes = $resource(restUrl + "rest/gr/kvm/install");
			return grInstRes.save(config).$promise;
		},
		osinstall:function(config){
			var grInstRes = $resource(restUrl + "rest/gr/os/install");
			return grInstRes.save(config).$promise;
		},
		checkInstalled:function(name){
			var grinstalled = $resource(restUrl + "rest/gr/kvm/checkinstalled");
			return grinstalled.get(name).$promise;
		},
		kvmuninstall: function (config) {
			var grInstRes = $resource(restUrl + "rest/gr/kvm/uninstall");
			return grInstRes.save(config).$promise;
		},
		osuninstall: function (config) {
			var grInstRes = $resource(restUrl + "rest/gr/os/uninstall");
			return grInstRes.save(config).$promise;
		}
	};
});

