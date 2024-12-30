package a;

import java.util.ArrayList;
import java.util.List;

public class CourseManagement {
    private static List<Course> courses = new ArrayList<>();  // List to store all courses
    private static List<Student> students = new ArrayList<>();  // List to store all students

    // Method to add a new course
    public static void addCourse(Course course) {
        courses.add(course);
    }

    // Method to get all courses
    public static List<Course> getCourses() {
        return courses;
    }

    // Method to get all students
    public static List<Student> getStudents() {
        return students;
    }
    
    public static String getName (Student student) {
    	return student.getName();
    }

    public static ArrayList<String> getStudentsList() {
    	ArrayList<String> studentsList = new ArrayList<String>();
        for (Student student : students) {
        	studentsList.add("ID: " + student.getId() + ", Name: " + student.getName());
        }
        return studentsList;
    }
    
    // Method to assign a grade to a student for a course
    public static Boolean assignGradeToStudent(Student student, Course course, int grade) {
        if (grade >= 0) {
            student.assignGrade(course, grade);
            return true;
        } else {
        	return false;
        }
    }
    
    public static ArrayList<String> getEnrolledCourses (Student student) {
    	ArrayList<String> coursesList = new ArrayList<String>();
        for (Course course : student.getEnrolledCourses()) {
        	coursesList.add(course.getCourseCode());
        }
        return coursesList;
    }
    
    public static Student findStudentById(String studentId) {
        for (Student student : CourseManagement.getStudents()) {
            if (student.getId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }
    
    public static boolean courseExists(String code) {
        for (Course course : getCourses()) { // Assuming `courses` is a list of existing courses
            if (course.getCourseCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }
    
    public static Course findCourseByName(String courseName) {
        for (Course course : CourseManagement.getCourses()) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }

    public static Course findCourseByCode(String courseCode) {
        for (Course course : CourseManagement.getCourses()) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }
    
    // Method to list all courses
    public static ArrayList<String> listCourses() {
    	ArrayList<String> coursesList = new ArrayList<String>();
        for (Course course : courses) {
        	coursesList.add(course.getCourseCode());
        }
        return coursesList;
    }
    // Method to enroll a student in a course
    public static String enrollStudent(Student student, Course course) {
        // Ensure course and student objects are valid
        if (student == null || course == null) {
            return "Invalid student or course selection.";
        }

        // Check if the course has enough capacity
        if (course.hasCapacity()) {
            // Check if the student is already enrolled in the course
            if (student.getEnrolledCourses().contains(course)) {
                return "Student is already enrolled in the course: " + course.getCourseName();
            }

            // Enroll the student in the course
            student.enrollCourse(course);
            course.incrementEnrolledStudents();  // Increment the course's enrolled count

            return "Student " + student.getName() + " successfully enrolled in " + course.getCourseName() + ".";
        } else {
            return "The course " + course.getCourseName() + " is full.";
        }
    }
    
    public static void updateStudent (String id, String newName) {
    	Student student = findStudentById(id);
    	student.setName(newName);
    }

    public static double calculateOverallGrade(Student student) {
        List<Integer> grades = student.getGrades();
        int totalGrades = 0;
        int gradeCount = 0;

        for (Integer grade : grades) {
            if (grade != null) { // Ignore null grades
                totalGrades += grade;
                gradeCount++;
            }
        }

        if (gradeCount == 0) {
            System.out.println("No grades assigned for student: " + student.getName());
            return 0.0; // Return 0.0 if no grades are assigned
        }

        return (double) totalGrades / gradeCount; // Calculate and return the average
    }

}
