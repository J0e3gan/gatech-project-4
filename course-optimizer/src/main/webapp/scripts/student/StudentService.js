angular.module('courseOpt').service('StudentService', function ($http, $q) {


    this.details = {};

    this.getDetails = function (studentId) {

        $http.get("/student/"+ studentId).success(function (response) {
                return response; 
        }).error(function (response) {
            return "error";
            console.log("Error getting user details. " + response);
        });

    };

});