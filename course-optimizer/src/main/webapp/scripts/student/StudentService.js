angular.module('courseOpt').service('StudentService', function ($http, $q) {


    this.getDetails = function (studentId) {
        return $http.get("/student/"+ studentId);

    };

});