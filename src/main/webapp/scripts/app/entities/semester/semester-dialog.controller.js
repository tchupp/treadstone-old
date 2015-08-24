'use strict';

angular.module('treadstoneApp').controller('SemesterDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Semester',
        function ($scope, $stateParams, $modalInstance, entity, Semester) {

            $scope.semester = entity;
            $scope.load = function (id) {
                Semester.get({id: id}, function (result) {
                    $scope.semester = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('treadstoneApp:semesterUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.semester.id != null) {
                    Semester.update($scope.semester, onSaveFinished);
                } else {
                    Semester.save($scope.semester, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
