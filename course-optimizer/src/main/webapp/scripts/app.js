/*
 *  Dependencies
 */
var app = angular.module('courseOpt', ['ngRoute', 'ui.router']);

/*
 *  Config
 */
app.config(function ($stateProvider) {
    $stateProvider
        .state('admin', {
            controller: 'AdminCtrl',
            templateUrl: 'scripts/admin/admin.html',
            data: {},
            url: '/admin',
            resolve: {
                loggedInUser: function (AuthService) {
                    return AuthService.getUser();
                }
            },
            onEnter: function () {
            },
            onExit: function () {
            }
        }).state('student', {
            controller: 'StudentCtrl',
            templateUrl: 'scripts/student/student.html',
            url: '/student',
            resolve: {
                loggedInUser: function (AuthService) {
                    return AuthService.getUser();
                }
            }
        }).state('auth', {
            controller: 'AuthCtrl',
            templateUrl: 'scripts/auth/auth.html',
            url: '/',
            resolve: {}
        });

});

app.run(function ($rootScope, $state) {
    $rootScope.$on('$stateChangeError', function (event) {
        console.log("State change error - unauthorized");
        $state.go('auth');
    });
});
