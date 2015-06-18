'use strict';

/**
 * @ngdoc directive
 * @name izzyposWebApp.directive:adminPosHeader
 * @description
 * # adminPosHeader
 */
angular.module('comoamApp').directive('mutuexclude',function($log,KVMService){
    return {
        require: 'ngModel',
        link: function(scope, elem, attrs, ctrl) {
            var validateNotBothFilled = function(value) {
                var form = scope[attrs.form];
                var theOther = form[attrs.mutuexclude];
                var isNotValid = (value && value !== '' 
                	&& theOther.$modelValue && theOther.$modelValue !== '' && value == theOther.$modelValue );
                ctrl.$setValidity('samefieldvalue', !isNotValid);//set validity of the current element
                theOther.$setValidity('samefieldvalue', !isNotValid);//set validity of the other element.
                return value;
            };

            ctrl.$parsers.unshift(validateNotBothFilled);

        }
    };
});
