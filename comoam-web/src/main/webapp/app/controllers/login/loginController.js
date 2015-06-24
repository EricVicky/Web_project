angular.module('login', [
	'ui.bootstrap',
	'ngCookies',
	'ngResource',
	'pascalprecht.translate',
	'auth'
])
.controller('LoginController', function($rootScope, $scope, $cookieStore, $log, $state, $translate, $resource, $modal, Auth) {
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
		    //alert(i);
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
	$scope.login = function() {
		Auth.login({
			username: $rootScope._user.username,
			password: $rootScope._user.password
		},
		function(data) {
			if (!data.reason) {
				$log.info("user " + $rootScope._user.username + " logged on successfully!");
				$state.go('dashboard.home');
			} else {
					alert("Error - "+ data.reason);
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
}).controller('LogoutController', function($rootScope, $scope, $cookieStore, $log, $state, $translate, $resource, Auth) {
	$scope.logout = function() {
	Auth.logout(
		function(data) {
			$cookieStore.remove("token");
            $cookieStore.remove('currentApp');
            $rootScope._user.reason = '';
            $rootScope.auth = null;
            $rootScope._entityList = null;
			$state.go('login');
		},
		function(response) {
			// Failed to login
			$log.info("failed to log out!");
		});
	};
}).controller('LanguageController', function($scope, $translate){
    $scope.changeLanguage = function (langKey) {
        $translate.use(langKey);
      };
});