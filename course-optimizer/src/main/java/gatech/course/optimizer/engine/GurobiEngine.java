package gatech.course.optimizer.engine;

import gatech.course.optimizer.dto.ScheduleInput;
import gatech.course.optimizer.dto.StudentDTO;
import gatech.course.optimizer.dto.TakenCourseDTO;
import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Faculty;
import gatech.course.optimizer.model.ScheduleSolution;
import gatech.course.optimizer.model.Semester;
import gatech.course.optimizer.model.Semester.SemesterTerm;
import gatech.course.optimizer.model.Specialization;
import gurobi.GRB;
import gurobi.GRB.DoubleAttr;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBVar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by 204069126 on 2/6/15.
 */
public class GurobiEngine implements EngineInterface {


    @Override
    public ScheduleSolution createScheduleSolution(ScheduleInput scheduleInput) {

        try {
            // Create environment and model
            GRBEnv env = new GRBEnv("schedule_optimization.log");
            GRBModel model = new GRBModel(env);

            Faculty[] facultyTemp = new Faculty[0];
            
            // Setup the inputs
            int numberOfStudents = scheduleInput.getStudents().size(); // i
            StudentDTO[] students = new StudentDTO[numberOfStudents];
            scheduleInput.getStudents().toArray( students );
            
            int numberOfCourses = scheduleInput.getCoursesThatCanBeOffered().size(); // j
            Course[] courses = new Course[numberOfCourses];
            scheduleInput.getCoursesThatCanBeOffered().toArray(courses);
            
            int numberOfProfessors = scheduleInput.getProfessors().size();
            Faculty[] professors = scheduleInput.getProfessors().toArray( facultyTemp );
            
            int numberOfTAs = scheduleInput.getTeacherAssistants().size();
            Faculty[] teachingAssistants = scheduleInput.getTeacherAssistants().toArray( facultyTemp );
            
            int maxCourseCapacity = scheduleInput.getMaxCourseCapacity();

            // Hardcoded constraints
            int numberOfSemesters = 3;
            int minEnrollmentToBeOffered = 1;
            int maxCoursesPerProfessorPerSemester = 1;
            int maxCoursesPerTAPerSemester = 1;
            int numberOfCoursesToGraduate = 3;
            
            // Add variables
            double upperBound = numberOfStudents * numberOfCourses * numberOfStudents;
            GRBVar x = model.addVar(0.0, upperBound, 0.0, GRB.INTEGER, "X");

            // History variables for [student][course]
            GRBVar[][] studentHistoryVariables = new GRBVar[numberOfStudents][numberOfCourses];
            for (int i = 0; i < numberOfStudents; i++){
            	for (int j = 0; j < numberOfCourses; j++){
            		studentHistoryVariables[i][j] = model.addVar(0, 1, 0, GRB.BINARY, "sh_" + (i+1) + "_" + (j+1));
            	}
            }
            
            // Variables for [student][course][semester]
            GRBVar[][][] studentVariables = new GRBVar[numberOfStudents][numberOfCourses][numberOfSemesters];
            for (int i = 0; i < numberOfStudents; i++) {
                for (int j = 0; j < numberOfCourses; j++) {
                    for (int k = 0; k < numberOfSemesters; k++) {
                        studentVariables[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, getVariableName(i, j, k));
                    }
                }
            }
            
            // Variables for [professor][course][semester]
            GRBVar[][][] professorVariables = new GRBVar[numberOfProfessors][numberOfCourses][numberOfSemesters];
            for (int i = 0; i < numberOfProfessors; i++){
            	for (int j = 0; j < numberOfCourses; j++) {
                    for (int k = 0; k < numberOfSemesters; k++) {
                        professorVariables[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "p_" + (i+1) + "_" + (j+1) + "_" + (k+1));
                    }
                }
            }

            // Variables for [ta][course][semester]
            GRBVar[][][] taVariables = new GRBVar[numberOfTAs][numberOfCourses][numberOfSemesters];
            for (int i = 0; i < numberOfTAs; i++){
            	for (int j = 0; j < numberOfCourses; j++) {
                    for (int k = 0; k < numberOfSemesters; k++) {
                        taVariables[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "t_" + (i+1) + "_" + (j+1) + "_" + (k+1));
                    }
                }
            }
            
            model.update();
            
            // Add constraints to set student history courses
            for (int i = 0; i < numberOfStudents; i++){
            	GRBLinExpr notTaken = new GRBLinExpr();
            	GRBLinExpr taken = new GRBLinExpr();
            	int takenCount = 0;
            	for (int j = 0; j < numberOfCourses; j++){
            		TakenCourseDTO takenCourse = this.getTakenCourseDTOById( j+1, students[i].getTakenCourses() );
            		if (takenCourse == null){
            			// This course was not taken
            			notTaken.addTerm(1, studentHistoryVariables[i][j]);
            		} else {
            			taken.addTerm(1, studentHistoryVariables[i][j]);
            			takenCount++;
            		}
            	}
            	model.addConstr( notTaken, GRB.EQUAL, 0, "coursesNotAlreadyTakenForStudent" + (i+1) );
            	model.addConstr( taken, GRB.EQUAL, takenCount, "coursesAlreadyTakenForStudent" + (i+1) );
            }

            // Add the capacity limit constraint
            for (int k = 0; k < numberOfSemesters; k++) {
                for (int j = 0; j < numberOfCourses; j++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    expr.addTerm(-1, x);
                    for (int i = 0; i < numberOfStudents; i++) {
                        expr.addTerm(1, studentVariables[i][j][k]);
                    }
                    model.addConstr(expr, GRB.LESS_EQUAL, 0, "capacityLimitForCourse" + (j + 1) + "inSemester" + (k + 1));
                }
            }

            // Add constraint that student can only take nMax courses per semester
            int nMax = scheduleInput.getAllowedClassesPerSemester();
            for (int i = 0; i < numberOfStudents; i++) {
                for (int k = 0; k < numberOfSemesters; k++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    for (int j = 0; j < numberOfCourses; j++) {
                        expr.addTerm(1, studentVariables[i][j][k]);
                    }
                    model.addConstr(expr, GRB.LESS_EQUAL, nMax, "maxCoursesForStudent" + (i + 1) + "inSemester" + (k + 1));
                }
            }
            
            // Constraints for number of courses to graduate
            for (int i = 0; i < numberOfStudents; i++){
            	GRBLinExpr expr = new GRBLinExpr();
            	for (int j = 0; j < numberOfCourses; j++){
            		// Historical courses:
            		expr.addTerm( 1, studentHistoryVariables[i][j] );
            		for (int k = 0; k < numberOfSemesters; k++){
            			// TODO : Limit number of semesters based on current seniority
            			expr.addTerm( 1, studentVariables[i][j][k] );
            		}
            	}
            	model.addConstr( expr, GRB.EQUAL, numberOfCoursesToGraduate, "courseCreditRequirementsForStudent" + students[i].getId() );
            }

            // Constraints for course prerequisites
            // TODO : Add course history
            for ( int j = 0; j < numberOfCourses; j++ ){ // Course course : scheduleInput.getCoursesThatCanBeOffered()) {
            	Course course = courses[j];
                if (course.getPrerequisites() != null && course.getPrerequisites().size() > 0) {
                    //int courseId = course.getId(); // j1
                    //int courseId = course.getId().intValue();
                    for (int l = 0; l < course.getPrerequisites().size(); l++) {
                        // j0 must be taken prior to j1
                        int prerequisiteId = course.getPrerequisites().iterator().next().getId().intValue();
                        System.out.println("Course: " + course.getId() + " has prerequisite: " + prerequisiteId);
                        // For a pair course and its prerequisite
                        GRBLinExpr firstSemesterNoCoursesWithPrerequisites = new GRBLinExpr();
                        for (int i = 0; i < numberOfStudents; i++) {
                            // Course with prerequisites cannot be taken in first semester
                            firstSemesterNoCoursesWithPrerequisites.addTerm(1.0, studentVariables[i][j][0]);
                            for (int k = 1; k < numberOfSemesters; k++) {  //
                                GRBLinExpr leftExpr = new GRBLinExpr();
                                GRBLinExpr rightExpr = new GRBLinExpr();
                                int prereqIndex = this.getCourseIndexById( prerequisiteId, courses );
                                if (prereqIndex > -1){
	                                for (int m = 0; m <= k; m++) {
	                                    leftExpr.addTerm(1, studentVariables[i][j][m]);
	                                    rightExpr.addTerm(1, studentVariables[i][prereqIndex][m]);
	                                }
	                                model.addConstr(leftExpr, GRB.LESS_EQUAL, rightExpr, "forStudent" + (i + 1) +
	                                		"semester" + (k + 1) + "course" + prerequisiteId + "mustBeTakenBefore" + course.getId());
                                }
                            }
                        }
                        model.addConstr(firstSemesterNoCoursesWithPrerequisites, GRB.EQUAL, 0, "noStudentCanTakeCourse" + course.getId() + "inFirstSemester");
                    }
                }
            }
            
            // Add constraint that required courses have at least one student
            for (CourseOffering course : scheduleInput.getRequiredOfferings()){
            	GRBLinExpr expr = new GRBLinExpr();
            	int courseIndex = this.getCourseIndexById( course.getCourse().getId().intValue(), courses );
            	if (courseIndex > -1){
	            	for (int i = 0; i < numberOfStudents; i++){
	            		expr.addTerm(1, studentVariables[i][courseIndex][convertSemesterToIndex(scheduleInput.getSemesterToSchedule(), course.getSemester())]);
	            	}
	            	model.addConstr( expr, GRB.GREATER_EQUAL, minEnrollmentToBeOffered, "requiredCourseOffering" + (course.getId().intValue()));
            	}
            }

            // Add constraint that students must complete a specialization
            // TODO : Add course history
            for (int studentIndex = 0; studentIndex < numberOfStudents; studentIndex++) {
            	StudentDTO student = students[studentIndex];
            	Specialization specialization = student.getChosenSpecialization();
            	if (specialization != null){
	            	// Add constraint that the student must take the required courses
            		Set<Course> requiredCourses = specialization.getRequiredCourses();
            		if (requiredCourses != null){
            			for (Course requiredCourse : requiredCourses){
		            		int courseIndex = this.getCourseIndexById( requiredCourse.getId().intValue(), courses );
	            			GRBLinExpr expr = new GRBLinExpr();
		            		for (int k = 0; k < numberOfSemesters; k++){
		            			expr.addTerm( 1, studentVariables[studentIndex][courseIndex][k]);
		            		}
		            		model.addConstr( expr, GRB.EQUAL, 1, "requiredSpecializationCourse" + (requiredCourse.getId().intValue()) + "forStudent" + student.getId().intValue() );
            			}
            		}
            		
            		// Add constraint for core courses
            		Set<Course> coreCourses = specialization.getCoreCourses();
            		if (coreCourses != null && specialization.getNumberOfCoreCoursesRequired() > 0){
            			GRBLinExpr expr = new GRBLinExpr();
            			for (Course coreCourse : coreCourses){
            				int courseIndex = this.getCourseIndexById( coreCourse.getId().intValue(), courses );
            				for (int k = 0; k < numberOfSemesters; k++){
            					expr.addTerm(1, studentVariables[studentIndex][courseIndex][k]);
            				}
            			}
            			// This is an okay constraint because of the other constraint that says 
            			// a student may take a course only once.
            			model.addConstr( expr, GRB.GREATER_EQUAL, specialization.getNumberOfCoreCoursesRequired(), "specializationCoreCoursesForStudent" + student.getId().intValue() );
            		}
            		
            		// Add constraint for elective courses
            		Set<Course> electiveCourses = specialization.getCoreCourses();
            		if (electiveCourses != null && specialization.getNumberOfElectiveCoursesRequired() > 0){
            			GRBLinExpr expr = new GRBLinExpr();
            			for (Course electiveCourse : electiveCourses){
            				int courseIndex = this.getCourseIndexById( electiveCourse.getId().intValue(), courses );
            				for (int k = 0; k < numberOfSemesters; k++){
            					expr.addTerm(1, studentVariables[studentIndex][courseIndex][k]);
            				}
            			}
            			// This is an okay constraint because of the other constraint that says 
            			// a student may take a course only once.
            			model.addConstr( expr, GRB.GREATER_EQUAL, specialization.getNumberOfElectiveCoursesRequired(), "specializationElectiveCoursesForStudent" + student.getId().intValue() );
            		}
            	}
            }
            
            // Add constraint that a student can only take a course once
            for (int i = 0; i < numberOfStudents; i++){
            	for (int j = 0; j < numberOfCourses; j++){
            		GRBLinExpr expr = new GRBLinExpr();
            		// Take into account course history:
            		expr.addTerm( 1, studentHistoryVariables[i][j] );
            		for (int k = 0; k < numberOfSemesters; k++){
            			expr.addTerm( 1, studentVariables[i][j][k] );
            		}
            		model.addConstr( expr, GRB.LESS_EQUAL, 1, "courseRepetitionLimitForStudent" + (i+1) + "Course" + (j+1) );
            	}
            }
            
            // Add constraint that each course offered has 1 professor assigned to it
            for (int k = 0; k < numberOfSemesters; k++){
            	for (int j = 0; j < numberOfCourses; j++){
	            	GRBLinExpr expr = new GRBLinExpr();
            		for (int i = 0; i < numberOfProfessors; i++){
	            		expr.addTerm( maxCourseCapacity, professorVariables[i][j][k] );
	            	}
            		
            		for (int i = 0; i < numberOfStudents; i++){
            			expr.addTerm( -1, studentVariables[i][j][k] );
            		}
            		model.addConstr( expr, GRB.LESS_EQUAL, maxCourseCapacity - 1, "course_" + (j+1) + "_NeedsOneProfessorIfOfferedAtMost" );
            		model.addConstr( expr, GRB.GREATER_EQUAL, 0, "course_" + (j+1) + "_NeedsOneProfessorIfOfferedAtLeast" );
	            }
            }
            
            // Add constraint that each course offered has 1 TA assigned to it
            for (int k = 0; k < numberOfSemesters; k++){
            	for (int j = 0; j < numberOfCourses; j++){
	            	GRBLinExpr expr = new GRBLinExpr();
	            	for (int i = 0; i < numberOfTAs; i++){
	            		expr.addTerm( maxCourseCapacity, taVariables[i][j][k] );
	            	}
            		
            		for (int i = 0; i < numberOfStudents; i++){
            			expr.addTerm( -1, studentVariables[i][j][k] );
            		}
            		model.addConstr( expr, GRB.LESS_EQUAL, maxCourseCapacity - 1, "course_" + (j+1) + "_NeedsOneTAIfOfferedAtMost" );
            		model.addConstr( expr, GRB.GREATER_EQUAL, 0, "course_" + (j+1) + "_NeedsOneTAIfOfferedAtLeast" );
	            }
            }
            
            // Add constraint that each faculty only assigned to 1 course a semester
            for (int k = 0; k < numberOfSemesters; k++){
            	for (int i = 0; i < numberOfProfessors; i++){
            		GRBLinExpr professorExpr = new GRBLinExpr();
	            	for (int j = 0; j < numberOfCourses; j++){
	            		professorExpr.addTerm( 1, professorVariables[i][j][k] );
	            	}
	            	model.addConstr( professorExpr, GRB.LESS_EQUAL, maxCoursesPerProfessorPerSemester, "professor" +  (i+1) + "_MaxCoursesForSemester" + (k+1));
            	}
            	for (int i = 0; i < numberOfTAs; i++){
            		GRBLinExpr taExpr = new GRBLinExpr();
            		for (int j = 0; j < numberOfCourses; j++){
            			taExpr.addTerm(1, taVariables[i][j][k]);
            		}
            		model.addConstr( taExpr, GRB.LESS_EQUAL, maxCoursesPerTAPerSemester, "ta" + (i+1) + "_MaxCoursesForSemester" + (k+1) );
            	}
            }
            
            // Add constraint that each professor only assigned to relevant courses
            for (int i = 0; i < numberOfProfessors; i++){
        		if (professors[i].getCompetencies() != null){
	            	for (int k = 0; k < numberOfSemesters; k++){
	            		GRBLinExpr expr = new GRBLinExpr();
	            		for (int j = 0; j < numberOfCourses; j++){
	            			// The constraint will be that all non-competency course are not assigned:
	            			if (! professors[i].getCompetencies().contains( courses[j] )){
	            				expr.addTerm( 1, professorVariables[i][j][k] );
	            			}
	            		}
	            		model.addConstr( expr, GRB.EQUAL, 0, "professor" + (i + 1) + "_CompetenciesForSemester" + (k+1) );		
	            	}
        		}
            }
            
            // Add constraint that each TA only assigned to relevant courses
            for (int i = 0; i < numberOfTAs; i++){
        		if (teachingAssistants[i].getCompetencies() != null){
	            	for (int k = 0; k < numberOfSemesters; k++){
	            		GRBLinExpr expr = new GRBLinExpr();
	            		for (int j = 0; j < numberOfCourses; j++){
	            			// The constraint will be that all non-competency course are not assigned:
	            			if (! teachingAssistants[i].getCompetencies().contains( courses[j] )){
	            				expr.addTerm( 1, taVariables[i][j][k] );
	            			}
	            		}
	            		model.addConstr( expr, GRB.EQUAL, 0, "ta" + (i + 1) + "_CompetenciesForSemester" + (k+1) );		
	            	}
        		}
            }
            
            // Add constraint that each professor only assigned to courses in available times
            for (int i = 0; i < numberOfProfessors; i++){
            	if (professors[i].getAvailability() != null){
            		GRBLinExpr expr = new GRBLinExpr();
            		for (int j = 0; j < numberOfCourses; j++){
            			for (int k = 0; k < numberOfSemesters; k++){
	            			boolean available = false;
            				for (Semester semester : professors[i].getAvailability()){
	            				int semesterIndex = this.convertSemesterToIndex( scheduleInput.getSemesterToSchedule(), semester );
	            				if (semesterIndex == k){
	            					available = true;
	            					break;
	            				}
	            			}
            				if (!available){
            					expr.addTerm( 1, professorVariables[i][j][k] );
            				}
            			}
            		}
            		model.addConstr( expr, GRB.EQUAL, 0, "professor" + (i+1) + "_NotAvailable");
            	}
            }
            
            // Add constraint that each ta only assigned to courses in available times
            for (int i = 0; i < numberOfTAs; i++){
            	if (teachingAssistants[i].getAvailability() != null){
            		GRBLinExpr expr = new GRBLinExpr();
            		for (int j = 0; j < numberOfCourses; j++){
            			for (int k = 0; k < numberOfSemesters; k++){
	            			boolean available = false;
            				for (Semester semester : teachingAssistants[i].getAvailability()){
	            				int semesterIndex = this.convertSemesterToIndex( scheduleInput.getSemesterToSchedule(), semester );
	            				if (semesterIndex == k){
	            					available = true;
	            					break;
	            				}
	            			}
            				if (!available){
            					expr.addTerm( 1, taVariables[i][j][k] );
            				}
            			}
            		}
            		model.addConstr( expr, GRB.EQUAL, 0, "ta" + (i+1) + "_NotAvailable");
            	}
            }
            
            // Add objective
            // TODO : Tie to priorities of each student.
            GRBLinExpr expr = new GRBLinExpr();
            expr.addTerm(1, x);
            model.setObjective(expr, GRB.MINIMIZE);
            model.optimize();

            int status = model.get(GRB.IntAttr.Status);
            if (status == GRB.Status.INFEASIBLE){
            	System.out.println("Model is infeasible");
            	model.computeIIS();
            	return null;
            }
            
            ScheduleSolution solution = new ScheduleSolution();
            
            Set<CourseOffering> offerings = new HashSet<CourseOffering>();
            for (int k = 0; k < numberOfSemesters; k++){
	            for (int j = 0; j < numberOfCourses; j++){
	            	CourseOffering offering = null;
	            	for (int i = 0; i < numberOfStudents; i++){
	            		if (studentVariables[i][j][k].get(DoubleAttr.X) == 1) {
	            			if (offering == null){
	            				offering = new CourseOffering("1000", courses[j], this.getSemesterByIndex(k, scheduleInput.getSemesterToSchedule()));
	            			}
	            			offering.enrollStudent( students[i].toStudent() );
	            		}
	            	}
	            	if (offering != null){
		            	for (int i = 0; i < numberOfProfessors; i++){
		            		if (professorVariables[i][j][k].get(DoubleAttr.X) == 1){
		            			offering.setProfessor( professors[i] );
		            			break;
		            		}
		            	}
		            	Set<Faculty> tas = new HashSet<Faculty>();
		            	for (int i = 0; i < numberOfTAs; i++){
		            		if (taVariables[i][j][k].get(DoubleAttr.X) == 1){
		            			tas.add( teachingAssistants[i] );
		            		}
			            }
		            	offering.setTeacherAssistants( tas );
		            	offerings.add( offering );
	            	}
	            }
            }

            solution.setSchedule( offerings );
            
            model.dispose();
            env.dispose();

            //return result;
            return solution;

        } catch (GRBException e) {
            System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
            return null;
        }
    }
    
    private int getCourseIndexById(int id, Course[] courses){
    	if (courses == null)
    		return -1;
    	for (int i = 0; i < courses.length; i++){
    		if (courses[i].getId().intValue() == id){
    			return i;
    		}
    	}
    	return -1;
    }
    
    private TakenCourseDTO getTakenCourseDTOById(int id, List<TakenCourseDTO> courses){
    	if (courses == null)
    		return null;
    	for (TakenCourseDTO course : courses){
    		if (course.getCourse().getId().intValue() == id){
    			return course;
    		}
    	}
    	return null;
    }

    private String getVariableName(int i, int j, int k) {
        return "y" + (i + 1) + "_" + (j + 1) + "_" + (k + 1);
    }
    
    private int convertSemesterToIndex(Semester referenceSemester, Semester semesterToConvert){
    	int referenceYear = referenceSemester.getYear();
    	int referenceTerm = termToNumber(referenceSemester.getTerm());
    	
    	int targetYear = semesterToConvert.getYear();
    	int targetTerm = termToNumber(semesterToConvert.getTerm());
    	
    	// 2014, 2 ==> 2015,0 ... 1 * 3 + (0 - 2) = 1
    	// 2015, 0 ==> 2015,2 ... 0 * 3 + (2 - 0) = 2
    	// 2014, 2 ==> 2016,0 ... 2 * 3 + (0 - 2) = 4
    	// 2014, 0 ==> 2015,0 ... 1 * 3 + (0 - 0) = 3
    	return ((targetYear - referenceYear) * 3) + (targetTerm - referenceTerm);
    }
    
    private int termToNumber(Semester.SemesterTerm term){
    	int targetTerm = 0;
    	switch (term){
    		case SPRING:
    			targetTerm = 0;
    			break;
    		case SUMMER:
    			targetTerm = 1;
    			break;
    		case FALL:
    			targetTerm = 2;
    			break;
    	}   
    	return targetTerm;
    }
    
    private SemesterTerm numberToTerm(int number){
    	switch (number){
    		case 0:
    			return SemesterTerm.SPRING;
    		case 1:
    			return SemesterTerm.SUMMER;
    		case 2:
    			return SemesterTerm.FALL;
    	}
    	return null;
    }
    
    private Semester getSemesterByIndex(int index, Semester referenceSemester){
    	int year = referenceSemester.getYear() + (index / 3);
    	int semester = termToNumber(referenceSemester.getTerm()) + (index % 3);
    	if (semester >= 3){
    		year++;
    		semester = semester - 3;
    	}
    	return new Semester(year + "", numberToTerm(semester) + "");
    }
}
