'use strict';

angular.module('treadstoneApp')
    .controller('ConfigurationController', function ($scope, ConfigurationService) {
        ConfigurationService.get().then(function (configuration) {
            $scope.configuration = configuration;
        });
    });
