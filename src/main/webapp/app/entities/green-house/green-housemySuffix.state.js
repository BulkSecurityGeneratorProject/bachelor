(function() {
    'use strict';

    angular
        .module('bachelorApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('green-housemySuffix', {
            parent: 'entity',
            url: '/green-housemySuffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.greenHouse.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/green-house/green-housesmySuffix.html',
                    controller: 'GreenHouseMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('greenHouse');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('green-housemySuffix-detail', {
            parent: 'entity',
            url: '/green-housemySuffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'bachelorApp.greenHouse.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/green-house/green-housemySuffix-detail.html',
                    controller: 'GreenHouseMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('greenHouse');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'GreenHouse', function($stateParams, GreenHouse) {
                    return GreenHouse.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'green-housemySuffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('green-housemySuffix-detail.edit', {
            parent: 'green-housemySuffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/green-house/green-housemySuffix-dialog.html',
                    controller: 'GreenHouseMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GreenHouse', function(GreenHouse) {
                            return GreenHouse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('green-housemySuffix.new', {
            parent: 'green-housemySuffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/green-house/green-housemySuffix-dialog.html',
                    controller: 'GreenHouseMySuffixDialogController',
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
                    $state.go('green-housemySuffix', null, { reload: 'green-housemySuffix' });
                }, function() {
                    $state.go('green-housemySuffix');
                });
            }]
        })
        .state('green-housemySuffix.edit', {
            parent: 'green-housemySuffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/green-house/green-housemySuffix-dialog.html',
                    controller: 'GreenHouseMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GreenHouse', function(GreenHouse) {
                            return GreenHouse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('green-housemySuffix', null, { reload: 'green-housemySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('green-housemySuffix.delete', {
            parent: 'green-housemySuffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/green-house/green-housemySuffix-delete-dialog.html',
                    controller: 'GreenHouseMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GreenHouse', function(GreenHouse) {
                            return GreenHouse.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('green-housemySuffix', null, { reload: 'green-housemySuffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
