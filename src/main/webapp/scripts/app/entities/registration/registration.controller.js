'use strict';

angular.module('treadstoneApp')
    .controller('RegistrationController', function ($scope, Registration) {
        $scope.registrations = [];
        $scope.loadAll = function() {
            Registration.query(function(result) {
               $scope.registrations = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Registration.get({id: id}, function(result) {
                $scope.registration = result;
                $('#deleteRegistrationConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Registration.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteRegistrationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.registration = {id: null};
        };
    });
