'use strict';

var directives = angular.module('common', []);

directives.directive('loading', function() {
    return {
        restrict: 'AE',
        replace: true,
        template: '<span class="loading"><img src="images/loading_black.gif"/></span>',
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

