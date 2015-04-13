angular
  .module('comoamApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar', 
    'ghiscoding.validation',
    'pascalprecht.translate',
    'kvminstall', 'osinstall',  'rcWizard', 'mgo-angular-wizard',
                        'rcForm', 'rest' ,'login'
  ])
  .config(['$translateProvider', function ($translateProvider) {
	  $translateProvider.useStaticFilesLoader({
	    prefix: 'locales/validation/',
	    suffix: '.json'
		});

  	// load English ('en') table on startup
		$translateProvider.preferredLanguage('en');
	}])
	.config(['$translateProvider', function ($translateProvider) {
	  $translateProvider.useStaticFilesLoader({
	    prefix: 'locales/login/',
	    suffix: '.json'
		});

  	// load English ('en') table on startup
		$translateProvider.preferredLanguage('en');
	}])
  .config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider',function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider) {
    
    $ocLazyLoadProvider.config({
      debug:false,
      events:true,
    });

    $urlRouterProvider.otherwise('/dashboard/home');

    $stateProvider
      .state('dashboard', {
        url:'/dashboard',
        templateUrl: 'views/dashboard/main.html',
        resolve: {
            loadMyDirectives:function($ocLazyLoad){
                return $ocLazyLoad.load(
                {
                    name:'comoamApp',
                    files:[
                    'app/directives/header/header.js',
                    'app/directives/header/header-notification/header-notification.js',
                    'app/directives/sidebar/sidebar.js',
                    'app/directives/sidebar/sidebar-search/sidebar-search.js'
                    ]
                }),
                $ocLazyLoad.load(
                {
                   name:'toggle-switch',
                   files:["vendor/angular-toggle-switch/angular-toggle-switch.min.js",
                          "vendor/angular-toggle-switch/angular-toggle-switch.css"
                      ]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngAnimate',
                  files:['vendor/angular-animate/angular-animate.js']
                })
                $ocLazyLoad.load(
                {
                  name:'ngCookies',
                  files:['vendor/angular-cookies/angular-cookies.js']
                })
                $ocLazyLoad.load(
                {
                  name:'ngResource',
                  files:['vendor/angular-animate/angular-animate.js']
                })
                $ocLazyLoad.load(
                {
                  name:'ngSanitize',
                  files:['vendor/angular-sanitize/angular-sanitize.js']
                })
                $ocLazyLoad.load(
                {
                  name:'ngTouch',
                  files:['vendor/angular-touch/angular-touch.js']
                })
            }
        }
    })
      .state('dashboard.home',{
        url:'/home',
        controller: 'MainCtrl',
        templateUrl:'views/dashboard/home.html',
        resolve: {
          loadMyFiles:function($ocLazyLoad) {
            return $ocLazyLoad.load({
              name:'comoamApp',
              files:[
              'app/controllers/main.js',
              'app/directives/dashboard/stats/stats.js'
              ]
            })
          }
        }
      })
      .state('dashboard.kvminstall',{
        templateUrl:'views/kvm/install_kvm.html',
        url:'/kvminstall'
    })
      .state('dashboard.kvmupgrade',{
        templateUrl:'views/kvm/upgrade_kvm.html',
        url:'/kvmupgrade'
    })
    .state('dashboard.osinstall',{
        templateUrl:'views/os/install_os.html',
        url:'/osinstall'
    })
      .state('login',{
        templateUrl:'views/pages/login.html',
        url:'/login',
        controller: 'LoginController'
    })
  }])
.
controller('AppController', function($rootScope, $scope, $translate, $cookieStore, $window, $state, $location, $log, $modal, Auth, RestService) {
    // current user
    $rootScope._user = {
        username: $cookieStore.get("username") || "",
        password: "",
        rememberme: true,
        language: "en",
        reason: ""
    };

    $scope.lang = 'en-US';
    $scope.language = 'English';


    $rootScope.showNoPermission = function() {
        $modal.open({
            templateUrl: 'app/comApp/view/noPermission.html',
            controller: ModalInstanceCtrl,
            size: '',
            resolve: {
                node: function() {
                    return $scope.node;
                }
            }
        });
    }

    $rootScope.showError = function(errMsg) {
        alert(errMsg);
    }

    $(document).click(function() {
    	$cookieStore.put("lastClickedTime", new Date().getTime());
    	//$log.info("Clicked: " + $cookieStore.get("lastClickedTime"));
    });


    $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
        $log.info("State change from " + angular.toJson(fromState) + ", to " + angular.toJson(toState));
        $log.info("location=" + $location.path());

        if (toState.name === "noChange") {
            $log.info("Should keep the current state, no change");
            event.preventDefault();
        }
 
    	var currentURL = window.location.href;
        if ((!$cookieStore.get("token") || !$cookieStore.get("username")) && (currentURL.indexOf('#') != -1 || currentURL.indexOf("/login") != -1)) {
        	$(document).ready(function() {
            	//setTimeout('window.location.replace("' + currentURL.substring(0, currentURL.indexOf("#")) + '");', 1);
            	setTimeout('window.location.replace("' + currentURL.substring(0, currentURL.indexOf("#")) + '#/login");', 1);
        	});	
        }
    });


});


  
