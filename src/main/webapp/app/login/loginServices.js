'use strict';

angular.module('login')
.factory('Auth', function($rootScope, $window, $cookieStore, $log, RestService) {
	function changeUser(data) {
		// copy user to currentUser
//		angular.extend($rootScope._user, user);
		$rootScope._user.username = data.username;
		$rootScope._user.password = data.password;
//		$rootScope._user.token = data.token;
		if (data && data.token && (!data.reason || data.reason == "1")) {
			$cookieStore.put("token", data.token);
		} else {
			$cookieStore.remove("token");
		}
//		$rootScope._user.reason = data.reason;
		
		$cookieStore.put('username', data.username);
		if ($rootScope._user.rememberme) {
			$cookieStore.put('password', data.password);
			$cookieStore.put('pwdEncrypted', 'true');
		} else {
			$cookieStore.remove('password');
			$cookieStore.remove('pwdEncrypted');
		}
		
		$cookieStore.put('rememberme', $rootScope._user.rememberme);
		$cookieStore.put('language', $rootScope._user.language);
	}
	
	function logoutUser(username) {
//		$rootScope._user.token = '';
		$cookieStore.remove("token");
		$cookieStore.remove('currentApp');
		$rootScope._user.reason = '';
	}
		
	return {
		authorize: function() {
			return "true";
		},
		isLoggedIn: function(user) {
//			if (user === undefined) {
//				user = currentUser;
//			}
				
			return true;
		},
		login: function(user, success, error) {
			RestService.login(user,
				function(data) {
					changeUser(data);
					success(data);
				},
				function(response) {
					$cookieStore.remove("token");
					error(response);
				});
		},
		logout: function(user, success, error) {
			RestService.logout(user,
				function(data) {
					logoutUser(user.username);
					success(data);
				},
				function() {
					logoutUser(user.username);
					error();
				});
		},
		encryptPwd: function (username, password) {
			if($cookieStore.get("pwdEncrypted") == "true" 
				&& $cookieStore.get('password') == password && $cookieStore.get('username') == username) {
				return password;
			}
			var key = username.length + "" + username.lastIndexOf(username.substring(0, 1)) + username;
	        if (key.length < 8) {
	            for (var i = 0; key.length < 8; i++) {
	            	key+=key.charAt(i);
	            }
	        }
			var keyHex = CryptoJS.enc.Utf8.parse(key);
		    var encrypted = CryptoJS.DES.encrypt(password, keyHex, {
		        mode: CryptoJS.mode.ECB,
		        padding: CryptoJS.pad.Pkcs7
		    });
		    
			var words = encrypted.ciphertext.words;
			var hexes = new Array ("0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f");
			var hex = "";
			for(var i = 0; i < words.length; i ++) {
				var word = words[i];
				var highNumber = (word & 0xf0000000) >> 28;
				if(highNumber < 0) {
					highNumber += 16;
				}
				hex += hexes[highNumber];
				hex += hexes[(word & 0xf000000) >> 24];
				hex += hexes[(word & 0xf00000) >> 20];
				hex += hexes[(word & 0xf0000) >> 16];
				hex += hexes[(word & 0xf000) >> 12];
				hex += hexes[(word & 0xf00) >> 8];
				hex += hexes[(word & 0xf0) >> 4];
				hex += hexes[word & 0xf];
			}
			return hex;
		}
//		user: currentUser
	};
});