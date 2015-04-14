angular.module('courseOpt').service('DevService', function ($http, $q) {

    this.getAllCourses = function () {

        var deferred = $q.defer();

        $http.get("/courses").success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };

    this.getAllStudents = function () {

        var deferred = $q.defer();

        $http.get("/students").success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };

    this.getStudentDetails = function (studentId) {

        var deferred = $q.defer();

        $http.get("/student/" + studentId).success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };


});