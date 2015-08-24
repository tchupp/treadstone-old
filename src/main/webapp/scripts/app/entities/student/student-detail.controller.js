'use strict';

angular.module('treadstoneApp')
    .controller('StudentDetailController', function ($scope, $rootScope, $stateParams, entity, Student, Major, User) {
        $scope.student = entity;
        $scope.load = function (id) {
            Student.get({id: id}, function (result) {
                $scope.student = result;
            });
        };
        $rootScope.$on('treadstoneApp:studentUpdate', function (event, result) {
            $scope.student = result;
        });
    });
