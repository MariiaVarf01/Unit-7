package a;

public class Course {
    private String courseCode;
    private String courseName;
    private int maxCapacity;
    private static int totalEnrolledStudents = 0;  // Static variable to keep track of total enrolled students

    public Course(String courseCode, String courseName, int maxCapacity) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    // Method to check if the course has space for more students
    public boolean hasCapacity() {
        return totalEnrolledStudents < maxCapacity;
    }

    // Static method to get total enrolled students across all courses
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    // Method to increase total enrolled students (should be called when a student enrolls)
    public static void incrementEnrolledStudents() {
        totalEnrolledStudents++;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return courseCode.equals(course.courseCode);
    }

    public int hashCode() {
        return courseCode.hashCode();
    }
}
