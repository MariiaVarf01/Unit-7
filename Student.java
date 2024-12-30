package a;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String id;
    private List<Course> enrolledCourses;
    private List<Integer> grades;

    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.enrolledCourses = new ArrayList<>();
        this.grades = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
    	this.name = name;
    }
    
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    // Method to assign a grade to a student
    public void assignGrade(Course course, int grade) {
        int index = enrolledCourses.indexOf(course);
        if (index != -1) {
            grades.set(index, grade);
        }
    }

    // Method to print student info
    public void printStudentInfo() {
        System.out.println("Student Name: " + name);
        System.out.println("Student ID: " + id);
        System.out.println("Enrolled Courses:");
        for (int i = 0; i < enrolledCourses.size(); i++) {
            Course course = enrolledCourses.get(i);
            Integer grade = grades.get(i);
            System.out.println(course.getCourseCode() + " - " + course.getCourseName() + " | Grade: " + (grade == null ? "Not Assigned" : grade));
        }
    }
    // Method to enroll a student in a course
    public String enrollCourse(Course course) {
        if (!enrolledCourses.contains(course)) {  // Check if the course is not already enrolled
            enrolledCourses.add(course);
            grades.add(null); // Initially, no grade assigned
            return "Success";
        }
        return "Student is already enrolled in the course: " + course.getCourseName();
    }
}
