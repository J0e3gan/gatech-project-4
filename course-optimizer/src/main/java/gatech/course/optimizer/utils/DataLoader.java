package gatech.course.optimizer.utils;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.Semester;
import gatech.course.optimizer.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 204069126 on 2/6/15.
 */
@Component
public class DataLoader {

    public static Logger logger = LoggerFactory.getLogger(DataLoader.class);


    public void loadStudents(InputStream in) {

        Scanner scanner;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        scanner = new Scanner(reader);
        List<Student> students = new ArrayList<Student>();
        List<Course> courses = new ArrayList<Course>();
        List<Semester> semesters = new ArrayList<Semester>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.startsWith("ANON_ID")){
                continue;
            }

            String[] parts = line.split(",");
            InfoLine infoLine = new InfoLine(parts);
            logger.info(infoLine.toString());
        }
        scanner.close();

    }


    private class InfoLine {

        //ANON_ID,Term,Course,Course Code,Course Number,Course Reference Number,Course Title,Grade
        public String studentId;
        public String year;
        public String semester;
        public String courseShortName;
        public String courseDeptartment;
        public String courseNumber;
        public String courseRefNumber;
        public String courseName;
        public String courseGrade;

        public InfoLine() {}

        public InfoLine(String[] fileLine) {
            this.studentId = fileLine[0]+fileLine[1]+fileLine[2];
            this.studentId = studentId.substring(1,studentId.length()-1);
            this.year = fileLine[3].substring(0,4);
            this.semester = getSemesterName(fileLine[3].substring(4,6));
            this.courseShortName = fileLine[4];
            this.courseDeptartment = fileLine[5];
            this.courseNumber = fileLine[6];
            this.courseRefNumber = fileLine[7];
            this.courseName = fileLine[8];
            this.courseGrade = (fileLine.length > 9) ? fileLine[9] : "";
        }


        private String getSemesterName(String semesterCode) {
            if("02".equals(semesterCode)){
                return "SPRING";
            }

            if("05".equals(semesterCode)){
                return "SUMMER";
            }
            if("08".equals(semesterCode)){
                return "FALL";
            }
            return "ERROR";
        }

        @Override
        public String toString() {
            return String.format("studentId='%s' year='%s' semester='%s' shortName='%s' CRN='%s' name='%s' grade='%s'",studentId,year,semester,courseShortName,courseRefNumber,courseName,courseGrade);
        }


    }





}
