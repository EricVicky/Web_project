'use strict';
var rest=angular.module('rest', [ 'ngResource', ]);

rest.factory('OSService', function($location, $resource, $log) {
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
				}
			);
		},
		getSecurityGroupStore: function (success, error) {
			var securityGroupRes = $resource(restUrl + "nfv/os/compute/securitygroup/list");
			securityGroupRes.query(
				// success
				function (data) {
					$log.info(data);
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		getComTypeStore: function(success,error) {
			var comTypeRes = $resource(baseUrl + "data/comType.json");
			comTypeRes.query(
				// success
				function (data) {
					$log.info(data);
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		getTimezoneStore: function(success,error) {
			var timezoneRes = $resource(baseUrl + "data/timezone.json");
			timezoneRes.query(
				// success
				function (data) {
					$log.info(data);
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		getComputeAvailZoneStore: function(success,error) {
			var computeavailzoneRes = $resource(restUrl + "/nfv/os/compute/availabilityzone/list");
			computeavailzoneRes.query(
				// success
				function (data) {
					$log.info(data);
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		getNetworkStore: function(success,error) {
			var networkRes = $resource(restUrl + "/nfv/os/neutron/network/list");
			networkRes.query(
				// success
				function (data) {
					$log.info(data);
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		getSubNetworkStore: function(success,error) {
			var subNetworkRes = $resource(restUrl + "/nfv/os/neutron/"+"/subnet/list/names");
			subNetworkRes.query(
				// success
				function (data) {
					$log.info(data);
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		}
	};
});

rest.factory('KVMService', function($location, $resource, $log) {
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
				}
			);
		}
	};
});