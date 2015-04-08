angular.module('courseOpt').controller('AdminCtrl', function ($rootScope, $scope, $window, $http, AuthService) {


    $scope.user = AuthService.getUser();


});