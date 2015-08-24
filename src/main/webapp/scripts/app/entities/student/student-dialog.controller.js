'use strict';

angular.module('treadstoneApp').controller('StudentDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Student', 'Major', 'User',
        function ($scope, $stateParams, $modalInstance, entity, Student, Major, User) {

            $scope.student = entity;
            $scope.majors = Major.query();
            $scope.users = User.query({filter: 'student-is-null'});
            $scope.load = function (id) {
                Student.get({id: id}, function (result) {
                    $scope.student = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('treadstoneApp:studentUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.student.id != null) {
                    Student.update($scope.student, onSaveFinished);
                } else {
                    Student.save($scope.student, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
