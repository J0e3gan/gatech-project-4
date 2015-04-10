angular.module('courseOpt').controller('AuthCtrl', function ($rootScope, $scope, $window, $http, $state, AuthService) {

    //var loggedInUser = null;
    //$scope.username = 902880094;
    //$scope.password = 'Pawel';
    $scope.username = null;
    $scope.password = null;
    $scope.loginError = false;

    $scope.login = function () {
        console.log($scope.username + " --  " + $scope.password);
        AuthService.login($scope.username, $scope.password).then(function (response) {
            console.log("Login successfull");
            console.log(JSON.stringify(response));
            console.log(response);
            AuthService.setUser(response);
            if (response.role === 'ADMIN') {
                $state.go('admin');
            } else {
                $state.go('student');
            }
        }, function (error) {
            console.log("Login Failed");
            $scope.username = null;
            $scope.password = "";
            $scope.loginError = true;
        });
    }
});