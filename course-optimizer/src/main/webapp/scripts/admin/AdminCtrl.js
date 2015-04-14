angular.module('courseOpt').controller('AdminCtrl', function ($rootScope, $scope, $window, $http, AuthService) {


   $scope.user = AuthService.getUser();

   $http.get('/students').success(function(response){
    	 $scope.students = response;
    }).error(function(response){

    });


});