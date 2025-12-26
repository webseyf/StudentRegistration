package model;

/**
 * Enrollment represents the relationship between a Student and a Course.
 * It includes the enrollment status.
 */
public class Enrollment {

    private Course course;
    private EnrollmentStatus status;

    // Constructor
    public Enrollment(Course course, EnrollmentStatus status) {
        this.course = course;
        this.status = status;
    }

    // --- Getters & Setters ---

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public EnrollmentStatus getStatus() {
        return status;
    }

    public void setStatus(EnrollmentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return course.getCourseName() + " (" + status + ")";
    }
}
