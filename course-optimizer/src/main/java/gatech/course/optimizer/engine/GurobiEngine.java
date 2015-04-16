package gatech.course.optimizer.engine;

import gatech.course.optimizer.dto.ScheduleInput;
import gatech.course.optimizer.dto.StudentDTO;
import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Faculty;
import gatech.course.optimizer.model.ScheduleSolution;
import gatech.course.optimizer.model.Semester;
import gatech.course.optimizer.model.Specialization;
import gurobi.GRB;
import gurobi.GRBEnv;
import gurobi.GRBException;
import gurobi.GRBExpr;
import gurobi.GRBLinExpr;
import gurobi.GRBModel;
import gurobi.GRBQuadExpr;
import gurobi.GRBVar;

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

            int numberOfStudents = scheduleInput.getStudents().size(); // i
            //int numberOfCourses = university.getCourses().size(); // j
            int numberOfCourses = scheduleInput.getCoursesThatCanBeOffered().size(); // j
            //int numberOfSemesters = university.getSemesters().size(); // k
            int numberOfSemesters = 3;
            
            int numberOfProfessors = scheduleInput.getProfessors().size();
            int numberOfTAs = scheduleInput.getTeacherAssistants().size();

            int minEnrollmentToBeOffered = 1;
            int maxCoursesPerProfessorPerSemester = 1;
            int maxCoursesPerTAPerSemester = 1;
            int maxCourseCapacity = scheduleInput.getMaxCourseCapacity();
            
            // Add variables
            double upperBound = numberOfStudents * numberOfCourses * numberOfStudents;
            GRBVar x = model.addVar(0.0, upperBound, 0.0, GRB.INTEGER, "X");

            GRBVar[][][] modelVariables = new GRBVar[numberOfStudents][numberOfCourses][numberOfSemesters];
            for (int i = 0; i < numberOfStudents; i++) {
                for (int j = 0; j < numberOfCourses; j++) {
                    for (int k = 0; k < numberOfSemesters; k++) {
                        modelVariables[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, getVariableName(i, j, k));
                    }
                }
            }
            
            Faculty[] facultyTemp = new Faculty[0];
            Faculty[] professors = scheduleInput.getProfessors().toArray( facultyTemp );
            GRBVar[][][] professorVariables = new GRBVar[numberOfProfessors][numberOfCourses][numberOfSemesters];
            for (int i = 0; i < numberOfProfessors; i++){
            	for (int j = 0; j < numberOfCourses; j++) {
                    for (int k = 0; k < numberOfSemesters; k++) {
                        professorVariables[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "p_" + (i+1) + "_" + (j+1) + "_" + (k+1));
                    }
                }
            }

            Faculty[] teachingAssistants = scheduleInput.getTeacherAssistants().toArray( facultyTemp );
            GRBVar[][][] taVariables = new GRBVar[numberOfTAs][numberOfCourses][numberOfSemesters];
            for (int i = 0; i < numberOfTAs; i++){
            	for (int j = 0; j < numberOfCourses; j++) {
                    for (int k = 0; k < numberOfSemesters; k++) {
                        taVariables[i][j][k] = model.addVar(0, 1, 0, GRB.BINARY, "t_" + (i+1) + "_" + (j+1) + "_" + (k+1));
                    }
                }
            }
            
            model.update();

            // Add the capacity limit constraint
            for (int k = 0; k < numberOfSemesters; k++) {
                for (int j = 0; j < numberOfCourses; j++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    expr.addTerm(-1, x);
                    for (int i = 0; i < numberOfStudents; i++) {
                        expr.addTerm(1, modelVariables[i][j][k]);
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
                        expr.addTerm(1, modelVariables[i][j][k]);
                    }
                    model.addConstr(expr, GRB.LESS_EQUAL, nMax, "maxCoursesForStudent" + (i + 1) + "inSemester" + (k + 1));
                }
            }

            // Constraints for course prerequisites
            // for (Course course : university.getCourses()) {
            for (Course course : scheduleInput.getCoursesThatCanBeOffered()) {
                if (course.getPrerequisites() != null && course.getPrerequisites().size() > 0) {

                    //int courseId = course.getId(); // j1
                    int courseId = course.getId().intValue();
                    for (int l = 0; l < course.getPrerequisites().size(); l++) {
                        // j0 must be taken prior to j1
                        //int prerequisiteId = course.getPrerequisites().get(l); // j0
                        int prerequisiteId = course.getPrerequisites().iterator().next().getId().intValue(); // TODO: fix
                        System.out.println("Course: " + courseId + " has prerequisite: " + prerequisiteId);
                        // For a pair course and its prerequisite
                        GRBLinExpr firstSemesterNoCoursesWithPrerequisites = new GRBLinExpr();
                        for (int i = 0; i < numberOfStudents; i++) {
                            // Course with prerequisites cannot be taken in first semester
                            firstSemesterNoCoursesWithPrerequisites.addTerm(1.0, modelVariables[i][courseId - 1][0]);
                            for (int k = 1; k < numberOfSemesters; k++) {  //
                                GRBLinExpr leftExpr = new GRBLinExpr();
                                GRBLinExpr rightExpr = new GRBLinExpr();
                                for (int m = 0; m <= k; m++) {
                                    leftExpr.addTerm(1, modelVariables[i][courseId - 1][m]);
                                    rightExpr.addTerm(1, modelVariables[i][prerequisiteId - 1][m]);
                                }
                                model.addConstr(leftExpr, GRB.LESS_EQUAL, rightExpr, "forStudent" + (i + 1) +
                                        "semester" + (k + 1) + "course" + prerequisiteId + "mustBeTakenBefore" + courseId);
                            }
                        }
                        model.addConstr(firstSemesterNoCoursesWithPrerequisites, GRB.EQUAL, 0, "noStudentCanTakeCourse" + course.getId() + "inFirstSemester");
                    }
                }
            }
            
            // Add constraint that required courses have at least one student
            for (CourseOffering course : scheduleInput.getRequiredOfferings()){
            	GRBLinExpr expr = new GRBLinExpr();
            	int courseIndex = course.getId().intValue() - 1;
            	for (int i = 0; i < numberOfStudents; i++){
            		expr.addTerm(1, modelVariables[i][courseIndex][convertSemesterToIndex(scheduleInput.getSemesterToSchedule(), course.getSemester())]);
            	}
            	model.addConstr( expr, GRB.GREATER_EQUAL, minEnrollmentToBeOffered, "requiredCourseOffering" + (course.getId().intValue()));
            }

            // Add constraint that students must complete a specialization
            for (StudentDTO student : scheduleInput.getStudents()){
            	int studentIndex = student.getId().intValue() - 1;
            	Specialization specialization = student.getChosenSpecialization();
            	if (specialization != null){
	            	// Add constraint that the student must take the required courses
            		Set<Course> requiredCourses = specialization.getRequiredCourses();
            		if (requiredCourses != null){
            			for (Course requiredCourse : requiredCourses){
		            		int courseIndex = requiredCourse.getId().intValue() - 1;
	            			GRBLinExpr expr = new GRBLinExpr();
		            		for (int k = 0; k < numberOfSemesters; k++){
		            			expr.addTerm( 1, modelVariables[studentIndex][courseIndex][k]);
		            		}
		            		model.addConstr( expr, GRB.EQUAL, 1, "requiredSpecializationCourse" + (requiredCourse.getId().intValue()) + "forStudent" + student.getId().intValue() );
            			}
            		}
            		
            		// Add constraint for core courses
            		Set<Course> coreCourses = specialization.getCoreCourses();
            		if (coreCourses != null && specialization.getNumberOfCoreCoursesRequired() > 0){
            			GRBLinExpr expr = new GRBLinExpr();
            			for (Course coreCourse : coreCourses){
            				int courseIndex = coreCourse.getId().intValue();
            				for (int k = 0; k < numberOfSemesters; k++){
            					expr.addTerm(1, modelVariables[studentIndex][courseIndex][k]);
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
            				int courseIndex = electiveCourse.getId().intValue();
            				for (int k = 0; k < numberOfSemesters; k++){
            					expr.addTerm(1, modelVariables[studentIndex][courseIndex][k]);
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
            		for (int k = 0; k < numberOfSemesters; k++){
            			expr.addTerm( 1, modelVariables[i][j][k] );
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
            			expr.addTerm( -1, modelVariables[i][j][k] );
            		}
            		model.addConstr( expr, GRB.LESS_EQUAL, maxCourseCapacity - 1, "course_" + (j+1) + "_NeedsOneProfessorIfOffered" );
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
            			expr.addTerm( -1, modelVariables[i][j][k] );
            		}
            		model.addConstr( expr, GRB.LESS_EQUAL, maxCourseCapacity - 1, "course_" + (j+1) + "_NeedsOneTAIfOffered" );
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
	            		for (Course course : scheduleInput.getCoursesThatCanBeOffered()){
	            			// The constraint will be that all non-competency course are not assigned:
	            			if (! professors[i].getCompetencies().contains( course )){
	            				expr.addTerm( 1, professorVariables[i][course.getId().intValue() - 1][k] );
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
	            		for (Course course : scheduleInput.getCoursesThatCanBeOffered()){
	            			// The constraint will be that all non-competency course are not assigned:
	            			if (! teachingAssistants[i].getCompetencies().contains( course )){
	            				expr.addTerm( 1, taVariables[i][course.getId().intValue() - 1][k] );
	            			}
	            		}
	            		model.addConstr( expr, GRB.EQUAL, 0, "ta" + (i + 1) + "_CompetenciesForSemester" + (k+1) );		
	            	}
        		}
            }
            
            // Add constraint that each professor only assigned to courses in available times
            for (int i = 0; i < numberOfProfessors; i++){
            	if (professors[i].getAvailabiity() != null){
            		GRBLinExpr expr = new GRBLinExpr();
            		for (int j = 0; j < numberOfCourses; j++){
            			for (int k = 0; k < numberOfSemesters; k++){
	            			boolean available = false;
            				for (Semester semester : professors[i].getAvailabiity()){
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
            	if (teachingAssistants[i].getAvailabiity() != null){
            		GRBLinExpr expr = new GRBLinExpr();
            		for (int j = 0; j < numberOfCourses; j++){
            			for (int k = 0; k < numberOfSemesters; k++){
	            			boolean available = false;
            				for (Semester semester : teachingAssistants[i].getAvailabiity()){
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

            /*
            System.out.println("The results for X (objective) is " + model.get(GRB.DoubleAttr.ObjVal));
            String modelFile = writeModelFile(model);
            OptimizationResult result = formulateResult(university, modelVariables, x);
            result.setModelFile(modelFile);
            result.setObjectiveValue(model.get(GRB.DoubleAttr.ObjVal));
            */

            model.dispose();
            env.dispose();

            //return result;
            return new ScheduleSolution();

        } catch (GRBException e) {
            System.out.println("Error code: " + e.getErrorCode() + ". " + e.getMessage());
            return null;
        }
    }


    /*
    private OptimizationResult formulateResult(University university, GRBVar[][][] modelVariables, GRBVar x) throws GRBException {
        OptimizationResult result = new OptimizationResult();
        Map<Integer, Map<Integer, List<Integer>>> studentSchedules = new HashMap<Integer, Map<Integer, List<Integer>>>();
        for (Student student : university.getStudents()) {
            studentSchedules.put(student.getId(), new HashMap<Integer, List<Integer>>());
            for (Semester semester : university.getSemesters()) {
                studentSchedules.get(student.getId()).put(semester.getId(), new ArrayList<Integer>());
                for (Course course : university.getCourses()) {
                    GRBVar variable = modelVariables[student.getId() - 1][course.getId() - 1][semester.getId() - 1];
                    double value = variable.get(GRB.DoubleAttr.X);
                    if (value == 1.0d) {
                        studentSchedules.get(student.getId()).get(semester.getId()).add(course.getId());
                    }
                }
            }
        }
        result.setStudentSchedules(studentSchedules);
        return result;
    }


    private String writeModelFile(GRBModel model) throws GRBException {
        Date now = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss");
        String dateString = dateformat.format(now);
        String fileName = "course-optimization-model-" + dateString + ".lp";
        model.write(fileName);
        return fileName;
    }
    */

    private String getVariableName(int i, int j, int k) {
        return "y" + (i + 1) + "_" + (j + 1) + "_" + (k + 1);
    }
    
    private int convertSemesterToIndex(Semester referenceSemester, Semester semesterToConvert){
    	int referenceYear = referenceSemester.getYear();
    	int referenceTerm = 0;
    	switch (referenceSemester.getTerm()){
    		case SPRING:
    			referenceTerm = 0;
    			break;
    		case SUMMER:
    			referenceTerm = 1;
    			break;
    		case FALL:
    			referenceTerm = 2;
    			break;
    	}
    	
    	int targetYear = semesterToConvert.getYear();
    	int targetTerm = 0;
    	switch (semesterToConvert.getTerm()){
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
    	// 2014, 2 ==> 2015,0 ... 1 * 3 + (0 - 2) = 1
    	// 2015, 0 ==> 2015,2 ... 0 * 3 + (2 - 0) = 2
    	// 2014, 2 ==> 2016,0 ... 2 * 3 + (0 - 2) = 4
    	// 2014, 0 ==> 2015,0 ... 1 * 3 + (0 - 0) = 3
    	return ((targetYear - referenceYear) * 3) + (targetTerm - referenceTerm);
    }
}
