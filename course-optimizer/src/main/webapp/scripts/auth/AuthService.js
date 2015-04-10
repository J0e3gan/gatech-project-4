angular.module('courseOpt').service('AuthService', function ($http, $q) {

    var loggedInUser = null;

    this.login = function (username, password) {

        var formData = new FormData();
        formData.append('username', username);
        formData.append('password', password);

        var deferred = $q.defer();

        $http.post("/auth", formData, {
            transformRequest: angular.identity,
            headers: {
                'Content-Type': undefined
            }
        }).success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };


    this.setUser = function (user) {
        loggedInUser = user;
    };

    this.getUser = function () {
        console.log("Getting logged in user");
        if (loggedInUser) {
            return loggedInUser;
        } else {
            console.log("Reject")
            return $q.reject('noCurrentUser');
        }

    };

    this.logout = function () {
        loggedInUser = null;
    }

});