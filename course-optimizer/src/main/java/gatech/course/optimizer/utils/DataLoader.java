package gatech.course.optimizer.utils;

import gatech.course.optimizer.model.*;
import gatech.course.optimizer.repo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by 204069126 on 2/6/15.
 */
@Component
public class DataLoader {

    public static Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private ProfessorRepo professorRepo;

    @Autowired
    private TARepo taRepo;

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private SemesterRepo semesterRepo;

    @Autowired
    private CourseOfferingRepo courseOfferingRepo;

    @Autowired
    private StudentRecordRepo studentRecordRepo;

    @Autowired
    private DesiredCourseRepo desiredCourseRepo;

    @Autowired
    private ExecutionTrigger executionTrigger;

    private final int POINTS_FOR_A = 10;
    private final int POINTS_FOR_B = 7;
    private final int POINTS_FOR_C = 5;

    static Map<Integer, String> firstNames = new HashMap<Integer, String>();

    static {
        firstNames.put(0, "Bruce");
        firstNames.put(1, "Kelly");
        firstNames.put(2, "John");
        firstNames.put(3, "Michael");
        firstNames.put(4, "Kate");
        firstNames.put(5, "Arnold");
        firstNames.put(6, "Robert");
        firstNames.put(7, "Peter");
        firstNames.put(8, "Maggie");
        firstNames.put(9, "Pawel");
        firstNames.put(10, "Faith");

    }


    static Map<Integer, String> lastNames = new HashMap<Integer, String>();

    static {
        lastNames.put(0, "Downey");
        lastNames.put(1, "Roberts");
        lastNames.put(2, "Parker");
        lastNames.put(3, "Rambo");
        lastNames.put(4, "Smith");
        lastNames.put(5, "Kowalski");
        lastNames.put(6, "Ohearn");
        lastNames.put(7, "Patel");
        lastNames.put(8, "Freeman");
        lastNames.put(9, "Springsteam");
        lastNames.put(10, "Caine");
    }


    public void loadData(InputStream studentFile, InputStream courseFile, InputStream professorFile, InputStream taFile) {

        Map<String, Student> studentMap = new HashMap<String, Student>();
        Map<String, Professor> professorMap = new HashMap<String, Professor>();
        Map<String, TA> taMap = new HashMap<String, TA>();
        Map<String, Course> courseMap = new HashMap<String, Course>();
        Map<String, Semester> semesterMap = new HashMap<String, Semester>();
        Map<String, CourseOffering> courseOfferingMap = new HashMap<String, CourseOffering>();

        BufferedReader reader;
        Scanner scanner;


        // Process course data.
        reader = new BufferedReader(new InputStreamReader(courseFile));
        scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            //Extract distinct courses
            String courseNumber = parts[0];
            String courseShortName = parts[1];
            String courseName = parts[2];
            //String creditHours = parts[3];
            String description = "";
            for (int i = 4; i < parts.length; i++) {
                description = description + parts[i];
            }
            if (!courseMap.containsKey(courseNumber)) {
                Course course = new Course(courseShortName, courseName);
                course.setDescription(description);
                Course savedCourse = courseRepo.save(course);
                courseMap.put(courseNumber, savedCourse);
            }
        }


        // Process professor data.
        reader = new BufferedReader(new InputStreamReader(professorFile));
        scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Skip the first line (with column headers).
            if (line.startsWith("First Name")) {
                continue;
            }

            String[] parts = line.split(",");
            String profFirstName = parts[0];
            String profLastName = parts[1];

            // Ensure no duplicates are loaded.
            String profKey = profFirstName + " " + profLastName;
            System.out.println("** DEBUG:  Loading competencies for " + profKey + "..."); // FORNOW
            if (!professorMap.containsKey(profKey)) {
                Professor prof = new Professor(profFirstName, profLastName);

                Set<Course> competencies = new HashSet<Course>();
                // Elements 3 through n are the course numbers (e.g. "CS 6310") for the professor's competencies.
                for (int i = 2; i < parts.length; i++) {
                    String courseNumber = parts[i].replace("\"", "");
                    System.out.println("** DEBUG:  courseNumber == \"" + courseNumber + "\""); // FORNOW
                    Course competency = courseRepo.getCourseByNumber(courseNumber);
                    if (competency != null && !competencies.contains(competency)) {
                        competencies.add(competency);
                    }
                }
                prof.setCompetencies(competencies);

                ///*
                // FORNOW
                System.out.print("** DEBUG:  Competencies to save for " + profKey + " are");
                Iterator<Course> competenciesIter = competencies.iterator();
                int i = 0;
                while (competenciesIter.hasNext()) {
                    if (i != 0) {
                        System.out.print(" and");
                    }
                    System.out.print(" " + competenciesIter.next().getNumber());
                    i++;
                }
                System.out.println(".");
                //*/

                Professor savedProf = professorRepo.save(prof);
                professorMap.put(profKey, savedProf);
            }
        }


        // Process TA data.
        reader = new BufferedReader(new InputStreamReader(taFile));
        scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Skip the first line (with column headers).
            if (line.startsWith("First Name")) {
                continue;
            }

            String[] parts = line.split(",");
            String taFirstName = parts[0];
            String taLastName = parts[1];

            // Ensure no duplicates are loaded.
            String taKey = taFirstName + " " + taLastName;
            if (!taMap.containsKey(taKey)) {
                TA ta = new TA(taFirstName, taLastName);
                TA savedTA = taRepo.save(ta);
                taMap.put(taKey, savedTA);
            }
        }


        // Process student data.
        reader = new BufferedReader(new InputStreamReader(studentFile));
        scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            // Skip first line
            if (line.startsWith("ANON_ID")) {
                continue;
            }

            // Create object from the line so its easier to create model objects down the line
            String[] parts = line.split(",");
            InfoLine infoLine = new InfoLine(parts);
            logger.info("Processing: " + infoLine.toString());
            // Extract distinct semesters
            String semesterKey = infoLine.year + "-" + infoLine.semester;
            if (!semesterMap.containsKey(semesterKey)) {
                Semester semester = semesterRepo.save(new Semester(infoLine.year, infoLine.semester));
                semesterMap.put(semesterKey, semester);
            }

            //Extract distinct courseFile
            if (!courseMap.containsKey(infoLine.courseNumber)) {
                Course course = courseRepo.save(new Course(infoLine.courseShortName, infoLine.courseName));
                courseMap.put(infoLine.courseNumber, course);
            }

            // Create students
            if (!studentMap.containsKey(infoLine.studentId)) {
                Student student = studentRepo.save(new Student(infoLine.studentId, "password", makeupFirstName(), makeupLastName(), infoLine.studentId));
                studentMap.put(infoLine.studentId, student);
            }


            //Extract distinct course instances
            if (!courseOfferingMap.containsKey(infoLine.courseRefNumber)) {
                CourseOffering courseOffering = courseOfferingRepo.save(new CourseOffering(infoLine.courseRefNumber,
                        courseMap.get(infoLine.courseNumber), semesterMap.get(semesterKey)));
                courseOfferingMap.put(infoLine.courseRefNumber, courseOffering);

            }

            courseOfferingMap.get(infoLine.courseRefNumber).enrollStudent(studentMap.get(infoLine.studentId));

            // Save student grades for different course offerings
            Long courseOfferingId = courseOfferingMap.get(infoLine.courseRefNumber).getId();
            Long studentId = studentMap.get(infoLine.studentId).getId();
            String grade = (infoLine.courseGrade.length() == 0) ? makeUpGrade() : infoLine.courseGrade;
            studentRecordRepo.save(new StudentRecord(courseOfferingId, studentId, grade));

        }

        // Update course offerings with enrolled students
        for (String crn : courseOfferingMap.keySet()) {
            courseOfferingRepo.save(courseOfferingMap.get(crn));
        }


        // Compute Student Seniority
        for (String studentId : studentMap.keySet()) {
            Student student = studentMap.get(studentId);
            List<CourseOffering> takenCourses = courseOfferingRepo.findCoursesByStudent(student);
            int studentSeniority = 0;
            for (CourseOffering courseOffering : takenCourses) {
                String grade = studentRecordRepo.getGradeForStudent(courseOffering.getId(), student.getId());
                if ("A".equalsIgnoreCase(grade)) {
                    studentSeniority = studentSeniority + POINTS_FOR_A;
                } else if ("B".equalsIgnoreCase(grade)) {
                    studentSeniority = studentSeniority + POINTS_FOR_B;
                } else {
                    studentSeniority = studentSeniority + POINTS_FOR_C;
                }
            }

            student.setSeniority(studentSeniority);

            // Select 2 random courses as desired courses
            List<DesiredCourse> desiredCourses = new ArrayList<DesiredCourse>();
            int desiredCount = 1;
            int randomSkip = getRandomNumber() / 2;
            int numOfSkips = 0;
            for (String courseKey : courseMap.keySet()) {

                // Introducing some randomness
                if (numOfSkips < randomSkip) {
                    numOfSkips++;
                    continue;
                }

                if (!courseAlreadyTaken(courseMap.get(courseKey), takenCourses) && desiredCount < 3) {
                    DesiredCourse desiredCourse = new DesiredCourse(student.getId(), courseMap.get(courseKey), desiredCount);
                    desiredCourse = desiredCourseRepo.save(desiredCourse);
                    desiredCourses.add(desiredCourse);
                    desiredCount++;
                }
            }
            student.setDesiredCourses(desiredCourses);
            studentRepo.save(student);
        }
        scanner.close();

        // Create future semesters
        //createFutureSemesters();

        // Create first solution before any user changes
        executionTrigger.createScheduleSolution();
    }

    private void createFutureSemesters() {
        semesterRepo.save(new Semester("2015", "SUMMER"));
        semesterRepo.save(new Semester("2015", "FALL"));

        semesterRepo.save(new Semester("2016", "SPRING"));
        semesterRepo.save(new Semester("2016", "SUMMER"));
        semesterRepo.save(new Semester("2016", "FALL"));

        semesterRepo.save(new Semester("2017", "SPRING"));
        semesterRepo.save(new Semester("2017", "SUMMER"));
        semesterRepo.save(new Semester("2017", "FALL"));

        semesterRepo.save(new Semester("2018", "SPRING"));
        semesterRepo.save(new Semester("2018", "SUMMER"));
        semesterRepo.save(new Semester("2018", "FALL"));

        semesterRepo.save(new Semester("2019", "SPRING"));
        semesterRepo.save(new Semester("2019", "SUMMER"));
        semesterRepo.save(new Semester("2019", "FALL"));

        semesterRepo.save(new Semester("2020", "SPRING"));
        semesterRepo.save(new Semester("2020", "SUMMER"));
        semesterRepo.save(new Semester("2020", "FALL"));

        semesterRepo.save(new Semester("2021", "SPRING"));
        semesterRepo.save(new Semester("2021", "SUMMER"));
        semesterRepo.save(new Semester("2021", "FALL"));

        semesterRepo.save(new Semester("2022", "SPRING"));
        semesterRepo.save(new Semester("2022", "SUMMER"));
        semesterRepo.save(new Semester("2022", "FALL"));
    }


    private boolean courseAlreadyTaken(Course course, List<CourseOffering> takenCourses) {
        for (CourseOffering courseOffering : takenCourses) {
            if (courseOffering.getCourse().getNumber().equals(course.getNumber())) {
                return true;
            }
        }
        return false;
    }

    private String makeUpGrade() {
        int randomNum = getRandomNumber();
        if (randomNum < 4) {
            return "A";
        }
        if (randomNum < 7) {
            return "B";
        }
        return "C";
    }

    private String makeupFirstName() {
        return firstNames.get(getRandomNumber());
    }

    private String makeupLastName() {
        return lastNames.get(getRandomNumber());
    }

    private Integer getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt((10 - 1) + 1) + 1;
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
            return String.format("studentId='%s' year='%s' semester='%s' shortName='%s' CRN='%s' name='%s' grade='%s'",
                    studentId, year, semester, courseShortName, courseRefNumber, courseName, courseGrade);
        }
    }
}
