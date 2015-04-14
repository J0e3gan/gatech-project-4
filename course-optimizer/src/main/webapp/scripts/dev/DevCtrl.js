/**
 * Created by 204069126 on 4/9/15.
 */
angular.module('courseOpt').controller('DevCtrl', function ($rootScope, $scope, $window, $http, DevService) {

    $scope.services = [];

    var getAllCourses = {}
    getAllCourses.title = 'Get All Courses';
    getAllCourses.method = 'GET';
    getAllCourses.endpoint = '/courses';
    getAllCourses.params = [];
    getAllCourses.result = null;
    getAllCourses.loading = false;
    getAllCourses.execute = function() {
        getAllCourses.loading = true;
        getAllCourses.result = null;
        DevService.getAllCourses().then(function(response){
            getAllCourses.loading = false;
            getAllCourses.result = response;
        });
    };

    var getAllStudents = {}
    getAllStudents.title = 'Get All Students';
    getAllStudents.method = 'GET';
    getAllStudents.endpoint = '/students';
    getAllStudents.params = [];
    getAllStudents.result = null;
    getAllStudents.loading = false;
    getAllStudents.execute = function() {
        getAllStudents.loading = true;
        getAllStudents.result = null;
        DevService.getAllStudents().then(function(response){
            getAllStudents.loading = false;
            getAllStudents.result = response;
        });
    };


    var getStudentDetails = {};
    getStudentDetails.title = 'Get Student Details';
    getStudentDetails.method = 'GET';
    getStudentDetails.endpoint = '/student/{studentId}';
    var param = {
        "name" : "studentId",
        "value" : null
    };
    getStudentDetails.params = [];
    getStudentDetails.params.push(param);
    getStudentDetails.result = null;
    getStudentDetails.loading = false;
    getStudentDetails.execute = function() {
        getStudentDetails.loading = true;
        getStudentDetails.result = null;
        DevService.getStudentDetails(getStudentDetails.params[0].value).then(function(response){
            getStudentDetails.loading = false;
            getStudentDetails.result = response;
        });
    };

    var getAllConstriants = {};
    getAllConstriants.title = "Get All Constraints";
    getAllConstriants.method = 'GET';
    getAllConstriants.endpoint = '/constraints';
    getAllConstriants.loading = false;
    getAllConstriants.result = null;
    getAllConstriants.execute = function() {
        getAllConstriants.loading = true;
        getAllConstriants.result = null;
        DevService.getConstraints().then(function(response){
            getAllConstriants.loading = false;
            getAllConstriants.result = response.data;
        });
    };


    var someConstraint =  {
        "name": "NEW_CONSTRAINT",
        "description": "Wazzup ???",
        "value": "Some Value"
    };

    var createConstraint = {};
    createConstraint.title = "Create Constraint";
    createConstraint.method = 'POST';
    createConstraint.endpoint = '/constraint/create';
    createConstraint.loading = false;
    createConstraint.result = null;
    createConstraint.body = JSON.stringify(someConstraint);
    createConstraint.execute = function() {
        createConstraint.loading = true;
        createConstraint.result = null;
        DevService.createConstraint(JSON.parse(createConstraint.body)).then(function(response){
            createConstraint.loading = false;
            createConstraint.result = response.data;
        });
    };


    var getCourseOfferings = {};
    getCourseOfferings.title = "Get All Scheduled Courses (Past and Present)";
    getCourseOfferings.method = 'GET';
    getCourseOfferings.endpoint = '/offerings';
    getCourseOfferings.loading = false;
    getCourseOfferings.result = null;
    getCourseOfferings.execute = function() {
        getCourseOfferings.loading = true;
        getCourseOfferings.result = null;
        DevService.getAllCourseOfferings().then(function(response){
            getCourseOfferings.loading = false;
            getCourseOfferings.result = response.data;
        });
    };

    $scope.services.push(getAllCourses);
    $scope.services.push(getAllStudents);
    $scope.services.push(getStudentDetails);
    $scope.services.push(getAllConstriants);
    $scope.services.push(createConstraint);
    $scope.services.push(getCourseOfferings);

});