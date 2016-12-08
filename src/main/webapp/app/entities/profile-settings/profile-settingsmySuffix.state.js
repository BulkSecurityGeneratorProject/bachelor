(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('profile-settingsmySuffix', {
            parent: 'entity',
            url: '/profile-settingsmySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.profileSettings.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profile-settings/profile-settingsmySuffix.html',
                    controller: 'ProfileSettingsMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profileSettings');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('profile-settingsmySuffix-detail', {
            parent: 'entity',
            url: '/profile-settingsmySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.profileSettings.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profile-settings/profile-settingsmySuffix-detail.html',
                    controller: 'ProfileSettingsMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profileSettings');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProfileSettings', function($stateParams, ProfileSettings) {
                    return ProfileSettings.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'profile-settingsmySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('profile-settingsmySuffix-detail.edit', {
            parent: 'profile-settingsmySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profile-settings/profile-settingsmySuffix-dialog.html',
                    controller: 'ProfileSettingsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProfileSettings', function(ProfileSettings) {
                            return ProfileSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profile-settingsmySuffix.new', {
            parent: 'profile-settingsmySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profile-settings/profile-settingsmySuffix-dialog.html',
                    controller: 'ProfileSettingsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                maxGroundHumidity: null,
                                minGrounHumidity: null,
                                minHumidity: null,
                                maxHumidity: null,
                                maxTemperature: null,
                                minTemperature: null,
                                startHour: null,
                                startMinute: null,
                                endMinute: null,
                                endHour: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('profile-settingsmySuffix', null, { reload: 'profile-settingsmySuffix' });
                }, function() {
                    $state.go('profile-settingsmySuffix');
                });
            }]
        })
        .state('profile-settingsmySuffix.edit', {
            parent: 'profile-settingsmySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profile-settings/profile-settingsmySuffix-dialog.html',
                    controller: 'ProfileSettingsMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProfileSettings', function(ProfileSettings) {
                            return ProfileSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profile-settingsmySuffix', null, { reload: 'profile-settingsmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profile-settingsmySuffix.delete', {
            parent: 'profile-settingsmySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profile-settings/profile-settingsmySuffix-delete-dialog.html',
                    controller: 'ProfileSettingsMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProfileSettings', function(ProfileSettings) {
                            return ProfileSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profile-settingsmySuffix', null, { reload: 'profile-settingsmySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
