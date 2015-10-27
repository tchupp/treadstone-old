'use strict';

angular.module('treadstoneApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('semester', {
                parent: 'entity',
                url: '/semesters',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Semesters'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/semester/semesters.html',
                        controller: 'SemesterController'
                    }
                },
                resolve: {}
            })
            .state('semester.detail', {
                parent: 'entity',
                url: '/semester/{id}',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Semester'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/semester/semester-detail.html',
                        controller: 'SemesterDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Semester', function ($stateParams, Semester) {
                        return Semester.get({id: $stateParams.id});
                    }]
                }
            })
            .state('semester.new', {
                parent: 'semester',
                url: '/new',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/semester/semester-dialog.html',
                        controller: 'SemesterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {semesterId: null, semesterName: null, id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('semester', null, {reload: true});
                        }, function () {
                            $state.go('semester');
                        })
                }]
            })
            .state('semester.edit', {
                parent: 'semester',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/semester/semester-dialog.html',
                        controller: 'SemesterDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Semester', function (Semester) {
                                return Semester.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('semester', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
