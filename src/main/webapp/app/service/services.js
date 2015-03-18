'use strict';
angular.module('rest', [ 'ngResource', ]);

angular.module('rest').factory('OSService', function($location, $resource, $log) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl + "rest/";
	var flavor = $resource(restUrl + "os/compute/flavor");
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		flavorlist: function () {
			return flavor.query();
		},
		flavor: function () {
			return flavor.get();
		},
	};
}).factory('KVMService', function($location, $resource, $log) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl + "rest/";
	var flavor = $resource(restUrl + "kvm/compute/flavor");
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		flavorlist: function () {
			return flavor.query();
		},
		flavor: function () {
			return flavor.get();
		},
	};
});