'use strict';

wizard.controller('ImageSetController', function($scope, ImageService) {

    ImageService.initImages();
    ImageService.getImages().then(function(result) {
        $scope.images = result.data;
    });
});