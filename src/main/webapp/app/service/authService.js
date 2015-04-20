'use strict';
angular.module('auth', [ 'ngResource' ]).factory('authInterceptor', function($q, $window, $cookieStore) {
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

angular.module("auth").factory('authService', function($location, $resource, $log) {
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


