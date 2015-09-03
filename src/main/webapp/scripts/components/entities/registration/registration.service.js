'use strict';

angular.module('treadstoneApp')
    .factory('Registration', function ($resource, DateUtils) {
        return $resource('api/registrations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
