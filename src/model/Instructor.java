package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instructor class represents a course instructor.
 * Now supports tracking assigned courses.
 */
public class Instructor {

    private String id;
    private String name;
    private String department;
    private List<Course> assignedCourses; // New: courses taught

    // Constructor
    public Instructor(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.assignedCourses = new ArrayList<>();
    }

    // --- Getters and Setters ---
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    // --- Course Helper Methods ---
    public boolean assignCourse(Course course) {
        if(course == null) return false;
        if(!assignedCourses.contains(course)) {
            assignedCourses.add(course);
            course.setInstructor(this); // Sync instructor with course
            return true;
        }
        return false; // already assigned
    }

    public boolean removeCourse(Course course) {
        if(course != null) {
            assignedCourses.remove(course);
            if(course.getInstructor() == this) course.setInstructor(null);
            return true;
        }
        return false;
    }

    // --- ToString ---
    @Override
    public String toString() {
        return "Instructor ID: " + id + ", Name: " + name + ", Department: " + department;
    }

    // Optional: List all course names taught by instructor
    public String listCourseNames() {
        if(assignedCourses.isEmpty()) return "No courses assigned";
        StringBuilder sb = new StringBuilder();
        for(Course c : assignedCourses) {
            sb.append(c.getCourseName()).append(" (").append(c.getId()).append(")\n");
        }
        return sb.toString();
    }
}
