package gatech.course.optimizer.utils;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Semester;
import gatech.course.optimizer.model.Student;
import gatech.course.optimizer.repo.CourseOfferingRepo;
import gatech.course.optimizer.repo.CourseRepo;
import gatech.course.optimizer.repo.SemesterRepo;
import gatech.course.optimizer.repo.StudentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by 204069126 on 2/6/15.
 */
@Component
public class DataLoader {

    public static Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private SemesterRepo semesterRepo;

    @Autowired
    private CourseOfferingRepo courseOfferingRepo;


    public void loadData(InputStream in) {

        Scanner scanner;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        scanner = new Scanner(reader);

        Map<String, Student> studentsMap = new HashMap<String, Student>();
        Map<String, Course> courseMap = new HashMap<String, Course>();
        Map<String, Semester> semestersMap = new HashMap<String, Semester>();
        Map<String, CourseOffering> courseOfferingsMap = new HashMap<String, CourseOffering>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Skip first line
            if (line.startsWith("ANON_ID")) {
                continue;
            }

            // Create object from the line
            String[] parts = line.split(",");
            InfoLine infoLine = new InfoLine(parts);

            // Extract distinct semesters
            String semesterKey = infoLine.year + "-" + infoLine.semester;
            if (!semestersMap.containsKey(semesterKey)) {
                Semester semester = semesterRepo.save(new Semester(infoLine.year, infoLine.semester));
                semestersMap.put(semesterKey, semester);
            }

            //Extract distinct courses
            if (!courseMap.containsKey(infoLine.courseNumber)) {
                Course course = courseRepo.save(new Course(infoLine.courseShortName, infoLine.courseName));
                courseMap.put(infoLine.courseNumber, course);
            }

            // Create students
            if (!studentsMap.containsKey(infoLine.studentId)) {
                Student student = studentRepo.save(new Student(infoLine.studentId, "password", "Bruce", "Wayne", infoLine.studentId));
                studentsMap.put(infoLine.studentId, student);
            }
            // TODO: should taken course be CourseInstance - that would cause a problem because CourseInstance has students inside of itself
            // so student has courseInstance that has student, I ll just leave it for now we can search CourseInstances by Student
            // so maybe remove this takenCourse attribute
            //studentsMap.get(infoLine.studentId).addTakenCourse(courseMap.get(infoLine.courseNumber));

            //Extract distinct course instances
            if (!courseOfferingsMap.containsKey(infoLine.courseRefNumber)) {

            } else {


            }


            logger.info(infoLine.toString());
        }
        scanner.close();
    }


    private class InfoLine {

        public String studentId;
        public String year;
        public String semester;
        public String courseShortName;
        public String courseDeptartment;
        public String courseNumber;
        public String courseRefNumber;
        public String courseName;
        public String courseGrade;

        public InfoLine() {
        }

        public InfoLine(String[] fileLine) {
            this.studentId = fileLine[0] + fileLine[1] + fileLine[2];
            this.studentId = studentId.substring(1, studentId.length() - 1);
            this.year = fileLine[3].substring(0, 4);
            this.semester = getSemesterName(fileLine[3].substring(4, 6));
            this.courseShortName = fileLine[4];
            this.courseDeptartment = fileLine[5];
            this.courseNumber = fileLine[6];
            this.courseRefNumber = fileLine[7];
            this.courseName = fileLine[8];
            this.courseGrade = (fileLine.length > 9) ? fileLine[9] : "";
        }


        private String getSemesterName(String semesterCode) {
            if ("02".equals(semesterCode)) {
                return "SPRING";
            }

            if ("05".equals(semesterCode)) {
                return "SUMMER";
            }
            if ("08".equals(semesterCode)) {
                return "FALL";
            }
            return "ERROR";
        }

        @Override
        public String toString() {
            return String.format("studentId='%s' year='%s' semester='%s' shortName='%s' CRN='%s' name='%s' grade='%s'", studentId, year, semester, courseShortName, courseRefNumber, courseName, courseGrade);
        }
    }
}
