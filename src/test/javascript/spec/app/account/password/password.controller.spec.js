'use strict';

describe('Password Controllers Test', function () {

    beforeEach(module('treadstoneApp'));

    var $scope, $httpBackend, q, Auth;

    beforeEach(function () {
        Auth = {
            changePassword: function () {
            }
        };
    });

    beforeEach(inject(function ($rootScope, $controller, $q, $injector) {
        $scope = $rootScope.$new();
        q = $q;
        $httpBackend = $injector.get('$httpBackend');
        $controller('PasswordController', {$scope: $scope, Auth: Auth});
    }));

    describe('PasswordController', function () {
        it('should show error if passwords do not match', function () {
            $scope.password = 'password1';
            $scope.confirmPassword = 'password2';

            $scope.changePassword();

            expect($scope.doNotMatch).toBe('ERROR');
        });
        it('should call Service and set OK on Success', function () {
            var pass = 'myPassword';
            $scope.password = pass;
            $scope.confirmPassword = pass;

            spyOn(Auth, 'changePassword').and.returnValue(new function () {
                var deferred = q.defer();
                $scope.error = null;
                $scope.success = 'OK';
                return deferred.promise;
            });

            $scope.changePassword();

            expect($scope.error).toBeNull();
            expect($scope.success).toBe('OK');
        });
    });
});
