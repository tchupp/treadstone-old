'use strict';

angular.module('treadstoneApp')
    .controller('SemesterController', function ($scope, Semester) {
        $scope.semesters = [];
        $scope.loadAll = function () {
            Semester.query(function (result) {
                $scope.semesters = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Semester.get({id: id}, function (result) {
                $scope.semester = result;
                $('#deleteSemesterConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Semester.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteSemesterConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.semester = {semesterId: null, semesterName: null, id: null};
        };
    });
