angular.module('login').controller('LoginController', function($rootScope, $scope, $cookieStore, $log, $state, $translate, $resource, $modal, dialogs, Auth, RestService, ComAppService) {
	// Get all the saved properties from cookie for the user
	$rootScope._user.username = $cookieStore.get('username') || '';
	$rootScope._user.password = $cookieStore.get('password') || '';
	
	var remMe = $cookieStore.get('rememberme');
	if ((typeof remMe) == 'boolean') {
		$rootScope._user.rememberme = remMe;
	} else {
		$rootScope._user.rememberme = true;
	}
	
	$rootScope._user.language = $cookieStore.get('language') || 'en';
	
	// LYY temp log
	$log.info("Current language is: " + $rootScope._user.language);
	
	$scope.languages = [
	    {
			id: "en",
			label: "English",
		},
		{
			id: "zh-cn",
			label: "\u7b80\u4f53\u4e2d\u6587",
		}];
	
	var getCurrentLanguage = function(languageId) {
		for (var i = 0; i < $scope.languages.length; i++) {
		    alert(i);
			if ($scope.languages[i].id == languageId) {
				$log.info("GOT the language, id is " + languageId + ", index is " + i);
				return $scope.languages[i];
			}
		}
		
		// set default to en
		$rootScope._user.language = 'en';
		return $scope.languages[0];
	};
	
	$scope.language = getCurrentLanguage($rootScope._user.language);
	$translate.use($rootScope._user.language);
	$log.info("Set the language to " + $rootScope._user.language);
	
	$scope.changeLanguage = function() {
		$rootScope._user.language = $scope.language.id;
		$translate.use($rootScope._user.language);
//		$state.go($state.$current, null, {reload: true});
	};
	
	// get the server info from local firstly,
	// if cannot find, assume that we're in COM server already!
	var set2UnknownServer = function() {
		$rootScope._server.name = "?";
		$rootScope._server.release = "?";
	};
	

	
	$scope.demoUser = "";
	$scope.demoPassword = "";
	
	RestService.getServerInfo(
	function(data) {
		// success
		$log.info(JSON.stringify(data));
		
		if (data.name && data.release) {
			$rootScope._server.name = data.name;
			$rootScope._server.release = data.release;
			$cookieStore.put("servername", data.name);
			$cookieStore.put("serverrelease", data.release);
			
			if (data.name === "duang") {
				$scope.demoUser = ': demo';
				$scope.demoPassword = ': demo';
			}
		} else {
			$log.error("Failed to get the server name and release!");
			set2UnknownServer();
		}
	},
	function(response) {
		$log.error("Failed to get response from server: " + JSON.stringify(response));
		set2UnknownServer();
	});
		
	$scope.login = function() {
		Auth.login({
			username: $rootScope._user.username,
			password: Auth.encryptPwd($rootScope._user.username, $rootScope._user.password)
		},
		function(data) {
			if (!data.reason) {
				$log.info("user " + $rootScope._user.username + " logged on successfully!");
				$rootScope.getAppList().$promise.then(function() {
					if ($rootScope._appList.length < 1) {
						$log.error("There's no application authorized for this user: " + $rootScope._user.username);
						return;
					}
					
					// get default app from cookie
					var defaultAppName = $cookieStore.get(ComAppService.getDefaultAppKey());
					var defaultApp;
					if (defaultAppName) {
						// get app from appList
						defaultApp = ComAppService.getAppFromRoot(defaultAppName);
					}
					
					$rootScope._application = defaultApp ? defaultApp : $rootScope._appList[0];
					
					$state.go($rootScope._application.state);
				});
				// TODO: set dashboard as the home page later on...
//				$state.go('app.networkManagement');
//				$state.go('app.appList');
				setTimeout($rootScope._heartbeat, 3000);
			} else {
				// failed to login, error case already handled 
				if(data.reason == 1) {
					$modal.open({
						templateUrl: 'app/csa/view/changePassword.html',
						controller: ChangePasswordCtrl,
						size: 'lg',
						resolve: {
							node : function () {
								return $scope.node;
							}
						}
					});
				}
				else {
					dialogs.notify("Error", data.reason);
				}
			}
		},
		function(response) {
			// Failed to login
			$rootScope._user.reason = response.status;
		});
	};
	
//	$scope.loginOauth = function(provider) {
//		$window.location.href = '/auth/' + provider;
//	};
	
	$scope.toggleRememberMe = function(event) {
		if (event.target.tagName == "LABEL") {
			$rootScope._user.rememberme = !$rootScope._user.rememberme;
		}
	};
});
