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
        }).state('courseCatalog', {
            controller: 'CourseCatalogCtrl',
            templateUrl: 'scripts/courseCatalog/courseCatalog.html',
            url:'/courseCatalog'
        }).state('schedule', {
            controller: 'ScheduleCtrl',
            templateUrl: 'scripts/scheduleGenerator/schedule.html',
            url: '/scheduleGenerator'
        }).state('dev', {
            controller: 'DevCtrl',
            templateUrl: 'scripts/dev/dev.html',
            url: '/dev',
            resolve: {}
        });

});

app.run(function ($rootScope, $state) {
    $rootScope.$on('$stateChangeError', function (event) {
        console.log("State change error - unauthorized");
        $state.go('auth');
    });

    $rootScope.state = $state;
});
