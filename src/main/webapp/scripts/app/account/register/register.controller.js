'use strict';

app.controller('RegisterController', function ($scope, $timeout, Auth) {
    $scope.success = null;
    $scope.error = null;
    $scope.doNotMatch = null;
    $scope.errorUserExists = null;
    $scope.registerAccount = {};
    $timeout(function () {
        angular.element('[ng-model="registerAccount.login"]').focus();
    });

    $scope.register = function () {
        if ($scope.registerAccount.password !== $scope.confirmPassword) {
            $scope.doNotMatch = 'ERROR';
        } else {
            $scope.registerAccount.langKey = 'en';
            $scope.doNotMatch = null;
            $scope.error = null;
            $scope.errorUserExists = null;
            $scope.errorEmailExists = null;
            $scope.errorStudentIdExists = null;

            Auth.createAccount($scope.registerAccount).then(function () {
                $scope.success = 'OK';
            }).catch(function (response) {
                $scope.success = null;
                if (response.status === 400 && response.data === 'login already in use') {
                    $scope.errorUserExists = 'ERROR';
                } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                    $scope.errorEmailExists = 'ERROR';
                }else if (response.status === 400 && response.data === 'student id already in use') {
                    $scope.errorStudentIdExists = 'ERROR';
                } else {
                    $scope.error = 'ERROR';
                }
            });
        }
    };
});
