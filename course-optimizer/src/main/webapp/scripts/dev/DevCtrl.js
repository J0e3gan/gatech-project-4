/**
 * Created by 204069126 on 4/9/15.
 */
angular.module('courseOpt').controller('DevCtrl', function ($rootScope, $scope, $window, $http, DevService) {

    $scope.services = [];

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

    $scope.services.push(getAllStudents);
    $scope.services.push(getStudentDetails);

});