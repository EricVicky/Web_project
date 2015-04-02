'use strict';

var directives = angular.module('yao.directives', []);

directives.directive('loading', function() {
    return {
        restrict: 'AE',
        replace: true,
        template: '<span class="loading"><img src="images/loading.gif"/></span>',
        link: function(scope, element, attr) {
            scope.$watch('loading', function(val) {
                if (val)
                    $(element).show();
                else
                    $(element).hide();
            });
        }
    }
});

directives.directive('installTemplate', function() {
    return {
        templateUrl: 'views/wizard/publish.html'
    };
});

directives.directive('mrfTemplate', function() {
    return {
        templateUrl: 'views/wizard/mrfWizard.html'
    };
});