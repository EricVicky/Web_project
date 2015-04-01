app.config(function($stateProvider, $urlRouterProvider, $locationProvider,
    $modalProvider) {
    $stateProvider.state('noChange', {
        url: '/#',
    }).state('login', {
        url: '/login',
        templateUrl: 'login.html'
    }).state('kvminstall', {
        url: '/kvminstall',
        templateUrl: 'views/kvm/install_kvm.html'
    }).state('osinstall', {
        url: '/osinstall',
        templateUrl: 'views/os/install_os.html'
    }).state('main', {
        url: '/main',
        templateUrl: 'main.html'
    });

    $locationProvider.html5Mode(false);
    $locationProvider.hashPrefix('');
});