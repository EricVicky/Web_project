'use strict';
angular.module('gr').factory('GRService', function($location, $q, $resource, $log, $http) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		getComInstance: function(success,error) {
			var comInstanceRes = $resource(restUrl + "rest/instances");
			return comInstanceRes.query().$promise;
		},
		install: function (config, success, error) {
			var grInstRes = $resource(restUrl + "rest/gr/kvm/install");
			grInstRes.save(config,
				function (data) {
					success(data);
				},
				function (response) {
					error(response);
				});
		},
		uninstall: function (config, success, error) {
			var grInstRes = $resource(restUrl + "rest/gr/kvm/uninstall");
			return grInstRes.save().$promise;
		}
	};
});

