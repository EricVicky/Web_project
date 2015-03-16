'use strict';

wizard.controller('ZoneSetController', function($scope, ZoneService) {

    ZoneService.initZones();
    ZoneService.getZones().then(function(result) {
        $scope.zones = result.data;
    });
});