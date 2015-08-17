'use strict';

angular.module('treadstoneApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function (response) {
                var alertKey = response.headers('X-treadstoneApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, {param: response.headers('X-treadstoneApp-params')});
                }
                return response;
            },
        };
    });
