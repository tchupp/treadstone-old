'use strict';

angular.module('treadstoneApp')
    .controller('MajorDetailController', function ($scope, $rootScope, $stateParams, entity, Major) {
        $scope.major = entity;
        $scope.load = function (id) {
            Major.get({id: id}, function (result) {
                $scope.major = result;
            });
        };
        $rootScope.$on('treadstoneApp:majorUpdate', function (event, result) {
            $scope.major = result;
        });
    });
