'use strict';

angular.module('treadstoneApp')
    .controller('CourseController', function ($scope, Course) {
        $scope.courses = [];
        $scope.loadAll = function () {
            Course.query(function (result) {
                $scope.courses = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Course.get({id: id}, function (result) {
                $scope.course = result;
                $('#deleteCourseConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Course.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCourseConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.course = {courseId: null, courseName: null, credits: null, id: null};
        };
    });
