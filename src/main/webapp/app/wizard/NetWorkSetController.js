'use strict';

wizard.controller('NetWorkSetController', function($scope, $parse, $timeout, NetWorkService, SubNetWorkService) {

    $scope.splitText = function(string, nb) {
        if (string === undefined) {
            return false;
        }
        $scope.array = string.split(':');
        return $scope.array[nb];
    }

    $scope.getSubNets = function(network, type) {

        var obj = type + "SubNets";
        var model = $parse(obj);

        SubNetWorkService.initSubNets(network.id);
        SubNetWorkService.getSubNets().then(function(result) {
            $timeout(function() {
                model.assign($scope, result.data);
                $scope.$apply();
            });
        });
    }

    NetWorkService.initNetWorks();
    NetWorkService.getNetWorks().then(function(result) {
        $scope.netWorks = result.data;
    });
});