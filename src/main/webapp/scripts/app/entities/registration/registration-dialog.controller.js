'use strict';

angular.module('treadstoneApp').controller('RegistrationDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Registration', 'Student', 'Course', 'Semester',
        function($scope, $stateParams, $modalInstance, entity, Registration, Student, Course, Semester) {

        $scope.registration = entity;
        $scope.students = Student.query();
        $scope.courses = Course.query();
        $scope.semesters = Semester.query();
        $scope.load = function(id) {
            Registration.get({id : id}, function(result) {
                $scope.registration = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('treadstoneApp:registrationUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.registration.id != null) {
                Registration.update($scope.registration, onSaveFinished);
            } else {
                Registration.save($scope.registration, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
