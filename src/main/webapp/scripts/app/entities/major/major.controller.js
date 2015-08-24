'use strict';

angular.module('treadstoneApp')
    .controller('MajorController', function ($scope, Major) {
        $scope.majors = [];
        $scope.loadAll = function () {
            Major.query(function (result) {
                $scope.majors = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Major.get({id: id}, function (result) {
                $scope.major = result;
                $('#deleteMajorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Major.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteMajorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.major = {majorId: null, majorName: null, id: null};
        };
    });
