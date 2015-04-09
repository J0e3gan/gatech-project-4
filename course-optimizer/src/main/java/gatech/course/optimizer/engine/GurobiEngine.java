package gatech.course.optimizer.engine;

import gatech.course.optimizer.dto.ScheduleInput;
import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.ScheduleSolution;
import gurobi.*;


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
            int numberOfSemesters = 1;

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

                // Constraints for course offerings during certain semesters

                /*
                if (course.getSemestersOffered().size() != university.getSemesters().size()) {
                    List<Integer> semestersNotOffered = EntityUtil.semestersNotOffered(university.getSemesters(), course);
                    System.out.println(course.getId() + " is not offered during semesters :" + semestersNotOffered.toString());
                    for (int k = 0; k < semestersNotOffered.size(); k++) {
                        GRBLinExpr courseNotOffered = new GRBLinExpr();
                        int semesterNotOfferedId = semestersNotOffered.get(k).intValue();
                        for (int i = 0; i < numberOfStudents; i++) {
                            courseNotOffered.addTerm(1, modelVariables[i][course.getId() - 1][semesterNotOfferedId - 1]);
                        }
                        model.addConstr(courseNotOffered, GRB.EQUAL, 0, "course" + course.getId() + "notOfferedDuringSemester" + semestersNotOffered.get(k));
                    }
                }
                */
            }

            // Add constraint that students must take certain courses
            /*
            for (int i = 0; i < numberOfStudents; i++) {
                Student student = university.getStudents().get(i);
                //int[] courseIds = student.getDesiredCourses();
                for (int j = 0; j < student.getDesiredCourses().size(); j++) {
                    GRBLinExpr expr = new GRBLinExpr();
                    int desiredCourseId = student.getDesiredCourses().get(j); // Ids start from 1 indexed from 0
                    for (int k = 0; k < numberOfSemesters; k++) {
                        expr.addTerm(1, modelVariables[i][desiredCourseId - 1][k]);
                    }
                    model.addConstr(expr, GRB.EQUAL, 1, "student" + (i + 1) + "mustTakeCourse" + desiredCourseId);
                }
            }
            */

            // Add objective
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
}
