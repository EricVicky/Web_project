'use strict';
angular.module('sysconst', [ 'ngResource' ]).factory('timezoneService', function($location, $q, $resource, $log) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	var VNFType = '';
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		timezonelist: function(success,error) {
			var timezoneRes = $resource(restUrl + "rest/sysinfo/timezone");
			return timezoneRes.query().$promise;
		}
	};
});

