'use strict';

angular.module('treadstoneApp')
    .controller('CourseDetailController', function ($scope, $rootScope, $stateParams, entity, Course) {
        $scope.course = entity;
        $scope.load = function (id) {
            Course.get({id: id}, function (result) {
                $scope.course = result;
            });
        };
        $rootScope.$on('treadstoneApp:courseUpdate', function (event, result) {
            $scope.course = result;
        });
    });
