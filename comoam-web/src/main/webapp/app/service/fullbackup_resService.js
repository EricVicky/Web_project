'use strict';
angular.module('fullbackup_restore').factory('fullBackup_ResService', function($location, $q, $resource, $log, $http) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		getComInstance: function(success,error) {
			var comInstanceRes = $resource(restUrl + "rest/kvm/instances");
			return comInstanceRes.query().$promise;
		},
		kvmfullbackup:function(config){
			var backupRes = $resource(restUrl + "rest/kvm/backup");
			return backupRes.save(config).$promise;
		},
	};
});

