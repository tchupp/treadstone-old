'use strict';

angular.module('treadstoneApp')
    .factory('Student', function ($resource, DateUtils) {
        return $resource('api/students/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    });
