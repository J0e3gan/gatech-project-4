/**
 * 
 */
package gatech.course.optimizer.engine;

import gatech.course.optimizer.dto.ScheduleInput;
import gatech.course.optimizer.dto.StudentDTO;
import gatech.course.optimizer.model.Course;
import gatech.course.optimizer.model.CourseOffering;
import gatech.course.optimizer.model.Faculty;
import gatech.course.optimizer.model.ScheduleSolution;
import gatech.course.optimizer.model.Semester;
import gatech.course.optimizer.model.Specialization;
import gatech.course.optimizer.model.Student;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

/**
 * @author John Raffensperger
 *
 */
public class GurobiEngineTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link gatech.course.optimizer.engine.GurobiEngine#createScheduleSolution(gatech.course.optimizer.dto.ScheduleInput)}.
	 */
	@SuppressWarnings("serial")
	@Test
	public void testCreateScheduleSolution() {
		
		// Semesters
		Semester startSemester = new Semester("2014", "FALL");
		
		// Courses
		final Course cs6290 = new Course();
		cs6290.setId( 1L );
		cs6290.setName( "CS6290" );
		
		final Course cs6300 = new Course();
		cs6300.setId( 2L);
		cs6300.setName( "CS6300" );
		
		final Course cs6310 = new Course();
		cs6310.setId( 3L );
		cs6310.setName( "CS6310" );
		cs6310.setPrerequisites( new HashSet<Course>(){ {this.add( cs6290 ); } } );
		
		final CourseOffering offering = new CourseOffering("101010", cs6300, startSemester);
		offering.setId( 1L );
		
		// Faculty
		final Faculty prof1 = new Faculty();
		prof1.setId( 1L );
		prof1.setLastName( "Professor 1" );
		
		final Faculty prof2 = new Faculty();
		prof2.setId( 2L );
		prof2.setLastName( "Professor 2" );
		prof2.setCompetencies( new HashSet<Course>(){ {this.add(cs6290); } });
		
		final Faculty ta1 = new Faculty();
		ta1.setId( 3L );
		ta1.setLastName( "TA 1" );
		
		final Faculty ta2 = new Faculty();
		ta2.setId( 4L );
		ta2.setLastName( "TA 2" );
		
		// Specialization
		final Specialization specialization1 = new Specialization();
		specialization1.setRequiredCourses( new HashSet<Course>(){ { this.add(cs6310); } } );
		specialization1.setCoreCourses( new HashSet<Course>(){ { this.add(cs6300); } } );
		specialization1.setElectiveCourses (new HashSet<Course>(){ { this.add( cs6290 ); } } );
		specialization1.setNumberOfCoreCoursesRequired( 1 );
		specialization1.setNumberOfElectiveCoursesRequired( 1 );
		
		// Students
		final StudentDTO student1 = new StudentDTO();
		student1.setId( 1L );
		student1.setLastName( "Student 1" );
		student1.setChosenSpecialization( specialization1 );
		
		final StudentDTO student2 = new StudentDTO();
		student2.setId( 2L );
		student2.setLastName( "Student 2" );
		
		ScheduleInput testInput = new ScheduleInput();
		testInput.setAllowedClassesPerSemester( 1 );
		testInput.setAvailableSpecializations( new HashSet<Specialization>() { { this.add(specialization1); } } );
		testInput.setCoursesThatCanBeOffered( new HashSet<Course>(){ { this.add(cs6290); this.add( cs6300 ); this.add( cs6310 ); } } );
		testInput.setProfessors( new HashSet<Faculty>() { { this.add(prof1); this.add( prof2 ); } } );
		testInput.setRequiredOfferings( new HashSet<CourseOffering>(){ { this.add(offering); } } );
		testInput.setSemesterToSchedule( startSemester );
		testInput.setStudents( new HashSet<StudentDTO>() { { this.add(student1); this.add(student2); } } );
		testInput.setTeacherAssistants( new HashSet<Faculty>() { { this.add(ta1); this.add( ta2 ); } } );
		testInput.setMaxCourseCapacity( 200 );
		
		GurobiEngine engine = new GurobiEngine();
		ScheduleSolution solution = engine.createScheduleSolution( testInput );
		
		for (CourseOffering scheduledOffering : solution.getSchedule()){
			System.out.print("Semester: " + scheduledOffering.getSemester().getTerm() + " " + scheduledOffering.getSemester().getYear() + 
					": Course " + scheduledOffering.getCourse().getName() + " taught by " + scheduledOffering.getProfessor().getLastName() + " with TA ");
			for (Faculty faculty : scheduledOffering.getTeacherAssistants()){
				System.out.println(faculty.getLastName() + " " );
			}
			System.out.println("\tStudents:");
			for (Student student : scheduledOffering.getEnrolledStudents()){
				System.out.println("\t\t" + student.getFirstName() + " " + student.getLastName());
			}
		}
		
		/* ============================== PASSING SCHEDULE: ================================
		    Semester: FALL 2014: Course CS6300 taught by Professor 1 with TA TA 2 
				Students:
					null Student 1
			Semester: SPRING 2015: Course CS6290 taught by Professor 2 with TA TA 2 
				Students:
					null Student 1
			Semester: FALL 2014: Course CS6290 taught by Professor 2 with TA TA 1 
				Students:
					null Student 2
			Semester: SUMMER 2015: Course CS6310 taught by Professor 1 with TA TA 2 
				Students:
					null Student 1
		 */
	}

}
