'use strict';
angular.module('kvm').factory('KVMService', function($location, $q, $resource, $log, $http) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl;
	var pingcheck = function(host){
		var pingrul = "rest/check/ping";
		var deferred = $q.defer();
		$http.post(pingrul, host).success(function(data){
			deferred.resolve(data);
		}).error(function(response){
			deferred.reject(response)
		})
		return deferred.promise;
	};
	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		pingcheck: pingcheck,
		getFlavorStore: function () {
			var flavorRes = $resource(restUrl + "data/kvmflavor.json");
			return flavorRes.get().$promise;
		},
		images: function(success,error) {
			var imageRes = $resource(restUrl + "rest/kvm/images");
			return imageRes.query().$promise;
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
			return comTypeRes.query().$promise;
		},
		getTimezoneStore: function(success,error) {
			var timezoneRes = $resource(baseUrl + "data/timezone.json");
			return timezoneRes.query().$promise;
		},
		hostips: function(success,error) {
			var AcHostIPRes = $resource(restUrl + "rest/kvm/hostips");
			return AcHostIPRes.query().$promise;
		},
		imagelist: function(host ) {
			var OamCmImagesRes = $resource(restUrl + "rest/kvm/images");
			return OamCmImagesRes.query( host).$promise;
		},
		isLockedHost: function(host ) {
			var lockedHostRes = $resource(restUrl + "rest/check/lockedhost");
			return lockedHostRes.get( host).$promise;
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
			return comInstanceRes.query().$promise;
		}
	};
});

