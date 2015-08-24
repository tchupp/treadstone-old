'use strict';

angular.module('treadstoneApp')
    .controller('SemesterDetailController', function ($scope, $rootScope, $stateParams, entity, Semester) {
        $scope.semester = entity;
        $scope.load = function (id) {
            Semester.get({id: id}, function (result) {
                $scope.semester = result;
            });
        };
        $rootScope.$on('treadstoneApp:semesterUpdate', function (event, result) {
            $scope.semester = result;
        });
    });
