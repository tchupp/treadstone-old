'use strict';

angular.module('treadstoneApp')
    .factory('Course', function ($resource, DateUtils) {
        return $resource('api/courses/:id', {}, {
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
