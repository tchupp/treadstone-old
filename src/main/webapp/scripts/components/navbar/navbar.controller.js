'use strict';

angular.module('treadstoneApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal) {
        $scope.isAdmin = false;
        Principal.isAdmin().then(function (result) {
            $scope.isAdmin = result;
        });

        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });
