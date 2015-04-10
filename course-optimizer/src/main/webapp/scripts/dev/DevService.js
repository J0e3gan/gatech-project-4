angular.module('courseOpt').service('DevService', function ($http, $q) {

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


});