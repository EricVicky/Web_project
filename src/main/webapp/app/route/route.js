'use strict';
angular.module('comoamApp')
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
                  name:'logview',
                  files:['app/directives/ansiblelog/logview.js']
                }),
                $ocLazyLoad.load(
                {
                  name:'hostcheck',
                  files:['app/directives/validation/hostcheck.js']
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
              'app/controllers/home/main.js',
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
      .state('dashboard.oscredential',{
        templateUrl:'views/os/credential.html',
        url:'/oscredential',
    })
  }])