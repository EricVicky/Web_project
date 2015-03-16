'use strict';

wizard.controller('SystemSetController', function($scope,$rootScope,VkeyPairService,MRFInfos) {

    VkeyPairService.initKeyPairs();
    VkeyPairService.getKeyPairs().then(function(result) {
        $scope.keyPairs = result.data;
    });

    $scope.template_versions = [{
        label: "Icehouse",
        value: "2013-05-23"
    }, {
        label: "Juno",
        value: "2014-10-16"
    }];

    $scope.prefix_status = true;
    document.getElementById("stack_prefix").disabled = $scope.prefix_status;

    $scope.changePrefixStatus = function() {
        $scope.prefix_status = !$scope.prefix_status;
        document.getElementById("stack_prefix").disabled = $scope.prefix_status;
    }
});