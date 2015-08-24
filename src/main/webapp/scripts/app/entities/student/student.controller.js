'use strict';

angular.module('treadstoneApp')
    .controller('StudentController', function ($scope, Student) {
        $scope.students = [];
        $scope.loadAll = function () {
            Student.query(function (result) {
                $scope.students = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Student.get({id: id}, function (result) {
                $scope.student = result;
                $('#deleteStudentConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Student.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteStudentConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.student = {studentId: null, id: null};
        };
    });
