'use strict';

angular.module('treadstoneApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
