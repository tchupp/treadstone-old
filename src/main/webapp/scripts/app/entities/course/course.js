'use strict';

angular.module('treadstoneApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('course', {
                parent: 'entity',
                url: '/courses',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Course List'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/course/courses.html',
                        controller: 'CourseController'
                    }
                },
                resolve: {}
            })
            .state('course.detail', {
                parent: 'entity',
                url: '/course/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Course'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/course/course-detail.html',
                        controller: 'CourseDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Course', function ($stateParams, Course) {
                        return Course.get({id: $stateParams.id});
                    }]
                }
            })
            .state('course.new', {
                parent: 'course',
                url: '/new',
                data: {
                    roles: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/course/course-dialog.html',
                        controller: 'CourseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {courseId: null, courseName: null, credits: null, id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('course', null, {reload: true});
                        }, function () {
                            $state.go('course');
                        })
                }]
            })
            .state('course.edit', {
                parent: 'course',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/course/course-dialog.html',
                        controller: 'CourseDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Course', function (Course) {
                                return Course.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('course', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
