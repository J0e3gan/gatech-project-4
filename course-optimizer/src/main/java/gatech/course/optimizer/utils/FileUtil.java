package gatech.course.optimizer.utils;

import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.Semester;
import gatech.course.optimizer.model.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 204069126 on 2/6/15.
 */
@Deprecated
public class FileUtil {

    private final static String COURSES_FILENAME = "courses.txt";
    private final static String STUDENTS_FILENAME = "student_schedule.txt";
    private final static String ALL_SEMESTERS = "ALL";

    public static List<Student> loadStudents(String filePath) {

        try {
            Scanner scanner;
            if (filePath == null) {
                InputStream in = FileUtil.class.getResourceAsStream(File.separator + STUDENTS_FILENAME);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                scanner = new Scanner(reader);
            } else {
                File studentsFile = new File(filePath);
                scanner = new Scanner(studentsFile);
            }

            List<Student> students = new ArrayList<Student>();

            int studentIdCounter = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!ignoreLine(line)) {
                    String[] studentInfo = line.split("\\.");
                    List<Integer> desiredCourses = new ArrayList<Integer>();
                    //int[] desiredCourses = new int[studentInfo.length];
                    for (int i = 0; i < studentInfo.length; i++) {
                        if (studentInfo[i].trim() != null && studentInfo[i].trim().length() != 0) {
                            desiredCourses.add(Integer.valueOf(studentInfo[i].trim()));
                        }
                    }

                    Student student = new Student();
                    //student.setId(studentIdCounter);
                    //student.setName("Student" + studentIdCounter);
                    //student.setDesiredCourses(desiredCourses);
                    students.add(student);
                    studentIdCounter++;
                }
            }
            scanner.close();
            return students;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Course> loadCourses(List<Semester> semesters) {

        InputStream in = FileUtil.class.getResourceAsStream(File.separator + COURSES_FILENAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        Scanner scanner = new Scanner(reader);
        List<Course> courses = new ArrayList<Course>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!ignoreLine(line)) {
                String[] courseParts = line.split(";");
                Course course = new Course();
                //course.setId(Integer.valueOf(courseParts[0].trim()));
                course.setName(courseParts[1].trim());
                String offeredTerm = courseParts[2].trim();
                List<Semester.SemesterTerm> termsOffered = new ArrayList<Semester.SemesterTerm>();

                if (ALL_SEMESTERS.equals(offeredTerm)) {
                    termsOffered.add(Semester.SemesterTerm.FALL);
                    termsOffered.add(Semester.SemesterTerm.SPRING);
                    termsOffered.add(Semester.SemesterTerm.SUMMER);
                } else {
                    Semester.SemesterTerm term = Semester.SemesterTerm.valueOf(offeredTerm.trim());
                    termsOffered.add(term);
                }
                //course.setSemestersOffered(EntityUtil.getSemesterIds(semesters, termsOffered));

                if (courseParts.length > 3) {
                    String prerequisites = courseParts[3];
                    String[] prerequisitesArray = prerequisites.split(",");
                    List<Integer> prerequisiteIds = new ArrayList<Integer>();
                    //int[] prerequisiteIds = new int[prerequisitesArray.length];
                    for (int i = 0; i < prerequisitesArray.length; i++) {
                        Integer id = Integer.valueOf(prerequisitesArray[i].trim());
                        prerequisiteIds.add(id);
                    }
                    //course.setPrerequisites(prerequisiteIds);
                }
                courses.add(course);
            }
        }

        scanner.close();
        return courses;
    }

    private static boolean ignoreLine(String line) {
        if (line == null || line.length() == 0 || line.charAt(0) == '#' || line.charAt(0) == '%') {
            return true;
        }
        return false;
    }


}
