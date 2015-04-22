'use strict';
angular.module('kvm').factory('KVMService', function($location, $q, $resource, $log, $http) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	var pingcheck = function(host){
		var pingrul = "rest/check/ping";
		var deferred = $q.defer();
		$http.get(pingrul, host).success(function(data){
			deferred.resolve(data);
		}).error(function(response){
			deferred.reject(response)
		})
		return deferred.promise;
	}
	var uniqueDeploy = function(name){
		var nameurl = "rest/kvm/check/unique";
		var deferred = $q.defer();
		$http.get(nameurl, name).success(function(data){
			deferred.resolve(data);
		}).error(function(response){
			deferred.reject(response)
		})
		return deferred.promise;
	}
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		pingcheck: pingcheck,
		uniqueDeploy: uniqueDeploy,
		getFlavorStore: function (success, error) {
			var flavorRes = $resource(restUrl + "data/kvmflavor.json");
			flavorRes.get(
				// success
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		images: function(success,error) {
			var imageRes = $resource(restUrl + "rest/kvm/images");
			imageRes.query(
				// success
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		deploy: function (config, success, error) {
			var deployRes = $resource(restUrl + "rest/kvm/deployment");
			deployRes.save(config,
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				});
		},
		getComTypeStore: function(success,error) {
			var comTypeRes = $resource(restUrl + "data/comType.json");
			comTypeRes.query(
				// success
				function (data) {
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
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		hostips: function(success,error) {
			var AcHostIPRes = $resource(restUrl + "rest/kvm/hostips");
			AcHostIPRes.query(
				// success
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		imagelist: function(host, success,error) {
			var OamCmImagesRes = $resource(restUrl + "rest/kvm/images");
			OamCmImagesRes.query(
				host,
				// success
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				}
			);
		},
		upgrade: function (config, success, error) {
			var upgradeRes = $resource(restUrl + "rest/kvm/upgrade");
			upgradeRes.save(config,
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				});
		},
		getComInstance: function(success,error) {
			var comInstanceRes = $resource(restUrl + "rest/kvm/instances");
			comInstanceRes.query(
				// success
				function (data) {
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

