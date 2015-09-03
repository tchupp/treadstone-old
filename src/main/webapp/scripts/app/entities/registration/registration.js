'use strict';

angular.module('treadstoneApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('registration', {
                parent: 'entity',
                url: '/registrations',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Registrations'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/registration/registrations.html',
                        controller: 'RegistrationController'
                    }
                },
                resolve: {}
            })
            .state('registration.detail', {
                parent: 'entity',
                url: '/registration/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Registration'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/registration/registration-detail.html',
                        controller: 'RegistrationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Registration', function ($stateParams, Registration) {
                        return Registration.get({id: $stateParams.id});
                    }]
                }
            })
            .state('registration.new', {
                parent: 'registration',
                url: '/new',
                data: {
                    roles: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/registration/registration-dialog.html',
                        controller: 'RegistrationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {id: null};
                            }
                        }
                    }).result.then(function (result) {
                            $state.go('registration', null, {reload: true});
                        }, function () {
                            $state.go('registration');
                        })
                }]
            })
            .state('registration.edit', {
                parent: 'registration',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/registration/registration-dialog.html',
                        controller: 'RegistrationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Registration', function (Registration) {
                                return Registration.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                            $state.go('registration', null, {reload: true});
                        }, function () {
                            $state.go('^');
                        })
                }]
            });
    });
