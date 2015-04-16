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

    this.getCourse = function (courseId) {

        var deferred = $q.defer();

        $http.get("/course/" + courseId).success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };

    this.getCourseByNumber = function (courseNumber) {

        var deferred = $q.defer();

        $http.get("/course/number/" + courseNumber).success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };

    this.getAllProfessors = function () {

        var deferred = $q.defer();

        $http.get("/professors").success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };

    this.deleteProfessor = function (professorId) {

        var deferred = $q.defer();

        $http.delete("/professor/delete/" + professorId).success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };

    this.getAllTAs = function () {

        var deferred = $q.defer();

        $http.get("/tas").success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };

    this.deleteTA = function (taId) {

        var deferred = $q.defer();

        $http.delete("/ta/delete/" + taId).success(function (response) {
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


    this.getConstraints = function() {
        return $http.get("/constraints");
    };

    this.createConstraint = function(constraint) {
        return $http.post("/constraint/create",constraint);
    };

    this.getAllCourseOfferings = function() {
        return $http.get("/offerings");
    };

    this.deleteCourseOffering = function (offeringId) {

        var deferred = $q.defer();

        $http.delete("/offering/delete/" + offeringId).success(function (response) {
            deferred.resolve(response);
        })
            .error(function (response) {
                deferred.reject(response);
            });

        return deferred.promise;
    };

    this.enrollStudent = function(student) {
        return $http.post("/student/enroll",student);
    };

    this.scheduleCourse = function(courseOffering) {
        return $http.post("/offering/schedule",courseOffering);
    }



});