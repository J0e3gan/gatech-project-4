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

    var getAllProfessors = {}
    getAllProfessors.title = 'Get All Professors';
    getAllProfessors.method = 'GET';
    getAllProfessors.endpoint = '/professors';
    getAllProfessors.params = [];
    getAllProfessors.result = null;
    getAllProfessors.loading = false;
    getAllProfessors.execute = function() {
        getAllProfessors.loading = true;
        getAllProfessors.result = null;
        DevService.getAllProfessors().then(function(response){
            getAllProfessors.loading = false;
            getAllProfessors.result = response;
        });
    };

    var getAllTAs = {}
    getAllTAs.title = 'Get All TAs';
    getAllTAs.method = 'GET';
    getAllTAs.endpoint = '/tas';
    getAllTAs.params = [];
    getAllTAs.result = null;
    getAllTAs.loading = false;
    getAllTAs.execute = function() {
        getAllTAs.loading = true;
        getAllTAs.result = null;
        DevService.getAllTAs().then(function(response){
            getAllTAs.loading = false;
            getAllTAs.result = response;
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


    var student = {
        "username": "99999999",
        "password": "password",
        "firstName": "Steward",
        "lastName": "Little",
        "desiredCourses": [],
        "seniority": 0,
        "studentId": "99999999"
    }

    var enrollStudent = {};
    enrollStudent.title = "Enroll Student";
    enrollStudent.method = 'POST';
    enrollStudent.endpoint = '/student/enroll';
    enrollStudent.loading = false;
    enrollStudent.body = JSON.stringify(student);
    enrollStudent.result = null;
    enrollStudent.execute = function() {
        enrollStudent.loading = true;
        enrollStudent.result = null;
        DevService.enrollStudent(JSON.parse(enrollStudent.body)).then(function(response){
            enrollStudent.loading = false;
            enrollStudent.result = response.data;
        });
    };


    var courseOffering = {
        "crn": 999999,
        "studentCapacity": 100,
        "course": {
            "id": 1,
            "name": "Advanced OS",
            "number": "CS 6210",
            "prerequisites": []
        },
        "semester": {
            "id": 2,
            "term": "SPRING",
            "year": 2015
        },
        "professor": null,
        "teacherAssistants": [],
        "enrolledStudents": [],
        "studentsOnWaitList": []
    };

    var scheduleCourse = {};
    scheduleCourse.title = "Schedule Course";
    scheduleCourse.method = 'POST';
    scheduleCourse.endpoint = '/offering/schedule';
    scheduleCourse.loading = false;
    scheduleCourse.body = JSON.stringify(courseOffering);
    scheduleCourse.result = null;
    scheduleCourse.execute = function() {
        scheduleCourse.loading = true;
        scheduleCourse.result = null;
        DevService.scheduleCourse(JSON.parse(scheduleCourse.body)).then(function(response){
            scheduleCourse.loading = false;
            scheduleCourse.result = response.data;
        });
    };

    $scope.services.push(getAllCourses);
    $scope.services.push(getAllProfessors);
    $scope.services.push(getAllTAs);
    $scope.services.push(getAllStudents);
    $scope.services.push(getStudentDetails);
    $scope.services.push(getAllConstriants);
    $scope.services.push(createConstraint);
    $scope.services.push(getCourseOfferings);
    $scope.services.push(enrollStudent);
    $scope.services.push(scheduleCourse);

});