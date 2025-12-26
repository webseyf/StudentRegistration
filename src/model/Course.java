package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Course class represents a course in the system.
 * Supports assigned instructor and enrolled students.
 */
public class Course {

    private String id;
    private String courseName;
    private Instructor instructor;   // New: course instructor
    private List<Student> enrolledStudents; // New: students enrolled

    // Constructor
    public Course(String id, String courseName, Instructor instructor) {
        this.id = id;
        this.courseName = courseName;
        this.instructor = instructor;
        this.enrolledStudents = new ArrayList<>();
        if (instructor != null) {
            instructor.assignCourse(this); // Sync with instructor
        }
    }

    // --- Getters and Setters ---
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

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
        if(instructor != null && !instructor.getAssignedCourses().contains(this)) {
            instructor.assignCourse(this);
        }
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    // --- Enrollment helper methods ---
    public boolean enrollStudent(Student student) {
        if(student == null) return false;
        if(!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
            student.addCourse(this); // Sync with student
            return true;
        }
        return false;
    }

    public boolean removeStudent(Student student) {
        if(student != null) {
            enrolledStudents.remove(student);
            student.removeCourse(this);
            return true;
        }
        return false;
    }

    // Optional: list names of enrolled students
    public String listStudentNames() {
        if(enrolledStudents.isEmpty()) return "No students enrolled";
        StringBuilder sb = new StringBuilder();
        for(Student s : enrolledStudents) {
            sb.append(s.getName()).append(" (").append(s.getId()).append(")\n");
        }
        return sb.toString();
    }

    // --- ToString ---
    @Override
    public String toString() {
        return "Course ID: " + id + ", Name: " + courseName + 
               ", Instructor: " + (instructor != null ? instructor.getName() : "None");
    }
}
