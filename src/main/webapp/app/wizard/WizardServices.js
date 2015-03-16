'use strict';

var wizardService = angular.module('yao.wizard.services', ['yao.restURL']);

wizardService.service('VkeyPairService', function($http, RestService) {

    var restUrl = RestService.getRestUrl('/os/compute/keypair/list/names');

    this.keyPairData = {};

    this.initKeyPairs = function() {
            this.keyPairData = $http.post(restUrl);
        }
        // Get compute infos
    this.getKeyPairs = function() {
        return this.keyPairData;
    };
});

wizardService.service('ZoneService', function($http, RestService) {

    var restUrl = RestService.getRestUrl('/os/compute/availabilityzone/list/names');

    this.zoneData = {};

    this.initZones = function() {
            this.zoneData = $http.post(restUrl);
        }
        // Get compute infos
    this.getZones = function() {
        return this.zoneData;
    };
});

wizardService.service('FlavorService', function($http, RestService) {

    var restUrl = RestService.getRestUrl('/os/compute/flavor/list/names');

    this.flavorData = {};

    this.initFlavors = function() {
            this.flavorData = $http.post(restUrl);
        }
        // Get compute infos
    this.getFlavors = function() {
        return this.flavorData;
    };
});

wizardService.service('ImageService', function($http, RestService) {

    var restUrl = RestService.getRestUrl('/os/glance/image/list/names');

    this.imageData = {};

    this.initImages = function() {
            this.imageData = $http.post(restUrl);
        }
        // Get image infos
    this.getImages = function() {
        return this.imageData;
    };
});

wizardService.service('NetWorkService', function($http, RestService) {

    var restUrl = RestService.getRestUrl('/os/neutron/network/list/names');

    this.netWorkData = {};

    this.initNetWorks = function() {
            this.netWorkData = $http.post(restUrl);
        }
        // Get image infos
    this.getNetWorks = function() {
        return this.netWorkData;
    };
});

wizardService.service('SubNetWorkService', function($http, RestService) {

    this.subNetData = {};

    this.initSubNets = function(networkId) {
            var restUrl = RestService.getRestUrl('/os/neutron/' + networkId + '/subnet/list/names');
            this.subNetData = $http.post(restUrl);
        }
        // Get image infos
    this.getSubNets = function() {
        return this.subNetData;
    };
});

wizardService.service('NEWizard', function() {

    this.wizardObj = "";

    this.initWizard = function(ne_type) {
        $.fn.wizard.logging = false;
        var wizard = $('#' + ne_type).wizard({
            keyboard: false,
            increaseHeight: 80,
            contentHeight: 400,
            contentWidth: 900,
            backdrop: 'static'
        });

        wizard.setTitle(ne_type + " Wizard");
        this.wizardObj = wizard;
    }

    this.getWizard = function() {
        return this.wizardObj;
    };
});

wizardService.service('MRFInfos', function($rootScope) {

    $rootScope.csar_settings = {
        friendly_name: "",
        csar_operation: "",
        ne_type: "",
        ne_release: "",
        path: ""
    };

    $rootScope.os_settings = {
        os_username: "com-user1",
        os_password: "",
        os_tenant_name: "COM",
        os_region: "regionOne",
        os_auth_url: "http://135.111.74.75:5000/v2.0"
    };

    $rootScope.system_settings = {
        stack_prefix: "",
        key_name: "",
        media_target: "135.111.74.75",
        template_version: ""
    };

    $rootScope.nova_zone_settings = {
        nova_zone_1: "",
        nova_zone_2: ""
    };

    $rootScope.flavor_settings = {
        mrfc_flavor: "",
        mrfp_flavor: ""
    };

    $rootScope.image_settings = {
        mrfc_image: "",
        mrfp_image: ""
    };

    $rootScope.network_settings = {
        sig_network: "",
        sig_subnet: "",
        oam_network: "",
        oam_subnet: "",
        bearer_network: "",
        bearer_subnet: "",
        mngt_network: "",
        mngt_subnet: ""
    };

    $rootScope.result = {
        csar_settings: $rootScope.csar_settings,
        os_settings: $rootScope.os_settings,
        system_settings: $rootScope.system_settings,
        nova_zone_settings: $rootScope.nova_zone_settings,
        flavor_settings: $rootScope.flavor_settings,
        image_settrings: $rootScope.image_settings,
        network_settings: $rootScope.network_settings
    };
});