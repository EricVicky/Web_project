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
		kvmbackup: function (config, success, error) {
			var backupRes = $resource(restUrl + "rest/kvm/backup");
			backupRes.save(config,
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				});
		},
		osbackup: function (config, success, error) {
			var backupRes = $resource(restUrl + "rest/os/backup");
			backupRes.save(config,
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				});
		},
	};
});

