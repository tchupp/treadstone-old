'use strict';

angular.module('treadstoneApp')
    .factory('Semester', function ($resource, DateUtils) {
        return $resource('api/semesters/:id', {}, {
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
