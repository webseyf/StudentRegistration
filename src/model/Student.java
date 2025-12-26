package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Student class represents a student in the system.
 * Now supports assigned courses, batch, and enrollment status.
 */
public class Student {

    private String id;
    private String name;
    private String department;
    private String batch; // new field for batch
    private List<Course> courses;

    // Constructor
    public Student(String id, String name, String department, String batch) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.batch = batch;
        this.courses = new ArrayList<>();
    }

    // --- Getters and Setters ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }

    public List<Course> getCourses() { return courses; }

    // --- Course Helper Methods ---
    public boolean addCourse(Course course) {
        if (course == null) return false;
        if (!courses.contains(course)) {
            courses.add(course);
            return true;
        }
        return false;
    }

    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }

    public String listCourseNames() {
        if (courses.isEmpty()) return "No courses assigned";
        StringBuilder sb = new StringBuilder();
        for (Course c : courses) {
            sb.append(c.getCourseName()).append(" (").append(c.getId()).append(")\n");
        }
        return sb.toString();
    }

    // Enrollment status
    public String getEnrollmentStatus() {
        return courses.isEmpty() ? "Not Enrolled" : "Enrolled";
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name + ", Department: " + department + ", Batch: " + batch;
    }
}
