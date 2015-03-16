'use strict';

app.controller('wizardController', function($scope) {

    $scope.data = [{
        name: "ZONE",
        params: {
            OAMzoneA: "#OAMzoneA:text",
            OAMzoneB: "#OAMzoneB:list"
        }
    }, {
        name: "Image",
        params: {
            imageA: "#imageA:list",
            imageB: "#imageB:list",
            imageC: "#imageC:list",
            imageD: "#imageD:list",
            imageE: "#imageE:list",
            imageF: "#imageF:list",
            imageG: "#imageG:list"
        }
    }];

    $scope.typeSplit = function(string, nb) {
        if (string === undefined) {
            return false;
        }
        $scope.array = string.split(':');
        return $scope.array[nb];
    }

    $.fn.wizard.logging = true;
    var wizard = $('#some-wizard').wizard({
        keyboard: false,
        contentHeight: 500,
        contentWidth: 900,
        backdrop: 'static'
    });

    wizard.setTitle("Install NE");

    wizard.on("submit", function(wizard) {
        setTimeout(function() {
            wizard.trigger("success");
            wizard.hideButtons();
            wizard._submitting = false;
            wizard.updateProgressBar(0);
            wizard.reset();
            wizard.hide();
        }, 2000);
    });

    $('#open-wizard').click(function(e) {
        e.preventDefault();
        wizard.show();
    });

});

app.directive('installTemplate', function() {
    return {
        templateUrl: 'views/wizard/publish.html'
    };
});