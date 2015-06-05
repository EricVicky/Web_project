'use strict';
angular.module('backup_restore').factory('Backup_ResService', function($location, $q, $resource, $log, $http) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		getComInstance: function(success,error) {
			var comInstanceRes = $resource(restUrl + "rest/kvm/instances");
			return comInstanceRes.query().$promise;
		},
		kvmbackup:function(config){
			var backupRes = $resource(restUrl + "rest/kvm/backup");
			return backupRes.save(config).$promise;
		},
		osbackup:function(config){
			var backupRes = $resource(restUrl + "rest/os/backup");
			return backupRes.save(config).$promise;
		},
		kvmrestore:function(config){
			var restoreRes = $resource(restUrl + "rest/kvm/restore");
			return restoreRes.save(config).$promise;
		},
		osrestore:function(config){
			var restoreRes = $resource(restUrl + "rest/os/restore");
			return restoreRes.save(config).$promise;
		},
	};
});

