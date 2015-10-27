'use strict';

angular.module('treadstoneApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('major', {
                parent: 'entity',
                url: '/majors',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Majors'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/major/majors.html',
                        controller: 'MajorController'
                    }
                },
                resolve: {}
            })
            .state('major.detail', {
                parent: 'entity',
                url: '/major/{id}',
                data: {
                    roles: ['ROLE_ADMIN'],
                    pageTitle: 'Major'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/major/major-detail.html',
                        controller: 'MajorDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Major', function ($stateParams, Major) {
                        return Major.get({id: $stateParams.id});
                    }]
                }
            })
            .state('major.new', {
                parent: 'major',
                url: '/new',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/major/major-dialog.html',
                        controller: 'MajorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {majorId: null, majorName: null, id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('major', null, {reload: true});
                        }, function () {
                            $state.go('major');
                        })
                }]
            })
            .state('major.edit', {
                parent: 'major',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/major/major-dialog.html',
                        controller: 'MajorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Major', function (Major) {
                                return Major.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('major', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
