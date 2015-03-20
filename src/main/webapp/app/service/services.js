'use strict';
angular.module('rest', [ 'ngResource', ]);

angular.module('rest').factory('OSService', function($location, $resource, $log) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl + "rest/";
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		getFlavorStore: function (success, error) {
			var flavorRes = $resource(restUrl + "nfv/os/compute/flavor/list");
			flavorRes.query(
				// success
				function (data) {
					$log.info(data);
					success(data);
				},
				// error
				function (response) {
					error(response);
				});
		}
	};
}).factory('KVMService', function($location, $resource, $log) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		getFlavorStore: function (success, error) {
			var flavorRes = $resource(restUrl + "data/kvmflavor.json");
			flavorRes.get(
				// success
				function (data) {
					$log.info(data);
					success(data);
				},
				// error
				function (response) {
					error(response);
				});
		}
	};
});