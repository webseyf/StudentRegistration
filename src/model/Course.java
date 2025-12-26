package model;

/**
 * Course class represents a course in the system.
 * Each course can have an assigned instructor.
 */
public class Course {

    private String id;
    private String courseName;
    private Instructor assignedInstructor;

    // Constructor without instructor
    public Course(String id, String courseName) {
        this.id = id;
        this.courseName = courseName;
        this.assignedInstructor = null; // can be assigned later
    }

    // Constructor with instructor
    public Course(String id, String courseName, Instructor instructor) {
        this.id = id;
        this.courseName = courseName;
        this.assignedInstructor = instructor;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Instructor getAssignedInstructor() {
        return assignedInstructor;
    }

    public void setAssignedInstructor(Instructor assignedInstructor) {
        this.assignedInstructor = assignedInstructor;
    }

    // ToString method for display
    @Override
    public String toString() {
        String instructorInfo = (assignedInstructor != null) ? assignedInstructor.getName() : "No instructor assigned";
        return "Course ID: " + id + ", Name: " + courseName + ", Instructor: " + instructorInfo;
    }
}
