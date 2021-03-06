/**
 * Created by 204069126 on 4/9/15.
 */
angular.module('courseOpt').controller('DevCtrl', function ($rootScope, $scope, $window, $http, DevService) {

    $scope.services = [];

    var getAllCourses = {};
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

    var getCourse = {};
    getCourse.title = 'Get Course';
    getCourse.method = 'GET';
    getCourse.endpoint = '/course/{courseId}';
    var courseIdParam = {
        "name" : "courseId",
        "value" : null
    };
    getCourse.params = [];
    getCourse.params.push(courseIdParam);
    getCourse.result = null;
    getCourse.loading = false;
    getCourse.execute = function() {
        getCourse.loading = true;
        getCourse.result = null;
        DevService.getCourse(getCourse.params[0].value).then(function(response){
            getCourse.loading = false;
            getCourse.result = response;
        });
    };

    var getCourseByNumber = {};
    getCourseByNumber.title = 'Get Course By Number';
    getCourseByNumber.method = 'GET';
    getCourseByNumber.endpoint = '/course/number/{courseNumber}';
    var courseNumberParam = {
        "name" : "courseNumber",
        "value" : null
    };
    getCourseByNumber.params = [];
    getCourseByNumber.params.push(courseNumberParam);
    getCourseByNumber.result = null;
    getCourseByNumber.loading = false;
    getCourseByNumber.execute = function() {
        getCourseByNumber.loading = true;
        getCourseByNumber.result = null;
        DevService.getCourseByNumber(getCourseByNumber.params[0].value).then(function(response){
            getCourseByNumber.loading = false;
            getCourseByNumber.result = response;
        });
    };

    var getAllProfessors = {};
    getAllProfessors.title = 'Get All Professors';
    getAllProfessors.method = 'GET';
    getAllProfessors.endpoint = '/professors';
    getAllProfessors.params = [];
    getAllProfessors.result = null;
    getAllProfessors.loading = false;
    getAllProfessors.execute = function () {
        getAllProfessors.loading = true;
        getAllProfessors.result = null;
        DevService.getAllProfessors().then(function (response) {
            getAllProfessors.loading = false;
            getAllProfessors.result = response;
        });
    };

    var someProf = {
        "firstName": "Some",
        "lastName": "Prof"
    };

    var createProfessor = {};
    createProfessor.title = "Create Professor";
    createProfessor.method = 'POST';
    createProfessor.endpoint = '/professor/create';
    createProfessor.loading = false;
    createProfessor.result = null;
    createProfessor.body = JSON.stringify(someProf);
    createProfessor.execute = function () {
        createProfessor.loading = true;
        createProfessor.result = null;
        DevService.createProfessor(JSON.parse(createProfessor.body)).then(function (response) {
            createProfessor.loading = false;
            createProfessor.result = response.data;
        });
    };

    var deleteProfessor = {};
    deleteProfessor.title = 'Delete Professor';
    deleteProfessor.method = 'DELETE';
    deleteProfessor.endpoint = '/professor/delete/{professorId}';
    var professorIdParam = {
        "name": "professorId",
        "value": null
    };
    deleteProfessor.params = [];
    deleteProfessor.params.push(professorIdParam);
    deleteProfessor.result = null;
    deleteProfessor.loading = false;
    deleteProfessor.execute = function () {
        deleteProfessor.loading = true;
        deleteProfessor.result = null;
        DevService.deleteProfessor(deleteProfessor.params[0].value).then(function (response) {
            deleteProfessor.loading = false;
            deleteProfessor.result = response;
        });
    };

    var getAllTAs = {};
    getAllTAs.title = 'Get All TAs';
    getAllTAs.method = 'GET';
    getAllTAs.endpoint = '/tas';
    getAllTAs.params = [];
    getAllTAs.result = null;
    getAllTAs.loading = false;
    getAllTAs.execute = function () {
        getAllTAs.loading = true;
        getAllTAs.result = null;
        DevService.getAllTAs().then(function (response) {
            getAllTAs.loading = false;
            getAllTAs.result = response;
        });
    };

    var someTA = {
        "firstName": "Some",
        "lastName": "TA"
    };

    var createTA = {};
    createTA.title = "Create TA";
    createTA.method = 'POST';
    createTA.endpoint = '/ta/create';
    createTA.loading = false;
    createTA.result = null;
    createTA.body = JSON.stringify(someTA);
    createTA.execute = function () {
        createTA.loading = true;
        createTA.result = null;
        DevService.createTA(JSON.parse(createTA.body)).then(function (response) {
            createTA.loading = false;
            createTA.result = response.data;
        });
    };

    var deleteTA = {};
    deleteTA.title = 'Delete TA';
    deleteTA.method = 'DELETE';
    deleteTA.endpoint = '/ta/delete/{taId}';
    var taIdParam = {
        "name": "taId",
        "value": null
    };
    deleteTA.params = [];
    deleteTA.params.push(taIdParam);
    deleteTA.result = null;
    deleteTA.loading = false;
    deleteTA.execute = function () {
        deleteTA.loading = true;
        deleteTA.result = null;
        DevService.deleteTA(deleteTA.params[0].value).then(function (response) {
            deleteTA.loading = false;
            deleteTA.result = response;
        });
    };

    var getAllStudents = {};
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
    var studentIdParam = {
        "name" : "studentId",
        "value" : null
    };
    getStudentDetails.params = [];
    getStudentDetails.params.push(studentIdParam);
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

    var getAllConstraints = {};
    getAllConstraints.title = "Get All Constraints";
    getAllConstraints.method = 'GET';
    getAllConstraints.endpoint = '/constraints';
    getAllConstraints.loading = false;
    getAllConstraints.result = null;
    getAllConstraints.execute = function() {
        getAllConstraints.loading = true;
        getAllConstraints.result = null;
        DevService.getConstraints().then(function(response){
            getAllConstraints.loading = false;
            getAllConstraints.result = response.data;
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

    var deleteCourseOffering = {};
    deleteCourseOffering.title = 'Delete a Scheduled Course';
    deleteCourseOffering.method = 'DELETE';
    deleteCourseOffering.endpoint = '/offering/delete/{offeringId}';
    var offeringIdParam = {
        "name" : "offeringId",
        "value" : null
    };
    deleteCourseOffering.params = [];
    deleteCourseOffering.params.push(offeringIdParam);
    deleteCourseOffering.result = null;
    deleteCourseOffering.loading = false;
    deleteCourseOffering.execute = function() {
        deleteCourseOffering.loading = true;
        deleteCourseOffering.result = null;
        DevService.deleteCourseOffering(deleteCourseOffering.params[0].value).then(function(response){
            deleteCourseOffering.loading = false;
            deleteCourseOffering.result = response;
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
    };

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
    $scope.services.push(getCourse);
    $scope.services.push(getCourseByNumber);
    $scope.services.push(getAllProfessors);
    $scope.services.push(createProfessor);
    $scope.services.push(deleteProfessor);
    $scope.services.push(getAllTAs);
    $scope.services.push(createTA);
    $scope.services.push(deleteTA);
    $scope.services.push(getAllStudents);
    $scope.services.push(getStudentDetails);
    $scope.services.push(enrollStudent);
    $scope.services.push(getCourseOfferings);
    $scope.services.push(scheduleCourse);
    $scope.services.push(deleteCourseOffering);
    //$scope.services.push(getAllConstraints);
    //$scope.services.push(createConstraint);

});