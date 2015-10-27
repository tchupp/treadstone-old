'use strict';

angular.module('treadstoneApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('student', {
                parent: 'entity',
                url: '/students',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Students'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/student/students.html',
                        controller: 'StudentController'
                    }
                },
                resolve: {}
            })
            .state('student.detail', {
                parent: 'entity',
                url: '/student/{id}',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Student'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/student/student-detail.html',
                        controller: 'StudentDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Student', function ($stateParams, Student) {
                        return Student.get({id: $stateParams.id});
                    }]
                }
            })
            .state('student.new', {
                parent: 'student',
                url: '/new',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/student/student-dialog.html',
                        controller: 'StudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {studentId: null, id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('student', null, {reload: true});
                        }, function () {
                            $state.go('student');
                        })
                }]
            })
            .state('student.edit', {
                parent: 'student',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/student/student-dialog.html',
                        controller: 'StudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Student', function (Student) {
                                return Student.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('student', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
