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
		deploy: function (config, success, error) {
			var deployRes = $resource(restUrl + "os/deployment");
			deployRes.save(config,
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

rest.factory('authInterceptor', function($q, $window, $cookieStore) {
	return {
		request: function(config) {
			config.headers = config.headers || {};
			var token = $cookieStore.get("token");
			if (token) {
				config.headers.Authorization = token;
			}
			var username = $cookieStore.get("username");
			if (username) {
				config.headers.username = username;
			}
			return config;
		},
		response : function(response) {
			if (response.status === 401) {
				// handle the case where the user is not authenticated
			}
			return response || $q.when(response);
		}
	};
});

rest.factory('RestService', function($location, $resource, $log) {
	var baseUrl = $location.absUrl().split("#", 1)[0];
	var restUrl = baseUrl + "rest/";
	
	var loginRes = $resource(restUrl + "login", null,
		{
			'login': {method: 'POST'},
			'logout': {method: 'DELETE'}
		});
	

	return {
		baseUrl: baseUrl,
		restUrl: restUrl,
		login: function (user, success, error) {
			loginRes.login(user,
				// success
				function (data) {
					success(data);
				},
				// error
				function (response) {
					error(response);
				});
			},
		logout: function (user, success, error) {
			loginRes.logout(user,
				// success
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


rest.config(function($httpProvider) {
	$httpProvider.interceptors.push('authInterceptor');
});
