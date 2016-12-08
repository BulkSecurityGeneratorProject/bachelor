(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('green-house-managermySuffix', {
            parent: 'entity',
            url: '/green-house-managermySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.greenHouseManager.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/green-house-manager/green-house-managersmySuffix.html',
                    controller: 'GreenHouseManagerMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('greenHouseManager');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('green-house-managermySuffix-detail', {
            parent: 'entity',
            url: '/green-house-managermySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.greenHouseManager.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/green-house-manager/green-house-managermySuffix-detail.html',
                    controller: 'GreenHouseManagerMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('greenHouseManager');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'GreenHouseManager', function($stateParams, GreenHouseManager) {
                    return GreenHouseManager.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'green-house-managermySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('green-house-managermySuffix-detail.edit', {
            parent: 'green-house-managermySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/green-house-manager/green-house-managermySuffix-dialog.html',
                    controller: 'GreenHouseManagerMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GreenHouseManager', function(GreenHouseManager) {
                            return GreenHouseManager.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('green-house-managermySuffix.new', {
            parent: 'green-house-managermySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/green-house-manager/green-house-managermySuffix-dialog.html',
                    controller: 'GreenHouseManagerMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('green-house-managermySuffix', null, { reload: 'green-house-managermySuffix' });
                }, function() {
                    $state.go('green-house-managermySuffix');
                });
            }]
        })
        .state('green-house-managermySuffix.edit', {
            parent: 'green-house-managermySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/green-house-manager/green-house-managermySuffix-dialog.html',
                    controller: 'GreenHouseManagerMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GreenHouseManager', function(GreenHouseManager) {
                            return GreenHouseManager.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('green-house-managermySuffix', null, { reload: 'green-house-managermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('green-house-managermySuffix.delete', {
            parent: 'green-house-managermySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/green-house-manager/green-house-managermySuffix-delete-dialog.html',
                    controller: 'GreenHouseManagerMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GreenHouseManager', function(GreenHouseManager) {
                            return GreenHouseManager.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('green-house-managermySuffix', null, { reload: 'green-house-managermySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
