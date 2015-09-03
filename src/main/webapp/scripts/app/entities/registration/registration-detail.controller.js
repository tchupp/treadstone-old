'use strict';

angular.module('treadstoneApp')
    .controller('RegistrationDetailController', function ($scope, $rootScope, $stateParams, entity, Registration, Student, Course, Semester) {
        $scope.registration = entity;
        $scope.load = function (id) {
            Registration.get({id: id}, function(result) {
                $scope.registration = result;
            });
        };
        $rootScope.$on('treadstoneApp:registrationUpdate', function(event, result) {
            $scope.registration = result;
        });
    });
